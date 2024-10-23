package seedu.address.testutil;

import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

/**
 * A utility class containing a list of {@code Tutorials} objects to be used in tests.
 */
public class TypicalTutorials {

    public static final Tutorial TUTORIAL1 = Tutorial.of(new TutName("TutorialOne"), TutorialId.of("T1000"));
    public static final Tutorial TUTORIAL2 = Tutorial.of(new TutName("TutorialTwo"), TutorialId.of("T1001"));
    public static final Tutorial TUTORIAL3 = Tutorial.of(new TutName("TutorialThree"), TutorialId.of("T1002"));

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
