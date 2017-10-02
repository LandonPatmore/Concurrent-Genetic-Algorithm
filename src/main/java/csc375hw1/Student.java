package csc375hw1;


import javafx.scene.paint.Color;

public class Student implements Comparable<Student> {

    private Color color;
    private float localFitness;
    private int x;
    private int y;

    /**
     *
     * @param color JavaFX Color object
     * @param x x position
     * @param y y position
     */
    public Student(Color color, int x, int y){
        this.color = color;
        this.x = x;
        this.y = y;
        localFitness = 0;
    }

    /**
     * Copy Constructor
     * @param s student to copy
     */
    public Student(Student s){
        this.color = s.color;
        this.x = s.x;
        this.y = s.y;
        this.localFitness = s.localFitness;
    }

    /**
     *
     * @return color object
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return student local fitness
     */
    public float getFitness() {
        return localFitness;
    }

    /**
     *
     * @param fitness sets the fitness of the student
     */
    public void setFitness(float fitness) {
        this.localFitness = fitness;
    }

    /**
     *
     * @return x position
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y position
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param x x position
     * @param y y position
     */
    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Student o) {
        return Float.compare(o.getFitness(), this.getFitness());
    }
}