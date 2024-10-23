package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteCompanyCommand.
 */
public class DeleteCompanyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                companyToDelete.getName(), companyToDelete.getAddress(),
                companyToDelete.getBillingDate(), companyToDelete.getPhone());

        expectedModel.deleteCompany(companyToDelete);

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = INDEX_OUT_OF_BOUNDS;
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(outOfBoundsIndex);

        assertCommandFailure(deleteCompanyCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY); // Filter the list to show only one company

        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS,
                companyToDelete.getName(), companyToDelete.getAddress(),
                companyToDelete.getBillingDate(), companyToDelete.getPhone());

        expectedModel.deleteCompany(companyToDelete);
        showNoCompany(expectedModel); // Ensure the list in expectedModel is empty after deletion

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);

        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(outOfBoundsIndex);

        assertCommandFailure(deleteCompanyCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model = new ModelManager();
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);

        assertCommandFailure(deleteCompanyCommand, model, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    private void showNoCompany(Model model) {
        model.updateFilteredCompanyList(p -> false);

        assertTrue(model.getFilteredCompanyList().isEmpty());
    }
}
