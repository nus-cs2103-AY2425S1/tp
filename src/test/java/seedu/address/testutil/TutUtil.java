package seedu.address.testutil;

import java.util.Calendar;

import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.TutDate;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * A utility class for test cases relating to Tutorial.
 */
public class TutUtil {
    public static final String TUT_NAME = "CS2103T";
    public static final String TUT_01 = "1001";
    public static final TutorialClass TUTORIAL_CLASS = new TutorialClass(TUT_01);
    public static final Tutorial TUTORIAL_SAMPLE = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_CLASS);
    public static final TutDate TUT_DATE;

    public static final Tutorial NONE = Tutorial.none();
    // Creates new TUT_DATE instance, with 14/10/2024 as date
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.OCTOBER, 14);
        TUT_DATE = new TutDate(calendar.getTime());
    }
}
