package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.commands.FindSessionCommand;
import hallpointer.address.model.member.SessionContainsKeywordsPredicate;

public class FindSessionCommandParserTest {

    private final FindSessionCommandParser parser = new FindSessionCommandParser();

    @Test
    public void parse_validArgs_returnsFindSessionCommand() throws Exception {
        FindSessionCommand command = parser.parse("rehearsal match");
        SessionContainsKeywordsPredicate predicate = new SessionContainsKeywordsPredicate(
                Arrays.asList("rehearsal", "match"));
        assertEquals(new FindSessionCommand(predicate), command);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSessionCommand.MESSAGE_USAGE));
    }
}

