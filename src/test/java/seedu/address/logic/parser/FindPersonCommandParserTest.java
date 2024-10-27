package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;

public class FindPersonCommandParserTest {

    @Test
    public void parse_noPrefix_failure() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertParseFailure(parser, " person John",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unsupportedPrefix_failure() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertParseFailure(parser, " person p/81505050",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_success() {
        FindPersonCommandParser parser = new FindPersonCommandParser();;
        assertDoesNotThrow(() -> parser.parse(" person n/John"));
    }
}
