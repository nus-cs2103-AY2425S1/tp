package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model<InternshipApplication> model = new ModelManager<>();
        Model<InternshipApplication> expectedModel = new ModelManager<>();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model<InternshipApplication> model =
                new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
        Model<InternshipApplication> expectedModel =
                new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook<>());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClearCommand().execute(null));
    }

}
