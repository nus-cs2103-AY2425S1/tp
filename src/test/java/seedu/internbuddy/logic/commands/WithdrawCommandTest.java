package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.WithdrawCommand.getNewStatus;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Status;

public class WithdrawCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void getNewStatusTest() {
        Company company = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        List<Application> applicationList = company.getApplications();
        assertEquals(new Status("CLOSED"), getNewStatus(company, applicationList));
    }

    @Test
    public void getNewStatus_nonEmptyApplicationList() {
        Company company = model.getFilteredCompanyList().get(INDEX_SECOND_COMPANY.getZeroBased());
        List<Application> applicationList = company.getApplications();
        assertEquals(company.getStatus(), getNewStatus(company, applicationList));
    }

    @Test
    public void execute_withdrawApplicationSuccess() throws Exception {
        Company company = model.getFilteredCompanyList().get(INDEX_SECOND_COMPANY.getZeroBased());
        List<Application> applicationList = new ArrayList<>(company.getApplications());
        Application applicationToWithdraw = applicationList.get(0);
        WithdrawCommand withdrawCommand = new WithdrawCommand(INDEX_SECOND_COMPANY, INDEX_FIRST_COMPANY);

        CommandResult result = withdrawCommand.execute(model);

        assertEquals(String.format(WithdrawCommand.MESSAGE_WITHDRAW_APPLICATION_SUCCESS,
            applicationToWithdraw), result.getFeedbackToUser());
    }

    @Test
    public void execute_withdrawApplicationInvalidCompanyIndexThrowsCommandException() {
        WithdrawCommand withdrawCommand = new WithdrawCommand(
                Index.fromOneBased(model.getFilteredCompanyList().size() + 1), INDEX_FIRST_COMPANY);

        try {
            withdrawCommand.execute(model);
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void execute_withdrawApplication_invalidApplicationIndexThrowsCommandException() {
        WithdrawCommand withdrawCommand = new WithdrawCommand(INDEX_FIRST_COMPANY,
            Index.fromOneBased(model.getFilteredCompanyList().get(0).getApplications().size() + 1));

        try {
            withdrawCommand.execute(model);
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void equals() {
        final WithdrawCommand standardCommand = new WithdrawCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_APPLICATION);

        // same values -> return true
        WithdrawCommand commandWithSameValue = new WithdrawCommand(
                Index.fromOneBased(1), Index.fromOneBased(1));
        assertTrue(standardCommand.equals(commandWithSameValue));

        // same object -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different values -> false
        WithdrawCommand differentCompanyIndex = new WithdrawCommand(INDEX_SECOND_COMPANY, INDEX_FIRST_APPLICATION);
        WithdrawCommand differentApplicationIndex = new WithdrawCommand(INDEX_FIRST_COMPANY, INDEX_SECOND_APPLICATION);
        WithdrawCommand differntCommandValue = new WithdrawCommand(INDEX_SECOND_COMPANY, INDEX_SECOND_APPLICATION);
        assertFalse(standardCommand.equals(differentCompanyIndex));
        assertFalse(standardCommand.equals(differentApplicationIndex));
        assertFalse(standardCommand.equals(differntCommandValue));
    }
}
