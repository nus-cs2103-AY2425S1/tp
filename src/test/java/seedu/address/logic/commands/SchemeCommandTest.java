package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook3;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.scheme.MoeFinancialAssistanceScheme;
import seedu.address.model.scheme.StudentCareFeeAssistanceScheme;

public class SchemeCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook3(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook3(), new UserPrefs());

    @Test
    public void execute_notSchemeAvail_success() {
        CommandResult expectedCommandResult = new CommandResult("No schemes available for this family.",
                false, false);
        assertCommandSuccess(new SchemeCommand(INDEX_THIRD_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_allSchemeAvail_success() {
        CommandResult expectedCommandResult = new CommandResult("1. "
                + MoeFinancialAssistanceScheme.SCHEME_NAME + "\n"
                + "2. " + StudentCareFeeAssistanceScheme.SCHEME_NAME + "\n", false, false);
        assertCommandSuccess(new SchemeCommand(INDEX_FIRST_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_oneSchemeAvail_success() {
        CommandResult expectedCommandResult = new CommandResult("1. "
                + StudentCareFeeAssistanceScheme.SCHEME_NAME + "\n",
                false, false);
        assertCommandSuccess(new SchemeCommand(INDEX_SECOND_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        assertCommandFailure(new SchemeCommand(INDEX_FOURTH_PERSON), model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
    }
}
