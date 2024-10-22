package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.commands.eventcommands.EventDeleteCommand;
import seedu.address.logic.commands.eventcommands.EventNewCommand;
import seedu.address.logic.commands.eventcommands.EventViewCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.volunteercommands.FindVolunteerCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerDeleteCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    private String createCommand(String indicator, String commandWord) {
        return indicator + " " + commandWord;
    }
    @Test
    public void parseCommand_newEvent() throws Exception {
        assertTrue(
                parser.parseCommand(createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                        EventNewCommand.COMMAND_WORD)) instanceof EventNewCommand
        );
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        assertTrue(
                parser.parseCommand(createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                        EventDeleteCommand.COMMAND_WORD)) instanceof EventDeleteCommand
        );
    }

    @Test
    public void parseCommand_viewEvent() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                  EventViewCommand.COMMAND_WORD)) instanceof EventViewCommand
        );
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                    FindEventCommand.COMMAND_WORD)) instanceof FindEventCommand
        );
    }
    @Test
    public void parseCommand_newVolunteer() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
            VolunteerNewCommand.COMMAND_WORD)) instanceof VolunteerNewCommand
        );
    }

    @Test
    public void parseCommand_deleteVolunteer() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
                VolunteerDeleteCommand.COMMAND_WORD)) instanceof VolunteerDeleteCommand
        );
    }

    @Test
    public void parseCommand_viewVolunteer() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
            VolunteerViewCommand.COMMAND_WORD)) instanceof VolunteerViewCommand
        );
    }

    @Test void parseCommand_findVolunteer() throws Exception {
        assertTrue(
            parser.parseCommand(createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
                    FindVolunteerCommand.COMMAND_WORD)) instanceof FindVolunteerCommand
        );
    }

    @Test
    public void parseCommand_assign() throws Exception {
        assertTrue(parser.parseCommand(AssignCommand.COMMAND_WORD) instanceof AssignCommand);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        assertTrue(parser.parseCommand(UnassignCommand.COMMAND_WORD) instanceof UnassignCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
