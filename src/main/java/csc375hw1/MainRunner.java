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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class MainRunner extends Application {

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Exchanger<List<int[]>> exchanger = new Exchanger<>();

        GeneticAlgorithm g = new GeneticAlgorithm(4, 8, exchanger);

        Timer t = new Timer();
        Group group = new Group();
        Scene s = new Scene(group, 400, 200);
        primaryStage.setTitle("Classroom");
        primaryStage.setScene(s);
        primaryStage.show();

        DecimalFormat d = new DecimalFormat(".##");

        GridPane grid = new GridPane();
        group.getChildren().add(grid);

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Classroom c = g.getBestClassroom();

                    for (int i = 0; i < c.getRows(); i++) {
                        for (int j = 0; j < c.getCols(); j++) {
                            Paint p = c.getStudents()[i][j].getColor();
                            Rectangle r = new Rectangle(50, 50, p);
                            Text text = new Text();
                            text.setText(String.valueOf(d.format(c.getStudents()[i][j].getColor().getRed())));
                            text.autosize();
                            text.setFill(Color.WHITE);
                            text.setBoundsType(TextBoundsType.VISUAL);
                            StackPane stack = new StackPane();
                            stack.getChildren().addAll(r, text);

                            GridPane.setRowIndex(stack, i);
                            GridPane.setColumnIndex(stack, j);
                            grid.getChildren().addAll(stack);
                        }
                    }
                });
            }
        }, 2000, 2000);

        new Thread(g).start();
    }
}