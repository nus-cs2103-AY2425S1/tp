package seedu.edulog.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson SEC_4_MATH = new LessonBuilder()
            .withDescription("Sec 4 Math")
            .withDayOfWeek("Friday")
            .withStartTime("1930")
            .withEndTime("2030").build();

    public static final Lesson SEC_3_MATH = new LessonBuilder()
            .withDescription("Sec 3 Math")
            .withDayOfWeek("tue")
            .withStartTime("0030")
            .withEndTime("0330").build();

    public static final Lesson SEC_2_MATH = new LessonBuilder().withDescription("Sec 2 Math")
            .withDayOfWeek("wednesday")
            .withStartTime("2200")
            .withEndTime("0030").build();

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical lessons.
     */
    public static EdulogCalendar getTypicalEdulogCalendar() {
        EdulogCalendar ec = new EdulogCalendar();
        for (Lesson lesson : getTypicalLessons()) {
            ec.addLesson(lesson);
        }
        return ec;
    }

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(SEC_4_MATH, SEC_3_MATH, SEC_2_MATH));
    }
}
