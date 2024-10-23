package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListLogsCommand}.
 */
public class ListLogsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPerson_throwsCommandException() {
        Person deletedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(deletedPerson);

        ListLogsCommand listLogsCommand = new ListLogsCommand(deletedPerson.getIdentityNumber());

        String expectedMessage = String.format(ListLogsCommand.MESSAGE_PERSON_NOT_FOUND,
                deletedPerson.getIdentityNumber());

        assertCommandFailure(listLogsCommand, model, expectedMessage);
    }
}
