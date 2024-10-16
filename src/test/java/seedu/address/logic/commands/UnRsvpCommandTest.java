package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UnRsvpCommand.
 */
public class UnRsvpCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Test for valid index
     */
    @Test
    public void execute_rsvpValidIndexUnfilteredList_success() {
        Person personToUnRsvp = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnRsvpCommand unrsvpCommand = new UnRsvpCommand(INDEX_FIRST_PERSON);

        Person updatedPerson = new Person(personToUnRsvp.getName(), personToUnRsvp.getPhone(),
                personToUnRsvp.getEmail(), true, personToUnRsvp.getTags());

        String expectedMessage = String.format(UnRsvpCommand.MESSAGE_UNRSVP_SUCCESS + updatedPerson.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUnRsvp, updatedPerson);

        assertCommandSuccess(unrsvpCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for invalid index
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnRsvpCommand unrsvpCommand = new UnRsvpCommand(outOfBoundIndex);

        assertCommandFailure(unrsvpCommand, model, String.format(UnRsvpCommand.MESSAGE_INVALID_INDEX + model.getFilteredPersonList().size() + ")"));
    }

    @Test
    public void equals() {
        UnRsvpCommand unrsvpFirstCommand = new UnRsvpCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(unrsvpFirstCommand.equals(unrsvpFirstCommand));

        assertFalse(unrsvpFirstCommand.equals(null));
    }
}
