package seedu.hireme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.hireme.testutil.Assert.assertThrows;
import static seedu.hireme.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.commands.AddCommand;
import seedu.hireme.logic.commands.ChartCommand;
import seedu.hireme.logic.commands.ClearCommand;
import seedu.hireme.logic.commands.DeleteCommand;
import seedu.hireme.logic.commands.ExitCommand;
import seedu.hireme.logic.commands.FilterCommand;
import seedu.hireme.logic.commands.FindCommand;
import seedu.hireme.logic.commands.HelpCommand;
import seedu.hireme.logic.commands.ListCommand;
import seedu.hireme.logic.commands.SortCommand;
import seedu.hireme.logic.commands.StatusCommand;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.internshipapplication.DateComparator;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.NameContainsKeywordsPredicate;
import seedu.hireme.model.internshipapplication.Status;
import seedu.hireme.model.internshipapplication.StatusPredicate;
import seedu.hireme.testutil.InternshipApplicationBuilder;
import seedu.hireme.testutil.InternshipApplicationUtil;


public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add_success() throws Exception {
        InternshipApplication application = new InternshipApplicationBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(InternshipApplicationUtil
                                                .getAddCommand(application));
        assertEquals(new AddCommand(application), command);
    }


    @Test
    public void parseCommand_clear_success() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clear_failure() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClearCommand.MESSAGE_USAGE), () -> parser.parseCommand("/clear z"));
    }

    @Test
    public void parseCommand_delete_success() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), command);
    }

    @Test
    public void parseCommand_exit_success() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }
    @Test
    public void parseCommand_exit_failure() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExitCommand.MESSAGE_USAGE), () -> parser.parseCommand("/exit z"));
    }

    @Test
    public void parseCommand_find_success() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_sort_success() throws Exception {
        DateComparator comparator = new DateComparator(true);
        SortCommand command = (SortCommand) parser.parseCommand(SortCommand.COMMAND_WORD + " " + "earliest");
        assertEquals(new SortCommand(comparator), command);
    }

    @Test
    public void parseCommand_help_success() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }
    @Test
    public void parseCommand_help_failure() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand("/help z"));
    }

    @Test
    public void parseCommand_list_success() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }
    @Test
    public void parseCommand_list_failure() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE), () -> parser.parseCommand("/list z"));
    }

    @Test
    public void parseCommand_chart_success() throws Exception {
        assertTrue(parser.parseCommand(ChartCommand.COMMAND_WORD) instanceof ChartCommand);
    }
    @Test
    public void parseCommand_chart_failure() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChartCommand.MESSAGE_USAGE), () -> parser.parseCommand("/chart z"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_statusAccept_success() throws Exception {
        StatusCommand command = (StatusCommand) parser.parseCommand("/accept 1");
        assertEquals(new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.ACCEPTED), command);
    }

    @Test
    public void parseCommand_statusPending_success() throws Exception {
        StatusCommand command = (StatusCommand) parser.parseCommand("/pending 1");
        assertEquals(new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.PENDING), command);
    }

    @Test
    public void parseCommand_statusReject_success() throws Exception {
        StatusCommand command = (StatusCommand) parser.parseCommand("/reject 1");
        assertEquals(new StatusCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, Status.REJECTED), command);
    }

    @Test
    public void parseCommand_filter_success() throws Exception {
        Status status = Status.PENDING;
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + String.join(" ", status.toString()));
        assertEquals(new FilterCommand(new StatusPredicate(status)), command);
    }
}
