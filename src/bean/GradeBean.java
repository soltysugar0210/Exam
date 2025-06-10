package bean;

import java.io.Serializable;

public class GradeBean implements Serializable {
    private int id; // 成績ID（自動採番）
    private int studentId; // 学生番号
    private String studentName; // 生徒名（表示用）
    private int subjectId; // 科目ID
    private String subjectName; // 科目名（表示用）
    private int count; // 試験回数
    private int score; // 点数

    public GradeBean() {}

    // ゲッターとセッター
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}