package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;
import seedu.address.model.person.TransactionDateComparator;

public class AddTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_filteredList_success() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, transactionToAdd);

        List<Transaction> transactions = new ArrayList<>(personToEdit.getTransactions());
        transactions.add(transactionToAdd);
        transactions.sort(new TransactionDateComparator());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getCompany(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), transactions);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(transactionToAdd), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredList_success() {

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, transactionToAdd);

        List<Transaction> transactions = new ArrayList<>(personToEdit.getTransactions());
        transactions.add(transactionToAdd);
        transactions.sort(new TransactionDateComparator());

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getCompany(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), transactions);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(transactionToAdd), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(outOfBoundIndex, transactionToAdd);

        assertCommandFailure(addTransactionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {

        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(outOfBoundIndex, transactionToAdd);

        assertCommandFailure(addTransactionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_transactionListView_throwsCommandException() {
        Transaction transactionToAdd = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));
        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_PERSON, transactionToAdd);
        model.setViewTransactions(true);
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_PERSON_LIST, "addt");
        assertCommandFailure(addTransactionCommand, model, expectedMessage);
    }


    @Test
    public void equals() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Transaction t1 = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));
        Transaction t2 = new Transaction("sell raw materials", 200,
                "Company XYZ", LocalDate.parse("2024-10-16", DateTimeUtil.DEFAULT_DATE_PARSER));

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
