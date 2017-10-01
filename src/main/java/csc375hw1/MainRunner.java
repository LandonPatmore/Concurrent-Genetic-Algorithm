package csc375hw1;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class MainRunner {

    public static void main(String[] args) throws InterruptedException {
        Classroom master = new Classroom(8, 4);
        master.calculateTotalFitness();
        System.out.println("Original Fitness: " + master.getTotalFitness());
        Classroom[] classrooms = new Classroom[10];
        ExecutorService e = Executors.newCachedThreadPool();
        Exchanger<List<int[]>> exchanger = new Exchanger<>();
        for(int i = 0; i < classrooms.length; i++){
            classrooms[i] = new Classroom(master, i);
            e.execute(new Tester(classrooms[i], exchanger));
        }

        for(;;){
            int random = new Random().nextInt(classrooms.length);

            e.execute(() -> classrooms[random].setSelected(true));
        }
//
//        e.execute(() -> classrooms[0].setSelected(true));
//        e.execute(() -> classrooms[1].setSelected(true));
//
//        e.execute(new Tester(c1, exchanger));
//        e.execute(() -> c1.setSelected(true));
//        e.execute(new Tester(c2, exchanger));
//        e.execute(() -> c2.setSelected(true));



    }

}