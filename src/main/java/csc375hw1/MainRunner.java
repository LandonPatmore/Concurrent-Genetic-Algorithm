package csc375hw1;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainRunner {

    public static void main(String[] args) throws InterruptedException {
        Classroom master = new Classroom(8, 4);
        master.calculateTotalFitness();
        System.out.println("Original Fitness: " + master.getTotalFitness());
        Classroom[] classrooms = new Classroom[10];
        Exchanger<List<int[]>> exchanger = new Exchanger<>();

        ExecutorService e = Executors.newCachedThreadPool();

//        for(int i = 0; i < classrooms.length; i++){
//            Classroom c = new Classroom(master);
//            c.setClassNumber(i);
//            classrooms[i] = c;
//        }

        new Thread(new Tester(new Classroom(master), exchanger)).start();
        new Thread(new Tester(new Classroom(master), exchanger)).start();
        new Thread(new Tester(new Classroom(master), exchanger)).start();
        new Thread(new Tester(new Classroom(master), exchanger)).start();

//        for (Classroom classroom : classrooms) {
//            new Thread(new Tester(classroom, exchanger)).start();
//        }
//
//        for (Classroom classroom : classrooms) {
//            new Thread(new Tester(classroom, exchanger)).start();
//        }

    }

}