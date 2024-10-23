package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_FEE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorease.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.Model;
import tutorease.address.model.TutorEase;
import tutorease.address.model.person.NameContainsKeywordsPredicate;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_TAG_SUPPORTIVE = "supportive";
    public static final String VALID_TAG_MENTOR = "mentor";
    public static final String VALID_ROLE_ANY = "Student";
    public static final String VALID_ROLE_AMY = "Student";
    public static final String VALID_ROLE_BOB = "Student";
    public static final String VALID_NAME_MEG = "Meg";
    public static final String VALID_PHONE_MEG = "97554213";
    public static final String VALID_EMAIL_MEG = "meg@guardian.com";
    public static final String VALID_ADDRESS_MEG = "Yishun 1234";
    public static final String VALID_ROLE_MEG = "Guardian";
    public static final String VALID_NAME_CHICK = "Chick";
    public static final String VALID_PHONE_CHICK = "96541178";
    public static final String VALID_EMAIL_CHICK = "ChickenGuard@guardian.com";
    public static final String VALID_ADDRESS_CHICK = "Pasir Ris Chicken";
    public static final String VALID_ROLE_CHICK = "Guardian";


    public static final String VALID_STUDENT_ID = "1";
    public static final String VALID_FEE = "10";
    public static final String VALID_START_DATE = "01-01-2024 12:00";
    public static final String VALID_END_DATE = "01-01-2024 13:00";

    public static final String VALID_DURATION = "1";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_MEG = " " + PREFIX_NAME + VALID_NAME_MEG;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_MEG = " " + PREFIX_PHONE + VALID_PHONE_MEG;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_MEG = " " + PREFIX_EMAIL + VALID_EMAIL_MEG;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_MEG = " " + PREFIX_ADDRESS + VALID_ADDRESS_MEG;
    public static final String ROLE_DESC_AMY = " " + PREFIX_ROLE + VALID_ROLE_AMY;
    public static final String ROLE_DESC_BOB = " " + PREFIX_ROLE + VALID_ROLE_BOB;
    public static final String ROLE_DESC_MEG = " " + PREFIX_ROLE + VALID_ROLE_MEG;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_SUPPORTIVE = " " + PREFIX_TAG + VALID_TAG_SUPPORTIVE;
    public static final String TAG_DESC_MENTOR = " " + PREFIX_TAG + VALID_TAG_MENTOR;
    public static final String STUDENT_ID_DESC = " " + PREFIX_STUDENT_ID + VALID_STUDENT_ID;
    public static final String FEE_DESC = " " + PREFIX_FEE + VALID_FEE;
    public static final String START_DATE_TIME_DESC = " " + PREFIX_START_DATE + VALID_START_DATE;
    public static final String DURATION_DESC = " " + PREFIX_DURATION + VALID_DURATION;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_STUDENT_ID_CHAR = " " + PREFIX_STUDENT_ID + "a";
    public static final String INVALID_STUDENT_ID_ZERO = " " + PREFIX_STUDENT_ID + "0";
    public static final String INVALID_FEE = " " + PREFIX_FEE + "-1";
    public static final String INVALID_DAY = "00-03-2000 12:00";
    public static final String INVALID_START_DATE_DAY = " " + PREFIX_START_DATE + INVALID_DAY;
    public static final String INVALID_MONTH = "03-00-2000 12:00";
    public static final String INVALID_START_DATE_MONTH = " " + PREFIX_START_DATE + INVALID_MONTH;
    public static final String INVALID_YEAR = "03-03-0000 12:00";
    public static final String INVALID_START_DATE_YEAR = " " + PREFIX_START_DATE + INVALID_YEAR;
    public static final String INVALID_HOUR = "03-03-2000 25:00";
    public static final String INVALID_START_DATE_HOUR = " " + PREFIX_START_DATE + INVALID_HOUR;
    public static final String INVALID_MINUTE = "03-03-2000 12:60";
    public static final String INVALID_START_DATE_MINUTE = " " + PREFIX_START_DATE + INVALID_MINUTE;
    public static final String INVALID_DURATION_CHAR = " " + PREFIX_DURATION + "a";
    public static final String INVALID_DURATION_ZERO = " " + PREFIX_DURATION + "0";
    public static final String INVALID_DURATION_TWENTY_FIVE = " " + PREFIX_DURATION + "25";
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
        TutorEase expectedTutorEase = new TutorEase(actualModel.getTutorEase());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTutorEase, actualModel.getTutorEase());
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
