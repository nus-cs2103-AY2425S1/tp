package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;
import seedu.address.model.company.Remark;
import seedu.address.testutil.CompanyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() throws Exception {
        Company companyToEdit = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Remark newRemark = new Remark(VALID_REMARK);

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_COMPANY, newRemark);
        Company editedCompany = new CompanyBuilder(companyToEdit).withRemark(VALID_REMARK).build();

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(companyToEdit, editedCompany);

        CommandResult result = remarkCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    @Disabled
    public void execute_removeRemarkUnfilteredList_success() throws Exception {
        Company companyToEdit = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Remark emptyRemark = new Remark(""); // Removing remark

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_COMPANY, emptyRemark);
        Company editedCompany = new CompanyBuilder(companyToEdit).withRemark("").build();

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(companyToEdit, editedCompany);

        CommandResult result = remarkCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK));

        assertThrows(CommandException.class, MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX, ()
                -> remarkCommand.execute(model));
    }

    @Test
    public void execute_addRemarkFilteredList_success() throws Exception {
        // Filter the list to show only one company
        CommandTestUtil.showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyToEdit = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Remark newRemark = new Remark(VALID_REMARK);

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_COMPANY, newRemark);
        Company editedCompany = new CompanyBuilder(companyToEdit).withRemark(VALID_REMARK).build();

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedCompany);
        CommandTestUtil.showCompanyAtIndex(expectedModel, INDEX_FIRST_COMPANY);

        CommandResult result = remarkCommand.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        // Filter the list to show only one company
        CommandTestUtil.showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCompanyList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK));

        assertThrows(CommandException.class, MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX, ()
                -> remarkCommand.execute(model));
    }

    @Test
    public void equals() {
        Remark remark = new Remark(VALID_REMARK);
        Remark differentRemark = new Remark("Different remark");

        RemarkCommand remarkFirstCommand = new RemarkCommand(INDEX_FIRST_COMPANY, remark);
        RemarkCommand remarkSecondCommand = new RemarkCommand(INDEX_SECOND_COMPANY, remark);
        RemarkCommand remarkDifferentRemarkCommand = new RemarkCommand(INDEX_FIRST_COMPANY, differentRemark);

        // same object -> returns true
        assertEquals(remarkFirstCommand, remarkFirstCommand);

        // same values -> returns true
        RemarkCommand remarkFirstCommandCopy = new RemarkCommand(INDEX_FIRST_COMPANY, remark);
        assertEquals(remarkFirstCommand, remarkFirstCommandCopy);

        // different index -> returns false
        assertTrue(!remarkFirstCommand.equals(remarkSecondCommand));

        // different remark -> returns false
        assertTrue(!remarkFirstCommand.equals(remarkDifferentRemarkCommand));

        // null -> returns false
        assertTrue(!remarkFirstCommand.equals(null));

        // different types -> returns false
        assertTrue(!remarkFirstCommand.equals(new ClearCommand()));
    }
}
