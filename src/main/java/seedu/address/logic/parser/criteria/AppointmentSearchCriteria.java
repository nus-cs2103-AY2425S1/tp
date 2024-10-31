package seedu.address.logic.parser.criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
/**
 * Represents a search criteria for appointments.
 */
public class AppointmentSearchCriteria implements SearchCriteria {

    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalDate endDate;
    private final LocalTime endTime;

    /**
     * Constructs an {@code AppointmentSearchCriteria} with the specified start and end dates and times.
     *
     * @param startDate the start date of the search criteria
     * @param startTime the start time of the search criteria
     * @param endDate the end date of the search criteria
     * @param endTime the end time of the search criteria
     */
    public AppointmentSearchCriteria(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * Returns true if the given {@code Person} has an appointment that matches the search criteria, false otherwise.
     *
     * @param person the person to check
     * @return true if the given {@code Person} has an appointment that matches the search criteria
     */
    @Override
    public boolean test(Person person) {
        Set<Appointment> matchingAppointments = person.getAppointments()
                .stream().filter(this::test).collect(Collectors.toSet());
        person.setFilteredAppointments(matchingAppointments);
        return !matchingAppointments.isEmpty();
    }

    /**
     * Returns true if the given {@code Appointment} matches the search criteria, false otherwise.
     *
     * @param appointment the appointment to check
     * @return true if the given {@code Appointment} matches the search criteria
     */
    public boolean test(Appointment appointment) {
        LocalDateTime appointmentStart = appointment.getStartTime();
        LocalDateTime appointmentEnd = appointment.getEndTime();

        LocalDateTime criteriaStart = LocalDateTime.of(this.startDate, this.startTime);
        LocalDateTime criteriaEnd = LocalDateTime.of(this.endDate, this.endTime);

        return !appointmentStart.isBefore(criteriaStart) && !appointmentEnd.isAfter(criteriaEnd);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AppointmentSearchCriteria)) {
            return false;
        }
        AppointmentSearchCriteria otherCriteria = (AppointmentSearchCriteria) other;
        return this.startDate.equals(otherCriteria.startDate) && this.startTime.equals(otherCriteria.startTime)
                && this.endDate.equals(otherCriteria.endDate) && this.endTime.equals(otherCriteria.endTime);
    }
}
