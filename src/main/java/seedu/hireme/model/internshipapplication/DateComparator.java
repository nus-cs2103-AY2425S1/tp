package seedu.hireme.model.internshipapplication;

import java.util.Comparator;

import seedu.hireme.commons.util.ToStringBuilder;

/**
 * Compares and sorts the internship application list by date.
 */
public class DateComparator implements Comparator<InternshipApplication> {

    private final boolean isEarliestOrder;

    /**
     * Constructs a {@code DateComparator} with the given sorting order.
     *
     * @param isEarliestOrder A boolean which represents whether the list should be sorted by earliest or latest
     *                         order.
     */
    public DateComparator(boolean isEarliestOrder) {
        this.isEarliestOrder = isEarliestOrder;
    }

    /**
     * Compares which internship application should appear first in the list based on their {@code Date} and the sorting
     *     order.
     *
     * @param internshipApplication1 The first internship application.
     * @param internshipApplication2 The second internship application.
     * @return 0 if the internshipApplication1 object occurs at the same date as internshipApplication2 object. Returns
     *     a negative number if the internshipApplication1 object occurs before the other and a positive number
     *     otherwise if the sorting order is by earliest date. Conversely, it returns a positive number if the
     *     internshipApplication1 object occurs before the other and a negative number otherwise if the sorting order
     *     is by latest date.
     */
    @Override
    public int compare(InternshipApplication internshipApplication1, InternshipApplication internshipApplication2) {
        Date date1 = internshipApplication1.getDateOfApplication();
        Date date2 = internshipApplication2.getDateOfApplication();
        int multiplier = isEarliestOrder ? 1 : -1;
        return multiplier * date1.compareTo(date2);
    }

    /**
     * Compares this comparator with another object for equality.
     *
     * @param other The other object to compare with.
     * @return True if both comparators contain the same order, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateComparator)) {
            return false;
        }

        DateComparator otherComparator = (DateComparator) other;
        return isEarliestOrder == otherComparator.isEarliestOrder;
    }

    /**
     * Returns a string representation of this comparator.
     *
     * @return A string that contains the sorting order.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isEarliestOrder", String.valueOf(isEarliestOrder))
                .toString();
    }
}
