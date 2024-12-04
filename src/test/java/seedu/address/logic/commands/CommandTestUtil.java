package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
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

    public static final int VALID_ID_AMY = 8;
    public static final int VALID_ID_BOB = 9;
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "81111111";
    public static final String VALID_PHONE_BOB = "92222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1, 123456";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3, 123456";
    public static final String VALID_HOURS_AMY = "20";
    public static final String VALID_HOURS_BOB = "10";
    public static final int VALID_ID_CLARA = 10;
    public static final int VALID_ID_DEACON = 11;
    public static final String VALID_NAME_CLARA = "Clara Doe";
    public static final String VALID_NAME_DEACON = "Deacon Smith";
    public static final String VALID_PHONE_CLARA = "93333333";
    public static final String VALID_PHONE_DEACON = "84444444";
    public static final String VALID_EMAIL_CLARA = "clara@example.com";
    public static final String VALID_EMAIL_DEACON = "deacon@example.com";
    public static final String VALID_ADDRESS_CLARA = "Block 456, Clara Street 5, 123456";
    public static final String VALID_ADDRESS_DEACON = "Block 789, Deacon Avenue 8, 123456";
    public static final String VALID_HOURS_CLARA = "15";
    public static final String VALID_HOURS_DEACON = "25";
    public static final String VALID_SUBJECT_MATH = "Math";
    public static final String VALID_SUBJECT_SCIENCE = "Science";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_CLARA = " " + PREFIX_NAME + VALID_NAME_CLARA;
    public static final String NAME_DESC_DEACON = " " + PREFIX_NAME + VALID_NAME_DEACON;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_CLARA = " " + PREFIX_PHONE + VALID_PHONE_CLARA;
    public static final String PHONE_DESC_DEACON = " " + PREFIX_PHONE + VALID_PHONE_DEACON;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_CLARA = " " + PREFIX_EMAIL + VALID_EMAIL_CLARA;
    public static final String EMAIL_DESC_DEACON = " " + PREFIX_EMAIL + VALID_EMAIL_DEACON;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_CLARA = " " + PREFIX_ADDRESS + VALID_ADDRESS_CLARA;
    public static final String ADDRESS_DESC_DEACON = " " + PREFIX_ADDRESS + VALID_ADDRESS_DEACON;
    public static final String HOURS_DESC_AMY = " " + PREFIX_HOURS + VALID_HOURS_AMY;
    public static final String HOURS_DESC_BOB = " " + PREFIX_HOURS + VALID_HOURS_BOB;
    public static final String HOURS_DESC_CLARA = " " + PREFIX_HOURS + VALID_HOURS_CLARA;
    public static final String HOURS_DESC_DEACON = " " + PREFIX_HOURS + VALID_HOURS_DEACON;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_HOURS_DESC = " " + PREFIX_HOURS + "-5"; // negative hours not allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withHours(VALID_HOURS_AMY).withSubjects(VALID_SUBJECT_MATH).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withHours(VALID_HOURS_BOB).withSubjects(VALID_SUBJECT_SCIENCE).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistory, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, actualCommandHistory));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        assertEquals(expectedCommandHistory, actualCommandHistory);
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

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
