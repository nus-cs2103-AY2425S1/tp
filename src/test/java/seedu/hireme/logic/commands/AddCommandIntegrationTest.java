package seedu.hireme.logic.commands;

import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.hireme.logic.Messages;
import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.testutil.InternshipApplicationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model<InternshipApplication> model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newInternshipApplication_success() {
        InternshipApplication validApplication = new InternshipApplicationBuilder().build();

        Model<InternshipApplication> expectedModel = new ModelManager<>(model.getAddressBook(), new UserPrefs());
        expectedModel.addItem(validApplication);

        assertCommandSuccess(new AddCommand(validApplication), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validApplication)),
                expectedModel);
    }

    @Test
    public void execute_duplicateInternshipApplication_throwsCommandException() {
        InternshipApplication applicationInList = model.getAddressBook().getList().get(0);

        assertCommandFailure(new AddCommand(applicationInList), model,
                AddCommand.MESSAGE_DUPLICATE_APPLICATION);
    }

    @Test
    public void execute_nullInternshipApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, null, () -> new AddCommand(null));
    }

}
