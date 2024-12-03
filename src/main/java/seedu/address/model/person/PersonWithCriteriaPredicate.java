package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Range;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.tag.Tag;

/**
 * Tests that a person's age and appointment dates are within a given range.
 */
public class PersonWithCriteriaPredicate implements Predicate<Person> {
    public static final String MESSAGE_CONSTRAINTS = "All ranges should be of type Integer or LocalDate.";
    private final List<Range<?>> ranges;
    private final Set<Tag> tags;

    /**
     * Constructs a {@code PersonWithCriteriaPredicate} with the given list of ranges.
     *
     * @param ranges a list of {@code Range<?>} objects that define the criteria for filtering a person.
     */
    public PersonWithCriteriaPredicate(List<Range<?>> ranges) {
        requireNonNull(ranges);
        for (Range<?> range : ranges) {
            checkArgument(isValidRange(range), MESSAGE_CONSTRAINTS);
        }
        this.ranges = ranges;
        this.tags = new HashSet<>();
    }

    /**
     * Constructs a {@code PersonWithCriteriaPredicate} with the given list of ranges and set of tags.
     *
     * @param ranges a list of {@code Range<?>} objects that define the criteria for filtering a person.
     * @param tags a set of {@code Tags} to be filtered.
     */
    public PersonWithCriteriaPredicate(List<Range<?>> ranges, Set<Tag> tags) {
        requireNonNull(ranges);
        requireNonNull(tags);
        for (Range<?> range : ranges) {
            checkArgument(isValidRange(range), MESSAGE_CONSTRAINTS);
        }
        this.ranges = ranges;
        this.tags = tags;
    }

    /**
     * Checks if range is of Integer or LocalDate type.
     *
     * @param range {@code Range<?>} objects that define the criteria for filtering a person
     * @return {@code True} if range if of type Integer or LocalDate
     *         {@code False} otherwise.
     */
    public static boolean isValidRange(Range range) {
        requireNonNull(range);
        return (range.lowerBound instanceof Integer && range.upperBound instanceof Integer)
                || (range.lowerBound instanceof LocalDate && range.upperBound instanceof LocalDate);
    }

    /**
     * Checks if the age and appointment date of a person is within a given range
     * and tags contains a given tag.
     */
    @Override
    public boolean test(Person person) {
        return ranges.stream()
                .allMatch(range -> isWithinRange(range, person))
                && tags.stream().allMatch(tag -> withTag(tag, person));
    }

    /**
     * Checks if a person's age or appointment date is within a given range.
     *
     * @param r the given range as the criteria
     * @param person the person whose age or appointment date is checked
     * @return True if the person's age or appointment date is within the given range;
     *         False otherwise
     */
    public boolean isWithinRange(Range<?> r, Person person) {
        if (r.upperBound instanceof Integer) {
            Age strAge = person.getAge();
            int age = Integer.parseInt(strAge.toString());
            return age >= (Integer) r.lowerBound && age <= (Integer) r.upperBound;
        } else if (r.upperBound instanceof LocalDate) {
            Set<Appointment> appointments = person.getAppointment();
            return appointments.stream()
                    .anyMatch(appointment ->
                            !appointment.getAppointmentDate().isAfter((LocalDate) r.upperBound)
                                   && !appointment.getAppointmentDate().isBefore((LocalDate) r.lowerBound));
        } else {
            throw new IllegalArgumentException("Unsupported type: T must be either Integer or LocalDate");
        }
    }

    /**
     * Checks if the person's tag list contains a specific tag.
     */
    private boolean withTag(Tag tag, Person person) {
        Set<Tag> tags = person.getTags();
        return tags.contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonWithCriteriaPredicate)) {
            return false;
        }

        PersonWithCriteriaPredicate otherPersonWithCriteriaPredicate = (PersonWithCriteriaPredicate) other;
        return ranges.equals(otherPersonWithCriteriaPredicate.ranges);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("criteria", ranges).toString();
    }
}
