package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.CompanyToViewPredicate;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToView = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(companyToView);
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(ViewCommand.MESSAGE_SUCCESS,
                companyToView.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(companyToView, new Company(companyToView.getName(),
                companyToView.getPhone(), companyToView.getEmail(),
                companyToView.getAddress(), companyToView.getTags(),
                companyToView.getStatus(), companyToView.getApplications(),
                companyToView.getIsFavourite(), true));
        expectedModel.updateFilteredCompanyList(predicate);

        assertCommandSuccess(viewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);
        String expectedMessage = String.format(
                Messages.MESSAGE_INDEX_EXCEEDS_SIZE, model.getFilteredCompanyList().size());

        assertCommandFailure(viewCommand, model, expectedMessage);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_COMPANY);

        // same object -> returns true
        assertTrue(viewCommand.equals(viewCommand));
    }
}
