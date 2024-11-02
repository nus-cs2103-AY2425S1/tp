package seedu.address.logic.commands.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_MEETUPS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.FIRST_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.SECOND_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.THIRD_MEETUP;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());
    private Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());

    @Test
    public void equals() {
        MeetUpContainsKeywordsPredicate firstPredicate =
                new MeetUpContainsKeywordsPredicate(Collections.singletonList("first"));
        MeetUpContainsKeywordsPredicate secondPredicate =
                new MeetUpContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different buyer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noMeetUpsFound() {
        String expectedMessage = String.format(MESSAGE_MEETUPS_LISTED_OVERVIEW, 0);
        MeetUpContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMeetUpList(predicate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                true, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMeetUpList());
    }

    @Test
    public void execute_multipleKeywords_multipleMeetUpsFound() {
        String expectedMessage = String.format(MESSAGE_MEETUPS_LISTED_OVERVIEW, 3);
        MeetUpContainsKeywordsPredicate predicate = preparePredicate("Client Contract Closure");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredMeetUpList(predicate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                true, false, false);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(FIRST_MEETUP, SECOND_MEETUP, THIRD_MEETUP), model.getFilteredMeetUpList());
    }

    @Test
    public void toStringMethod() {
        MeetUpContainsKeywordsPredicate predicate = new MeetUpContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code MeetUpContainsKeywordsPredicate}.
     */
    private MeetUpContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MeetUpContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
