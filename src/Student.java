public class Student {

    private final String id;
    private final int height;
    private int xPoint;
    private int yPoint;

    public Student(String id, int height, int x, int y){
        this.id = id;
        this.xPoint = x;
        this.yPoint = y;
        this.height = height;

    }

    public synchronized void move(int newX, int newY){
        this.xPoint = newX;
        this.yPoint = newY;
    }

    public synchronized String getId(){
        return id;
    }

    public synchronized int getX(){
        return xPoint;
    }

    public synchronized int getY(){
        return yPoint;
    }

    public synchronized int getHeight(){
        return height;
    }
}
