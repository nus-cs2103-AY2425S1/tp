package seedu.address.logic.parser.criteria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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
        Set<Appointment> appointments = person.getAppointments();
        return appointments.stream().anyMatch(this::test);
    }

    /**
     * Returns true if the given {@code Appointment} matches the search criteria, false otherwise.
     *
     * @param appointment the appointment to check
     * @return true if the given {@code Appointment} matches the search criteria
     */
    public boolean test(Appointment appointment) {
        LocalDate appointmentStartDate = appointment.getStartTime().toLocalDate();
        LocalDate appointmentEndDate = appointment.getEndTime().toLocalDate();
        LocalTime appointmentStartTime = appointment.getStartTime().toLocalTime();
        LocalTime appointmentEndTime = appointment.getEndTime().toLocalTime();
        return !appointmentStartDate.isBefore(this.startDate) && !appointmentStartTime.isBefore(this.startTime)
                && !appointmentEndDate.isAfter(this.endDate) && !appointmentEndTime.isAfter(this.endTime);

    }
}
