package csc375hw1;

import java.util.ArrayList;

public class Classroom {
    private Student[][] students;
    private int rowsLength;
    private int colsLength;


    public Classroom(int rowLength, int colLength){
        students = new Student[rowLength][colLength];
        this.rowsLength = rowLength;
        this.colsLength = colLength;
    }

    public Student[][] getStudents() {
        return students;
    }

    public void addStudent(Student s){

        for(int i = 0; i < rowsLength; i++){
            for(int j = 0; j < colsLength; j++){
                if(students[i][j] == null){
                    s.setPos(i,j);
                    students[i][j] = s;
                    return;
                }
            }
        }
    }

    public ArrayList<Student> getStudentNeighbors(Student s){
        ArrayList<Student> neighbors = new ArrayList<>();
        int rowIndexes[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int colIndexes[] = {-1, 0, 1, -1, 1, -1, 0, 1};

        for(int i = 0; i < rowIndexes.length; i++){
            try {
                neighbors.add(students[s.getX() + rowIndexes[i]][s.getY() + colIndexes[i]]);
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }
        return neighbors;
    }
}