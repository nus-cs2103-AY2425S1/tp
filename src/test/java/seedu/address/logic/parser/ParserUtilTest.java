package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Ward;

public class ParserUtilTest {
    private static final String INVALID_NAME = "Rachel!";
    private static final String INVALID_ID = "P54^12";
    private static final String INVALID_WARD = "";
    private static final String INVALID_DIAGNOSIS = "<>";
    private static final String INVALID_MEDICATION = "$<>";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_ID = "P28905";
    private static final String VALID_WARD = "B06";
    private static final String VALID_DIAGNOSIS = "Patient suffers from hearing loss.";
    private static final String VALID_MEDICATION = "Fluticasone (Flonase Veramyst)";

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
    public void parseId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseId((String) null));
    }

    @Test
    public void parseId_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseId(INVALID_ID));
    }

    @Test
    public void parseId_validValueWithoutWhitespace_returnsId() throws Exception {
        Id expectedId = new Id(VALID_ID);
        assertEquals(expectedId, ParserUtil.parseId(VALID_ID));
    }

    @Test
    public void parseId_validValueWithWhitespace_returnsTrimmedId() throws Exception {
        String idWithWhitespace = WHITESPACE + VALID_ID + WHITESPACE;
        Id expectedId = new Id(VALID_ID);
        assertEquals(expectedId, ParserUtil.parseId(idWithWhitespace));
    }

    @Test
    public void parseWard_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWard((String) null));
    }

    @Test
    public void parseWard_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWard(INVALID_WARD));
    }

    @Test
    public void parseWard_validValueWithoutWhitespace_returnsWard() throws Exception {
        Ward expectedWard = new Ward(VALID_WARD);
        assertEquals(expectedWard, ParserUtil.parseWard(VALID_WARD));
    }

    @Test
    public void parseWard_validValueWithWhitespace_returnsTrimmedWard() throws Exception {
        String wardWithWhitespace = WHITESPACE + VALID_WARD + WHITESPACE;
        Ward expectedWard = new Ward(VALID_WARD);
        assertEquals(expectedWard, ParserUtil.parseWard(wardWithWhitespace));
    }

    @Test
    public void parseDiagnosis_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDiagnosis((String) null));
    }

    @Test
    public void parseDiagnosis_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDiagnosis(INVALID_DIAGNOSIS));
    }

    @Test
    public void parseDiagnosis_validValueWithoutWhitespace_returnsDiagnosis() throws Exception {
        Diagnosis expectedDiagnosis = new Diagnosis(VALID_DIAGNOSIS);
        assertEquals(expectedDiagnosis, ParserUtil.parseDiagnosis(VALID_DIAGNOSIS));
    }

    @Test
    public void parseDiagnosis_validValueWithWhitespace_returnsTrimmedDiagnosis() throws Exception {
        String diagnosisWithWhitespace = WHITESPACE + VALID_DIAGNOSIS + WHITESPACE;
        Diagnosis expectedDiagnosis = new Diagnosis(VALID_DIAGNOSIS);
        assertEquals(expectedDiagnosis, ParserUtil.parseDiagnosis(diagnosisWithWhitespace));
    }

    @Test
    public void parseMedication_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMedication((String) null));
    }

    @Test
    public void parseMedication_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMedication(INVALID_MEDICATION));
    }

    @Test
    public void parseMedication_validValueWithoutWhitespace_returnsMedication() throws Exception {
        Medication expectedMedication = new Medication(VALID_MEDICATION);
        assertEquals(expectedMedication, ParserUtil.parseMedication(VALID_MEDICATION));
    }

    @Test
    public void parseMedication_validValueWithWhitespace_returnsTrimmedMedication() throws Exception {
        String medicationWithWhitespace = WHITESPACE + VALID_MEDICATION + WHITESPACE;
        Medication expectedMedication = new Medication(VALID_MEDICATION);
        assertEquals(expectedMedication, ParserUtil.parseMedication(medicationWithWhitespace));
    }

}
