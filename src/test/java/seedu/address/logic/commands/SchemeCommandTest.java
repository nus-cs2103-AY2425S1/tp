package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook3;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SchemeCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook3(), new ArrayList<>(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook3(), new ArrayList<>(), new UserPrefs());

    @Test
    public void execute_notSchemeAvail_success() {
        String result = "Schemes available for: Terry\n"
                + "No schemes available for this person.";
        CommandResult expectedCommandResult = new CommandResult(result);
        assertCommandSuccess(new SchemeCommand(INDEX_THIRD_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_allSchemeAvail_success() {
        String result = "Schemes available for: Berry\n"
                + "1. MOE Financial Assistance Scheme (MOE FAS)\n"
                + "2. Student Care Fee Assistance (SCFA)\n";
        CommandResult expectedCommandResult = new CommandResult(result);
        assertCommandSuccess(new SchemeCommand(INDEX_FIRST_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_oneSchemeAvail_success() {
        String result = "Schemes available for: Cherry\n"
                + "1. Student Care Fee Assistance (SCFA)\n";
        CommandResult expectedCommandResult = new CommandResult(result);
        assertCommandSuccess(new SchemeCommand(INDEX_SECOND_PERSON), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        assertCommandFailure(new SchemeCommand(INDEX_FOURTH_PERSON), model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
    }
}
