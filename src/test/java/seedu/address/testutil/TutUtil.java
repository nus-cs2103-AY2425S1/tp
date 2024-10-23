package seedu.address.testutil;

import java.util.Calendar;

import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutDate;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * A utility class for test cases relating to Tutorial.
 */
public class TutUtil {
    public static final String TUT_NAME = "CS2103T";
    public static final String TUT_01 = "T1001";
    public static final TutorialId TUTORIAL_ID = TutorialId.of(TUT_01);
    public static final Tutorial TUTORIAL_SAMPLE = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
    public static final TutDate TUT_DATE;

    public static final Tutorial NONE = Tutorial.none();
    // Creates new TUT_DATE instance, with 14/10/2024 as date
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.OCTOBER, 14);
        TUT_DATE = new TutDate(calendar.getTime());
    }
}
