package seedu.address.model.person;

import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ReminderManager {
    private static final String MESSAGE_NO_REMINDERS = "No upcoming reminders.";
    private static final String MESSAGE_DEADLINE_IN = "'s deadline is in ";
    private static final String DAY_SINGULAR = "1 day";
    private static final String DAY_PLURAL = " days";
    private static final String DUE_TODAY = "'s deadline is due today.";
    private static final String PERIOD = ".";

    private ObservableList<Person> persons;
    private int currentReminderIndex = 0;

    /**
     * Initializes the ReminderManager with the list of persons.
     * @param persons ObservableList<Person> from the address book.
     */
    public ReminderManager(ObservableList<Person> persons) {
        this.persons = persons;
    }

    /**
     * Fetches the latest reminders based on the nearest deadlines of persons in the list.
     * This method will gather all persons who share the nearest upcoming deadline.
     * @return A list of reminder messages, or an empty list if no reminders are due.
     */
    public List<String> getLatestReminders() {
        LocalDate now = LocalDate.now();
        List<String> reminders = new ArrayList<>();
        LocalDate latestDeadline = null;

        for (Person person : persons) {
            var deadline = person.getDeadline();
            if (!deadline.isOverdue()) {
                LocalDate deadlineDate = deadline.value;

                if (latestDeadline == null || deadlineDate.isBefore(latestDeadline)) {
                    latestDeadline = deadlineDate;
                    reminders.clear();
                }

                if (deadlineDate.equals(latestDeadline)) {
                    if (deadlineDate.isEqual(now)) {
                        reminders.add(person.getName() + DUE_TODAY);
                    } else {
                        long daysUntilDeadline = ChronoUnit.DAYS.between(now, deadlineDate);
                        String dayMessage = daysUntilDeadline == 1 ? DAY_SINGULAR : daysUntilDeadline + DAY_PLURAL;
                        reminders.add(person.getName() + MESSAGE_DEADLINE_IN + dayMessage + PERIOD);
                    }
                }
            }
        }

        return reminders;
    }

    /**
     * Fetches the next reminder in the rotation.
     * If multiple persons share the same deadline, this will rotate through them.
     * @return The next reminder message.
     */
    public String getNextReminder() {
        List<String> reminders = getLatestReminders();
        if (reminders.isEmpty()) {
            currentReminderIndex = 0;
            return MESSAGE_NO_REMINDERS;
        } else {
            if (currentReminderIndex >= reminders.size()) {
                currentReminderIndex = 0;
            }

            String nextReminder = reminders.get(currentReminderIndex);
            currentReminderIndex++;

            if (currentReminderIndex >= reminders.size()) {
                currentReminderIndex = 0;
            }

            return nextReminder;
        }
    }
}
