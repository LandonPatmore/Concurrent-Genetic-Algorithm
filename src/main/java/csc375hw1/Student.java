package csc375hw1;

public class Student {

    private int color;
    private float fitness;
    private int x;
    private int y;

    public Student(int color, int x, int y, float fitness){
        this.color = color;
        this.x = x;
        this.y = y;
        this.fitness = fitness;
    }

    public Student(int color){
        this.color = color;
        fitness = 0;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
