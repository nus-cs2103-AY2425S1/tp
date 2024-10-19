package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HIGH_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_LOW_RISK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEDIUM_RISK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonHasTagPredicate;
import seedu.address.model.tag.Tag;

public class FilterByTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private PersonHasTagPredicate highPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_HIGH_RISK)));
    private PersonHasTagPredicate lowPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_LOW_RISK)));

    private PersonHasTagPredicate mediumPredicate =
          new PersonHasTagPredicate(Collections.singletonList(new Tag(VALID_TAG_MEDIUM_RISK)));

    @Test
    public void equals() {

        FilterByTagCommand findFirstCommand = new FilterByTagCommand(highPredicate);
        FilterByTagCommand findSecondCommand = new FilterByTagCommand(lowPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterByTagCommand findFirstCommandCopy = new FilterByTagCommand(highPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    @Test
    public void execute_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterByTagCommand command = new FilterByTagCommand(highPredicate);
        expectedModel.updateFilteredPersonList(highPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterByTagCommand command = new FilterByTagCommand(mediumPredicate);
        expectedModel.updateFilteredPersonList(mediumPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FilterByTagCommand findCommand = new FilterByTagCommand(highPredicate);
        String expected = FilterByTagCommand.class.getCanonicalName() + "{predicate=" + highPredicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
