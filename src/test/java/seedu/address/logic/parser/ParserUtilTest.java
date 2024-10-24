package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.parsePathWithCheck;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_MODULE = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_MODULE = "CS2103T";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String INVALID_PATH_1 = "notjson";
    private static final String INVALID_PATH_2 = "have/";
    private static final String VALID_PATH = "TestingParser.json";

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
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
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
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModule((String) null));
    }

    @Test
    public void parseModule_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModule(INVALID_MODULE));
    }
    @Test
    public void parseModule_validValueWithoutWhitespace_returnsModule() throws Exception {
        Module expectedModule = new Module(VALID_MODULE);
        assertEquals(expectedModule, ParserUtil.parseModule(VALID_MODULE));
    }

    @Test
    public void parseModule_validValueWithWhitespace_returnsTrimmedModule() throws Exception {
        String moduleWithWhitespace = WHITESPACE + VALID_MODULE + WHITESPACE;
        Module expectedModule = new Module(VALID_MODULE);
        assertEquals(expectedModule, ParserUtil.parseModule(moduleWithWhitespace));
    }

    @Test
    public void parseModules_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModules(null));
    }

    @Test
    public void parseModules_collectionWithInvalidModules_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModules(Arrays.asList(VALID_MODULE, INVALID_MODULE)));
    }

    @Test
    public void parseModules_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseModules(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseModules_collectionWithValidModules_returnsModuleSet() throws Exception {
        Set<Module> actualModuleSet = ParserUtil.parseModules(Arrays.asList(VALID_MODULE, VALID_MODULE));
        Set<Module> expectedModuleSet = new HashSet<>(Arrays.asList(new Module(VALID_MODULE),
                new Module(VALID_MODULE)));

        assertEquals(expectedModuleSet, actualModuleSet);
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
    @Test
    public void parseGrade_validInput_success() throws Exception {
        assertEquals(100, ParserUtil.parseGrade("100"));
    }

    @Test
    public void parseGrade_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, Module.GRADE_CONSTRAINTS, () -> ParserUtil.parseGrade("101"));
    }
    @Test
    public void parsePathWithCheck_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePathWithCheck((String) null));
    }

    @Test
    public void parsePathWithCheck_invalid_path() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePathWithCheck(INVALID_PATH_1));
        assertThrows(ParseException.class, () -> ParserUtil.parsePathWithCheck(INVALID_PATH_2));
        assertThrows(ParseException.class, () -> ParserUtil.parsePathWithCheck(VALID_PATH));
    }

    @Test
    public void parsePathWithCheck_valid_path() throws Exception {
        Path tempDir = Paths.get("archived");
        if (!Files.exists(tempDir)) {
            tempDir = Files.createDirectory(tempDir);
        }
        Path tempFile = tempDir.resolve("TestingParser.json");
        Files.createFile(tempFile);
        assertEquals(tempFile, parsePathWithCheck(VALID_PATH));
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void parsePathWithoutCheck_invalid_path() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePathWithoutCheck(INVALID_PATH_1));
        assertThrows(ParseException.class, () -> ParserUtil.parsePathWithoutCheck(INVALID_PATH_2));
    }


}
