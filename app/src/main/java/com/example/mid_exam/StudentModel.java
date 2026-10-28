package com.example.mid_exam;

public class StudentModel {
    private Integer id;           // SQLite row ID; null before insertion
    private String studentId;     // Student's own ID
    private String studentName;
    private int studentAge;

    public StudentModel(String studentId, String studentName, int studentAge) {
        this(null, studentId, studentName, studentAge);
    }

    public StudentModel(Integer id, String studentId, String studentName, int studentAge) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public int getStudentAge() { return studentAge; }
    public void setStudentAge(int studentAge) { this.studentAge = studentAge; }
}
