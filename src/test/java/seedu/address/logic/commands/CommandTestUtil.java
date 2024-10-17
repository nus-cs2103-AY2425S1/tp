package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOC_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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
    public static final String VALID_ECNAME_AMY = "Lim Jun Kai";
    public static final String VALID_ECNAME_BOB = "Henry Wong";
    public static final String VALID_ECPHONE_AMY = "84651752";
    public static final String VALID_ECPHONE_BOB = "78465912";
    public static final String VALID_ECRS_AMY = "Sibling";
    public static final String VALID_ECRS_BOB = "Cousin";
    public static final String VALID_DOC_NAME_AMY = "Tan";
    public static final String VALID_DOC_NAME_BOB = "Lee";
    public static final String VALID_DOC_PHONE_AMY = "12345678";
    public static final String VALID_DOC_PHONE_BOB = "87654321";
    public static final String VALID_DOC_EMAIL_AMY = "tan@gmail.com";
    public static final String VALID_DOC_EMAIL_BOB = "lee@gmail.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ECNAME_DESC_AMY = " " + PREFIX_EMERGENCY_CONTACT_NAME + VALID_ECNAME_AMY;
    public static final String ECNAME_DESC_BOB = " " + PREFIX_EMERGENCY_CONTACT_NAME + VALID_ECNAME_BOB;
    public static final String ECPHONE_DESC_AMY = " " + PREFIX_EMERGENCY_CONTACT_PHONE + VALID_ECPHONE_AMY;
    public static final String ECPHONE_DESC_BOB = " " + PREFIX_EMERGENCY_CONTACT_PHONE + VALID_ECPHONE_BOB;
    public static final String ECRS_DESC_AMY = " " + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + VALID_ECRS_AMY;
    public static final String ECRS_DESC_BOB = " " + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + VALID_ECRS_BOB;
    public static final String DOC_NAME_DESC_AMY = " " + PREFIX_DOC_NAME + VALID_DOC_NAME_AMY;
    public static final String DOC_NAME_DESC_BOB = " " + PREFIX_DOC_NAME + VALID_DOC_NAME_BOB;
    public static final String DOC_EMAIL_DESC_AMY = " " + PREFIX_DOC_EMAIL + VALID_DOC_EMAIL_AMY;
    public static final String DOC_EMAIL_DESC_BOB = " " + PREFIX_DOC_EMAIL + VALID_DOC_EMAIL_BOB;
    public static final String DOC_PHONE_DESC_AMY = " " + PREFIX_DOC_PHONE + VALID_DOC_PHONE_AMY;
    public static final String DOC_PHONE_DESC_BOB = " " + PREFIX_DOC_PHONE + VALID_DOC_PHONE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

    // '&' not allowed in names
    public static final String INVALID_ECNAME_DESC = " " + PREFIX_EMERGENCY_CONTACT_NAME + "James&";

    // 'a' not allowed in phones
    public static final String INVALID_ECPHONE_DESC = " " + PREFIX_EMERGENCY_CONTACT_PHONE + "911a";

    // '*' not allowed in relationships
    public static final String INVALID_ECRS_DESC = " " + PREFIX_EMERGENCY_CONTACT_RELATIONSHIP + "hubby*";
    public static final String INVALID_DOC_EMAIL_DESC = " " + PREFIX_DOC_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DOC_NAME_DESC = " " + PREFIX_DOC_NAME + "John*"; // '*' not allowed in names
    public static final String INVALID_DOC_PHONE_DESC = " " + PREFIX_DOC_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withEmergencyContactName(VALID_ECNAME_AMY).withEmergencyContactPhone(VALID_ECPHONE_AMY)
                .withEmergencyContactRelationship(VALID_ECRS_AMY).withDoctorName(VALID_DOC_NAME_AMY)
                .withDoctorPhone(VALID_DOC_PHONE_AMY).withDoctorEmail(VALID_DOC_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withEmergencyContactName(VALID_ECNAME_BOB).withEmergencyContactPhone(VALID_ECPHONE_BOB)
                .withEmergencyContactRelationship(VALID_ECRS_BOB)
                .withDoctorName(VALID_DOC_NAME_BOB).withDoctorPhone(VALID_DOC_PHONE_BOB)
                .withDoctorEmail(VALID_DOC_EMAIL_BOB)
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
