package csc375hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneticAlgorithm implements Runnable{
    private int totalClassrooms;
    private Classroom master;
    private ArrayList<Classroom> classrooms;
    private Random random;
    private float totalFitness;
    private Exchanger<List<int[]>> exchanger;
    private int crossoverPoint;
    private float bestFitness;
    private Classroom bestClassroom;

    /**
     *
     * @param rows the amount of rows to pass to the classroom
     * @param cols the amount of cols to pass to the classroom
     * @param exchanger the exchanger object to be used to exchange swaps between classrooms
     */
    public GeneticAlgorithm(int rows, int cols, Exchanger<List<int[]>> exchanger){
        this.totalClassrooms = rows * cols;
        this.master = new Classroom(rows, cols);
        this.classrooms = new ArrayList<>();
        this.random = new Random();
        this.totalFitness = 0.0f;
        this.exchanger = exchanger;
        master.calculateTotalFitness();
        this.bestFitness = master.getTotalFitness();
        this.bestClassroom = master;

        generateClassrooms();
    }

    /**
     * Generates copies of the master classroom
     */
    public void generateClassrooms(){
        for(int i = 0; i < totalClassrooms; i++){
            classrooms.add(new Classroom(master, i));
            classrooms.get(i).calculateTotalFitness();
            totalFitness += classrooms.get(i).getTotalFitness();
        }
    }

    /**
     * Picks classrooms to be notified to then swap their swaps between each other
     */
    public synchronized void roulette(){
//        Collections.sort(classrooms);

        float slice = random.nextFloat() * totalFitness;
        float fitnessSoFar = 0.0f;

        for(Classroom c : classrooms){
            fitnessSoFar += c.getTotalFitness();

            if(fitnessSoFar >= slice){
                c.notify(true);
                return;
            }
        }
    }

    /**
     *
     * @param fitness classroom fitness
     * @param c classroom
     */
    public synchronized void updateBestFitness(float fitness, Classroom c){
        if(fitness < bestFitness){
            bestFitness = fitness;
            System.out.println("Best Fitness: " + bestFitness + " || Classroom: " + c.getClassNumber());
            bestClassroom = new Classroom(c, c.getClassNumber());
        }
    }

    /**
     *
     * @return the best classroom at the time
     */
    public synchronized Classroom getBestClassroom() {
        return bestClassroom;
    }

    @Override
    public void run() {
        ExecutorService e = Executors.newFixedThreadPool(totalClassrooms);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        for (;;) {
            crossoverPoint = random.nextInt(totalClassrooms);
            for (Classroom c : classrooms) {
                e.execute(() -> {
                    c.calculateTotalFitness();
                    c.generateSwaps();
                    try {
                        c.updateCrossedOverSwaps(exchanger.exchange(c.crossover(crossoverPoint)));
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    c.mutate();
                    c.swapStudents();
                    c.calculateTotalFitness();
                    updateBestFitness(c.getTotalFitness() ,c);
                });
            }
            roulette();
            roulette();
        }
    }
}