package seedu.academyassist.logic.commands;

import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DetailCommand}.
 */
public class DetailCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
}
