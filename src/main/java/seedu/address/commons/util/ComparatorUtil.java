package seedu.address.commons.util;

import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.DaysAttended;
/**
 * Utility class for comparing class strings.
 */
public class ComparatorUtil {

    /**
     * Compares two class strings based on their class number and section.
     * @param class1 The first class string.
     * @param class2 The second class string.
     * @return A negative integer, zero, or a positive integer as the first class string is less than, equal to, or
     *     greater than the second class string.
     */
    public static int compareClassStrings(String class1, String class2) {
        int number1 = Integer.parseInt(class1.replaceAll("[^0-9]", ""));
        int number2 = Integer.parseInt(class2.replaceAll("[^0-9]", ""));
        String section1 = class1.replaceAll("[0-9]", "");
        String section2 = class2.replaceAll("[0-9]", "");

        int numberComparison = Integer.compare(number1, number2);
        return numberComparison != 0 ? numberComparison : section1.compareTo(section2);
    }

    /**
     * Returns the primary class string for sorting from a list of classes.
     * @param classes the list of class strings
     * @return the primary class string for sorting, or an empty string if the list is empty
     */
    public static String getPrimaryClassForSorting(List<String> classes) {
        return classes.stream()
                .sorted(ComparatorUtil::compareClassStrings)
                .findFirst()
                .orElse("");
    }

    /**
     * Comparator for DaysAttended objects, placing null values last.
     * @return A Comparator for DaysAttended that handles nulls by placing them last.
     */
    public static Comparator<DaysAttended> getDaysAttendedComparator() {
        return Comparator.comparing(
                daysAttended -> daysAttended != null ? daysAttended.getValue() : null,
                Comparator.nullsLast(Comparator.reverseOrder())
        );
    }
}
