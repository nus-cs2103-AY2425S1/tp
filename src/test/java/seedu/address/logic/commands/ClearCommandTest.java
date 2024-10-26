package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.GenderMatchesKeywordsPredicate;

public class ClearCommandTest {

    @Test
    public void execute_clearPromptsConfirmationCheck_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand.setIsClear(false);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CHECK, expectedModel);
        assertTrue(ClearCommand.getIsClear());
    }

}
