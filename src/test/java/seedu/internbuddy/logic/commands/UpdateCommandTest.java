package seedu.internbuddy.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
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
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.ApplicationBuilder;

public class UpdateCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        Application newApplication = new ApplicationBuilder().build();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Company companyToUpdate = model.getFilteredCompanyList().get(0);
        List<Application> updatedApplications = new ArrayList<>(companyToUpdate.getApplications());
        updatedApplications.add(newApplication);

        Company updatedCompany = new Company(companyToUpdate.getName(), companyToUpdate.getPhone(),
                companyToUpdate.getEmail(), companyToUpdate.getAddress(), companyToUpdate.getTags(),
                companyToUpdate.getStatus(), updatedApplications, companyToUpdate.getIsFavourite());

        model.setCompany(companyToUpdate, updatedCompany);

        AppStatus newAppStatus = new AppStatus("INTERVIEWED");

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_COMPANY, newAppStatus);

        Application applicationToUpdate = updatedCompany.getApplications().get(0);
        Application updatedApplication = applicationToUpdate.setAppStatus(newAppStatus);

        CommandResult result = updateCommand.execute(model);

        assertEquals(String.format(UpdateCommand.MESSAGE_UPDATE_APPLICATION_SUCCESS, updatedApplication),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidCompanyIndex_throwsCommandException() {
        AppStatus newAppStatus = new AppStatus("INTERVIEWED");
        UpdateCommand updateCommand = new UpdateCommand(Index.fromOneBased(999),
                Index.fromOneBased(1), newAppStatus);
        assertThrows(CommandException.class, () -> updateCommand.execute(model),
                Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidApplicationIndex_throwsCommandException() {
        AppStatus newAppStatus = new AppStatus("INTERVIEWED");
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_COMPANY,
                Index.fromOneBased(999), newAppStatus);
        assertThrows(CommandException.class, () -> updateCommand.execute(model),
                Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AppStatus appStatus = new AppStatus("INTERVIEWED");
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_COMPANY,
                Index.fromOneBased(1), appStatus);
        UpdateCommand updateCommand2 = new UpdateCommand(INDEX_FIRST_COMPANY,
                Index.fromOneBased(1), appStatus);

        // same object -> returns true
        assertEquals(updateCommand, updateCommand2);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        AppStatus appStatus1 = new AppStatus("INTERVIEWED");
        AppStatus appStatus2 = new AppStatus("OFFERED");
        UpdateCommand updateCommand1 = new UpdateCommand(INDEX_FIRST_COMPANY,
                Index.fromOneBased(1), appStatus1);
        UpdateCommand updateCommand2 = new UpdateCommand(INDEX_SECOND_COMPANY,
                Index.fromOneBased(1), appStatus2);

        // different objects -> returns false
        assertNotEquals(updateCommand1, updateCommand2);
    }
}
