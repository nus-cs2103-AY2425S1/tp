package keycontacts.model.student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a comparator that compares two students based on multiple fields.
 * Wraps around a {@code List<Comparator<Student>>} to compare students by each
 * field
 */
public class StudentComparator implements Comparator<Student> {
    public static final String NAME_ASCENDING = "Name (ascending)";
    public static final String NAME_DESCENDING = "Name (descending)";
    public static final String PHONE_ASCENDING = "Phone (ascending)";
    public static final String PHONE_DESCENDING = "Phone (descending)";
    public static final String ADDRESS_ASCENDING = "Address (ascending)";
    public static final String ADDRESS_DESCENDING = "Address (descending)";
    public static final String GRADE_LEVEL_ASCENDING = "Grade level (ascending)";
    public static final String GRADE_LEVEL_DESCENDING = "Grade level (descending)";
    public static final String GROUP_ASCENDING = "Group (ascending)";
    public static final String GROUP_DESCENDING = "Group (descending)";
    private final List<Comparator<Student>> comparators;
    private final List<String> comparatorMessages;

    /**
     * Constructs a {@code StudentComparator} with an empty list of comparators
     */
    public StudentComparator() {
        comparators = new ArrayList<>();
        comparatorMessages = new ArrayList<>();
    }

    /**
     * Adds a comparator to the list of comparators
     * @param comparator
     */
    public void addComparator(StudentComparatorByField comparator) {
        comparators.add(comparator.getComparator());
        comparatorMessages.add(comparator.getMessage());
    }

    public String getSortDescription() {
        return String.join(", ", comparatorMessages);
    }

    @Override
    public int compare(Student o1, Student o2) {
        for (Comparator<Student> comparator : comparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public static StudentComparatorByField getComparatorForName(SortOrder sortOrder) {
        if (sortOrder.isAscending()) {
            return new StudentComparatorByField(Comparator.comparing(Student::getName), NAME_ASCENDING);
        }
        return new StudentComparatorByField(Comparator.comparing(Student::getName).reversed(), NAME_DESCENDING);
    }

    public static StudentComparatorByField getComparatorForPhone(SortOrder sortOrder) {
        if (sortOrder.isAscending()) {
            return new StudentComparatorByField(Comparator.comparing(Student::getPhone), PHONE_ASCENDING);
        }
        return new StudentComparatorByField(Comparator.comparing(Student::getPhone).reversed(), PHONE_DESCENDING);
    }

    public static StudentComparatorByField getComparatorForAddress(SortOrder sortOrder) {
        if (sortOrder.isAscending()) {
            return new StudentComparatorByField(Comparator.comparing(Student::getAddress), ADDRESS_ASCENDING);
        }
        return new StudentComparatorByField(Comparator.comparing(Student::getAddress).reversed(), ADDRESS_DESCENDING);
    }

    public static StudentComparatorByField getComparatorForGradeLevel(SortOrder sortOrder) {
        if (sortOrder.isAscending()) {
            return new StudentComparatorByField(Comparator.comparing(Student::getGradeLevel), GRADE_LEVEL_ASCENDING);
        }
        return new StudentComparatorByField(Comparator.comparing(Student::getGradeLevel).reversed(),
                GRADE_LEVEL_DESCENDING);
    }

    public static StudentComparatorByField getComparatorForGroup(SortOrder sortOrder) {
        if (sortOrder.isAscending()) {
            return new StudentComparatorByField(Comparator.comparing(Student::getGroup), GROUP_ASCENDING);
        }
        return new StudentComparatorByField(Comparator.comparing(Student::getGroup).reversed(), GROUP_DESCENDING);
    }

    /**
     * Represents the sort order of a comparator
     * Can be either ascending (ASC) or descending (DESC)
     */
    public static class SortOrder {
        public static final String INVALID_SORT_ORDER = "Invalid sort order";

        private static final String ASCENDING = "ASC";
        private static final String DESCENDING = "DESC";

        private final String sortOrder;

        /**
         * Constructs a {@code SortOrder} with the specified sort order
         * @param sortOrder
         */
        public SortOrder(String sortOrder) {
            if (!isValidSortOrder(sortOrder)) {
                throw new IllegalArgumentException(INVALID_SORT_ORDER);
            }
            this.sortOrder = sortOrder;
        }

        /**
         * Checks if a sort order is ascending.
         */
        public boolean isAscending() {
            return sortOrder.equalsIgnoreCase(ASCENDING);
        }

        /**
         * Checks if a sort order is valid.
         */
        public static boolean isValidSortOrder(String sortOrder) {
            return sortOrder.equalsIgnoreCase(ASCENDING) || sortOrder.equalsIgnoreCase(DESCENDING);
        }
    }
}
