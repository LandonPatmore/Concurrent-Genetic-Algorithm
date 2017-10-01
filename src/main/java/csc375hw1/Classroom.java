package csc375hw1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Classroom {
    private Student[][] students;
    private int rows;
    private int cols;
    private float totalFitness;
    private int studentPopulation;
    private int classNumber;
    private boolean selected;

    private ArrayList<Student> listOfStudents;
    private ArrayList<int[]> swaps;
    private Random random;

    public Classroom(int rowLength, int colLength){
        students = new Student[rowLength][colLength];
        this.rows = rowLength;
        this.cols = colLength;
        random = new Random();
        listOfStudents = new ArrayList<>();
        studentPopulation = rowLength * colLength;
        swaps = new ArrayList<>();
        selected = false;

        generateStudents();
    }

    public Classroom(Classroom c, int num){
        this.classNumber = num;
        this.selected = c.selected;
        this.rows = c.rows;
        this.cols = c.cols;
        listOfStudents = new ArrayList<>();
        this.students = copyStudents(c.students);
        this.studentPopulation = c.studentPopulation;
        random = new Random();
        swaps = new ArrayList<>();
    }

    public synchronized void updateCrossedOverSwaps(List<int []> newSwaps){
        swaps.subList(0, newSwaps.size()).clear();
        swaps.addAll(0, newSwaps);
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public int getClassNumber() {
        return classNumber;
    }

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

    public synchronized void orderStudents(){
        Collections.sort(listOfStudents);
    }

    public synchronized void generateSwaps() {
        swaps.clear();
        orderStudents();
        ArrayList<int[]> newSwaps = new ArrayList<>();

        for(int i = 0; i < studentPopulation; i++){
            newSwaps.add(pickRandomStudents());
        }

        swaps.addAll(newSwaps);

        try {
            System.out.println("Classroom: " + classNumber + " waiting...");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Classroom: " + classNumber + " will be crossed");

    }

    public synchronized void swapStudents(){
        for(int[] s : swaps){
            Student tempStudent = students[s[0]][s[1]];

            students[s[0]][s[1]] = students[s[2]][s[3]];
            students[s[2]][s[3]] = tempStudent;
        }

        setStudentPos();
    }

    public void setListOfStudents(){
        listOfStudents.clear();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                    listOfStudents.add(students[i][j]);
            }
        }
    }

    public synchronized void setStudentPos(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                students[i][j].setPos(i,j);
            }
        }

        setListOfStudents();
    }

    public synchronized void setSwaps(ArrayList<int[]> swaps) {
        this.swaps = swaps;
    }

    public synchronized ArrayList<int[]> getSwaps() {
        return swaps;
    }

    public synchronized void calculateTotalFitness(){
        totalFitness = 0.0f;

        for(Student s : listOfStudents){
            totalFitness += assignLocalFitness(s);
        }
    }

    private synchronized float assignLocalFitness(Student s){
        ArrayList<Student> neighbors = getStudentNeighbors(s);
        float fitness = 0.0f;

        for(Student n : neighbors){
            fitness += Math.abs(s.getColor() - n.getColor());
        }
        s.setFitness(fitness);

        return fitness;
    }

    public void generateStudents(){
        int number = 0;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(students[i][j] == null){
                    Student s = new Student(random.nextInt(100), number, i, j);
                    students[i][j] = s;
                    listOfStudents.add(s);
                    number++;
                }
            }
        }
    }

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

    public synchronized void showClassroom(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                System.out.printf("%-10s", students[i][j].getFitness());
            }
            System.out.println();
        }
    }

    public synchronized void setTotalFitness(float totalFitness) {
        this.totalFitness = totalFitness;
    }

    public synchronized float getTotalFitness() {
        return totalFitness;
    }

    public boolean isSelected() {
        return selected;
    }

    public synchronized void setSelected(boolean selected) {
        this.selected = selected;
        notify();
    }
}
