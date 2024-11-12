package seedu.hiredfiredpro.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hiredfiredpro.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.logic.commands.AddCommand;
import seedu.hiredfiredpro.logic.commands.ClearCommand;
import seedu.hiredfiredpro.logic.commands.DeleteCommand;
import seedu.hiredfiredpro.logic.commands.EditCommand;
import seedu.hiredfiredpro.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.hiredfiredpro.logic.commands.ExitCommand;
import seedu.hiredfiredpro.logic.commands.FindCommand;
import seedu.hiredfiredpro.logic.commands.HelpCommand;
import seedu.hiredfiredpro.logic.commands.HireCommand;
import seedu.hiredfiredpro.logic.commands.ListCommand;
import seedu.hiredfiredpro.logic.commands.RejectCommand;
import seedu.hiredfiredpro.logic.commands.SortCommand;
import seedu.hiredfiredpro.logic.commands.ViewStatusCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.NameContainsKeywordsPredicate;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.testutil.EditPersonDescriptorBuilder;
import seedu.hiredfiredpro.testutil.PersonBuilder;
import seedu.hiredfiredpro.testutil.PersonUtil;

public class HiredFiredProParserTest {

    private final HiredFiredProParser parser = new HiredFiredProParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parse_sortCommand_success() throws Exception {
        // Testing with valid ascending order argument 'a'
        String arguments = "a";
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD + " " + arguments);

        // Check if the parsed command has ascending order set to true
        assertEquals(true, command.isAscending());
    }

    @Test
    public void parse_sortCommand_successDescending() throws Exception {
        // Testing with valid descending order argument 'd'
        String arguments = "d";
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD + " " + arguments);

        // Check if the parsed command has ascending order set to false
        assertEquals(false, command.isAscending());
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                  parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_hire() throws Exception {
        String userInput = "hire n/Amy Bee j/Software Engineer";
        HireCommand command = (HireCommand) parser.parseCommand(userInput);

        // Check that the parsed command is an instance of HireCommand
        assertTrue(command instanceof HireCommand);

        // Verify the parsed command details
        Name expectedName = new Name("Amy Bee");
        Job job = new Job("Software Engineer");
        HireCommand expectedCommand = new HireCommand(expectedName, job);

        // Assert the values of the parsed command match the expected command
        assertEquals(expectedCommand.getName(), command.getName());
        assertEquals(expectedCommand.getJob(), command.getJob());
    }

    @Test
    public void parseCommand_reject() throws Exception {
        String userInput = "reject n/John Doe j/Software Engineer";
        RejectCommand command = (RejectCommand) parser.parseCommand(userInput);

        // Check that the parsed command is an instance of RejectCommand
        assertTrue(command instanceof RejectCommand);

        // Verify the parsed command details
        Name expectedName = new Name("John Doe");
        Job job = new Job("Software Engineer");
        RejectCommand expectedCommand = new RejectCommand(expectedName, job);

        // Assert the values of the parsed command match the expected command
        assertEquals(expectedCommand.getName(), command.getName());
        assertEquals(expectedCommand.getJob(), command.getJob());
    }

    @Test
    public void parseCommand_viewStatus() throws Exception {
        String userInput = "view n/John Doe j/Software Engineer";
        ViewStatusCommand command = (ViewStatusCommand) parser.parseCommand(userInput);

        // Check that the parsed command is an instance of ViewStatusCommand
        assertTrue(command instanceof ViewStatusCommand);

        // Verify the parsed command details
        Name expectedName = new Name("John Doe");
        Job job = new Job("Software Engineer");
        ViewStatusCommand expectedCommand = new ViewStatusCommand(expectedName, job);

        // Assert the values of the parsed command match the expected command
        assertEquals(expectedCommand.name, command.name);
        assertEquals(expectedCommand.job, command.job);
    }

    @Test
    public void parseCommand_withExtraWhitespace() throws Exception {
        String userInput = "view       n/    John Doe     j/    Software Engineer";
        ViewStatusCommand command = (ViewStatusCommand) parser.parseCommand(userInput);

        // Check that the parsed command is an instance of ViewStatusCommand
        assertTrue(command instanceof ViewStatusCommand);

        // Verify the parsed command details
        Name expectedName = new Name("John Doe");
        Job job = new Job("Software Engineer");
        ViewStatusCommand expectedCommand = new ViewStatusCommand(expectedName, job);

        // Assert the values of the parsed command match the expected command
        assertEquals(expectedCommand.name, command.name);
        assertEquals(expectedCommand.job, command.job);
    }

    // Invalid Input (Job not specified in hire command)
    @Test
    public void parseCommand_invalidHireCommandFormatMissingJob_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HireCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand("hire n/Amy Bee"));
    }

    // Invalid Input (Name not specified in hire command)
    @Test
    public void parseCommand_invalidHireCommandFormatMissingName_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HireCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand("hire j/Software Engineer"));
    }

    // Invalid Input (Job not specified in reject command)
    @Test
    public void parseCommand_invalidRejectCommandFormatMissingJob_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RejectCommand.MESSAGE_USAGE), () -> parser.parseCommand("reject n/Amy Bee"));
    }

    // Invalid Input (Name not specified in reject command)
    @Test
    public void parseCommand_invalidRejectCommandFormatMissingName_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RejectCommand.MESSAGE_USAGE), () -> parser.parseCommand("reject j/Software Engineer"));
    }

    // Invalid Input (Job not specified in view command)
    @Test
    public void parseCommand_invalidViewStatusFormatMissingJob_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatusCommand.MESSAGE_USAGE), () -> parser.parseCommand("view n/Amy Bee"));
    }

    // Invalid Input (Name not specified in view command)
    @Test
    public void parseCommand_invalidViewStatusFormatMissingName_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatusCommand.MESSAGE_USAGE), () -> parser.parseCommand("view j/Software Engineer"));
    }

    // Invalid Input (Missing arguments in add command)
    @Test
    public void parseCommand_invalidArgumentFormat_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE), () -> parser.parseCommand("add"));
    }

}
