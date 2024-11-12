package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.CompanyBuilder;

public class ReopenCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        ReopenCommand reopenCommand = new ReopenCommand(outOfBoundIndex);

        String expectedMessage = String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE,
            model.getFilteredCompanyList().size());

        assertCommandFailure(reopenCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        ReopenCommand reopenCommand = new ReopenCommand(outOfBoundIndex);

        String expectedMessage = String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE,
            model.getFilteredCompanyList().size());

        assertCommandFailure(reopenCommand, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ReopenCommand reopenCommand = new ReopenCommand(targetIndex);
        String expected = ReopenCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, reopenCommand.toString());
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Company companyToReopen = model.getFilteredCompanyList().get(Index.fromOneBased(6).getZeroBased());
        ReopenCommand reopenCommand = new ReopenCommand(Index.fromOneBased(6));

        String expectedMessage = String.format(ReopenCommand.MESSAGE_REOPEN_COMPANY_SUCCESS, companyToReopen.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        CompanyBuilder newCompany = new CompanyBuilder(companyToReopen);
        Company newCompanyToSet = newCompany.withStatus("INTERESTED").build();

        expectedModel.setCompany(companyToReopen, newCompanyToSet);
        assertCommandSuccess(reopenCommand, model, expectedMessage, expectedModel);
    }

}
