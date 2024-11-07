package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_MEMBER;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.DeleteSessionCommand;
import hallpointer.address.model.session.SessionName;

public class DeleteSessionCommandParserTest {

    private final DeleteSessionCommandParser parser = new DeleteSessionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteSessionCommand() {
        Set<Index> memberIndexes = Stream.of(INDEX_FIRST_MEMBER).collect(Collectors.toSet());
        assertParseSuccess(parser, " " + PREFIX_SESSION_NAME + "rehearsal " + PREFIX_MEMBER + "1",
                new DeleteSessionCommand(new SessionName("rehearsal"), memberIndexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SESSION_NAME + "rehearsal " + PREFIX_MEMBER + "a",
                String.format(MESSAGE_INVALID_INDEX, DeleteSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSessionName_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_MEMBER + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingMemberIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SESSION_NAME + "rehearsal",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithMultipleMembers_returnsDeleteSessionCommand() {
        Set<Index> memberIndexes = Stream.of(INDEX_FIRST_MEMBER, INDEX_SECOND_MEMBER).collect(Collectors.toSet());
        assertParseSuccess(parser,
                " " + PREFIX_SESSION_NAME + "rehearsal " + PREFIX_MEMBER + "1 " + PREFIX_MEMBER + "2",
                new DeleteSessionCommand(new SessionName("rehearsal"), memberIndexes));
    }
}
