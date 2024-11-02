package tuteez.logic.parser;


import static tuteez.logic.Messages.MESSAGE_DUPLICATE_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_INVALID_ADDLESSON_PREFIX;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_DELETELESSON_PREFIX;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_FIELD_PREFIX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX;
import static tuteez.logic.Messages.MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX;
import static tuteez.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_MONDAY;
import static tuteez.logic.commands.CommandTestUtil.VALID_LESSON_WEDNESDAY;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.AddLessonCommand;
import tuteez.logic.commands.DeleteLessonCommand;
import tuteez.logic.commands.LessonCommand;
import tuteez.model.person.lesson.Lesson;

public class LessonCommandParserTest {

    private final LessonCommandParser addLessonParser = new LessonCommandParser("addlesson");
    private final LessonCommandParser deleteLessonParser = new LessonCommandParser("deletelesson");
    private final LessonCommandParser addLessonParserAlt = new LessonCommandParser("addlsn");
    private final LessonCommandParser deleteLessonParserAlt = new LessonCommandParser("dellsn");

    @Test
    public void parse_emptyPersonIndex_throwsParseException() {
        // Test empty string for Person Index using addLessonParser
        assertParseFailure(addLessonParser, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(addLessonParser, "  l/monday 0900-1000",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        // deleteLessonParser used
        assertParseFailure(deleteLessonParser, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParser, "  li/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        // addLessonParserAlt used
        assertParseFailure(addLessonParserAlt, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        // deleteLessonParserAlt used
        assertParseFailure(deleteLessonParserAlt, "  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PERSON_INDEX + "\n"
                        + LessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Test invalid index formats - boundary testing
        assertParseFailure(addLessonParser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(addLessonParserAlt, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParserAlt, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));

        // Test negative index
        assertParseFailure(addLessonParser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParser, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(addLessonParserAlt, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParserAlt, "-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));

        // Test zero index
        assertParseFailure(addLessonParser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n" + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(addLessonParserAlt, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
        assertParseFailure(deleteLessonParserAlt, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_PERSON_INDEX_FORMAT + "\n"
                        + LessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseAddLesson_validArgs_returnsAddLessonCommand() {
        // Test single lesson addition - basic success case
        Lesson lesson1 = new Lesson(VALID_LESSON_MONDAY);
        AddLessonCommand expectedCommand = new AddLessonCommand(INDEX_FIRST_PERSON, Arrays.asList(lesson1));
        assertParseSuccess(addLessonParser, "1 l/" + VALID_LESSON_MONDAY, expectedCommand);

        // Test multiple lesson addition - testing list handling
        Lesson lesson2 = new Lesson(VALID_LESSON_WEDNESDAY);
        AddLessonCommand expectedCommandMultiple =
                new AddLessonCommand(INDEX_FIRST_PERSON, Arrays.asList(lesson1, lesson2));
        assertParseSuccess(addLessonParser, "1 l/" + VALID_LESSON_MONDAY + " l/" + VALID_LESSON_WEDNESDAY,
                expectedCommandMultiple);
    }

    @Test
    public void parseAddLesson_invalidPrefix_throwsParseException() {
        // Test wrong prefix usage - error case
        assertParseFailure(addLessonParser, "1 li/1", MESSAGE_INVALID_ADDLESSON_PREFIX);

        // Test missing l/ prefix
        assertParseFailure(addLessonParser, "1",
                String.format(MESSAGE_MISSING_LESSON_FIELD_PREFIX));

        // Test missing prefix - error case
        assertParseFailure(addLessonParser, "1 " + VALID_LESSON_MONDAY, MESSAGE_MISSING_LESSON_FIELD_PREFIX);
    }

    @Test
    public void parseDeleteLesson_validArgs_returnsDeleteLessonCommand() {
        // Test single lesson deletion - basic success case
        List<Index> indices = Arrays.asList(Index.fromOneBased(1));
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(INDEX_FIRST_PERSON, indices);
        assertParseSuccess(deleteLessonParser, "1 li/1", expectedCommand);

        // Test multiple lesson deletion - testing list handling
        List<Index> multipleIndices = Arrays.asList(Index.fromOneBased(1), Index.fromOneBased(2));
        DeleteLessonCommand expectedCommandMultiple =
                new DeleteLessonCommand(INDEX_FIRST_PERSON, multipleIndices);
        assertParseSuccess(deleteLessonParser, "1 li/1 li/2", expectedCommandMultiple);
    }

    @Test
    public void parseDeleteLesson_invalidPrefix_throwsParseException() {
        // Test wrong prefix usage - error case
        assertParseFailure(deleteLessonParser, "1 l/monday 0900-1000", MESSAGE_INVALID_DELETELESSON_PREFIX);

        // Test missing index - error case
        assertParseFailure(deleteLessonParser, "1 li/", MESSAGE_MISSING_LESSON_INDEX);

        // Test missing prefix - error case
        assertParseFailure(deleteLessonParser, "1 1", MESSAGE_MISSING_LESSON_INDEX_FIELD_PREFIX);
    }

    @Test
    public void parseDeleteLesson_duplicateIndices_throwsParseException() {
        // Test duplicate indices - error case
        assertParseFailure(deleteLessonParser, "1 li/1 li/1", MESSAGE_DUPLICATE_LESSON_INDEX);
    }
}
