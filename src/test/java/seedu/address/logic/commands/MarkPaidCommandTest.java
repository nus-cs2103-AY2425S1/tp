package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Fees;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkPaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMarkPaid = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person markedPerson = new PersonBuilder(personToMarkPaid).withPayment("200").build();
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_SECOND_PERSON, new Fees("0"));

        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_MARKED_PAID_SUCCESS,
                markedPerson.getFullName(), markedPerson.getPayment().toString());

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(1), markedPerson);
        assertCommandSuccess(markPaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(outOfBoundsIndex, new Fees("200"));

        assertCommandFailure(markPaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);

        Person personToMarkPaid = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person markedPerson = new PersonBuilder(personToMarkPaid).withPayment("200").build();
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, new Fees("0"));

        String expectedMessage = String.format(MarkPaidCommand.MESSAGE_MARKED_PAID_SUCCESS,
                markedPerson.getFullName(), markedPerson.getPayment().toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), markedPerson);

        assertCommandSuccess(markPaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkPaidCommand markPaidCommand = new MarkPaidCommand(outOfBoundIndex, new Fees("200"));

        assertCommandFailure(markPaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkPaidCommand markPaidFirstCommand = new MarkPaidCommand(INDEX_FIRST_PERSON, new Fees("200"));
        MarkPaidCommand markPaidSecondCommand = new MarkPaidCommand(INDEX_SECOND_PERSON, new Fees("200"));

        assertTrue(markPaidFirstCommand.equals(markPaidFirstCommand));

        MarkPaidCommand markPaidFirstCommandCopy = new MarkPaidCommand(INDEX_FIRST_PERSON, new Fees("200"));
        assertTrue(markPaidFirstCommand.equals(markPaidFirstCommandCopy));

        assertFalse(markPaidFirstCommand.equals(1));

        assertFalse(markPaidFirstCommand.equals(null));

        assertFalse(markPaidFirstCommand.equals(markPaidSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkPaidCommand markPaidCommand = new MarkPaidCommand(targetIndex, new Fees("200"));
        String expected = MarkPaidCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, markPaidCommand.toString());
    }
}
