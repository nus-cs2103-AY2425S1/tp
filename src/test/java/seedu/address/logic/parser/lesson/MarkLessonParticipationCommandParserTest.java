package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_POINTS;
import static seedu.address.logic.parser.lesson.MarkLessonParticipationCommandParser.MESSAGE_TOO_MANY_PARTICIPATION_ARGUMENTS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.MarkLessonParticipationCommand;
import seedu.address.model.student.Name;

public class MarkLessonParticipationCommandParserTest {
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final List<Name> VALID_NAMES = List.of(new Name("Alice Tan"), new Name("Benson Son"));
    private static final int VALID_PARTICIPATION = 1;
    private final MarkLessonParticipationCommandParser parser = new MarkLessonParticipationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        MarkLessonParticipationCommand expectedCommand = new MarkLessonParticipationCommand(
                VALID_INDEX, VALID_NAMES, VALID_PARTICIPATION);
        MarkLessonParticipationCommand expectedCommand2 = new MarkLessonParticipationCommand(
                VALID_INDEX, VALID_NAMES, 0);
        MarkLessonParticipationCommand expectedCommand3 = new MarkLessonParticipationCommand(
                VALID_INDEX, VALID_NAMES, 100);

        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son pt/1", expectedCommand);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son pt/0", expectedCommand2);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son pt/100", expectedCommand3);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MarkLessonParticipationCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, " n/Alice Tan n/Benson Son pt/1", expectedMessage);
        assertParseFailure(parser, " 1 pt/1", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, " 0 n/Alice Tan n/Benson Son pt/1", expectedMessage);
        assertParseFailure(parser, " -1 n/Alice Tan n/Benson Son pt/1", expectedMessage);
        assertParseFailure(parser, " 1 2 n/Alice Tan n/Benson Son pt/1", expectedMessage);
        assertParseFailure(parser, " a n/Alice Tan n/Benson Son pt/1", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, " 1 n/Alic*@f n/Benson Son pt/1", expectedMessage);
        // note that use of ; is not yet supported
        assertParseFailure(parser, " 1 n/Alice Tan;Benson Son pt/1", expectedMessage);
    }

    @Test
    public void parse_participationOutOfIntegerBounds_failure() {
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son pt/7122647915963579", MESSAGE_INVALID_POINTS);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son pt/-712264795963579", MESSAGE_INVALID_POINTS);
    }

    @Test
    public void parse_tooManyParticipationFields_failure() {
        String expectedMessage = MESSAGE_TOO_MANY_PARTICIPATION_ARGUMENTS;
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son pt/1 pt/1", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son pt/1 pt/100", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son pt/79 pt/13", expectedMessage);
    }
}
