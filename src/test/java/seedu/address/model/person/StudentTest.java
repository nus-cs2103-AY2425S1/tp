package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void testStudentCreation() {
        Student student = new StudentBuilder().build();
        assertNotNull(student);
        assertEquals(new Name("Amy Bee"), student.getName());
        assertEquals(new Phone("85355255"), student.getPhone());
        assertEquals(new Email("amy@gmail.com"), student.getEmail());
        assertEquals(new Address("123, Jurong West Ave 6, #08-111"), student.getAddress());
        assertEquals(1, student.getSubjects().size());
        assertEquals(1, student.getTags().size());
        assertEquals(0, student.getClasses().size());
    }

    @Test
    public void testStudentWithCustomDetails() {
        Student student = new StudentBuilder().withName("John Doe")
                .withPhone("91234567")
                .withEmail("john@example.com")
                .withAddress("456, Clementi Ave 3, #12-34")
                .withSubjects("Physics", "Chemistry")
                .withTags("friend", "classmate")
                .withClasses("Math101", "Science202")
                .build();
        assertNotNull(student);
        assertEquals(new Name("John Doe"), student.getName());
        assertEquals(new Phone("91234567"), student.getPhone());
        assertEquals(new Email("john@example.com"), student.getEmail());
        assertEquals(new Address("456, Clementi Ave 3, #12-34"), student.getAddress());
        assertEquals(2, student.getSubjects().size());
        assertEquals(3, student.getTags().size());
        assertEquals(2, student.getClasses().size());
    }

    @Test
    public void testEquals() {
        Student student1 = new StudentBuilder().withName("John Doe").build();
        Student student2 = new StudentBuilder().withName("John Doe").build();
        Student student3 = new StudentBuilder().withName("Jane Doe").build();

        assertEquals(student1, student1); // Same object, should be equal
        assertEquals(student1, student2); // Same details, should be equal
        assertNotEquals(student1, student3); // Different names, should not be equal
        assertNotEquals(student1, null); // Null object, should not be equal
    }

    @Test
    public void testHashCode() {
        Student student1 = new StudentBuilder().withName("John Doe").build();
        Student student2 = new StudentBuilder().withName("John Doe").build();
        assertEquals(student1.hashCode(), student2.hashCode());
    }
    @Test
    public void getSubjectString_validSubject_returnsSubjectString() {
        Student student = new StudentBuilder().withSubjects("Mathematics").build();
        assertEquals("[Mathematics]", student.getSubjects().toString());
    }

    @Test
    public void getClassesString_validClasses_returnsClassesString() {
        Student student = new StudentBuilder().withClasses("Class A", "Class B").build();
        assertEquals("Class A, Class B", student.getClasses().toString());
    }
}
