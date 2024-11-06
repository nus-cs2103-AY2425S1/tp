package seedu.address.logic.commands.contact.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.PersonIsRolePredicate;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Vendor;
import seedu.address.model.role.Volunteer;

/**
 * Contains integration tests (interaction with the Model) for {@code FindRoleCommand}.
 */
public class FindRoleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    @Test
    public void equals() {
        PersonIsRolePredicate firstPredicate =
                new PersonIsRolePredicate(Collections.singletonList(new Sponsor()));
        PersonIsRolePredicate secondPredicate =
                new PersonIsRolePredicate(Collections.singletonList(new Vendor()));

        FindRoleCommand searchFirstCommand = new FindRoleCommand(firstPredicate);
        FindRoleCommand searchSecondCommand = new FindRoleCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        FindRoleCommand searchFirstCommandCopy = new FindRoleCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));

    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        PersonIsRolePredicate predicate =
                new PersonIsRolePredicate(Arrays.asList());
        String expectedMessage = String.format(Messages.MESSAGE_USER_SEARCH_QUERY_ROLES, predicate.getRolesAsString())
                + "\n" + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindRoleCommand command = new FindRoleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        PersonIsRolePredicate predicate =
                new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer()));
        String expectedMessage = String.format(Messages.MESSAGE_USER_SEARCH_QUERY_ROLES, predicate.getRolesAsString())
                + "\n" + String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindRoleCommand command = new FindRoleCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonIsRolePredicate predicate = new PersonIsRolePredicate(Arrays.asList(new Sponsor(), new Volunteer()));
        FindRoleCommand findRoleCommand = new FindRoleCommand(predicate);
        String expected = FindRoleCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findRoleCommand.toString());
    }
}
