package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(0, student.getTags().size());
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
        assertEquals(2, student.getTags().size());
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
        assertEquals("[Class A, Class B]", student.getClasses().toString());
    }

    @Test
    public void testDaysAttendedProperty() {
        // Create a student with initial attendance
        Student student = new StudentBuilder().withDaysAttended(5).build();

        // Access the daysAttendedProperty and verify its initial value
        assertNotNull(student.daysAttendedProperty(), "daysAttendedProperty should not be null");
        assertEquals(5, student.daysAttendedProperty().get(), "Initial daysAttended value should be 5");

        // Increment attendance and verify if property reflects the updated value
        student.getDaysAttended().increment();
        assertEquals(6, student.daysAttendedProperty().get(), "daysAttended value should update to 6 after increment");

        // Decrement attendance and verify if property reflects the updated value
        student.getDaysAttended().decrement();
        assertEquals(5, student.daysAttendedProperty().get(), "daysAttended value should revert to 5 after decrement");

        // Reset attendance and verify if property reflects the reset value
        student.getDaysAttended().reset();
        assertEquals(0, student.daysAttendedProperty().get(), "daysAttended value should reset to 0");
    }

    @Test
    public void isSamePerson_sameObject_returnsTrue() {
        Student student = new StudentBuilder().withName("John Doe").build();
        assertTrue(student.isSamePerson(student));
    }

    @Test
    public void isSamePerson_differentObjectSameIdentityFields_returnsTrue() {
        Student student1 = new StudentBuilder().withName("John Doe").withPhone("12345678").build();
        Student student2 = new StudentBuilder().withName("John Doe").withPhone("12345678").build();
        assertTrue(student1.isSamePerson(student2));
    }

    @Test
    public void isSamePerson_differentObjectDifferentName_returnsFalse() {
        Student student1 = new StudentBuilder().withName("John Doe").withPhone("12345678").build();
        Student student2 = new StudentBuilder().withName("Jane Doe").withPhone("87654321").build();
        assertFalse(student1.isSamePerson(student2));
    }

    @Test
    public void isSamePerson_differentObjectDifferentEmail_returnsFalse() {
        Student student1 = new StudentBuilder().withName("John Doe").withEmail("johndoe@example.com").build();
        Student student2 = new StudentBuilder().withName("John Doe").withEmail("janedoe@example.com").build();
        assertFalse(student1.isSamePerson(student2));
    }

    @Test
    public void isSamePerson_differentObjectSameNameSameEmail_returnsTrue() {
        Student student1 = new StudentBuilder().withPhone("12345678").build();
        Student student2 = new StudentBuilder().withPhone("87654321").build();
        assertTrue(student1.isSamePerson(student2));
    }

    @Test
    public void isSamePerson_nullObject_returnsFalse() {
        Student student = new StudentBuilder().withName("John Doe").build();
        assertFalse(student.isSamePerson(null));
    }

}
