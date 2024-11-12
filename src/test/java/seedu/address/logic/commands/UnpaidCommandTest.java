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
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Frequency;
import seedu.address.model.person.LastPaidDate;
import seedu.address.model.person.Person;

public class UnpaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMarkUnpaid = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        descriptor.setFrequencyToZero();
        UnpaidCommand unpaidCommand = new UnpaidCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnpaidCommand.MESSAGE_UNPAID_PERSON_SUCCESS,
                Messages.format(personToMarkUnpaid));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = new Person(personToMarkUnpaid.getName(), personToMarkUnpaid.getPhone(),
                personToMarkUnpaid.getEmail(), personToMarkUnpaid.getAddress(),
                personToMarkUnpaid.getBirthday(), personToMarkUnpaid.getTags(),
                false, personToMarkUnpaid.getLastPaidDate(),
                personToMarkUnpaid.getFrequency(), personToMarkUnpaid.getProfilePicFilePath());

        expectedModel.setPerson(personToMarkUnpaid, editedPerson);

        assertCommandSuccess(unpaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        UnpaidCommand unpaidCommand = new UnpaidCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(unpaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMarkUnpaid = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        UnpaidCommand unpaidCommand = new UnpaidCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UnpaidCommand.MESSAGE_UNPAID_PERSON_SUCCESS,
                Messages.format(personToMarkUnpaid));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person editedPerson = new Person(personToMarkUnpaid.getName(), personToMarkUnpaid.getPhone(),
                personToMarkUnpaid.getEmail(), personToMarkUnpaid.getAddress(),
                personToMarkUnpaid.getBirthday(), personToMarkUnpaid.getTags(),
                false, personToMarkUnpaid.getLastPaidDate(),
                personToMarkUnpaid.getFrequency(), personToMarkUnpaid.getProfilePicFilePath());
        expectedModel.setPerson(personToMarkUnpaid, editedPerson);

        assertCommandSuccess(unpaidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        UnpaidCommand unpaidCommand = new UnpaidCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(unpaidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        UnpaidCommand unpaidFirstCommand = new UnpaidCommand(INDEX_FIRST_PERSON, descriptor);
        UnpaidCommand unpaidSecondCommand = new UnpaidCommand(INDEX_SECOND_PERSON, descriptor);

        // same object -> returns true
        assertTrue(unpaidFirstCommand.equals(unpaidFirstCommand));

        // same values -> returns true
        UnpaidCommand unpaidFirstCommandCopy = new UnpaidCommand(INDEX_FIRST_PERSON, descriptor);
        assertTrue(unpaidFirstCommand.equals(unpaidFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpaidFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpaidFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpaidFirstCommand.equals(unpaidSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        UnpaidCommand unpaidCommand = new UnpaidCommand(targetIndex, descriptor);
        String expected = UnpaidCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, unpaidCommand.toString());
    }

    @Test
    public void unpaidPersonDescriptor_equals() {
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // different types -> returns false
        assertFalse(descriptor.equals(1));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        assertFalse(descriptor.equals(new UnpaidCommand.UnpaidPersonDescriptor()));
    }

    @Test
    public void unpaidPersonDescriptor_toString() {
        UnpaidCommand.UnpaidPersonDescriptor descriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        descriptor.setHasNotPaid();
        LastPaidDate lastPaidDate = new LastPaidDate("01 01 0000");
        String expected = new ToStringBuilder(descriptor)
                .add("hasNotPaid", false)
                .add("frequency", new Frequency("0"))
                .add("lastPaidDate", lastPaidDate)
                .toString();
        assertEquals(expected, descriptor.toString());
    }

    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
