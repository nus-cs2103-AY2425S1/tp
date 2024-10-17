package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Tutor;
import seedu.address.testutil.TutorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTutorCommand}.
 */
public class AddTutorCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Tutor validTutor = new TutorBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validTutor);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddTutorCommand(validTutor), model, commandHistory,
                String.format(AddTutorCommand.MESSAGE_SUCCESS, Messages.format(validTutor)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Tutor tutorInList = (Tutor) model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddTutorCommand(tutorInList), model, commandHistory,
                AddTutorCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
