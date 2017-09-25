package csc375hw1;

import com.rits.cloning.Cloner;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {

    private final int studentPopulation;
    private boolean solutionFound;
    private int totalGenerations;
    private float totalFitness;

    private Classroom classroom;
    private Classroom tempClassroom;
    private final int classRoomRows;
    private final int classRoomCols;

    private Random random;
    private Cloner cloner;

    public GeneticAlgorithm(int rows, int cols){
        this.classRoomRows = rows;
        this.classRoomCols = cols;
        studentPopulation = rows * cols;
        classroom = new Classroom(rows, cols);
        random = new Random();
        solutionFound = false;
        totalGenerations = 0;
        totalFitness = 0.0f;
        cloner = new Cloner();
    }

    public void initialize(){
        for(int i = 0; i < studentPopulation; i++){
            classroom.addStudent(new Student(randomColor()));
        }
    }

    public void start() {
        int newPop = 0;

        while (!solutionFound) {

            for (int i = 0; i < classRoomRows; i++) {
                for (int j = 0; j < classRoomCols; j++) {
                    assignFitness(classroom.getStudents()[i][j], classroom);
                    totalFitness += classroom.getStudents()[i][j].getFitness();
                }
            }
            tempClassroom = cloner.deepClone(classroom);

            while (newPop < studentPopulation) {
                int[] offSpring1 = roulette();
                int[] offSpring2 = roulette();

                int[] positions = new int[4];
                positions[0] = offSpring1[0];
                positions[1] = offSpring1[1];
                positions[2] = offSpring2[0];
                positions[3] = offSpring1[1];

                crossover(positions);

                mutate(offSpring1);
                mutate(offSpring2);

                newPop++;
            }


            float tempFitness = 0.0f;

            for (int i = 0; i < classRoomRows; i++) {
                for (int j = 0; j < classRoomCols; j++) {
                    assignFitness(classroom.getStudents()[i][j], tempClassroom);
                    tempFitness += classroom.getStudents()[i][j].getFitness();
                }
            }
            totalGenerations++;
            System.out.println("Generation: " + totalGenerations);
            System.out.println("Old Fitness: " + totalFitness);
            System.out.println("New Fitness: " + tempFitness);
            System.out.println("---------------------------------------------------------");

            if(tempFitness < totalFitness){
                System.out.println("temp is a better fit!");
                classroom = cloner.deepClone(tempClassroom);
                for(int i = 0; i < classRoomRows; i++){
                    for(int j = 0; j < classRoomCols; j++){
                        System.out.print(tempClassroom.getStudents()[i][j].getColor() + " ");
                    }
                    System.out.println();
                }
                System.out.println("--------------------------");
            }
            totalFitness = 0.0f;
            newPop = 0;

        }
    }

    public int randomColor(){
        int r  = random.nextInt(256);
        int g  = random.nextInt(256);
        int b  = random.nextInt(256);

        return r + g + b;
    }

    public void assignFitness(Student s, Classroom room){
        ArrayList<Student> a = room.getStudentNeighbors(s);
        float q = 0;

        for (Student anA : a) {
            q += Math.abs(s.getColor() - anA.getColor());
        }

        s.setFitness(q);
    }

    public int[] roulette(){
        float slice = random.nextFloat() * totalFitness;
        float fitnessSoFar = 0.0f;

        for(int i = 0; i < classRoomRows; i++){
            for(int j = 0; j < classRoomCols; j++){
                Student s = tempClassroom.getStudents()[i][j];
                fitnessSoFar += s.getFitness();

                if(fitnessSoFar > slice){
                    return new int[] {s.getX(), s.getY()};
                }
            }
        }
        return null;
    }

    public void crossover(int[] positions){
        float crossoverRate = 0.7f;
        if((random.nextInt(10) / 10f) < crossoverRate){
            Student temp = tempClassroom.getStudents()[positions[0]][positions[1]];

            tempClassroom.getStudents()[positions[0]][positions[1]] = tempClassroom.getStudents()[positions[2]][positions[3]];
            tempClassroom.getStudents()[positions[2]][positions[3]] = temp;

        }
    }

    public void mutate(int[] positions){
        float mutationRate = 0.001f;

        if((random.nextInt(10) / 1000f) < mutationRate){
            int[] allPositions = new int[4];
            allPositions[0] = positions[0];
            allPositions[1] = positions[1];
            int[] otherStudent = roulette();
            allPositions[2] = otherStudent[0];
            allPositions[3] = otherStudent[1];

            Student temp = tempClassroom.getStudents()[allPositions[0]][allPositions[1]];

            tempClassroom.getStudents()[allPositions[0]][allPositions[1]] = tempClassroom.getStudents()[allPositions[2]][allPositions[3]];
            tempClassroom.getStudents()[allPositions[2]][allPositions[3]] = temp;

        }
    }
}
