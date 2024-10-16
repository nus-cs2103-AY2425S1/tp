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
 * Contains integration tests (interaction with the Model) and unit tests for RsvpCommand.
 */
public class RsvpCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Test for valid index
     */
    @Test
    public void execute_rsvpValidIndexUnfilteredList_success() {
        Person personToRsvp = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RsvpCommand rsvpCommand = new RsvpCommand(INDEX_FIRST_PERSON);

        Person updatedPerson = new Person(personToRsvp.getName(), personToRsvp.getPhone(),
                personToRsvp.getEmail(), true, personToRsvp.getTags());

        String expectedMessage = String.format(RsvpCommand.MESSAGE_RSVP_SUCCESS + updatedPerson.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToRsvp, updatedPerson);

        assertCommandSuccess(rsvpCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for invalid index
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RsvpCommand rsvpCommand = new RsvpCommand(outOfBoundIndex);

        assertCommandFailure(rsvpCommand, model, String.format(RsvpCommand.MESSAGE_INVALID_INDEX +
                model.getFilteredPersonList().size() + ")"));
    }

    @Test
    public void equals() {
        RsvpCommand rsvpFirstCommand = new RsvpCommand(INDEX_FIRST_PERSON);

        // same object -> returns true
        assertTrue(rsvpFirstCommand.equals(rsvpFirstCommand));

        assertFalse(rsvpFirstCommand.equals(null));
    }

}
