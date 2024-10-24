package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.SwitchParserModeCommand;
import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.buyer.BuyerBuilder;
import seedu.address.testutil.buyer.BuyerUtil;
import seedu.address.testutil.meetup.MeetUpBuilder;
import seedu.address.testutil.meetup.MeetUpUtil;

public class AbcliParserTest {

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(AbcliParser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(AbcliParser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(AbcliParser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(AbcliParser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_switch() throws Exception {
        SwitchParserModeCommand switchParserModeCommand = (SwitchParserModeCommand) AbcliParser.parseCommand(
                SwitchParserModeCommand.COMMAND_WORD + " m");
        assertEquals(new SwitchParserModeCommand(ParserMode.MEETUP), switchParserModeCommand);

        MeetUp meetUp = new MeetUpBuilder().build();
        AbcliParser.switchMode(ParserMode.MEETUP);
        AddCommand addCommandMeetUp = (AddCommand) AbcliParser.parseCommand(MeetUpUtil.getAddMeetUpCommand(meetUp));
        assertEquals(new AddCommand(meetUp), addCommandMeetUp);

        Buyer buyer = new BuyerBuilder().build();
        AbcliParser.switchMode(ParserMode.BUYER);
        seedu.address.logic.commands.buyer.AddCommand addCommandBuyer = (seedu.address.logic.commands.buyer.AddCommand)
                AbcliParser.parseCommand(BuyerUtil.getAddBuyerCommand(buyer));
        assertEquals(new seedu.address.logic.commands.buyer.AddCommand(buyer), addCommandBuyer);
    }

    @Test
    public void switchMode_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AbcliParser.switchMode(null));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> AbcliParser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                -> AbcliParser.parseCommand("unknownCommand"));
    }
}
