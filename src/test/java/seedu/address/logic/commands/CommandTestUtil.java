package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_CLASS;
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
    public static final String VALID_REGISTER_NUMBER_AMY = "1";
    public static final String VALID_REGISTER_NUMBER_BOB = "2";
    public static final String VALID_SEX_AMY = "F";
    public static final String VALID_SEX_BOB = "M";
    public static final String VALID_STUDENT_CLASS_AMY = "1A";
    public static final String VALID_STUDENT_CLASS_BOB = "2B";
    public static final String VALID_ECNAME_AMY = "Robert";
    public static final String VALID_ECNAME_BOB = "Sally";
    public static final String VALID_ECNUMBER_AMY = "91234567";
    public static final String VALID_ECNUMBER_BOB = "98765432";
    public static final String VALID_EXAM_MIDTERM = "Midterm";
    public static final String VALID_EXAM_FINAL = "Final";
    public static final String VALID_EXAM_PRACTICAL = "Practical";
    public static final String VALID_EXAM_QUIZ = "Quiz";
    public static final String VALID_EXAM_SCORE_AMY = "70";
    public static final String VALID_EXAM_SCORE_BOB = "65";
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
    public static final String REGISTER_NUMBER_DESC_AMY = " " + PREFIX_REGISTER_NUMBER + VALID_REGISTER_NUMBER_AMY;
    public static final String REGISTER_NUMBER_DESC_BOB = " " + PREFIX_REGISTER_NUMBER + VALID_REGISTER_NUMBER_BOB;
    public static final String SEX_DESC_AMY = " " + PREFIX_SEX + VALID_SEX_AMY;
    public static final String SEX_DESC_BOB = " " + PREFIX_SEX + VALID_SEX_BOB;
    public static final String STUDENT_CLASS_DESC_AMY = " " + PREFIX_STUDENT_CLASS + VALID_STUDENT_CLASS_AMY;
    public static final String STUDENT_CLASS_DESC_BOB = " " + PREFIX_STUDENT_CLASS + VALID_STUDENT_CLASS_BOB;
    public static final String ECNAME_DESC_AMY = " " + PREFIX_ECNAME + VALID_ECNAME_AMY;
    public static final String ECNAME_DESC_BOB = " " + PREFIX_ECNAME + VALID_ECNAME_BOB;
    public static final String ECNUMBER_DESC_AMY = " " + PREFIX_ECNUMBER + VALID_ECNUMBER_AMY;
    public static final String ECNUMBER_DESC_BOB = " " + PREFIX_ECNUMBER + VALID_ECNUMBER_BOB;
    public static final String EXAM_DESC_MIDTERM = " " + PREFIX_EXAM + VALID_EXAM_MIDTERM;
    public static final String EXAM_SCORE_DESC_AMY = " " + PREFIX_EXAM_SCORE + VALID_EXAM_SCORE_AMY;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_REGISTER_NUMBER_DESC = " " + PREFIX_REGISTER_NUMBER
            + "41"; // Only numbers between 1 and 40 allowed
    public static final String INVALID_SEX_DESC = " " + PREFIX_SEX + "H"; // Only 'M' and 'F' are allowed
    public static final String INVALID_STUDENT_CLASS_DESC = " " + PREFIX_STUDENT_CLASS
            + "A1"; // wrong format used for student class
    public static final String INVALID_ECNAME_DESC = " " + PREFIX_ECNAME + "John%"; // "%" not allowed in ecname
    public static final String INVALID_ECNUMBER_DESC = " " + PREFIX_ECNUMBER + "1234"; // 4 digit number
    public static final String INVALID_EXAM_DESC = " " + PREFIX_EXAM + "Midterm%"; // only alphanumeric characters
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRegisterNumber(VALID_REGISTER_NUMBER_AMY).withSex(VALID_SEX_AMY).withTags(VALID_TAG_FRIEND)
                .withStudentClass(VALID_STUDENT_CLASS_AMY).withEcName(VALID_ECNAME_AMY)
                .withEcNumber(VALID_ECNUMBER_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withRegisterNumber(VALID_REGISTER_NUMBER_BOB).withSex(VALID_SEX_BOB)
                .withStudentClass(VALID_STUDENT_CLASS_BOB).withEcName(VALID_ECNAME_BOB)
                .withEcNumber(VALID_ECNUMBER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
