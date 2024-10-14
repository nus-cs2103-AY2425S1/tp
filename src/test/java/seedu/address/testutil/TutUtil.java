package seedu.address.testutil;

import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.Tut;

/**
 * A utility class for test cases relating to Tutorial.
 */
public class TutUtil {
    public static final String TUT_NAME = "CS2103T";
    public static final String TUT_01 = "1001";
    public static final TutorialClass TUTORIAL_CLASS = new TutorialClass(TUT_01);
    public static final Tut TUT_SAMPLE = new Tut(TUT_NAME, TUTORIAL_CLASS);
}
