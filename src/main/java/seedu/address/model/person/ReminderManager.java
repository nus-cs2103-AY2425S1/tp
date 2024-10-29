package seedu.address.model.person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Manages reminders for upcoming and overdue deadlines of persons in the address book.
 * Only shows reminders for incomplete projects and active clients.
 */
public class ReminderManager {
    private static final String MESSAGE_NO_REMINDERS = "No upcoming or overdue reminders.";
    private static final String MESSAGE_DEADLINE_IN = " have deadlines in ";
    private static final String MESSAGE_DEADLINE_OVERDUE = " have overdue deadlines";
    private static final String MESSAGE_SINGLE_DEADLINE_IN = " has deadline in ";
    private static final String MESSAGE_SINGLE_DEADLINE_OVERDUE = " has overdue deadline";
    private static final String DAY_SINGULAR = "1 day";
    private static final String DAY_PLURAL = " days";
    private static final String DUE_TODAY = " have deadlines due today.";
    private static final String SINGLE_DUE_TODAY = " has deadline due today.";
    private static final String PERIOD = ".";
    private static final String AND = " and ";
    private static final String COMMA = ", ";
    private static final String MORE_SUFFIX = " and %d more";
    private static final int MAX_NAMES_TO_SHOW = 3;
    private static final String ACTIVE_STATUS = "active";

    private ObservableList<Person> persons;
    private final StringProperty currentReminder = new SimpleStringProperty();

    /**
     * Initializes the ReminderManager with the list of persons and sets up change listeners.
     *
     * @param persons ObservableList of {@code Person} from the address book.
     */
    public ReminderManager(ObservableList<Person> persons) {
        this.persons = persons;
        setupChangeListener();
        updateReminder(); // Initial update
    }

    private void setupChangeListener() {
        persons.addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                    updateReminder();
                    break;
                }
            }
        });
    }

    private void updateReminder() {
        List<String> reminders = getLatestReminders();
        currentReminder.set(reminders.isEmpty() ? MESSAGE_NO_REMINDERS : reminders.get(0));
    }

    public StringProperty currentReminderProperty() {
        return currentReminder;
    }

    private String formatNames(List<Person> persons) {
        // Filter out completed projects and non-active clients
        List<Person> eligiblePeople = persons.stream()
                .filter(person -> !person.getProjectStatus().isComplete
                        && person.getClientStatus().toString().equals(ACTIVE_STATUS))
                .collect(Collectors.toList());

        if (eligiblePeople.isEmpty()) {
            return "";
        }

        if (eligiblePeople.size() == 1) {
            return eligiblePeople.get(0).getName().toString();
        }

        StringBuilder names = new StringBuilder();
        int namesToShow = Math.min(eligiblePeople.size(), MAX_NAMES_TO_SHOW);

        for (int i = 0; i < namesToShow; i++) {
            if (i > 0) {
                // Add comma between names except before the last name
                if (i < namesToShow - 1) {
                    names.append(COMMA);
                } else {
                    // For the last name, add "and" if it's not followed by "X more"
                    if (eligiblePeople.size() <= MAX_NAMES_TO_SHOW) {
                        names.append(AND);
                    } else {
                        names.append(COMMA);
                    }
                }
            }
            names.append(eligiblePeople.get(i).getName());
        }

        // Add "and X more" if there are additional people
        if (eligiblePeople.size() > MAX_NAMES_TO_SHOW) {
            names.append(String.format(MORE_SUFFIX, eligiblePeople.size() - MAX_NAMES_TO_SHOW));
        }

        return names.toString();
    }

    private List<String> getLatestReminders() {
        LocalDate now = LocalDate.now();
        List<String> reminders = new ArrayList<>();

        // Collect all overdue persons (only active clients)
        List<Person> overduePersons = new ArrayList<>();
        for (Person person : persons) {
            if (!person.getProjectStatus().isComplete
                    && person.getClientStatus().toString().equals(ACTIVE_STATUS)
                    && person.getDeadline().isOverdue()) {
                overduePersons.add(person);
            }
        }

        // If there are overdue projects, show them first
        if (!overduePersons.isEmpty()) {
            String names = formatNames(overduePersons);
            if (!names.isEmpty()) {
                reminders.add(names
                        + (overduePersons.size() == 1 ? MESSAGE_SINGLE_DEADLINE_OVERDUE
                        : MESSAGE_DEADLINE_OVERDUE)
                        + PERIOD);
                return reminders;
            }
        }

        // Handle upcoming deadlines if no overdue ones
        Map<LocalDate, List<Person>> deadlineGroups = new LinkedHashMap<>();
        for (Person person : persons) {
            if (!person.getProjectStatus().isComplete
                    && person.getClientStatus().toString().equals(ACTIVE_STATUS)) {
                Deadline deadline = person.getDeadline();
                if (!deadline.isOverdue()) {
                    deadlineGroups.computeIfAbsent(deadline.value, k -> new ArrayList<>()).add(person);
                }
            }
        }

        if (!deadlineGroups.isEmpty()) {
            LocalDate nearestDate = deadlineGroups.keySet().stream()
                    .min(LocalDate::compareTo)
                    .orElse(null);

            if (nearestDate != null) {
                List<Person> upcomingPersons = deadlineGroups.get(nearestDate);
                String names = formatNames(upcomingPersons);
                if (!names.isEmpty()) {
                    if (nearestDate.isEqual(now)) {
                        reminders.add(names
                                + (upcomingPersons.size() == 1 ? SINGLE_DUE_TODAY : DUE_TODAY));
                    } else {
                        long daysUntil = ChronoUnit.DAYS.between(now, nearestDate);
                        String dayMessage = daysUntil == 1 ? DAY_SINGULAR : daysUntil + DAY_PLURAL;
                        reminders.add(names
                                + (upcomingPersons.size() == 1 ? MESSAGE_SINGLE_DEADLINE_IN : MESSAGE_DEADLINE_IN)
                                + dayMessage + PERIOD);
                    }
                }
            }
        }

        return reminders;
    }
}