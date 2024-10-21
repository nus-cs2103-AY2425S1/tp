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
import seedu.address.model.person.Tutee;
import seedu.address.testutil.TuteeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTuteeCommand}.
 */
public class AddTuteeCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();


    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Tutee validTutee = new TuteeBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validTutee);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddTuteeCommand(validTutee), model, commandHistory,
                String.format(AddTuteeCommand.MESSAGE_SUCCESS, Messages.format(validTutee)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Tutee tuteeInList = (Tutee) model.getAddressBook().getPersonList().get(4);
        assertCommandFailure(new AddTuteeCommand(tuteeInList), model, commandHistory,
                AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
