package csc375hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class Tester implements Runnable{

    private Classroom c;
    Exchanger<List<int[]>> exchanger;
    Random random = new Random();

    public Tester(Classroom c, Exchanger<List<int[]>> exchanger){
        this.exchanger = exchanger;
        this.c = c;
    }

    public synchronized List<int[]> crossover(Classroom c){
        int crossoverPoint = random.nextInt(32);
        return new ArrayList<>(c.getSwaps().subList(0, crossoverPoint));
    }

    public void mutate(Classroom c){
        final float mutation_rate = 0.001f;

        for(int i = 0; i < c.getSwaps().size(); i++){
            if(random.nextDouble() < mutation_rate){
                c.getSwaps().set(i, c.pickRandomStudents());
            }
        }
    }

    @Override
    public void run() {

        try {
            c.calculateTotalFitness();
            c.generateSwaps();
            c.updateCrossedOverSwaps(exchanger.exchange(crossover(c)));
            mutate(c);
            c.swapStudents();
            c.calculateTotalFitness();
            System.out.println("Fitness for classroom " + c.getClassNumber() + ": " + c.getTotalFitness());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
