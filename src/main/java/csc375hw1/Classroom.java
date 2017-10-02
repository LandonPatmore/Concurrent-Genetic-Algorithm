package csc375hw1;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Classroom implements Comparable<Classroom>{
    private Student[][] students;
    private int rows;
    private int cols;
    private float totalFitness;
    private int studentPopulation;
    private int classNumber;

    private ArrayList<Student> listOfStudents;
    private ArrayList<int[]> swaps;
    private Random random;
    private boolean selected;

    /**
     *
     * @param rowLength the amount of rows
     * @param colLength the amount of cols
     */
    public Classroom(int rowLength, int colLength){
        students = new Student[rowLength][colLength];
        this.rows = rowLength;
        this.cols = colLength;
        random = new Random();
        listOfStudents = new ArrayList<>();
        studentPopulation = rowLength * colLength;
        swaps = new ArrayList<>();
        this.selected = false;

        generateStudents();
    }

    /**
     *
     * @return students array
     */
    public Student[][] getStudents() {
        return students;
    }

    /**
     *
     * @return amount of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     *
     * @return amount of cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Copy Constructor
     * @param c classroom to copy
     * @param num classroom number
     */
    public Classroom(Classroom c, int num){
        this.classNumber = num;
        this.rows = c.rows;
        this.cols = c.cols;
        listOfStudents = new ArrayList<>();
        this.students = copyStudents(c.students);
        this.studentPopulation = c.studentPopulation;
        random = new Random();
        swaps = new ArrayList<>();
        this.selected = c.selected;
    }

    /**
     *
     * @param crossoverPoint the point at which to split the arraylist into a sublist
     * @return the new sublist of the arraylist
     */
    public synchronized ArrayList<int[]> crossover(int crossoverPoint){
        return new ArrayList<>(swaps.subList(0, crossoverPoint));
    }

    /**
     *
     * @param newSwaps list of swaps from another classroom
     */
    public synchronized void updateCrossedOverSwaps(List<int []> newSwaps){
        if(newSwaps.size() != 0) {
            swaps.subList(0, newSwaps.size()).clear();
            swaps.addAll(0, newSwaps);
        }
    }

    /**
     *
     * @return the class number
     */
    public int getClassNumber() {
        return classNumber;
    }

    /**
     *
     * @param s students array in a classroom to be copied into a new classroom
     * @return the new students array to be copied into a new classroom
     */
    public Student[][] copyStudents(Student[][] s) {
        Student[][] newStudents = new Student[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                Student student = new Student(s[i][j]);
                newStudents[i][j] = student;
                listOfStudents.add(student);
            }
        }
        return newStudents;
    }

    /**
     *
     * @return int[] array that hols 4 numbers which, when split, corresponds to a student's position
     */
    public synchronized int[] pickRandomStudents(){
        int[] studentsPos = new int[4];

        do {
            int amount = 0;

            for (int k = 0; k < 2; k++) {
                float fitnessSoFar = 0.0f;
                float slice = random.nextFloat() * totalFitness;

                for (Student s : listOfStudents) {
                    fitnessSoFar += s.getFitness();

                    if (fitnessSoFar >= slice) {
                        studentsPos[amount++] = s.getX();
                        studentsPos[amount++] = s.getY();
                        break;
                    }
                }
            }
        } while (studentsPos[0] == studentsPos[2] && studentsPos[1] == studentsPos[3]);

        return studentsPos;
    }

    /**
     * Orders the students inside the list of students
     */
    public synchronized void orderStudents(){
        Collections.sort(listOfStudents);
    }

    /**
     * Generates a list of swaps using a roulette method select students
     */
    public void generateSwaps() {
        synchronized (this) {
            swaps.clear();
            orderStudents();
            ArrayList<int[]> newSwaps = new ArrayList<>();

            for (int i = 0; i < studentPopulation; i++) {
                newSwaps.add(pickRandomStudents());
            }

            swaps.addAll(newSwaps);

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Swaps students within the swaps list with each other
     */
    public synchronized void swapStudents(){
        for(int[] s : swaps){
            Student tempStudent = students[s[0]][s[1]];

            students[s[0]][s[1]] = students[s[2]][s[3]];
            students[s[2]][s[3]] = tempStudent;
        }

        setStudentPos();
    }

    /**
     * Mutates random index inside the swaps list if the random number is below the mutation rate
     */
    public synchronized void mutate(){
        final float mutation_rate = 0.1f;

        for(int i = 0; i < swaps.size(); i++){
            if(random.nextDouble() < mutation_rate){
                swaps.set(i, pickRandomStudents());
                return;
            }
        }
    }

    /**
     * Sets the list of students after they have been swapped so that the other methods are updated with the new students' positions and fitnesses
     */
    public void setListOfStudents(){
        listOfStudents.clear();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                listOfStudents.add(students[i][j]);
            }
        }
    }

    /**
     * Sets the student's position corresponding to it's position within the students array
     */
    public synchronized void setStudentPos(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                students[i][j].setPos(i,j);
            }
        }

        setListOfStudents();
    }

    /**
     * Calculates the total fitness of the classroom
     */
    public synchronized void calculateTotalFitness(){
        totalFitness = 0.0f;

        for(Student s : listOfStudents){
            totalFitness += assignLocalFitness(s);
        }
    }

    /**
     *
     * @param s student you want to find the fitness of
     * @return the local fitness of the student
     */
    private synchronized float assignLocalFitness(Student s){
        ArrayList<Student> neighbors = getStudentNeighbors(s);
        float fitness = 0.0f;

        for(Student n : neighbors){
            fitness += Math.abs(s.getColor().getRed() - n.getColor().getRed());
        }

        s.setFitness(fitness);

        return fitness;
    }

    /**
     * Generates random students to be inserted into the classroom
     */
    public void generateStudents(){

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(students[i][j] == null){
                    Color c = Color.rgb(random.nextInt(256), 0, 0);
                    Student s = new Student(c, i, j);
                    students[i][j] = s;
                    listOfStudents.add(s);
                }
            }
        }
    }

    /**
     *
     * @param s student to be assessed
     * @return list of neighboring students around the student
     */
    private synchronized ArrayList<Student> getStudentNeighbors(Student s){
        ArrayList<Student> neighbors = new ArrayList<>();
        int rowIndexes[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int colIndexes[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        for(int i = 0; i < rowIndexes.length; i++){
            try {
                neighbors.add(students[s.getX() + rowIndexes[i]][s.getY() + colIndexes[i]]);
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }
        return neighbors;
    }

    /**
     *
     * @return the total fitness of the classroom
     */
    public synchronized float getTotalFitness() {
        return totalFitness;
    }

    /**
     *
     * @param selected whether the classroom has been selected to be swapped with another classroom
     */
    public synchronized void notify(boolean selected) {
        if (selected) {
            notify();
        }
    }

    @Override
    public int compareTo(Classroom o) {
        return Float.compare(this.totalFitness, o.totalFitness);
    }
}