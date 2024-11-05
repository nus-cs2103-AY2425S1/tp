package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentPersonContainsNamePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(
        getTypicalAddressBook(),
        getTypicalAppointmentBook(),
        new UserPrefs());
    private final Model expectedModel = new ModelManager(
        getTypicalAddressBook(),
        getTypicalAppointmentBook(),
        new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPersonPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPersonPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        AppointmentPersonContainsNamePredicate firstApptPredicate =
                new AppointmentPersonContainsNamePredicate(Collections.singletonList("2024-10-20"));
        AppointmentPersonContainsNamePredicate secondApptPredicate =
                new AppointmentPersonContainsNamePredicate(Collections.singletonList("2024-10-21"));

        FindCommand findFirstPersonCommand = new FindPersonCommand(firstPersonPredicate);
        FindCommand findSecondPersonCommand = new FindPersonCommand(secondPersonPredicate);
        FindCommand findFirstApptCommand = new FindAppointmentCommand(firstApptPredicate);
        FindCommand findSecondApptCommand = new FindAppointmentCommand(secondApptPredicate);

        assertEquals(findFirstPersonCommand, findFirstPersonCommand);
        assertEquals(findFirstApptCommand, findFirstApptCommand);

        FindCommand findFirstPersonCommandCopy = new FindPersonCommand(firstPersonPredicate);
        FindCommand findFirstApptCommandCopy = new FindAppointmentCommand(firstApptPredicate);
        assertEquals(findFirstPersonCommand, findFirstPersonCommandCopy);
        assertEquals(findFirstApptCommand, findFirstApptCommandCopy);

        assertNotEquals(findFirstPersonCommand, findFirstApptCommand);

        assertNotEquals(null, findFirstPersonCommand);
        assertNotEquals(null, findFirstApptCommand);

        assertNotEquals(findFirstPersonCommand, findSecondPersonCommand);
        assertNotEquals(findFirstApptCommand, findSecondApptCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA),
            model.getFilteredPersonList().stream().map(p -> p.getPersonDescriptor()).toList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(List.of("keyword"));
        FindPersonCommand findCommand = new FindPersonCommand(predicate);
        String expected = FindPersonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
