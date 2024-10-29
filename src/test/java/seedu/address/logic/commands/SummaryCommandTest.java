package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateTimeUtil.DEFAULT_DATE_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SummaryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        // same object -> returns true
        SummaryCommand summaryCommand = new SummaryCommand(YearMonth.parse("2020-11"),
                YearMonth.parse("2022-01"));
        assertTrue(summaryCommand.equals(summaryCommand));

        // same values -> returns true
        SummaryCommand summaryCommandCopy = new SummaryCommand(YearMonth.parse("2020-11"),
                YearMonth.parse("2022-01"));
        assertTrue(summaryCommand.equals(summaryCommandCopy));

        // different types -> returns false
        assertFalse(summaryCommand.equals(1));

        // null -> returns false
        assertFalse(summaryCommand.equals(null));
        // different start month -> returns false
        SummaryCommand summaryCommandDifferentStartMonth = new SummaryCommand(YearMonth.parse("2020-12"),
                YearMonth.parse("2022-01"));
        assertFalse(summaryCommand.equals(summaryCommandDifferentStartMonth));
        // different end month -> returns false
        SummaryCommand summaryCommandDifferentEndMonth = new SummaryCommand(YearMonth.parse("2020-11"),
                YearMonth.parse("2022-02"));
        assertFalse(summaryCommand.equals(summaryCommandDifferentEndMonth));
        // different start and end month -> returns false
        SummaryCommand summaryCommandDifferentStartAndEndMonth = new SummaryCommand(YearMonth.parse("2020-12"),
                YearMonth.parse("2022-02"));
        assertFalse(summaryCommand.equals(summaryCommandDifferentStartAndEndMonth));
    }
    @Test
    public void execute_validMonthRange_success() {
        // one month
        SummaryCommand summaryCommand = new SummaryCommand(YearMonth.parse("2024-08"),
                YearMonth.parse("2024-08"));
        showPersonAtIndex(expectedModel, Index.fromOneBased(3));
        expectedModel.updateTransactionList(CARL.getTransactions());
        expectedModel.updateTransactionList(CARL.getTransactions().stream().filter(transaction ->
                transaction.getDate().getYear() == 2024 && transaction.getDate().getMonthValue() == 8)
                .collect(java.util.stream.Collectors.toList()));
        showPersonAtIndex(model, Index.fromOneBased(3));
        model.updateTransactionList(CARL.getTransactions());
        assertCommandSuccess(summaryCommand, model, String.format(SummaryCommand.MESSAGE_SUCCESS,
                        YearMonth.parse("2024-08").atDay(1).format(DEFAULT_DATE_FORMATTER),
                        YearMonth.parse("2024-08").atEndOfMonth().format(DEFAULT_DATE_FORMATTER), -1000.0),
                expectedModel);
        // multiple months
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SummaryCommand summaryCommandMultipleMonths = new SummaryCommand(YearMonth.parse("2024-08"),
                YearMonth.parse("2024-11"));
        showPersonAtIndex(expectedModel, Index.fromOneBased(3));
        expectedModel.updateTransactionList(CARL.getTransactions());
        expectedModel.updateTransactionList(CARL.getTransactions().stream().filter(transaction -> (
                transaction.getDate().getYear() == 2024 && transaction.getDate().getMonthValue() >= 8
                        && transaction.getDate().getMonthValue() <= 11))
                .collect(java.util.stream.Collectors.toList()));
        showPersonAtIndex(model, Index.fromOneBased(3));
        model.updateTransactionList(CARL.getTransactions());
        assertCommandSuccess(summaryCommandMultipleMonths, model, String.format(SummaryCommand.MESSAGE_SUCCESS,
                        YearMonth.parse("2024-08").atDay(1).format(DEFAULT_DATE_FORMATTER),
                        YearMonth.parse("2024-11").atEndOfMonth().format(DEFAULT_DATE_FORMATTER), -1100.0),
                expectedModel);
    }

    @Test
    public void execute_personListView_throwsCommandException() {
        SummaryCommand summaryCommand = new SummaryCommand(YearMonth.parse("2024-08"),
                YearMonth.parse("2024-08"));
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, "summary");
        assertCommandFailure(summaryCommand, model, expectedMessage);
    }
}
