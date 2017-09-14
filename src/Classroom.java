import java.util.Random;

public class Classroom {
    private final Student[][] seats;
    private final int rows;
    private final int cols;

    public Classroom(int rows, int cols){
        this.seats = new Student[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.insertStudent();
    }

    public synchronized void viewClassroom(){
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                if(seats[i][j] == null){
                    System.out.println("Seat empty");
                } else {
                    System.out.println(seats[i][j].toString());
                }
            }
        }
    }

    public synchronized void insertStudent(){
        int counter = 0;
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Student(Integer.toString(counter), studentProgress(), i, j);
                counter++;
            }
        }
    }

    private synchronized double studentProgress(){
        double testScore = 0.0;

        for(int i = 0; i < 3; i++){
            double test = new Random().nextDouble() * 50;
            double correctTestScore = test + 50;
            testScore += correctTestScore;
        }

        return testScore / 3;
    }
}
