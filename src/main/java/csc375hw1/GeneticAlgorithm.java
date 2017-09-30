package csc375hw1;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm extends Thread{
    private float totalFitness;
    private ArrayList<Classroom> classrooms;
    private Random random;
    private int classroomPopulation;

    public GeneticAlgorithm(int rows, int cols){
        totalFitness = 0.0f;
        classrooms = new ArrayList<>();
        random = new Random();
        classroomPopulation = rows * cols;
    }

    public synchronized Classroom roulette(){
        float fitnessSoFar = 0.0f;

        float slice = random.nextFloat() * totalFitness;

        for(Classroom c : classrooms){
            fitnessSoFar += c.getTotalFitness();

            if(fitnessSoFar >= slice){
                return c;
            }
        }
        return null;
    }

    public synchronized void crossover(Classroom c1, Classroom c2){
        final float crossover_rate = 0.7f;

        if(random.nextDouble() < crossover_rate){
            int crossoverPoint = random.nextInt(classroomPopulation);
            ArrayList<int[]> cq1 = new ArrayList<>();
            cq1.addAll(c1.getSwaps().subList(0, crossoverPoint));
            cq1.addAll(c2.getSwaps().subList(crossoverPoint, c1.getSwaps().size()));

            ArrayList<int[]> cq2 = new ArrayList<>();
            cq2.addAll(c2.getSwaps().subList(0, crossoverPoint));
            cq2.addAll(c1.getSwaps().subList(crossoverPoint, c1.getSwaps().size()));

            c1.setSwaps(cq1);
            c2.setSwaps(cq2);
        }
    }

    public void mutate(Classroom c){
        final float mutation_rate = 0.001f;

        for(int i = 0; i < c.getSwaps().size(); i++){
            if(random.nextDouble() < mutation_rate){
                c.getSwaps().set(i, c.pickRandomStudents());
            }
        }
    }

}