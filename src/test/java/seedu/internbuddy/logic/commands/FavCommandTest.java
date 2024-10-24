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

public class FavCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToFav = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        FavCommand favCommand = new FavCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_COMPANY_SUCCESS,
                Messages.format(companyToFav));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setCompany(companyToFav, new Company(companyToFav.getName(),
                companyToFav.getPhone(), companyToFav.getEmail(),
                companyToFav.getAddress(), companyToFav.getTags(),
                companyToFav.getStatus(), companyToFav.getApplications(),
                true));

        assertCommandSuccess(favCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        FavCommand favCommand = new FavCommand(outOfBoundIndex);

        assertCommandFailure(favCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }
}
