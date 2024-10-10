package seedu.address.model;

import java.util.HashMap;
import java.util.Map;

public class GradingSystem {
    private Map<String, Integer> gradeScale;
    private Map<String, Double> studentGrades;

    public GradingSystem() {
        this.gradeScale = new HashMap<>();
        this.studentGrades = new HashMap<>();
        initializeGradeScale();
    }

    private void initializeGradeScale() {
        gradeScale.put("A", 90);
        gradeScale.put("B", 80);
        gradeScale.put("C", 70);
        gradeScale.put("D", 60);
        gradeScale.put("F", 0);
    }

    public void addStudentGrade(String studentName, double score) {
        studentGrades.put(studentName, score);
    }

    public String getLetterGrade(String studentName) {
        Double score = studentGrades.get(studentName);
        if (score == null) {
            return "No grade recorded";
        }

        for (Map.Entry<String, Integer> entry : gradeScale.entrySet()) {
            if (score >= entry.getValue()) {
                return entry.getKey();
            }
        }
        return "F";
    }

    public double getStudentScore(String studentName) {
        return studentGrades.getOrDefault(studentName, -1.0);
    }

    public Map<String, Double> getAllGrades() {
        return new HashMap<>(studentGrades);
    }
}
