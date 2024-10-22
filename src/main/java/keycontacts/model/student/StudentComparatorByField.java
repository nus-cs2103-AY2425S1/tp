package keycontacts.model.student;

import java.util.Comparator;

public class StudentComparatorByField {
    Comparator<Student> comparator;
    String message;

    public StudentComparatorByField(Comparator<Student> comparator, String message) {
        this.comparator = comparator;
        this.message = message;
    }
}
