package csc375hw1;

public class Student implements Comparable<Student> {

    private int color;
    private float localFitness;
    private int x;
    private int y;
    private int number;

    public Student(int color, int number, int x, int y){
        this.color = color;
        this.number = number;
        this.x = x;
        this.y = y;
        localFitness = 0;
    }

    public Student(Student s){
        this.color = s.color;
        this.number = s.number;
        this.x = s.x;
        this.y = s.y;
        this.localFitness = s.localFitness;
    }

    public String getNumber() {
        return Integer.toString(number);
    }

    public int getColor() {
        return color;
    }

    public double getFitness() {
        return localFitness;
    }

    public void setFitness(float fitness) {
        this.localFitness = fitness;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(o.getFitness(), this.getFitness());
    }
}
