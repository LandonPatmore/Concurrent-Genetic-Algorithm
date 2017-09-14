public class Student {

    private final String id;
    private final double loveDL;
    private int row;
    private int col;

    public Student(String id, double loveDL, int row, int col) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.loveDL = loveDL;

    }

    public synchronized void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized int getRow() {
        return row;
    }

    public synchronized int getCol() {
        return col;
    }

    public synchronized double getHeight() {
        return loveDL;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", loveDL=" + loveDL +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
