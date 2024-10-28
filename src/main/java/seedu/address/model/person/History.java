package seedu.address.model.person;

import static seedu.address.logic.Messages.MESSAGE_ACTIVITY_LIST_NOT_INITIALIZED;
import static seedu.address.logic.Messages.MESSAGE_BEFORE_DATE_OF_CREATION;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.storage.JsonAdaptedHistoryEntry;

/**
 * The {@code History} class stores the history of activities based on dates.
 * Each {@code LocalDate} is associated with a list of activities in the form of strings.
 * It provides functionality to add activities on a specific date and retrieve activities
 * that occurred on a particular date.
 */
public class History {
    // A TreeMap that maps each date to a list of activity messages.
    private TreeMap<LocalDate, ArrayList<String>> history;
    private final DateOfCreation dateOfCreation;

    /**
     * Constructs an empty {@code History} object.
     * The history will be initialized as an empty TreeMap.
     */
    public History(LocalDate dateOfCreation) {
        this.history = new TreeMap<>();
        this.dateOfCreation = new DateOfCreation(dateOfCreation);
    }

    /**
     * Constructs a non-empty {@code History} object.
     */
    public History(TreeMap<LocalDate, ArrayList<String>> history, LocalDate dateOfCreation) {
        this.history = history;
        this.dateOfCreation = new DateOfCreation(dateOfCreation);
    }

    /**
     * Adds an activity message for a specified date.
     * If there is no entry for the specified date, it initializes the entry before adding the activity.
     *
     * @param date    The {@code LocalDate} when the activity occurred.
     * @param message The activity message to be added for the specified date.
     */
    public void addActivity(LocalDate date, String message) throws IllegalArgumentException {
        if (!this.dateOfCreation.isAfter(date)) {
            throw new IllegalArgumentException(String.format(MESSAGE_BEFORE_DATE_OF_CREATION,
                    date, this.dateOfCreation));
        }
        if (!this.history.containsKey(date)) {
            this.history.put(date, new ArrayList<>());
        }
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.history.get(date).add(message);
    }

    /**
     * Creates a new {@code History} object by adding an activity message for a specified date
     * in an immutable manner, returning a new {@code History} object.
     * If there is no entry for the specified date, it initializes the entry before adding the activity.
     *
     * @param originalHistory The original {@code History} object to be copied.
     * @param date The {@code LocalDate} when the activity occurred.
     * @param message The activity message to be added for the specified date.
     * @return A new {@code History} object with the added activity.
     */
    public static History addActivity(History originalHistory, LocalDate date, String message) {
        // Check if the date is valid based on the date of creation.
        if (!originalHistory.dateOfCreation.isAfter(date)) {
            throw new IllegalArgumentException(String.format(MESSAGE_BEFORE_DATE_OF_CREATION,
                    date, originalHistory.dateOfCreation));
        }

        // Create a copy of the history map to ensure immutability of the original
        TreeMap<LocalDate, ArrayList<String>> newHistoryMap = new TreeMap<>(originalHistory.history);

        // If there is no entry for the given date, initialize the entry before adding the activity
        newHistoryMap.putIfAbsent(date, new ArrayList<>());
        newHistoryMap.get(date).add(message);

        // Return a new History object with the updated map
        return new History(newHistoryMap, originalHistory.dateOfCreation.getDateOfCreation());
    }

    /**
     * Retrieves the list of activities that occurred on the specified date.
     * If no activities are found for that date, it throws a {@code NoSuchElementException}.
     *
     * @param date The {@code LocalDate} for which to retrieve the activities.
     * @return A list of {@code Activity} objects that occurred on the specified date.
     * @throws NoSuchElementException If there are no activities for the specified date.
     */
    public List<Activity> getActivitiesOnDay(LocalDate date) throws RuntimeException {
        if (!this.dateOfCreation.isAfter(date)) {
            throw new DateTimeException(String.format(MESSAGE_BEFORE_DATE_OF_CREATION, date,
                    this.dateOfCreation));
        }
        try {
            ArrayList<String> listOfActivitiesMessage = this.history.get(date);
            return listOfActivitiesMessage.stream()
                    .map(s -> Activity.of(date, s))
                    .collect(Collectors.toList());
        } catch (NullPointerException e2) {
            throw new NullPointerException(MESSAGE_ACTIVITY_LIST_NOT_INITIALIZED);
        }
    }

    /**
     * Returns the entries in the history as a {@code Map} where each key is a {@code LocalDate}
     * and the corresponding value is a list of activities for that date.
     *
     * @return A map representing the history entries.
     */
    public Map<LocalDate, List<String>> getHistoryEntries() {
        return new TreeMap<>(history); // Return a copy of the history map to avoid modification
    }

    /**
     * Creates a new {@code History} object from a list of {@code JsonAdaptedHistoryEntry}.
     *
     * @param dateOfCreation The date of creation of the history.
     * @param historyEntries The list of {@code JsonAdaptedHistoryEntry} to be deserialized.
     * @return A new {@code History} object populated with activities.
     */
    public static History fromJsonEntries(DateOfCreation dateOfCreation, List<JsonAdaptedHistoryEntry> historyEntries) {
        History newHistory = new History(dateOfCreation.getDateOfCreation());

        for (JsonAdaptedHistoryEntry historyEntry : historyEntries) {
            LocalDate date = historyEntry.toDate();
            for (String activity : historyEntry.getActivities()) {
                newHistory.addActivity(date, activity);
            }
        }

        return newHistory;
    }
    @Override
    public boolean equals(Object other) {
        // Check if this is the same object reference
        if (this == other) {
            return true;
        }

        // Check if the other object is an instance of History
        if (!(other instanceof History)) {
            return false;
        }

        // Typecast other object to History and compare the fields
        History otherHistory = (History) other;

        // Compare dateOfCreation and history fields for equality
        return this.dateOfCreation.equals(otherHistory.dateOfCreation)
                && this.history.equals(otherHistory.history);
    }
    /**
     * @return if the history object contains entries other than the default creation entry {@code boolean}.
     */
    public boolean hasNoEntry() {
        return this.history.entrySet().stream().flatMap(e -> e.getValue().stream()).count() < 1;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date of Creation: ").append(dateOfCreation.getDateOfCreation()).append("\n");

        for (Map.Entry<LocalDate, ArrayList<String>> entry : history.entrySet()) {
            LocalDate date = entry.getKey();
            List<String> activities = entry.getValue();

            sb.append("[").append(date).append("]:\n");
            for (String activityMessage : activities) {
                Activity activity = Activity.of(date, activityMessage);
                sb.append("  ").append(activity).append("\n");
            }
        }

        return sb.toString();
    }

}
