package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_FIELD_PREFIX;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_MONDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_TUESDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_WEDNESDAY;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddLessonCommand;
import tuteez.model.person.lesson.Lesson;


public class AddLessonCommandParserTest {

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_addSingleLesson_success() {
        List<Lesson> lessons = Arrays.asList(new Lesson(VALID_LESSON_MONDAY));
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON + VALID_LESSON_MONDAY;
        AddLessonCommand expectedCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessons);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_addMultipleLessons_success() {
        List<Lesson> lessons = Arrays.asList(new Lesson(VALID_LESSON_MONDAY), new Lesson(VALID_LESSON_WEDNESDAY));
        String userInput = INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_LESSON + VALID_LESSON_MONDAY
                + " " + PREFIX_LESSON + VALID_LESSON_WEDNESDAY;
        AddLessonCommand expectedCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessons);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnlyAfterCommandWord_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        String userInput = "0 " + PREFIX_LESSON + VALID_LESSON_MONDAY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, "0")));
    }

    @Test
    public void parse_missingPersonIndex_throwsParseException() {
        // no person index, only lesson
        assertParseFailure(parser, " l/monday 1000-1200",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX));
    }

    @Test
    public void parse_missingLessonPrefix_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + VALID_LESSON_MONDAY;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_LESSON_FIELD_PREFIX));
    }

    @Test
    public void parse_emptyLessonContent_throwsParseException() {
        String userInput1 = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON;
        String userInput2 = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON + " ";
        assertParseFailure(parser, userInput1, Lesson.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput2, Lesson.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidLessonFormat_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LESSON + "InvalidLessonFormat";
        assertParseFailure(parser, userInput, Lesson.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateLessons_warningHandledInCommand() {
        List<Lesson> lessons = Arrays.asList(new Lesson(VALID_LESSON_MONDAY), new Lesson(VALID_LESSON_MONDAY));
        String userInput = INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_LESSON + VALID_LESSON_MONDAY
                + " " + PREFIX_LESSON + VALID_LESSON_MONDAY;
        AddLessonCommand expectedCommand = new AddLessonCommand(INDEX_FIRST_PERSON, lessons);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multiplePrefixes_returnsAddLessonCommand() {
        List<Lesson> lessonList = Arrays.asList(
                new Lesson(VALID_LESSON_MONDAY),
                new Lesson(VALID_LESSON_MONDAY),
                new Lesson(VALID_LESSON_TUESDAY)
        );
        AddLessonCommand expectedCommand = new AddLessonCommand(Index.fromOneBased(1), lessonList);

        // multiple l/ prefixes
        assertParseSuccess(parser, String.format("1 l/%s l/%s l/%s",
                        VALID_LESSON_MONDAY, VALID_LESSON_MONDAY, VALID_LESSON_TUESDAY),
                expectedCommand);
    }

    @Test
    public void parse_validArgsWithExtraSpaces_returnsAddLessonCommand() {
        Index targetPersonIndex = INDEX_FIRST_PERSON;
        Lesson lesson = new Lesson(VALID_LESSON_MONDAY);
        List<Lesson> lessonList = Arrays.asList(lesson);
        AddLessonCommand expectedCommand = new AddLessonCommand(targetPersonIndex, lessonList);

        // extra spaces around person index and lesson
        assertParseSuccess(parser, "  1    l/" + VALID_LESSON_MONDAY + "   ", expectedCommand);
    }
}
