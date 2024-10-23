package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.EnumUtil.inEnum;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Represents a LessonTime in the address book.
 * Guarantees: immutable; value is valid as declared in {@link #isValidLessonTime(String)}
 */
public class LessonTime {
    enum Days {
        MON, TUE, WED, THU, FRI, SAT, SUN
    }

    public static final String MESSAGE_CONSTRAINTS = "Lesson time should be in format DAY-STARTTIME-ENDTIME, where: "
            + "\n" + "DAY is in " + Arrays.toString(Days.values()) + ";\n"
            + "STARTTIME and ENDTIME are in the format HH:MM;\n"
            + "ENDTIME is later than STARTTIME.";
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final String lessonDay;
    private final LocalTime lessonStart;
    private final LocalTime lessonEnd;

    /**
     * Constructs a {@code LessonTime}
     * @param lessonTime A valid lesson time.
     */
    public LessonTime(String lessonTime) {
        requireNonNull(lessonTime);
        checkArgument(isValidLessonTime(lessonTime), MESSAGE_CONSTRAINTS);

        String[] segments = lessonTime.split("-");
        this.lessonDay = segments[0];
        this.lessonStart = LocalTime.parse(segments[1], timeFormatter);
        this.lessonEnd = LocalTime.parse(segments[2], timeFormatter);
    }

    public String getLessonDay() {
        return lessonDay;
    }

    public LocalTime getLessonStart() {
        return lessonStart;
    }

    public LocalTime getLessonEnd() {
        return lessonEnd;
    }

    /**
     * Returns true if given string is a valid lesson time.
     */
    public static boolean isValidLessonTime(String test) {
        String[] segments = test.split("-");
        if (segments.length != 3) {
            // make sure there are only 3 parts
            return false;
        } else if (!inEnum(segments[0], Days.class)) {
            // make sure first part is a day
            return false;
        }

        boolean isValid;
        try {
            LocalTime s = LocalTime.parse(segments[1], timeFormatter);
            LocalTime e = LocalTime.parse(segments[2], timeFormatter);
            isValid = e.isAfter(s);
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Returns true if this lesson time overlaps with other lesson time.
     */
    public boolean canMerge(LessonTime other) {
        return this.equals(other)
                || lessonDay.equals(other.getLessonDay())
                && (lessonEnd.equals(other.getLessonStart())
                || (!lessonEnd.isBefore(other.getLessonStart()) && !lessonStart.isAfter(other.lessonEnd))
                || lessonStart.equals(other.lessonEnd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonTime)) {
            return false;
        }

        LessonTime otherLessonTime = (LessonTime) other;
        return lessonDay.equals(otherLessonTime.getLessonDay())
                && lessonStart.equals(otherLessonTime.getLessonStart())
                && lessonEnd.equals(otherLessonTime.getLessonEnd());
    }

    @Override
    public int hashCode() {
        return lessonDay.hashCode() * lessonStart.hashCode() * lessonEnd.hashCode();
    }

    @Override
    public String toString() {
        return lessonDay + "-" + lessonStart.format(timeFormatter) + "-" + lessonEnd.format(timeFormatter);
    }
}
