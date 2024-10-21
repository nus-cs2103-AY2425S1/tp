package seedu.address.model.person;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;

public class PersonWithCriteriaPredicate<T> implements Predicate<Person> {
    private final T upperRange;
    private final T lowerRange;

    public PersonWithCriteriaPredicate(T lowerRange, T upperRange) {
        this.upperRange = upperRange;
        this.lowerRange = lowerRange;

        assert upperRange instanceof Integer || upperRange instanceof LocalDate :
                "Unsupported type: T must be either Integer (for age) or LocalDate (for appointments)";
    }

    @Override
    public boolean test(Person person) {

        if (upperRange instanceof Integer) {
            Age strAge = person.getAge();
            int age = Integer.parseInt(strAge.toString());
            return age >= (Integer) lowerRange && age <= (Integer) upperRange;
        } else if (upperRange instanceof LocalDate) {
            Set<Appointment> appointments = person.getAppointment();
            return appointments.stream()
                    .anyMatch(appointment ->
                            !appointment.getDate().isAfter((LocalDate) upperRange) &&
                            !appointment.getDate().isBefore((LocalDate) lowerRange));
        } else {
            throw new IllegalArgumentException("Unsupported type: T must be either Integer or LocalDate");
        }

    }
}
