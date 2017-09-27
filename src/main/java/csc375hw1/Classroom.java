package csc375hw1;

import java.util.ArrayList;
import java.util.Random;

public class Classroom {
    private Student[][] students;
    private int rows;
    private int cols;
    private float totalFitness;
    private int studentPopulation;
    private int number;

    private ArrayList<Student> listOfStudents;
    private ArrayList<int[]> swaps;
    private Random random;

    public Classroom(int rowLength, int colLength){
        students = new Student[rowLength][colLength];
        this.rows = rowLength;
        this.cols = colLength;
        totalFitness = 0.0f;
        random = new Random();
        listOfStudents = new ArrayList<>();
        swaps = new ArrayList<>();
        studentPopulation = rowLength * colLength;
        number = 0;

        generateStudents();
        assignTotalFitness();
    }

    public int[] pickRandomStudents(){
        int[] swap = new int[4];
        int amount = 0;

        for(int k = 0; k < 2; k++) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);

            swap[amount++] = x;
            swap[amount++] = y;

        }
        return swap;
    }

    public ArrayList<int[]> swaps() {

        for(int i = 0; i < studentPopulation; i++){
            swaps.add(pickRandomStudents());
        }

        return swaps;
    }

    public void setSwaps(ArrayList<int[]> swaps) {
        this.swaps = swaps;
    }

    public ArrayList<int[]> getSwaps() {
        return swaps;
    }

    public void assignTotalFitness(){
        for(Student s : listOfStudents){
            totalFitness += assignLocalFitness(s);
        }
    }

    private float assignLocalFitness(Student s){
        ArrayList<Student> neighbors = getStudentNeighbors(s);
        float fitness = 0.0f;

        for(Student n : neighbors){
            fitness += Math.abs(s.getColor() - n.getColor());
        }
        s.setFitness(fitness);

        return fitness;
    }

    public void generateStudents(){
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

    private ArrayList<Student> getStudentNeighbors(Student s){
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

    public void showClassroom(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                System.out.printf("%-30s", "Color: " + students[i][j].getColor() + " Fit: " + students[i][j].getFitness());
            }
            System.out.println();
        }
    }

    public float getTotalFitness() {
        return totalFitness;
    }
}
