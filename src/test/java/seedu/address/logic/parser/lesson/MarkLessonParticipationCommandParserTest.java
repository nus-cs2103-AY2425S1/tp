package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_PARTICIPATION;
import static seedu.address.logic.parser.lesson.MarkLessonParticipationCommandParser.MESSAGE_TOO_MANY_PARTICIPATION_ARGUMENTS;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.MarkLessonParticipationCommand;
import seedu.address.model.student.Name;

public class MarkLessonParticipationCommandParserTest {
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final Name ALICE_NAME = ALICE.getName();
    private static final Name BENSON_NAME = BENSON.getName();
    private static final List<Name> VALID_NAMES = List.of(ALICE_NAME, BENSON_NAME);
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

        assertParseSuccess(parser, " 1 n/" + ALICE_NAME + " n/Benson Meier pt/1", expectedCommand);
        // leading 0s should be ok
        assertParseSuccess(parser, " 1 n/" + ALICE_NAME + " n/Benson Meier pt/001", expectedCommand);
        assertParseSuccess(parser, " 1 n/" + ALICE_NAME + " n/Benson Meier pt/0", expectedCommand2);
        assertParseSuccess(parser, " 1 n/Alice Pauline n/Benson Meier pt/100", expectedCommand3);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MarkLessonParticipationCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, " n/" + ALICE_NAME + " n/Benson Meier pt/1", expectedMessage);
        assertParseFailure(parser, " 1 pt/1", expectedMessage);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/Benson Meier", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, " 0 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1", expectedMessage);
        assertParseFailure(parser, " -1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1", expectedMessage);
        assertParseFailure(parser, " 1 2 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1", expectedMessage);
        assertParseFailure(parser, " a n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, " 1 n/Alic*@f n/" + BENSON_NAME + " pt/1", expectedMessage);
        // note that use of ; is not yet supported
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + ";" + BENSON_NAME + " pt/1", expectedMessage);
    }

    @Test
    public void parse_invalidParticipation_failure() {
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME
                + " pt/1.0", MESSAGE_INVALID_PARTICIPATION);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME
                + " pt/0.5", MESSAGE_INVALID_PARTICIPATION);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME
                + " pt/0.0", MESSAGE_INVALID_PARTICIPATION);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/7122647915963579",
                MESSAGE_INVALID_PARTICIPATION);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/-712264795963579",
                MESSAGE_INVALID_PARTICIPATION);
    }

    @Test
    public void parse_participationOutOfIntegerBounds_failure() {
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/7122647915963579",
                MESSAGE_INVALID_PARTICIPATION);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/-712264795963579",
                MESSAGE_INVALID_PARTICIPATION);
    }

    @Test
    public void parse_tooManyParticipationFields_failure() {
        String expectedMessage = MESSAGE_TOO_MANY_PARTICIPATION_ARGUMENTS;
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1 pt/1", expectedMessage);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/1 pt/100", expectedMessage);
        assertParseFailure(parser, " 1 n/" + ALICE_NAME + " n/" + BENSON_NAME + " pt/79 pt/13", expectedMessage);
    }
}
