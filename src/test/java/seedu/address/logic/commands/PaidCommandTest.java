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
import seedu.address.model.person.Person;

public class PaidCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMarkPaid = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        PaidCommand paidCommand = new PaidCommand(INDEX_FIRST_PERSON, descriptor);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person paidPerson = new Person(personToMarkPaid.getName(), personToMarkPaid.getPhone(),
                personToMarkPaid.getEmail(), personToMarkPaid.getAddress(),
                personToMarkPaid.getBirthday(), personToMarkPaid.getTags(), true, personToMarkPaid.getFrequency());

        String expectedMessage = String.format(PaidCommand.MESSAGE_PAID_PERSON_SUCCESS,
                Messages.format(paidPerson));

        expectedModel.setPerson(personToMarkPaid, paidPerson);

        assertCommandSuccess(paidCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        PaidCommand paidCommand = new PaidCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(paidCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        PaidCommand paidFirstCommand = new PaidCommand(INDEX_FIRST_PERSON, descriptor);
        PaidCommand paidSecondCommand = new PaidCommand(INDEX_SECOND_PERSON, descriptor);

        // same object -> returns true
        assertTrue(paidFirstCommand.equals(paidFirstCommand));

        // same values -> returns true
        PaidCommand paidFirstCommandCopy = new PaidCommand(INDEX_FIRST_PERSON, descriptor);
        assertTrue(paidFirstCommand.equals(paidFirstCommandCopy));

        // different types -> returns false
        assertFalse(paidFirstCommand.equals(1));

        // null -> returns false
        assertFalse(paidFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(paidFirstCommand.equals(paidSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        PaidCommand paidCommand = new PaidCommand(targetIndex, descriptor);
        String expected = PaidCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, paidCommand.toString());
    }

    @Test
    public void paidPersonDescriptor_toString() {
        PaidCommand.PaidPersonDescriptor descriptor = new PaidCommand.PaidPersonDescriptor();
        descriptor.setHasPaid();
        Frequency frequency = new Frequency("3");
        descriptor.setFrequency(frequency);

        String expected = new ToStringBuilder(descriptor)
                .add("hasPaid", true)
                .add("frequency", frequency)
                .toString();

        assertEquals(expected, descriptor.toString());
    }

    @Test
    public void paidPersonDescriptor_equals() {
        PaidCommand.PaidPersonDescriptor descriptor1 = new PaidCommand.PaidPersonDescriptor();
        descriptor1.setHasPaid();
        Frequency frequency = new Frequency("3");
        descriptor1.setFrequency(frequency);

        // same object -> returns true
        assertTrue(descriptor1.equals(descriptor1));

        // same values -> returns true
        PaidCommand.PaidPersonDescriptor descriptor2 = new PaidCommand.PaidPersonDescriptor();
        descriptor2.setHasPaid();
        descriptor2.setFrequency(frequency);
        assertTrue(descriptor1.equals(descriptor2));

        // different types -> returns false
        assertFalse(descriptor1.equals(1));

        // null -> returns false
        assertFalse(descriptor1.equals(null));

        // different values -> returns false
        PaidCommand.PaidPersonDescriptor descriptor3 = new PaidCommand.PaidPersonDescriptor();
        descriptor3.setHasPaid();
        descriptor3.setFrequency(new Frequency("6"));
        assertFalse(descriptor1.equals(descriptor3));
    }

    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
