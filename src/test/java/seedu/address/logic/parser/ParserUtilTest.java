package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.edit.AddModuleRoleOperation.AddModuleRoleDescriptor;
import static seedu.address.logic.commands.edit.DeleteModuleRoleOperation.DeleteModuleRoleDescriptor;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.edit.AddModuleRoleOperation;
import seedu.address.logic.commands.edit.DeleteModuleRoleOperation;
import seedu.address.logic.commands.edit.EditModuleRoleOperation;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoleType;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_PHONE = "+65p1";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_ROLETYPE_KEYWORD = "#prof";
    private static final String INVALID_DESCRIPTION = "a".repeat(501);

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_ROLETYPE_KEYWORD_1 = "";
    private static final String VALID_ROLETYPE_KEYWORD_2 = "Student";
    private static final String VALID_ROLETYPE_KEYWORD_3 = "TA";
    private static final String VALID_ROLETYPE_KEYWORD_4 = "Tutor";
    private static final String VALID_ROLETYPE_KEYWORD_5 = "Professor";
    private static final String VALID_ROLETYPE_KEYWORD_6 = "Prof";
    private static final String VALID_DESCRIPTION_TEXT = "This is a description";
    private static final String VALID_DESCRIPTION_EMPTY = "";

    private static final String INVALID_MODULE_ROLE_PAIR_INVALID_MODULE_CODE = "1234-student";
    private static final String INVALID_MODULE_ROLE_PAIR_INVALID_ROLE_TYPE = "CS1101S-role";
    private static final String INVALID_MODULE_ROLE_PAIR_MISSING_ROLE_TYPE = "CS1101S-";
    private static final String VALID_MODULE_ROLE_PAIR = "CS1101S-student";
    private static final String VALID_MODULE_ROLE_PAIR_ONLY_MODULE_CODE = "CS1101S";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        String index = Long.toString(Integer.MAX_VALUE + 1);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_INDEX, index), ()
            -> ParserUtil.parseIndex(index));
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
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
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
    public void parseRoleType_validStudentKeyword1_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_1);
        RoleType expectedEnumValue = RoleType.STUDENT;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validStudentKeyword2_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_2);
        RoleType expectedEnumValue = RoleType.STUDENT;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validTutorKeyword1_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_3);
        RoleType expectedEnumValue = RoleType.TUTOR;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validTutorKeyword2_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_4);
        RoleType expectedEnumValue = RoleType.TUTOR;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validProfKeyword1_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_5);
        RoleType expectedEnumValue = RoleType.PROFESSOR;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validProfKeyword2_returnsRoleTypeEnum() throws Exception {
        RoleType actualEnumValue = ParserUtil.parseRoleType(VALID_ROLETYPE_KEYWORD_6);
        RoleType expectedEnumValue = RoleType.PROFESSOR;

        assertEquals(expectedEnumValue, actualEnumValue);
    }

    @Test
    public void parseRoleType_validProfKeyword2_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            ParserUtil.parseRoleType(INVALID_ROLETYPE_KEYWORD);
        });
    }

    @Test
    public void parseModuleRolePair_validInput_returnsModuleRolePair() throws Exception {
        ModuleRolePair result = ParserUtil.parseModuleRolePair(VALID_MODULE_ROLE_PAIR);
        ModuleRolePair expected = new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT);
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePair_onlyModuleCodeImpliesStudent_returnsModuleRolePair() throws Exception {
        ModuleRolePair result = ParserUtil.parseModuleRolePair(VALID_MODULE_ROLE_PAIR_ONLY_MODULE_CODE);
        ModuleRolePair expected = new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT);
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePair_invalidModuleCode_throwsParseException() throws Exception {
        assertThrows(ParseException.class, ModuleCode.MESSAGE_CONSTRAINTS, () -> {
            ParserUtil.parseModuleRolePair(INVALID_MODULE_ROLE_PAIR_INVALID_MODULE_CODE);
        });
    }

    @Test
    public void parseModuleRolePair_invalidRoleType_throwsParseException() throws Exception {
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_CONSTRAINTS, () -> {
            ParserUtil.parseModuleRolePair(INVALID_MODULE_ROLE_PAIR_INVALID_ROLE_TYPE);
        });
    }

    @Test
    public void parseModuleRolePair_missingRoleType_throwsParseException() throws Exception {
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_CONSTRAINTS, () -> {
            ParserUtil.parseModuleRolePair(INVALID_MODULE_ROLE_PAIR_MISSING_ROLE_TYPE);
        });
    }

    @Test
    public void parseModuleRoleMap_singleModuleRolePair_returnsModuleRoleMap() throws Exception {
        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();
        hashMap.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        ModuleRoleMap expected = new ModuleRoleMap(hashMap);

        List<String> input = Arrays.asList("CS1101S-student");
        ModuleRoleMap result = ParserUtil.parseModuleRoleMap(input);

        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRoleMap_multipleModuleRolePairs_returnsModuleRoleMap() throws Exception {
        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();
        hashMap.put(new ModuleCode("CS1101S"), RoleType.STUDENT);
        hashMap.put(new ModuleCode("MA1521"), RoleType.STUDENT);
        ModuleRoleMap expected = new ModuleRoleMap(hashMap);

        List<String> input = Arrays.asList("CS1101S-student", "MA1521-student");
        ModuleRoleMap result = ParserUtil.parseModuleRoleMap(input);

        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRoleMap_duplicateModuleCodeDifferentRoles_throwsParseException() throws Exception {
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            List<String> input = Arrays.asList("CS1101S-student", "CS1101S-prof");
            ModuleRoleMap result = ParserUtil.parseModuleRoleMap(input);
        });
    }

    @Test
    public void parseModuleRoleMap_duplicateModuleCodeSameRoles_throwsParseException() throws Exception {
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            List<String> input = Arrays.asList("CS1101S-student", "CS1101S-student");
            ModuleRoleMap result = ParserUtil.parseModuleRoleMap(input);
        });
    }

    @Test
    public void parseModuleRolePairs_noModuleRolePairs_returnsEmptyList() throws Exception {
        List<ModuleRolePair> expected = Collections.emptyList();
        List<ModuleRolePair> result = ParserUtil.parseModuleRolePairs(Collections.emptyList());
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePairs_singleModuleRolePair_returnsSingletonList() throws Exception {
        List<ModuleRolePair> expected = List.of(new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT));
        List<ModuleRolePair> result = ParserUtil.parseModuleRolePairs(List.of("CS1101S-student"));
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePairs_multipleModuleRolePairs_returnsList() throws Exception {
        List<ModuleRolePair> expected = List.of(
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
            new ModuleRolePair(new ModuleCode("MA1521"), RoleType.TUTOR)
        );
        List<ModuleRolePair> result = ParserUtil.parseModuleRolePairs(List.of("CS1101S-student", "MA1521-tutor"));
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePairs_duplicateModuleCodeDifferentRoles_returnsList() throws Exception {
        List<ModuleRolePair> expected = List.of(
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.TUTOR)
        );
        List<ModuleRolePair> result = ParserUtil.parseModuleRolePairs(List.of("CS1101S-student", "CS1101S-tutor"));
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePairs_duplicateModuleCodeSameRoles_returnsList() throws Exception {
        List<ModuleRolePair> expected = List.of(
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT)
        );
        List<ModuleRolePair> result = ParserUtil.parseModuleRolePairs(List.of("CS1101S-student", "CS1101S-student"));
        assertEquals(expected, result);
    }

    @Test
    public void parseModuleRolePairs_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleRolePairs(null));
    }

    @Test
    public void parseModuleRolePairs_invalidModuleRolePair_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleRolePairs(
                Arrays.asList(INVALID_MODULE_ROLE_PAIR_INVALID_MODULE_CODE)));
    }

    @Test
    public void parseEditModuleRoleOperation_validAddOperation_returnsAddModuleRoleOperation() throws Exception {
        String input = "+CS1101S-student MA1521-TA";
        EditModuleRoleOperation result = ParserUtil.parseEditModuleRoleOperation(input);

        AddModuleRoleDescriptor expectedDescriptor = new AddModuleRoleDescriptor(List.of(
            new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
            new ModuleRolePair(new ModuleCode("MA1521"), RoleType.TUTOR)
        ));
        AddModuleRoleOperation expected = new AddModuleRoleOperation(expectedDescriptor);

        assertEquals(expected, result);

        // with whitespaces and tabs
        String input2 = " + CS1101S   MA1521-TA ";
        EditModuleRoleOperation result2 = ParserUtil.parseEditModuleRoleOperation(input2);

        assertEquals(expected, result2);
    }

    @Test
    public void parseEditModuleRoleOperation_validDeleteOperation_returnsDeleteModuleRoleOperation() throws Exception {
        String input = "-CS1101S-student CS1231S-TA MA1521";
        EditModuleRoleOperation result = ParserUtil.parseEditModuleRoleOperation(input);

        DeleteModuleRoleDescriptor expectedDescriptor = new DeleteModuleRoleDescriptor(
                List.of(
                        new ModuleRolePair(new ModuleCode("CS1101S"), RoleType.STUDENT),
                        new ModuleRolePair(new ModuleCode("CS1231S"), RoleType.TUTOR)
                ),
                List.of(new ModuleCode("MA1521"))
        );
        EditModuleRoleOperation expected = new DeleteModuleRoleOperation(expectedDescriptor);

        assertEquals(expected, result);
    }

    @Test
    public void parseEditModuleRoleOperation_invalidAddOperation_throwsParseException() {
        // Invalid operation type
        String invalidOperationType = "=CS1101S-student";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidOperationType));
        // Invalid format
        String invalidFormat = "+CS1101S-student-";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidFormat));
        // Invalid module code
        String invalidModuleCode = "+1234-student";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidModuleCode));
        // Invalid role type
        String invalidRoleType = "+CS1101S-role";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidRoleType));
    }

    @Test
    public void parseEditModuleRoleOperation_invalidDeleteOperation_throwsParseException() {
        // Invalid operation type
        String invalidOperationType = "=CS1101S-student";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidOperationType));
        // Invalid format
        String invalidFormat = "-CS1101S-student-";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidFormat));
        // Invalid module code
        String invalidModuleCode = "-1234-student";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidModuleCode));
        // Invalid role type
        String invalidRoleType = "-CS1101S-role";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(invalidRoleType));
        // Mixed add and delete
        String mixedAddAndDelete = "+CS1101S-student -MA1521-student";
        assertThrows(ParseException.class, () -> ParserUtil.parseEditModuleRoleOperation(mixedAddAndDelete));
    }

    @Test
    public void parseDescription_validDescription_returnsDescription() throws Exception {
        Description expectedDescription1 = new Description(VALID_DESCRIPTION_TEXT);
        assertEquals(expectedDescription1, ParserUtil.parseDescription(VALID_DESCRIPTION_TEXT));
    }

    @Test
    public void parseDescription_emptyDescription_returnsDescription() throws Exception {
        Description expectedDescription1 = new Description(VALID_DESCRIPTION_TEXT);
        assertEquals(expectedDescription1, ParserUtil.parseDescription(VALID_DESCRIPTION_TEXT));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseEditModuleRoleOperation_duplicateModuleCode_throwsParseException() {
        // same module code and role type
        String input = "+CS1101S-student CS1101S-student";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input);
        });
        String input2 = "-CS1101S-student CS1101S-student";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input2);
        });
        // same module code different role type
        String input3 = "+CS1101S-student CS1101S-tutor";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input3);
        });
        String input4 = "-CS1101S-student CS1101S-tutor";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input4);
        });
        // same module code, role type unspecified
        String input5 = "+CS1101S CS1101S";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input5);
        });
        String input6 = "-CS1101S CS1101S";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input6);
        });
        // same module role, some role type unspecified and some specified
        String input7 = "+CS1101S-student CS1101S";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input7);
        });
        String input8 = "-CS1101S-student CS1101S";
        assertThrows(ParseException.class, ModuleRoleMap.MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS, () -> {
            ParserUtil.parseEditModuleRoleOperation(input8);
        });
    }
}
