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
     * Initializes the ReminderManager with a list of persons to track.
     * Sets up change listeners and creates initial reminders.
     *
     * @param persons An observable list of persons to monitor for deadlines
     */
    public ReminderManager(ObservableList<Person> persons) {
        this.persons = persons;
        setupChangeListener();
        updateReminder();
    }

    private void setupChangeListener() {
        ListChangeListener<Person> listener = this::handlePersonListChange;
        persons.addListener(listener);
    }

    private void handlePersonListChange(ListChangeListener.Change<? extends Person> change) {
        if (hasRelevantChanges(change)) {
            updateReminder();
        }
    }

    private boolean hasRelevantChanges(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (change.wasAdded() || change.wasRemoved() || change.wasUpdated()) {
                return true;
            }
        }
        return false;
    }

    private void updateReminder() {
        List<String> reminders = getLatestReminders();
        String message = reminders.isEmpty() ? MESSAGE_NO_REMINDERS : reminders.get(0);
        currentReminder.set(message);
    }

    public StringProperty currentReminderProperty() {
        return currentReminder;
    }

    private String formatNames(List<Person> persons) {
        List<Person> eligiblePeople = filterEligiblePeople(persons);

        if (eligiblePeople.isEmpty()) {
            return "";
        }

        if (eligiblePeople.size() == 1) {
            return eligiblePeople.get(0).getName().toString();
        }

        return formatMultipleNames(eligiblePeople);
    }

    private List<Person> filterEligiblePeople(List<Person> persons) {
        return persons.stream()
                .filter(this::isActiveAndIncomplete)
                .collect(Collectors.toList());
    }

    private String formatMultipleNames(List<Person> people) {
        StringBuilder names = new StringBuilder();
        int namesToShow = Math.min(people.size(), MAX_NAMES_TO_SHOW);

        appendInitialName(names, people.get(0));
        appendMiddleNames(names, people, namesToShow);
        appendFinalName(names, people, namesToShow);
        appendMoreSuffix(names, people.size(), namesToShow);

        return names.toString();
    }

    private void appendInitialName(StringBuilder names, Person person) {
        names.append(person.getName());
    }

    private void appendMiddleNames(StringBuilder names, List<Person> people, int namesToShow) {
        for (int i = 1; i < namesToShow - 1; i++) {
            names.append(COMMA).append(people.get(i).getName());
        }
    }

    private void appendFinalName(StringBuilder names, List<Person> people, int namesToShow) {
        if (namesToShow > 1) {
            String connector = people.size() <= MAX_NAMES_TO_SHOW ? AND : COMMA;
            names.append(connector).append(people.get(namesToShow - 1).getName());
        }
    }

    private void appendMoreSuffix(StringBuilder names, int totalSize, int shownSize) {
        if (totalSize > MAX_NAMES_TO_SHOW) {
            names.append(String.format(MORE_SUFFIX, totalSize - MAX_NAMES_TO_SHOW));
        }
    }

    private List<String> getLatestReminders() {
        List<String> reminders = new ArrayList<>();
        LocalDate now = LocalDate.now();

        addOverdueReminders(reminders);
        if (!reminders.isEmpty()) {
            return reminders;
        }

        addUpcomingReminders(reminders, now);
        return reminders;
    }

    private void addOverdueReminders(List<String> reminders) {
        List<Person> overduePersons = getOverduePersons();
        if (!overduePersons.isEmpty()) {
            String reminderText = createOverdueReminder(overduePersons);
            if (!reminderText.isEmpty()) {
                reminders.add(reminderText);
            }
        }
    }

    private void addUpcomingReminders(List<String> reminders, LocalDate now) {
        Map<LocalDate, List<Person>> deadlineGroups = groupUpcomingDeadlines();
        if (deadlineGroups.isEmpty()) {
            return;
        }

        LocalDate nearestDate = findNearestDeadline(deadlineGroups);
        if (nearestDate == null) {
            return;
        }

        String upcomingReminder = createUpcomingReminder(deadlineGroups.get(nearestDate), nearestDate, now);
        if (!upcomingReminder.isEmpty()) {
            reminders.add(upcomingReminder);
        }
    }

    private List<Person> getOverduePersons() {
        return persons.stream()
                .filter(this::isActiveAndIncomplete)
                .filter(person -> person.getDeadline().isOverdue())
                .collect(Collectors.toList());
    }

    private boolean isActiveAndIncomplete(Person person) {
        return !person.getProjectStatus().isComplete
                && person.getClientStatus().toString().equals(ACTIVE_STATUS);
    }

    private String createOverdueReminder(List<Person> overduePersons) {
        String names = formatNames(overduePersons);
        if (names.isEmpty()) {
            return "";
        }

        String message = overduePersons.size() == 1
                ? MESSAGE_SINGLE_DEADLINE_OVERDUE
                : MESSAGE_DEADLINE_OVERDUE;

        return names + message + PERIOD;
    }

    private Map<LocalDate, List<Person>> groupUpcomingDeadlines() {
        Map<LocalDate, List<Person>> deadlineGroups = new LinkedHashMap<>();

        List<Person> upcomingPersons = getUpcomingPersons();
        for (Person person : upcomingPersons) {
            addToDeadlineGroup(person, deadlineGroups);
        }

        return deadlineGroups;
    }

    private List<Person> getUpcomingPersons() {
        return persons.stream()
                .filter(this::isActiveAndIncomplete)
                .filter(person -> !person.getDeadline().isOverdue())
                .collect(Collectors.toList());
    }

    private void addToDeadlineGroup(Person person, Map<LocalDate, List<Person>> deadlineGroups) {
        LocalDate deadline = person.getDeadline().value;
        List<Person> group = deadlineGroups.computeIfAbsent(deadline, k -> new ArrayList<>());
        group.add(person);
    }

    private LocalDate findNearestDeadline(Map<LocalDate, List<Person>> deadlineGroups) {
        return deadlineGroups.keySet().stream()
                .min(LocalDate::compareTo)
                .orElse(null);
    }

    private String createUpcomingReminder(List<Person> upcomingPersons, LocalDate nearestDate, LocalDate now) {
        String names = formatNames(upcomingPersons);
        if (names.isEmpty()) {
            return "";
        }

        if (nearestDate.isEqual(now)) {
            return createTodayReminder(names, upcomingPersons.size());
        }

        return createFutureReminder(names, upcomingPersons.size(), now, nearestDate);
    }

    private String createTodayReminder(String names, int size) {
        String message = size == 1 ? SINGLE_DUE_TODAY : DUE_TODAY;
        return names + message;
    }

    private String createFutureReminder(String names, int size, LocalDate now, LocalDate deadline) {
        long daysUntil = ChronoUnit.DAYS.between(now, deadline);
        String dayMessage = daysUntil == 1 ? DAY_SINGULAR : daysUntil + DAY_PLURAL;
        String message = size == 1 ? MESSAGE_SINGLE_DEADLINE_IN : MESSAGE_DEADLINE_IN;

        return names + message + dayMessage + PERIOD;
    }
}
