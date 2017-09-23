import java.awt.*;

public class Student {

    private Color color;
    private int fitness;
    private int x;
    private int y;

    public Student(){
        fitness = 0;
    }

    public Student(Color color){
        this.color = color;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color.getRGB();
    }

    public int getfitness() {
        return fitness;
    }

    public void setfitness(int fitness) {
        this.fitness = fitness;
    }
}
