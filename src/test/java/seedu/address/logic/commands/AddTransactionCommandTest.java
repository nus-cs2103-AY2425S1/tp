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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;

public class AddTransactionCommandTest {

    private Model model;

    @Test
    public void execute_validInput_addSuccessful() throws Exception {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Transaction t = new Transaction("buy raw materials", -100,
                "Company ABC", "15/10/2024");
        AddTransactionCommand addTCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, t);

        CommandResult commandResult = addTCommand.execute(model);

        assertEquals(String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS, Messages.format(t)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(t), model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getTransactions());
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", "15/10/2024");
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, transactionToAdd);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(transactionToAdd));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToEdit = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionToAdd);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getCompany(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), transactions);
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", "15/10/2024");

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(outOfBoundIndex, transactionToAdd);

        assertCommandFailure(addTransactionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Transaction t1 = new Transaction("buy raw materials", -100,
                "Company ABC", "15/10/2024");
        Transaction t2 = new Transaction("sell raw materials", 200,
                "Company XYZ", "16/10/2024");

        AddTransactionCommand addT1Command = new AddTransactionCommand(INDEX_FIRST_PERSON, t1);
        AddTransactionCommand addT2Command = new AddTransactionCommand(INDEX_FIRST_PERSON, t2);

        // same object -> returns true
        assertTrue(addT1Command.equals(addT1Command));

        // same values -> returns true
        AddTransactionCommand addT1CommandCopy = new AddTransactionCommand(INDEX_FIRST_PERSON, t1);
        assertTrue(addT1Command.equals(addT1CommandCopy));

        // different types -> returns false
        assertFalse(addT1Command.equals(1));

        // null -> returns false
        assertFalse(addT1Command.equals(null));

        // different person -> returns false
        assertFalse(addT1Command.equals(addT2Command));
    }

}
