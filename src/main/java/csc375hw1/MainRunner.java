package csc375hw1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.*;

public class MainRunner {

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        GeneticAlgorithm g = new GeneticAlgorithm(4, 8);
//
//        int rows = g.getClassRoomRows();
//        int cols = g.getClassRoomCols();
//
//        Timer t = new Timer();
//
//        primaryStage.show();
//
//        Group group = new Group();
//
//        GridPane grid = new GridPane();
//
//        Counter c = new Counter();
//        DecimalFormat d = new DecimalFormat(".######");
//
//        group.getChildren().add(grid);
//        Scene s = new Scene(group, 1000, 800);
//
//        t.scheduleAtFixedRate(new TimerTask() { // Continuously runs until told to stop
//            @Override
//            public void run() {
//                Platform.runLater(() -> { // Used so that the GUI thread is not hung up
//                    g.move();
////                    c.increment();
////                    if(c.val() == 5) {
//
//                        Student[][] students = g.grabCurrentRoom();
//
//                        for (int i = 0; i < rows; i++) {
//                            for (int j = 0; j < cols; j++) {
//                                Paint p = students[i][j].getColor();
//                                Rectangle r = new Rectangle(100, 100, p);
//                                Text text = new Text();
//
//                                text.autosize();
//                                text.setFill(Color.WHITE);
//                                text.setBoundsType(TextBoundsType.VISUAL);
//                                StackPane stack = new StackPane();
//                                stack.getChildren().addAll(r, text);
//
//                                GridPane.setRowIndex(stack, i);
//                                GridPane.setColumnIndex(stack, j);
//                                grid.getChildren().addAll(stack);
//                            }
//                        }
//
//                        primaryStage.setTitle("Genetic Algorithm");
//                        primaryStage.setScene(s);
//                        c.set(0);
////                    }
//                });
//
//            }
//        }, 100, 100);
//    }

    public static void main(String[] args) {
        GeneticAlgorithm g = new GeneticAlgorithm(8, 4);

        Classroom classroom1 = new Classroom(8, 4);
        Classroom classroom2 = new Classroom(8, 4);

        classroom1.swaps();
        classroom2.swaps();

        for(int i = 0; i < classroom1.getSwaps().size(); i++){
            System.out.println(Arrays.toString(classroom1.getSwaps().get(i)));
        }

        g.crossover(classroom1, classroom2);

        System.out.println();
        System.out.println();

        for(int i = 0; i < classroom1.getSwaps().size(); i++){
            System.out.println(Arrays.toString(classroom1.getSwaps().get(i)));
        }

    }

}