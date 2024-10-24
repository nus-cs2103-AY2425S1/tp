package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_OFFER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_ONGOING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.ApplicationStatus;
import seedu.address.model.company.Company;
import seedu.address.testutil.CompanyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class ApplicationStatusCommandTest {

    private static final String STATUS_STUB = "Some status";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addStatusUnfilteredList_success() {
        Company firstCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Company editedCompany = new CompanyBuilder(firstCompany).withApplicationStatus(STATUS_STUB).build();

        ApplicationStatusCommand applicationStatusCommand = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                new ApplicationStatus(editedCompany.getApplicationStatus().value));

        String expectedMessage = String.format(ApplicationStatusCommand.MESSAGE_ADD_STATUS_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setCompany(firstCompany, editedCompany);

        assertCommandSuccess(applicationStatusCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCompanyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        ApplicationStatusCommand applicationStatusCommand = new ApplicationStatusCommand(outOfBoundIndex,
                new ApplicationStatus(VALID_STATUS_OFFER));

        assertCommandFailure(applicationStatusCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        final ApplicationStatusCommand standardCommand = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                new ApplicationStatus(VALID_STATUS_ONGOING));

        // same values -> returns true
        ApplicationStatusCommand commandWithSameValues = new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                new ApplicationStatus(VALID_STATUS_ONGOING));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ApplicationStatusCommand(INDEX_SECOND_COMPANY,
                new ApplicationStatus(VALID_STATUS_ONGOING))));

        // different status -> returns false
        assertFalse(standardCommand.equals(new ApplicationStatusCommand(INDEX_FIRST_COMPANY,
                new ApplicationStatus(VALID_STATUS_OFFER))));
    }
}
