package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class JsonAdaptedSubjectConverterTest {
    private final JsonAdaptedSubjectConverter converter = new JsonAdaptedSubjectConverter();

    @Test
    public void convert_nullValue_returnsEmptyList() {
        List<JsonAdaptedSubject> result = converter.convert(null);
        assertEquals(0, result.size());
    }

    @Test
    public void convert_emptyString_returnsEmptyList() {
        List<JsonAdaptedSubject> result = converter.convert("");
        assertEquals(0, result.size());
    }

    @Test
    public void convert_singleValue_returnsSingleElementList() {
        List<JsonAdaptedSubject> result = converter.convert("Math");
        assertEquals(1, result.size());
        assertEquals("Math", result.get(0).getSubjectName());
    }

    @Test
    public void convert_multipleValues_returnsListOfElements() {
        List<JsonAdaptedSubject> result = converter.convert("Math;Science;English");
        assertEquals(3, result.size());
        assertEquals("Math", result.get(0).getSubjectName());
        assertEquals("Science", result.get(1).getSubjectName());
        assertEquals("English", result.get(2).getSubjectName());
    }

    @Test
    public void convert_valuesWithSpaces_returnsTrimmedElements() {
        List<JsonAdaptedSubject> result = converter.convert(" Math ; Science ; English ");
        assertEquals(3, result.size());
        assertEquals("Math", result.get(0).getSubjectName());
        assertEquals("Science", result.get(1).getSubjectName());
        assertEquals("English", result.get(2).getSubjectName());
    }
}
