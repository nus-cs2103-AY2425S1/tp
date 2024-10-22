package keycontacts.model.student;

import java.util.Comparator;

/**
 * Represents a comparator that compares two students based on a single field.
 * Wraps around a {@code Comparator<Student>} to compare students by a single field
 * and a message to describe the field being compared.
 */
public class StudentComparatorByField {
    private Comparator<Student> comparator;
    private String message;

    /**
     * Constructs a {@code StudentComparatorByField} with the specified comparator
     * and message
     * @param comparator
     * @param message
     */
    public StudentComparatorByField(Comparator<Student> comparator, String message) {
        this.comparator = comparator;
        this.message = message;
    }

    public Comparator<Student> getComparator() {
        return comparator;
    }

    public String getMessage() {
        return message;
    }
}
