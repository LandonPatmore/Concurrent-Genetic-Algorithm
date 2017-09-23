import java.awt.*;
import java.util.Random;

public class GeneticAlgorithm {

    private final float crossoverRate = 0.7f;
    private final float mutationRate = 0.001f;
    private final int studentPopulation = 32;
    private boolean solutionFound;
    private int totalGenerations;

    private Classroom classroom;
    private final int row;
    private final int col;

    private Random random;


    public GeneticAlgorithm(int row, int col){
        this.row = row;
        this.col = col;
        classroom = new Classroom(row, col);
        random = new Random();
        solutionFound = false;
        totalGenerations = 0;
    }

    public void initialize(){
        int totalStudents = row * col;

        for(int i = 0; i < totalStudents; i++){
            classroom.addStudent(new Student(randomColor()));
            System.out.println("student added: " + i);
        }
    }

    public Color randomColor(){
        int r  = random.nextInt(256);
        int g  = random.nextInt(256);
        int b  = random.nextInt(256);

        return new Color(r,g,b);
    }

    public float assignFitness(int color, Student[] neighbors){
        return 0;
    }

    public void neighbors(){
        classroom.getStudentNeighbors(1,1);
    }
}
