package seedu.internbuddy.logic.commands;

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

public class UnfavCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToUnfav = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        UnfavCommand unfavCommand = new UnfavCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(UnfavCommand.MESSAGE_UNFAV_COMPANY_SUCCESS,
                Messages.format(companyToUnfav));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(companyToUnfav, new Company(companyToUnfav.getName(),
                companyToUnfav.getPhone(), companyToUnfav.getEmail(),
                companyToUnfav.getAddress(), companyToUnfav.getTags(),
                companyToUnfav.getStatus(), companyToUnfav.getApplications(),
                false));

        assertCommandSuccess(unfavCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        UnfavCommand unfavCommand = new UnfavCommand(outOfBoundIndex);

        assertCommandFailure(unfavCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }
}
