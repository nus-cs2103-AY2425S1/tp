package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_DUPLICATE_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_LESSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_SECOND_LESSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.DeleteLessonCommand;

public class DeleteLessonCommandParserTest {

    private DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteLesson_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON_INDEX + "1";
        List<Index> indices = List.of(INDEX_FIRST_LESSON);
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, indices);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_deleteMultipleLessons_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_LESSON_INDEX + "1"
                + " " + PREFIX_LESSON_INDEX + "2";
        List<Index> indices = List.of(INDEX_FIRST_LESSON, INDEX_SECOND_LESSON);
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, indices);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyAfterCommandWord_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        assertParseFailure(parser, "0 " + PREFIX_LESSON_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, "0")));
    }

    @Test
    public void parse_invalidCommandType_throwsParseException() {
        // Incorrect prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + "-x 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX));

        // Missing prefix but extra arguments
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " a/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX));
    }

    @Test
    public void parse_missingCommandType_throwsParseException() {
        // Missing prefix entirely
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX));

        // Only person index without lesson index
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX));
    }

    @Test
    public void parse_blankLessonIndex_throwsParseException() {
        // empty lesson index
        assertParseFailure(parser, "1 li/",
                MESSAGE_MISSING_LESSON_INDEX);

        // whitespace lesson index
        assertParseFailure(parser, "1 li/ ",
                MESSAGE_MISSING_LESSON_INDEX);
    }

    @Test
    public void parse_invalidLessonIndexFormat_throwsParseException() {
        // Non-numeric lesson index
        String userInput = "1 li/abc";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_LESSON_INDEX_FORMAT));

        // Zero lesson index
        userInput = "1 li/0";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_LESSON_INDEX_FORMAT));

        // Negative lesson index
        userInput = "1 li/-1";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_LESSON_INDEX_FORMAT));

        // Decimal lesson index
        userInput = "1 li/1.5";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_LESSON_INDEX_FORMAT));
    }

    @Test
    public void parse_duplicateLessonIndices_throwsParseException() {
        assertParseFailure(parser, "1 li/1 li/1",
                MESSAGE_DUPLICATE_LESSON_INDEX);
    }

    @Test
    public void parse_validArgsWithExtraSpaces_returnsDeleteLessonCommand() {
        Index targetPersonIndex = INDEX_FIRST_PERSON;
        List<Index> lessonIndices = new ArrayList<>();
        lessonIndices.add(Index.fromOneBased(1));
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(targetPersonIndex, lessonIndices);
        assertParseSuccess(parser, "  1    li/1   ", expectedCommand);
    }

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() {
        // single lesson index
        Index targetPersonIndex = INDEX_FIRST_PERSON;
        List<Index> lessonIndices = new ArrayList<>();
        lessonIndices.add(Index.fromOneBased(1));
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(targetPersonIndex, lessonIndices);
        assertParseSuccess(parser, "1 li/1", expectedCommand);

        // multiple lesson indices
        lessonIndices.add(Index.fromOneBased(2));
        expectedCommand = new DeleteLessonCommand(targetPersonIndex, lessonIndices);
        assertParseSuccess(parser, "1 li/1 li/2", expectedCommand);
    }
}
