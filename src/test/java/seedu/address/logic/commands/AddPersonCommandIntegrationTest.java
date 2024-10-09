package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPersonCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @BeforeEach
    public void resetCounter() throws Exception {
        // Use reflection to reset the personIDCounter
        Field personIdCounterField = Person.class.getDeclaredField("personIDCounter");

        personIdCounterField.setAccessible(true);

        // Reset counter to 0 before each test
        personIdCounterField.setInt(null, 0);
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        // Verify personId increments correctly
        assertEquals(1, validPerson.getPersonId());

        assertCommandSuccess(new AddPersonCommand(validPerson), model,
                String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.formatPerson(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddPersonCommand(personInList), model,
                AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
