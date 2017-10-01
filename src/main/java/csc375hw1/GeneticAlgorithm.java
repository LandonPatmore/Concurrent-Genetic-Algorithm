//package csc375hw1;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class GeneticAlgorithm implements Runnable{
//    private float totalFitness;
//    private ArrayList<Classroom> classrooms;
//    private Random random;
//    private int classroomPopulation;
//    private int rows;
//    private int cols;
//
//    public GeneticAlgorithm(int rows, int cols){
//        this.rows = rows;
//        this.cols = cols;
//        totalFitness = 0.0f;
//        classrooms = new ArrayList<>();
//        random = new Random();
//        classroomPopulation = rows * cols;
//    }
//
//    public synchronized Classroom roulette(){
//        float fitnessSoFar = 0.0f;
//
//        float slice = random.nextFloat() * totalFitness;
//
//        for(Classroom c : classrooms){
//            fitnessSoFar += c.getTotalFitness();
//
//            if(fitnessSoFar >= slice){
//                return c;
//            }
//        }
//        return null;
//    }
//
////    public synchronized void crossover(Classroom c1, Classroom c2){
////        final float crossover_rate = 0.7f;
////
////        if(random.nextDouble() < crossover_rate){
////            int crossoverPoint = random.nextInt(classroomPopulation);
////            c1.updateCrossedOverSwaps(new ArrayList<>(c.getSwaps().subList(0, crossoverPoint));
////        }
////    }
//
//    public void mutate(Classroom c){
//        final float mutation_rate = 0.001f;
//
//        for(int i = 0; i < c.getSwaps().size(); i++){
//            if(random.nextDouble() < mutation_rate){
//                c.getSwaps().set(i, c.pickRandomStudents());
//            }
//        }
//    }
//
//    @Override
//    public void run() {
//        Classroom master = new Classroom(rows, cols);
//        for(int i = 0; i < classroomPopulation; i++) {
//            classrooms.add(new Classroom(master));
//        }
//
////        for(;;){
//            for(Classroom c : classrooms){
//                new Thread(() -> {
//                    c.calculateTotalFitness();
//                    c.generateSwaps();
//                }).start();
//            }
//
//            for(Classroom c : classrooms){
//                new Thread(() -> {
//                    c.swapStudents();
//                    c.calculateTotalFitness();
//                }).start();
//            }
//
//        }
////    }
//}