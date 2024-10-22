package seedu.address.model.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.Range;
import seedu.address.model.appointment.Appointment;

/**
 * Tests that a person's age and appointment dates are within a given range.
 */
public class PersonWithCriteriaPredicate implements Predicate<Person> {
    private final List<Range<?>> ranges;

    /**
     * Constructs a {@code PersonWithCriteriaPredicate} with the given list of ranges.
     *
     * @param ranges a list of {@code Range<?>} objects that define the criteria for filtering a person.
     */
    public PersonWithCriteriaPredicate(List<Range<?>> ranges) {
        this.ranges = ranges;

    }

    @Override
    public boolean test(Person person) {
        return ranges.stream()
                .allMatch(range -> isWithinRange(range, person));

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
                            !appointment.getDate().isAfter((LocalDate) r.upperBound)
                                   && !appointment.getDate().isBefore((LocalDate) r.lowerBound));
        } else {
            throw new IllegalArgumentException("Unsupported type: T must be either Integer or LocalDate");
        }
    }
}
