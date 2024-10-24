package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.logic.commands.volunteercommands.VolunteerNewCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.TypicalIndexes;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    private String createCommand(String indicator, String commandWord) {
        return indicator + " " + commandWord;
    }

    private String createCommand(String indicator, String commandWord, String commandArgument) {
        return indicator + " " + commandWord + " " + commandArgument;
    }

    @Test
    public void parseCommand_newEvent() throws Exception {
        String eventDetails = "n/Food collection l/NTUC d/2024-11-29 s/00:00 e/23:59";
        Event event = new Event(new EventName("Food collection"), new Location("NTUC"),
                new seedu.address.model.event.Date("2024-11-29"), new Time("00:00"), new Time("23:59"));
        EventNewCommand command = (EventNewCommand) parser.parseCommand(
                createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR, EventNewCommand.COMMAND_WORD, eventDetails));
        assertEquals(new EventNewCommand(event), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        EventDeleteCommand command = (EventDeleteCommand) parser.parseCommand(
                createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                        EventDeleteCommand.COMMAND_WORD, "1"));
        assertEquals(new EventDeleteCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_viewEvent() throws Exception {
        EventViewCommand command = (EventViewCommand) parser.parseCommand(
                createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                        EventViewCommand.COMMAND_WORD, "1"));
        assertEquals(new EventViewCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        String keyword = "food";
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                createCommand(AddressBookParser.EVENT_COMMAND_INDICATOR,
                    FindEventCommand.COMMAND_WORD, keyword));
        assertEquals(new FindEventCommand(keyword), command);
    }
    @Test
    public void parseCommand_newVolunteer() throws Exception {
        String volunteerDetails = "n/John Doe p/98765432 em/johndoe@example.com d/2020-10-10";
        Volunteer volunteer = new Volunteer(new Name("John Doe"), new Phone("98765432"),
                new Email("johndoe@example.com"), new Date("2020-10-10"));
        VolunteerNewCommand command = (VolunteerNewCommand) parser.parseCommand(
                createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
            VolunteerNewCommand.COMMAND_WORD, volunteerDetails));
        assertEquals(new VolunteerNewCommand(volunteer), command);
    }

    @Test
    public void parseCommand_deleteVolunteer() throws Exception {
        VolunteerDeleteCommand command = (VolunteerDeleteCommand) parser.parseCommand(
                createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
                VolunteerDeleteCommand.COMMAND_WORD, "1"));
        assertEquals(new VolunteerDeleteCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_viewVolunteer() throws Exception {
        VolunteerViewCommand command = (VolunteerViewCommand) parser.parseCommand(
                createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
                        VolunteerViewCommand.COMMAND_WORD, "1"));
        assertEquals(new VolunteerViewCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test void parseCommand_findVolunteer() throws Exception {
        String keyword = "john";
        FindVolunteerCommand command = (FindVolunteerCommand) parser.parseCommand(
                createCommand(AddressBookParser.VOLUNTEER_COMMAND_INDICATOR,
                    FindVolunteerCommand.COMMAND_WORD, keyword));
        assertEquals(new FindVolunteerCommand(keyword), command);
    }

    @Test
    public void parseCommand_assign() throws Exception {
        String commandDetails = "e/ 1 v/ 1";
        AssignCommand command = (AssignCommand) parser.parseCommand(
                createCommand(AssignCommand.COMMAND_WORD, commandDetails));
        assertEquals(new AssignCommand(TypicalIndexes.INDEX_FIRST, TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        String commandDetails = "e/ 1 v/ 1";
        UnassignCommand command = (UnassignCommand) parser.parseCommand(
                createCommand(UnassignCommand.COMMAND_WORD, commandDetails));
        assertEquals(new UnassignCommand(TypicalIndexes.INDEX_FIRST, TypicalIndexes.INDEX_FIRST), command);
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
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(
            ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand")
        );
    }
}
