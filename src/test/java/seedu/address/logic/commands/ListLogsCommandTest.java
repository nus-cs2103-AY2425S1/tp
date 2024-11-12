package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

public class ListLogsCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidPerson_throwsCommandException() {
        Person deletedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(deletedPerson);

        ListLogsCommand listLogsCommand = new ListLogsCommand(deletedPerson.getIdentityNumber());
        String expectedMessage = String.format(ListLogsCommand.MESSAGE_PERSON_NOT_FOUND,
                deletedPerson.getIdentityNumber());

        assertCommandFailure(listLogsCommand, model, expectedMessage);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        IdentityNumber id = AMY.getIdentityNumber();
        ListLogsCommand listLogsCommand = new ListLogsCommand(id);

        assertEquals(listLogsCommand, listLogsCommand);
    }

    @Test
    public void equals_differentIdentityNumber_returnsFalse() {
        ListLogsCommand listLogsCommandAmy = new ListLogsCommand(AMY.getIdentityNumber());
        ListLogsCommand listLogsCommandBob = new ListLogsCommand(BOB.getIdentityNumber());

        assertNotEquals(listLogsCommandAmy, listLogsCommandBob);
    }

    @Test
    public void equals_sameIdentityNumber_returnsTrue() {
        IdentityNumber id = AMY.getIdentityNumber();
        ListLogsCommand listLogsCommand1 = new ListLogsCommand(id);
        ListLogsCommand listLogsCommand2 = new ListLogsCommand(id);

        assertEquals(listLogsCommand1, listLogsCommand2);
    }
}
