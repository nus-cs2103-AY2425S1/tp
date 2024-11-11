package seedu.address.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * A class that provides services for handling and retrieving schedules.
 */
public class ScheduleServices {
    /**
     * Retrieves the top three upcoming schedules from a list of persons, sorted by date and time.\
     *
     * @param personList The list of persons from which to retrieve schedules.
     * @return A list of strings representing the top three schedules
     *         If there are fewer than three schedules, it returns all of them
     */
    public static List<String> getTopThreeSchedules(List<Person> personList) {
        LocalDateTime now = LocalDateTime.now();
        List<Map.Entry<Person, LocalDateTime>> upcomingAppointments = new ArrayList<>();

        for (Person person : personList) {
            for (Schedule schedule : person.getSchedules()) {
                if (schedule.getDateTime().isEmpty()) {
                    continue;
                }

                LocalDateTime appointmentDateTime = LocalDateTime.parse(schedule.getDateTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                if (appointmentDateTime.isAfter(now)) {
                    upcomingAppointments.add(Map.entry(person, appointmentDateTime));
                }
            }
        }

        // Sort appointments by date and time
        List<Map.Entry<Person, LocalDateTime>> sortedAppointments = upcomingAppointments.stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedAppointments.size()); i++) {
            Map.Entry<Person, LocalDateTime> entry = sortedAppointments.get(i);
            String formattedAppointment = String.format("%d. %s: %s",
                    i + 1,
                    entry.getKey().getName(),
                    entry.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")));
            result.add(formattedAppointment);
        }

        return result;
    }
}
