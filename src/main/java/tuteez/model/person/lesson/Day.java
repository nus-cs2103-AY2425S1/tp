package tuteez.model.person.lesson;

/**
 * Enum to represent the days of the week.
 */
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

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
            // Should never reach here because we always check for valid date before calling function
            throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return name();
    }
}
