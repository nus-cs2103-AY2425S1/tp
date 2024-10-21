package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.student.TutorialClass;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_STUDENT_ID = "001"; // leading zeros is not acceptable
    private static final String INVALID_TUTORIAL_CLASS = " "; // assuming spaces are invalid
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_STUDENT_ID = "1001";
    private static final String VALID_TUTORIAL_CLASS = "1001";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_STUDENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTutorialClass_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTutorialClass(null));
    }

    @Test
    public void parseTutorialClass_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTutorialClass(INVALID_TUTORIAL_CLASS));
    }

    @Test
    public void parseTutorialClass_validValueWithoutWhitespace_returnsTutorialClass() throws Exception {
        TutorialClass expectedTutorialClass = TutorialClass.of(VALID_TUTORIAL_CLASS);
        assertEquals(expectedTutorialClass, ParserUtil.parseTutorialClass(VALID_TUTORIAL_CLASS));
    }

    @Test
    public void parseTutorialClass_validValueWithWhitespace_returnsTrimmedTutorialClass() throws Exception {
        String tutorialClassWithWhitespace = WHITESPACE + VALID_TUTORIAL_CLASS + WHITESPACE;
        TutorialClass expectedTutorialClass = TutorialClass.of(VALID_TUTORIAL_CLASS);
        assertEquals(expectedTutorialClass, ParserUtil.parseTutorialClass(tutorialClassWithWhitespace));
    }

}
