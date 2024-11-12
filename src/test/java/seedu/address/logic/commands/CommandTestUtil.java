package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_OWES_MONEY = "owesMoney";
    public static final String INEXISTENT_TAG_BESTFRIEND = "bestFriend";
    public static final String INEXISTENT_TAG_COLLEAGUE = "colleague";
    public static final String VALID_HANDLE = "username";
    public static final String VALID_HANDLE_SC = "-u.se_HH0rname_";
    public static final String VALID_SCHEDULE_NAME_AMY = "schedule";
    public static final String VALID_SCHEDULE_DATE_AMY = "2024-10-21";
    public static final String VALID_SCHEDULE_TIME_AMY = "16:00";
    public static final String VALID_SCHEDULE_NAME_BOB = "appointment";
    public static final String VALID_SCHEDULE_DATE_BOB = "2024-10-22";
    public static final String VALID_SCHEDULE_TIME_BOB = "08:00";
    public static final String VALID_DATETIME_BEGIN = "2024-10-10 00:00";
    public static final String VALID_DATETIME_END = "2024-10-12 00:00";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_OLD_TAG = " " + PREFIX_OLDTAG + VALID_TAG_FRIEND;
    public static final String TAG_NEW_TAG = " " + PREFIX_NEWTAG + VALID_TAG_HUSBAND;
    public static final String SOCIALMEDIA_IG = " " + PREFIX_IG + VALID_HANDLE;
    public static final String SOCIALMEDIA_FB = " " + PREFIX_FB + VALID_HANDLE;
    public static final String SOCIALMEDIA_CS = " " + PREFIX_CS + VALID_HANDLE;
    public static final String SOCIALMEDIA_IG_SC = " " + PREFIX_IG + VALID_HANDLE_SC;
    public static final String SOCIALMEDIA_FB_SC = " " + PREFIX_FB + VALID_HANDLE_SC;
    public static final String SOCIALMEDIA_CS_SC = " " + PREFIX_CS + VALID_HANDLE_SC;
    public static final String BEGIN_DATETIME_INPUT = " " + PREFIX_BEGIN + VALID_DATETIME_BEGIN;
    public static final String END_DATETIME_INPUT = " " + PREFIX_END + VALID_DATETIME_END;

    public static final String INVALID_BEGIN_DATETIME = "10-10-2024 00:00";
    public static final String INVALID_END_DATETIME = "10-10-2024 01:00";
    public static final String INVALID_END_EARLIER_DATETIME = "2024-10-08 00:00";
    public static final String INVALID_BEGIN_INPUT = " " + PREFIX_BEGIN + INVALID_BEGIN_DATETIME;
    public static final String INVALID_END_INPUT = " " + PREFIX_END + INVALID_END_DATETIME;
    public static final String INVALID_END_EARLIER_INPUT = " " + PREFIX_BEGIN + VALID_DATETIME_BEGIN
            + " " + PREFIX_END + INVALID_END_EARLIER_DATETIME;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_LENGTH_NAME_DESC = " " + PREFIX_NAME
            + "Maximiliano Alberto de la Cruz Gonzalez de la Torre y Mendoza"; // longer than 50 characters
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_LENGTH_LONG_PHONE_DESC = " " + PREFIX_PHONE
            + "123456789123456789"; // longer than 15 digits
    public static final String INVALID_LENGTH_SHORT_PHONE_DESC = " " + PREFIX_PHONE + "12"; // shorter than 3 digits
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_LENGTH_EMAIL_DESC = " " + PREFIX_EMAIL
            + "john.doe.john.doe.john.doe.john.doe.john.doe.john.doe.john.doe."
            + "john.doe.john.doe.john.doe.john.doe.john.doe.john.doe.john.doe."
            + "john.doe.john.doe.john.doe.john.doe.john.doe.john.doe.john.doe."
            + "john.doe.john.doe.john.doe.john.doe.john.doe.john.doe.john.doe."
            + "john.doe.john.doe.john.doe.john.doe@example.com"; // longer than 254 characters
    public static final String INVALID_SCHEDULE_NAME = " " + PREFIX_SCHEDULE_NAME + "appointment!";
    public static final String INVALID_SCHEDULE_DATE = " " + PREFIX_SCHEDULE_DATE + "22-10-2024";
    public static final String INVALID_SCHEDULE_TIME = " " + PREFIX_SCHEDULE_TIME + "4pm";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_NEW_TAG = " " + PREFIX_NEWTAG + "hubby*";
    public static final String INVALID_SOCIALMEDIA = " " + PREFIX_IG + "USER$$";
    public static final String INVALID_HANDLE_EXCEED = " " + PREFIX_IG + "12345678901234567890123456789012345";
    public static final String INVALID_INDEX_EXCEED_MAXINT = "2147483648";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
