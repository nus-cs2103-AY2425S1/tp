package seedu.address.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

public class ScheduleServices {
    public ScheduleServices() {

    }
    public static List<String> getTopThreeSchedules(List<Person> personList) {
        List<Map.Entry<Person, LocalDateTime>> allAppointments = new ArrayList<>();

        for (Person person : personList) {
            for (Schedule schedule : person.getSchedules()) {
                if (schedule.getDateTime().isEmpty()) {
                    continue;
                }

                LocalDateTime appointmentDateTime = LocalDateTime.parse(schedule.getDateTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                allAppointments.add(Map.entry(person, appointmentDateTime));
            }
        }

        allAppointments.sort(Map.Entry.comparingByValue());

        List<String> result = new ArrayList<>();
        int count = 1;
        for (Map.Entry<Person, LocalDateTime> entry : allAppointments) {
            if (count > 3) {
                break;
            }

            String formattedAppointment = String.format("%d. %s: %s",
                    count,
                    entry.getKey().getName(),
                    entry.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
            );
            result.add(formattedAppointment);
            count++;
        }
        return result;
    }
}
