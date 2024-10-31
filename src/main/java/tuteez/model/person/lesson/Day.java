package tuteez.model.person.lesson;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum to represent the days of the week.
 */
public enum Day {
    MONDAY("mon"),
    TUESDAY("tue"),
    WEDNESDAY("wed"),
    THURSDAY("thu"),
    FRIDAY("fri"),
    SATURDAY("sat"),
    SUNDAY("sun");

    private static final Map<String, Day> DAY_NAME_MAP = new HashMap<>();

    static {
        for (Day day : values()) {
            DAY_NAME_MAP.put(day.name().toLowerCase(), day);
            DAY_NAME_MAP.put(day.shortName, day);
        }
    }
    private final String shortName;

    /**
     * Constructs a Day enum value with its corresponding short name.
     *
     * @param shortName The abbreviated name of the day (e.g., "mon" for Monday)
     */
    private Day(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Converts a string representation of a day into the corresponding {@code Day} enum value.
     *
     * <p>The string must be in lowercase (e.g., "monday", "mon"). If the string does not
     * match any of the valid days of the week, an exception is thrown.</p>
     *
     * @param dayInStr The string representing the day of the week (e.g., "monday", "mon").
     * @return The {@code Day} enum corresponding to the input string.
     * @throws IllegalArgumentException If the provided string does not match a valid day.
     */
    public static Day convertDayToEnum(String dayInStr) {
        assert dayInStr != null;
        return DAY_NAME_MAP.get(dayInStr.toLowerCase());
    }

    /**
     * Checks if the day provided is valid (i.e., part of the defined Days enum).
     *
     * @param day The day to check.
     * @return true if the day is valid.
     */
    public static boolean isValidDay(String day) {
        return day != null && DAY_NAME_MAP.containsKey(day.toLowerCase());
    }

    /**
     * Gets the shortened name of the day in the week.
     *
     * @return The abbreviated name of the day (e.g., "mon" for Monday)
     */
    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return name();
    }
}
