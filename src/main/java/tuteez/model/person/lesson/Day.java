package tuteez.model.person.lesson;

/**
 * Enum to represent the days of the week.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    /**
     * Converts a string representation of a day into the corresponding {@code Day} enum value.
     *
     * <p>The string must be in lowercase (e.g., "monday", "tuesday"). If the string does not
     * match any of the valid days of the week, an exception is thrown.</p>
     *
     * @param dayInStr The string representing the day of the week (e.g., "monday").
     * @return The {@code Day} enum corresponding to the input string.
     * @throws IllegalArgumentException If the provided string does not match a valid day.
     */
    public static Day convertDayToEnum(String dayInStr) {
        switch (dayInStr) {
        case "monday":
            return MONDAY;
        case "tuesday":
            return TUESDAY;
        case "wednesday":
            return WEDNESDAY;
        case "thursday":
            return THURSDAY;
        case "friday":
            return FRIDAY;
        case "saturday":
            return SATURDAY;
        case "sunday":
            return SUNDAY;
        default:
            // This should not occur because input is validated before calling the method.
            throw new IllegalArgumentException("Invalid day string: " + dayInStr);
        }
    }

    @Override
    public String toString() {
        return name();
    }
}
