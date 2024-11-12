package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.ConfirmationHandler;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    // fix this
    @Test
    public void executeDuplicatePerson_userConfirm_success() {
        ConfirmationHandler mockHandler = person -> true;

        Person personInList = model.getAddressBook().getPersonList().get(0);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(personInList);
        assertCommandSuccess(new AddCommand(personInList, mockHandler), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(personInList)),
                expectedModel);
    }

    // fix this also
    @Test
    public void executeDuplicatePerson_userCancel_throwsCommandException() {
        ConfirmationHandler mockHandler = person -> false;

        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList, mockHandler), model,
                Messages.MESSAGE_USER_CANCEL);
    }

}
