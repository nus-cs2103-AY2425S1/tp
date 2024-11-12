package seedu.eventfulnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.eventfulnus.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.AddressBook;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.PersonContainsKeywordsPredicate;
import seedu.eventfulnus.testutil.EditEventDescriptorBuilder;
import seedu.eventfulnus.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROLE_ATHLETE = "Athlete - COM - Swimming Men, Tennis";
    public static final String VALID_ROLE_SPONSOR = "Sponsor - OATSIDE";
    public static final String VALID_ROLE_VOLUNTEER = "Volunteer - Photographer";

    public static final String VALID_EVENT_SPORT_LEAGUE = "League of Legends";
    public static final String VALID_EVENT_SPORT_HANDBALL_W = "Handball Women";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ROLE_DESC_ATHLETE = " " + PREFIX_ROLE + VALID_ROLE_ATHLETE;
    public static final String ROLE_DESC_VOLUNTEER = " " + PREFIX_ROLE + VALID_ROLE_VOLUNTEER;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ROLE_DESC = " " + PREFIX_ROLE + "hubby*"; // '*' not allowed in roles

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditEventCommand.EditEventDescriptor DESC_LEAGUE;
    public static final EditEventCommand.EditEventDescriptor DESC_HANDBALL_W;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withRoles(VALID_ROLE_VOLUNTEER).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withRoles(VALID_ROLE_ATHLETE, VALID_ROLE_SPONSOR).build();

        DESC_LEAGUE = new EditEventDescriptorBuilder().withSport(VALID_EVENT_SPORT_LEAGUE).build();
        DESC_HANDBALL_W = new EditEventDescriptorBuilder().withSport(VALID_EVENT_SPORT_HANDBALL_W).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().toString().split("\\s+");
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().toString().split("\\s+");
        model.updateFilteredEventList(new EventContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }
}
