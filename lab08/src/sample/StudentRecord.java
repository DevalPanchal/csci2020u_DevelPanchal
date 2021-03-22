package sample;

public class StudentRecord {
    private String studentID;
    private float midterm;
    private float assignment;
    private float finalExam;

    private double finalMark;
    private char letterGrade;

    public StudentRecord(String studentID, float midterm, float assignment, float finalExam) {
        this.studentID = studentID;
        this.midterm = midterm;
        this.assignment = assignment;
        this.finalExam = finalExam;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public float getMidterm() {
        return this.midterm;
    }

    public float getAssignment() {
        return this.assignment;
    }

    public float getFinalExam() {
        return this.finalExam;
    }

    public double getFinalMark() {
        double weightedAssignment = this.assignment * 0.2;
        double weightedMidterm = this.midterm * 0.3;
        double weightedFinalExam = this.finalExam * 0.5;

        this.finalMark = weightedAssignment + weightedMidterm + weightedFinalExam;

        return this.finalMark;
    }

    public char getLetterGrade() {
        int finalMarks = (int) getFinalMark();

        if (finalMarks >= 80 && finalMarks <= 100) {
            this.letterGrade = 'A';
        } else if (finalMarks >= 70 && finalMarks <= 79) {
            this.letterGrade = 'B';
        } else if (finalMarks >= 60 && finalMarks <= 69) {
            this.letterGrade = 'C';
        } else if (finalMarks >= 50 && finalMarks <= 59) {
            this.letterGrade = 'D';
        } else if (finalMarks >= 0 && finalMarks <= 49) {
            this.letterGrade = 'F';
        } else {
            this.letterGrade = '0';
        }
        return letterGrade;
    }

}