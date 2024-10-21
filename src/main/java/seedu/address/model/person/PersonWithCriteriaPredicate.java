package seedu.address.model.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.appointment.Appointment;

public class PersonWithCriteriaPredicate implements Predicate<Person> {
    private final List<Range<?>> ranges;

    public PersonWithCriteriaPredicate(List<Range<?>> ranges) {
        this.ranges = ranges;

    }

    @Override
    public boolean test(Person person) {
        return ranges.stream()
                .allMatch(range -> isWithinRange(range, person));

    }

    public boolean isWithinRange(Range<?> r, Person person) {
        if (r.upperRange instanceof Integer) {
            Age strAge = person.getAge();
            int age = Integer.parseInt(strAge.toString());
            return age >= (Integer) r.lowerRange && age <= (Integer) r.upperRange;
        } else if (r.upperRange instanceof LocalDate) {
            Set<Appointment> appointments = person.getAppointment();
            return appointments.stream()
                    .anyMatch(appointment ->
                            !appointment.getDate().isAfter((LocalDate) r.upperRange) &&
                                    !appointment.getDate().isBefore((LocalDate) r.lowerRange));
        } else {
            throw new IllegalArgumentException("Unsupported type: T must be either Integer or LocalDate");
        }
    }
}
