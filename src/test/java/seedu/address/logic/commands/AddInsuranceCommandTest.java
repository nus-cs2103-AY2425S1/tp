package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddInsuranceCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class AddInsuranceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute() {
        String fakeClientName = "some client";
        int fakePlanID = -1;
        assertCommandFailure(new AddInsuranceCommand(fakeClientName, fakePlanID), model,
                String.format(MESSAGE_ARGUMENTS, fakeClientName, fakePlanID));
    }
}
