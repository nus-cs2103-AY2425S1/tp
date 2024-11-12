package seedu.internbuddy.logic.commands;

import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internbuddy.testutil.TypicalCompanies.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.ModelManager;
import seedu.internbuddy.model.UserPrefs;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.testutil.CompanyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newcompany_success() {
        Company validcompany = new CompanyBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addCompany(validcompany);

        assertCommandSuccess(new AddCommand(validcompany), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validcompany)),
                expectedModel);
    }

    @Test
    public void execute_duplicatecompany_throwsCommandException() {
        Company companyInList = model.getAddressBook().getCompanyList().get(0);
        assertCommandFailure(new AddCommand(companyInList), model,
                AddCommand.MESSAGE_DUPLICATE_COMPANY);
    }

}
