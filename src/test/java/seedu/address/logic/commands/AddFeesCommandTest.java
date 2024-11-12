package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddFeesCommand.MESSAGE_MARKED_PAID_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Fees;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class AddFeesCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnpaidStudent_addsFees() throws CommandException {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Payment initialPayment = personToUpdate.getPayment();

        Fees feesToAdd = new Fees("200");
        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, feesToAdd);

        Person updatedPerson = new PersonBuilder(personToUpdate)
                .withPayment(Integer.toString(Integer.parseInt(initialPayment.overdueAmount) + 200))
                .build();

        String expectedMessage = String.format(MESSAGE_MARKED_PAID_SUCCESS,
                updatedPerson.getFullName(), updatedPerson.getPayment());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(addFeesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddFeesCommand addFeesCommand = new AddFeesCommand(outOfBoundsIndex, new Fees("200"));

        assertCommandFailure(addFeesCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addZeroFees_noChangeToPayment() throws CommandException {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Fees zeroFees = new Fees("0");

        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, zeroFees);

        CommandResult result = addFeesCommand.execute(model);

        String expectedMessage = String.format(MESSAGE_MARKED_PAID_SUCCESS,
                personToUpdate.getFullName(), personToUpdate.getPayment());

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(personToUpdate.getPayment(),
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getPayment());
    }

    @Test
    public void execute_negativeFees_throwsNumberFormatException() {
        assertThrows(AssertionError.class, () -> new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("-200")));
    }

    @Test
    public void execute_nonNumericFees_throwsNumberFormatException() {
        assertThrows(AssertionError.class, () -> new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("abc")));
    }

    @Test
    public void execute_duplicatePersonCheck() throws CommandException {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Fees feesToAdd = new Fees("200");
        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, feesToAdd);

        Person updatedPerson = new PersonBuilder(personToUpdate)
                .withPayment(Integer.toString(Integer.parseInt(personToUpdate.getPayment().overdueAmount) + 200))
                .build();

        addFeesCommand.execute(model);

        assertEquals(updatedPerson, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
    }

    @Test
    public void execute_sameFeesAdded_multipleTimes() throws CommandException {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Fees feesToAdd = new Fees("200");
        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, feesToAdd);

        addFeesCommand.execute(model);
        addFeesCommand.execute(model);

        int expectedAmount = Integer.parseInt(personToUpdate.getPayment().overdueAmount) + 400; // 200 added twice
        Payment updatedPayment = new Payment(String.valueOf(expectedAmount));

        assertEquals(updatedPayment, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getPayment());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("200"));
        assertEquals(addFeesCommand, addFeesCommand);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        AddFeesCommand addFeesCommand1 = new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("200"));
        AddFeesCommand addFeesCommand2 = new AddFeesCommand(Index.fromOneBased(2), new Fees("200"));

        assertFalse(addFeesCommand1.equals(addFeesCommand2));
    }

    @Test
    public void equals_differentFees_returnsFalse() {
        AddFeesCommand addFeesCommand1 = new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("200"));
        AddFeesCommand addFeesCommand2 = new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("300"));

        assertFalse(addFeesCommand1.equals(addFeesCommand2));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        AddFeesCommand addFeesCommand = new AddFeesCommand(INDEX_FIRST_PERSON, new Fees("200"));
        assertFalse(addFeesCommand.equals(new Object()));
    }

}

