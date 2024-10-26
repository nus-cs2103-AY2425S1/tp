package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncomeCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OweCommand;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;
import seedu.address.model.student.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.student.predicates.ScheduleContainsKeywordsPredicate;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_addRandomCase() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommandRandomCase(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearRandomCase() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_RANDOM_CASE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_STUDENT), command);
    }

    @Test
    public void parseCommand_deleteRandomCase() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_RANDOM_CASE + " " + INDEX_FIRST_STUDENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_STUDENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_editRandomCase() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_RANDOM_CASE + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_exitRandomCase() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_RANDOM_CASE) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // keywords for each prefix
        Collection<String> nameKeywords = Set.of("foo", "bar", "baz");
        Collection<String> dayKeywords = Set.of("Monday", "Tuesday", "Wednesday");

        // Collection of keyword for day
        Collection<Days> daySet = Set.of(Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY);

        // Predicates for prefixes
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        ScheduleContainsKeywordsPredicate dayPredicate = new ScheduleContainsKeywordsPredicate(daySet);
        FindCommand expectedFindCommand = new FindCommand(List.of(dayPredicate, namePredicate));

        // Constructing the input string
        String nameInput = PREFIX_NAME + String.join(" ", nameKeywords);
        String dayInput = PREFIX_DAY + String.join(" ", dayKeywords);
        String userInput = FindCommand.COMMAND_WORD + " " + nameInput + " " + dayInput;
        String userInputRandomCase = FindCommand.COMMAND_WORD_RANDOM_CASE + " " + dayInput + " " + nameInput;

        // Parsing the input string
        FindCommand command = (FindCommand) parser.parseCommand(userInput);
        FindCommand commandRandomCase = (FindCommand) parser.parseCommand(userInputRandomCase);

        // Random case allowed
        assertEquals(expectedFindCommand, command);
        assertEquals(expectedFindCommand, commandRandomCase);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_helpRandomCase() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_RANDOM_CASE) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listRandomCase() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_RANDOM_CASE) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_income() throws Exception {
        assertTrue(parser.parseCommand(IncomeCommand.COMMAND_WORD) instanceof IncomeCommand);
        assertTrue(parser.parseCommand(IncomeCommand.COMMAND_WORD + " 3") instanceof IncomeCommand);
    }

    @Test
    public void parseCommand_incomeRandomCase() throws Exception {
        assertTrue(parser.parseCommand(IncomeCommand.COMMAND_WORD_RANDOM_CASE) instanceof IncomeCommand);
        assertTrue(parser.parseCommand(IncomeCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof IncomeCommand);
    }

    @Test
    public void parseCommand_pay() throws Exception {
        PayCommand command = (PayCommand) parser.parseCommand(PayCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_HOUR + "1");
        assertEquals(new PayCommand(INDEX_FIRST_STUDENT, 1), command);
    }

    @Test
    public void parseCommand_payRandomCase() throws Exception {
        PayCommand command = (PayCommand) parser.parseCommand(PayCommand.COMMAND_WORD_RANDOM_CASE + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_HOUR + "1");
        assertEquals(new PayCommand(INDEX_FIRST_STUDENT, 1), command);
    }

    @Test
    public void parseCommand_owe() throws Exception {
        OweCommand command = (OweCommand) parser.parseCommand(OweCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_HOUR + "1");
        assertEquals(new OweCommand(INDEX_FIRST_STUDENT, 1), command);
    }

    @Test
    public void parseCommand_oweRandomCase() throws Exception {
        OweCommand command = (OweCommand) parser.parseCommand(OweCommand.COMMAND_WORD_RANDOM_CASE + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_HOUR + "1");
        assertEquals(new OweCommand(INDEX_FIRST_STUDENT, 1), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_remind() throws Exception {
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD) instanceof RemindCommand);
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD + " 3") instanceof RemindCommand);
    }

    @Test
    public void parseCommand_remindRandomCase() throws Exception {
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD_RANDOM_CASE) instanceof RemindCommand);
        assertTrue(parser.parseCommand(RemindCommand.COMMAND_WORD_RANDOM_CASE + " 3") instanceof RemindCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_settle() throws Exception {
        // Example index and amount
        double amount = 100.0; // Example amount

        // Assuming the command format is "settle i/1 a/100.0"
        SettleCommand command = (SettleCommand) parser.parseCommand(
                SettleCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_STUDENT.getOneBased() + " "
                        + PREFIX_AMOUNT + amount);

        assertEquals(new SettleCommand(INDEX_FIRST_STUDENT, amount), command);
    }

    @Test
    public void parseCommand_settleRandomCase() throws Exception {
        // Example index and amount
        double amount = 100.0; // Example amount

        // Test for random case command
        SettleCommand command = (SettleCommand) parser.parseCommand(
                SettleCommand.COMMAND_WORD_RANDOM_CASE + " "
                        + INDEX_FIRST_STUDENT.getOneBased() + " "
                        + PREFIX_AMOUNT + amount);

        assertEquals(new SettleCommand(INDEX_FIRST_STUDENT, amount), command);
    }
}
