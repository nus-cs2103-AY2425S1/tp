package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATEOFLASTVISIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
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
    public static final String VALID_NAME_BOB = "Bob Choo-Choo";
    public static final String VALID_PHONE_AMY = "8888\t8888";
    public static final String VALID_PHONE_BOB = "69281029";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMERGENCY_CONTACT_AMY = VALID_PHONE_AMY;
    public static final String VALID_EMERGENCY_CONTACT_BOB = VALID_PHONE_BOB;
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_CONVICT = "ex-convict";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_DATEOFLASTVISIT_AMY = "12-12-2023";
    public static final String VALID_DATEOFLASTVISIT_BOB = "09-09-2024";
    public static final String VALID_REMARK_AMY = "Family of four.";
    public static final String VALID_REMARK_BOB = "Lives in HDB";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMERGENCY_CONTACT_DESC_AMY = " " + PREFIX_EMERGENCY_CONTACT
            + VALID_EMERGENCY_CONTACT_AMY;
    public static final String EMERGENCY_CONTACT_DESC_BOB = " " + PREFIX_EMERGENCY_CONTACT
            + VALID_EMERGENCY_CONTACT_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_CONVICT = " " + PREFIX_TAG + VALID_TAG_CONVICT;
    public static final String DATEOFLASTVISIT_DESC_AMY = " " + PREFIX_DATEOFLASTVISIT
            + VALID_DATEOFLASTVISIT_AMY;
    public static final String DATEOFLASTVISIT_DESC_BOB = " " + PREFIX_DATEOFLASTVISIT
            + VALID_DATEOFLASTVISIT_BOB;

    public static final String ADDRESS_DELETION = " " + PREFIX_ADDRESS;
    public static final String EMAIL_DELETION = " " + PREFIX_EMAIL;
    public static final String EMERGENCY_CONTACT_DELETION = " " + PREFIX_EMERGENCY_CONTACT;
    public static final String DATEOFLASTVISIT_DELETION = " " + PREFIX_DATEOFLASTVISIT;

    // whitespace-only names are invalid
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "     ";
    // 'a' not allowed in phones or emergency contacts
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a";
    public static final String INVALID_EMERGENCY_CONTACT_DESC = " " + PREFIX_EMERGENCY_CONTACT + "911a";
    // missing '@' symbol
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo";
    // empty string not allowed for addresses
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "    ";
    // date should only be in the format: dd/MM/yyyy, separated by '-'
    public static final String INVALID_DATEOFLASTVISIT_DESC = " " + PREFIX_DATEOFLASTVISIT + "13/13/2023";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
                .withDateOfLastVisit(VALID_DATEOFLASTVISIT_AMY)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY).withRemark(VALID_REMARK_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_CONVICT, VALID_TAG_FRIEND).withDateOfLastVisit(VALID_DATEOFLASTVISIT_BOB)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB).withRemark(VALID_REMARK_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that takes a
     * string {@code expectedMessage}.
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
     * This filtering process uses the name as a predicate; if multiple people have the same name, they will
     * all be displayed with this method.
     */
    public static void showNameAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
