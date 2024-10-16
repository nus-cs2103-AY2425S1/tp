package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.tag.Tag;
import seedu.address.model.tut.TutDate;

public class SampleDataUtilTest {

    @Test
    public void getSampleStudents_validSampleData_correctData() {
        Student[] sampleStudents = SampleDataUtil.getSampleStudents();

        assertEquals(6, sampleStudents.length); // Assert there are 6 sample students

        // Assert the first student has the correct name and other fields
        Student firstStudent = sampleStudents[0];
        assertEquals(new Name("Alex Yeoh"), firstStudent.getName());
        assertEquals(new TutorialClass("1001"), firstStudent.getTutorialClass());
    }

    @Test
    public void getSampleAddressBook_validSampleData_correctStudentCount() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
        assertEquals(6, sampleAddressBook.getStudentList().size()); // Assert there are 6 students
    }

    @Test
    public void getTagSet_validInput_correctTags() {
        Set<Tag> tagSet = SampleDataUtil.getTagSet("tag1", "tag2");
        assertEquals(2, tagSet.size());
        assertTrue(tagSet.contains(new Tag("tag1")));
        assertTrue(tagSet.contains(new Tag("tag2")));
    }

    @Test
    public void getDateList_validDates_correctTutDates() throws ParseException {
        Set<TutDate> dateList = SampleDataUtil.getDateList("20/02/2024", "22/02/2024");

        assertEquals(2, dateList.size());
        assertTrue(dateList.contains(SampleDataUtil.parseDate("20/02/2024")));
        assertTrue(dateList.contains(SampleDataUtil.parseDate("22/02/2024")));
    }

    @Test
    public void parseDate_validDate_correctParsing() throws ParseException {
        TutDate tutDate = SampleDataUtil.parseDate("2024/02/20");
        assertEquals("2024/02/20", tutDate.toString()); // Check the correct date is parsed
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() {
        assertThrows(ParseException.class, () -> SampleDataUtil.parseDate("invalid-date"));
    }

    @Test
    public void getDateList_invalidDate_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> SampleDataUtil.getDateList("invalid-date"));
    }
}
