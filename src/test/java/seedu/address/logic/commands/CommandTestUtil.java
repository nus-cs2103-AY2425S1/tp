package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.*;
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

    public static final int VALID_ATTENDANCE_AMY = 10;
    public static final int VALID_ATTENDANCE_BOB = 10;

    public static final String VALID_SUBJECT_AMY = "Mathematics";
    public static final String VALID_SUBJECT_BOB = "Physics";
    public static final String VALID_CLASSES_AMY = "8H";
    public static final String VALID_CLASSES_BOB = "7A";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + "female";
    public static final String VALID_GENDER_AMY = "female";
    public static final String VALID_GENDER_BOB = "male";
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_SUBJECT_AMY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_SUBJECT_BOB;
    public static final String CLASS_DESC_AMY = " " + PREFIX_CLASSES + VALID_CLASSES_AMY;
    public static final String CLASS_DESC_BOB = " " + PREFIX_CLASSES + VALID_CLASSES_BOB;
    public static final String ATTENDANCE_DESC_AMY = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_AMY;
    public static final String ATTENDANCE_DESC_BOB = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "unknown"; // 'unknown' is not a valid gender
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "*"; // '*' is not a valid subject
    public static final String INVALID_CLASSES_DESC = " " + PREFIX_CLASSES + "*"; // '*' is not a valid class
    public static final String INVALID_ATTENDANCE_DESC = " " + PREFIX_ATTENDANCE + "-1"; // negative attendance not allowed

    public static final String VALID_NAME_MICHAEL = "Michael Tan";
    public static final String VALID_GENDER_MICHAEL = "male";
    public static final String VALID_PHONE_MICHAEL = "98765432";
    public static final String VALID_EMAIL_MICHAEL = "michael@example.com";
    public static final String VALID_ADDRESS_MICHAEL = "311, Clementi Ave 2, #02-25";
    public static final String VALID_SUBJECT_MICHAEL = "Physics";
    public static final String VALID_CLASSES_MICHAEL = "7A, 7B";
    public static final String VALID_TAG_HARDWORKING = "hardworking";
    public static final String VALID_TAG_ATHLETE = "athlete";
    public static final String VALID_ATTENDANCE_MICHAEL = "10";

    // Constants for Chris
    public static final String VALID_NAME_CHRIS = "Chris Lim";
    public static final String VALID_PHONE_CHRIS = "98192727";
    public static final String VALID_EMAIL_CHRIS = "chris@example.com";
    public static final String VALID_ADDRESS_CHRIS = "311, Lorong Ave 2, #02-25";
    public static final String VALID_SUBJECT_CHRIS = "Math";
    public static final String VALID_CLASSES_CHRIS = "7C, 7B";
    public static final String VALID_ATTENDANCE_CHRIS = "10";

    // Descriptions for fields for Michael
    public static final String NAME_DESC_MICHAEL = " " + PREFIX_NAME + VALID_NAME_MICHAEL;
    public static final String GENDER_DESC_MICHAEL = " " + PREFIX_GENDER + VALID_GENDER_MICHAEL;
    public static final String PHONE_DESC_MICHAEL = " " + PREFIX_PHONE + VALID_PHONE_MICHAEL;
    public static final String EMAIL_DESC_MICHAEL = " " + PREFIX_EMAIL + VALID_EMAIL_MICHAEL;
    public static final String ADDRESS_DESC_MICHAEL = " " + PREFIX_ADDRESS + VALID_ADDRESS_MICHAEL;
    public static final String SUBJECT_DESC_MICHAEL = " " + PREFIX_SUBJECT + VALID_SUBJECT_MICHAEL;
    public static final String CLASSES_DESC_MICHAEL = " " + PREFIX_CLASSES + VALID_CLASSES_MICHAEL;
    public static final String TAG_DESC_HARDWORKING = " " + PREFIX_TAG + VALID_TAG_HARDWORKING;
    public static final String TAG_DESC_ATHLETE = " " + PREFIX_TAG + VALID_TAG_ATHLETE;
    public static final String ATTENDANCE_DESC_MICHAEL = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_MICHAEL;
    public static final String INVALID_ATTENDANCE_DESC_MICHAEL = " " + PREFIX_ATTENDANCE + "-1";

    // Descriptions for fields for Chris
    public static final String NAME_DESC_CHRIS = " " + PREFIX_NAME + VALID_NAME_CHRIS;
    public static final String PHONE_DESC_CHRIS = " " + PREFIX_PHONE + VALID_PHONE_CHRIS;
    public static final String EMAIL_DESC_CHRIS = " " + PREFIX_EMAIL + VALID_EMAIL_CHRIS;
    public static final String ADDRESS_DESC_CHRIS = " " + PREFIX_ADDRESS + VALID_ADDRESS_CHRIS;
    public static final String SUBJECT_DESC_CHRIS = " " + PREFIX_SUBJECT + VALID_SUBJECT_CHRIS;
    public static final String CLASSES_DESC_CHRIS = " " + PREFIX_CLASSES + VALID_CLASSES_CHRIS;
    public static final String ATTENDANCE_DESC_CHRIS = " " + PREFIX_ATTENDANCE + VALID_ATTENDANCE_CHRIS;


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
