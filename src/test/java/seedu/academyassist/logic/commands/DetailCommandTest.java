package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.testutil.PersonBuilder;

public class DetailCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    @Test
    public void execute_validStudentId_success() throws Exception {
        Person personToView = new PersonBuilder("S10001").build();
        DetailCommand detailCommand = new DetailCommand(personToView.getStudentId());
        CommandResult commandResult = detailCommand.execute(model);
        String expectedMessage = String.format(commandResult.getFeedbackToUser());

        assertCommandSuccess(detailCommand, model, expectedMessage, model);
    }
}
