package seedu.address.testutil;

import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

/**
 * A utility class containing a list of {@code Tutorials} objects to be used in tests.
 */
public class TypicalTutorials {

    public static final Tutorial TUTORIAL1 = new Tutorial(new TutName("TutorialOne"), new TutorialClass("1000"));
    public static final Tutorial TUTORIAL2 = new Tutorial(new TutName("TutorialTwo"), new TutorialClass("1001"));
    public static final Tutorial TUTORIAL3 = new Tutorial(new TutName("TutorialThree"), new TutorialClass("1002"));

    /**
     * Returns an {@code TutorialList} with typical tutorials.
     */
    public static TutorialList getTypicalTutorialList() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL1);
        tutorialList.addTutorial(TUTORIAL2);
        tutorialList.addTutorial(TUTORIAL3);
        return tutorialList;
    }

}
