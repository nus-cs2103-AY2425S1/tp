package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_START_DATE;
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
    public static final String VALID_BIRTHDAY_AMY = "2000-12-12";
    public static final String VALID_BIRTHDAY_BOB = "1990-10-10";
    public static final String VALID_APPOINTMENT_AMY = "2024-12-12 10:00";
    public static final String VALID_APPOINTMENT_FIONA = "2024-12-12 10:00";
    public static final String VALID_APPOINTMENT_BOB = "2024-10-10 11:00";
    public static final String VALID_POLICY_NAME_LIFE = "life insurance ";
    public static final String VALID_POLICY_NAME_INVESTMENT = "Investment Insurance ";
    public static final String VALID_DATE_1 = "2024-10-16";
    public static final String VALID_DATE_2 = "2026-11-11";
    public static final String VALID_INSURANCE_PAYMENT = "2025-12-12 349.00";
    public static final String VALID_INSURANCE_PAYMENT_DATE = "2025-12-12";
    public static final String VALID_INSURANCE_AMOUNT_DUE = "349.00";
    public static final String INVALID_INSURANCE_AMOUNT_DUE = "349.00asdd";



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
    public static final String BIRTHDAY_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String APPOINTMENT_AMY = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_AMY;
    public static final String APPOINTMENT_BOB = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_BOB;
    public static final String EDIT_POLICY_LIFE_1 = " 1 "
            + PREFIX_POLICY_NAME + VALID_POLICY_NAME_LIFE + " "
            + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " "
            + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
            + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
            + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;
    public static final String VALID_EDIT_POLICY_LIFE = " " + PREFIX_POLICY + EDIT_POLICY_LIFE_1;
    public static final String VALID_ASSIGN_POLICY = " " + PREFIX_POLICY_NAME + VALID_POLICY_NAME_LIFE
            + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " " + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
            + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
            + PREFIX_PAYMENT_AMOUNT + VALID_INSURANCE_AMOUNT_DUE;
    public static final String INVALID_ASSIGN_POLICY = " " + PREFIX_POLICY_NAME + VALID_POLICY_NAME_LIFE
            + PREFIX_POLICY_START_DATE + VALID_DATE_1 + " " + PREFIX_POLICY_END_DATE + VALID_DATE_2 + " "
            + PREFIX_NEXT_PAYMENT_DATE + VALID_INSURANCE_PAYMENT_DATE + " "
            + PREFIX_PAYMENT_AMOUNT + INVALID_INSURANCE_AMOUNT_DUE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_APPOINTMENT_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withAppointment(VALID_APPOINTMENT_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    public static final String VALID_PARAMETER_NAME = "n/";

    public static final String VALID_PARAMETER_APPOINTMENT = "appt/";

    public static final String VALID_PARAMETER_BIRTHDAY = "b/";

    public static final String VALID_PARAMETER_PAYDATE = "paydate/";

    public static final String VALID_ORDER_ASC = "asc";

    public static final String VALID_ORDER_DESC = "desc";

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
