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
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonHasFeaturePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private PersonHasFeaturePredicate highTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), null, null, null);
    private PersonHasFeaturePredicate lowTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_LOW_RISK), null, null, null);

    private PersonHasFeaturePredicate mediumTagOnlyPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_MEDIUM_RISK), null, null, null);

    private PersonHasFeaturePredicate phoneOnlyPredicate =
          new PersonHasFeaturePredicate(null, new Phone(ALICE.getPhone().value), null, null);

    private PersonHasFeaturePredicate phoneAndTagPredicate =
          new PersonHasFeaturePredicate(new Tag(VALID_TAG_HIGH_RISK), new Phone(ALICE.getPhone().value), null, null);

    @Test
    public void equals() {

        FilterCommand findFirstCommand = new FilterCommand(phoneAndTagPredicate);
        FilterCommand findSecondCommand = new FilterCommand(lowTagOnlyPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(phoneAndTagPredicate);
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
        FilterCommand command = new FilterCommand(highTagOnlyPredicate);
        expectedModel.updateFilteredPersonList(highTagOnlyPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = new FilterCommand(mediumTagOnlyPredicate);
        expectedModel.updateFilteredPersonList(mediumTagOnlyPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());

        //Phone only
        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        command = new FilterCommand(phoneOnlyPredicate);
        expectedModel.updateFilteredPersonList(phoneOnlyPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_onePersonFound() {
        //Phone and Tag
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = new FilterCommand(phoneAndTagPredicate);
        expectedModel.updateFilteredPersonList(phoneAndTagPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE), model.getFilteredPersonList());

    }

    @Test
    public void toStringMethod() {
        FilterCommand findCommand = new FilterCommand(highTagOnlyPredicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + highTagOnlyPredicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
