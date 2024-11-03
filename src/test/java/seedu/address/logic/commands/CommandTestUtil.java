package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "91111111";
    public static final String VALID_PHONE_BOB = "82222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_HOUR_AMY = "4.5";
    public static final String VALID_HOUR_BOB = "3";
    public static final String VALID_SCHEDULE_AMY = "Sunday-1800-1900";
    public static final String VALID_SCHEDULE_BOB = "Monday-1800-1900";
    public static final String VALID_SUBJECT_AMY = "Mathematics";
    public static final String VALID_SUBJECT_BOB = "Mathematics";
    public static final String VALID_RATE_AMY = "250.00";
    public static final String VALID_RATE_BOB = "300.25";
    public static final String VALID_PAID_AMOUNT_AMY = "750.00";
    public static final String VALID_PAID_AMOUNT_BOB = "5.0";
    public static final String VALID_OWED_AMOUNT_AMY = "500.00";
    public static final String VALID_OWED_AMOUNT_BOB = "300.25";
    public static final String VALID_HOUR_DESC = " " + PREFIX_HOUR + VALID_HOUR_AMY;
    public static final String VALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "5.00";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SCHEDULE_DESC_AMY = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_AMY;
    public static final String SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_BOB;
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_SUBJECT_AMY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_SUBJECT_BOB;
    public static final String RATE_DESC_AMY = " " + PREFIX_RATE + VALID_RATE_AMY;
    public static final String RATE_DESC_BOB = " " + PREFIX_RATE + VALID_RATE_BOB;
    public static final String PAID_AMOUNT_DESC_AMY = " " + PREFIX_PAID_AMOUNT + VALID_PAID_AMOUNT_AMY;
    public static final String PAID_AMOUNT_DESC_BOB = " " + PREFIX_PAID_AMOUNT + VALID_PAID_AMOUNT_BOB;
    public static final String OWED_AMOUNT_DESC_AMY = " " + PREFIX_OWED_AMOUNT + VALID_OWED_AMOUNT_AMY;
    public static final String OWED_AMOUNT_DESC_BOB = " " + PREFIX_OWED_AMOUNT + VALID_OWED_AMOUNT_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SCHEDULE_DESC = " " + PREFIX_SCHEDULE + "Sunday 1800-1900";
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT + "Physical education";
    public static final String INVALID_RATE_DESC = " " + PREFIX_RATE + "12.398";
    public static final String INVALID_PAID_AMOUNT_DESC = " " + PREFIX_PAID_AMOUNT + " ";
    public static final String INVALID_OWED_AMOUNT_DESC = " " + PREFIX_OWED_AMOUNT + "19.000";
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "-60.00";
    public static final String INVALID_HOUR_DESC = " " + PREFIX_HOUR + "abc";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                                                     .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                                                     .withAddress(VALID_ADDRESS_AMY)
                                                     .withSchedule(VALID_SCHEDULE_AMY).withSubject(VALID_SUBJECT_AMY)
                                                     .withRate(VALID_RATE_AMY)
                                                     .withPaidAmount(VALID_PAID_AMOUNT_AMY)
                                                     .withOwedAmount(VALID_OWED_AMOUNT_AMY).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                                                     .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                                                     .withAddress(VALID_ADDRESS_BOB)
                                                     .withSchedule(VALID_SCHEDULE_BOB).withSubject(VALID_SUBJECT_BOB)
                                                     .withRate(VALID_RATE_BOB).withPaidAmount(VALID_PAID_AMOUNT_BOB)
                                                     .withOwedAmount(VALID_OWED_AMOUNT_BOB).build();
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
     * - the address book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        HashSet<String> nameSet = new HashSet<>(Collections.singletonList(splitName[0]));
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(nameSet));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
