package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getClassSet;
import static seedu.address.model.util.SampleDataUtil.getSubjectSet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.SampleDataUtil;

public class TeacherTest {

    @Test
    public void createTeacher_success() {

        Teacher teacher = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                getTagSet("Friends"), getSubjectSet("Math"),
                getClassSet("1A"));

        assertEquals("John Doe", teacher.getName().toString());
        assertEquals("male", teacher.getGender().toString());
        assertEquals("12345678", teacher.getPhone().toString());
        assertEquals("johndoe@hotmail.com", teacher.getEmail().toString());
        assertEquals("123 Main St", teacher.getAddress().toString());
        assertEquals("[Math]", teacher.getSubjects().toString());
        assertEquals(2, teacher.getTags().size());
        assertEquals(1, teacher.getClasses().size());
        assertEquals("seedu.address.model.person.Teacher{name=John Doe, gender=male, phone=12345678, "
                + "email=johndoe@hotmail.com, address=123 Main St, tags=[[teacher], [Friends]], "
                        + "subject=[Math], classes=[1A]}", teacher.toString());
    }

    @Test
    public void createTeacher_invalidName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teacher teacher = new Teacher(new Name(""), new Gender("M"), new Phone("12345678"),
                    new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                    getTagSet("Friends"), getSubjectSet("Math"),
                    getClassSet("1A"));
        });
    }

    @Test
    public void createTeacher_invalidEmail_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teacher teacher = new Teacher(new Name("John Doe"), new Gender("M"), new Phone("12345678"),
                    new Email("invalid-email"), new Address("123 Main St"),
                    getTagSet("Friends"), getSubjectSet("Math"),
                    getClassSet("1A"));
        });
    }

    @Test
    public void isSameTeacher() {
        Teacher teacher1 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        Teacher teacher2 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("87654321"),
                new Email("john.doe@example.com"), new Address("456 Elm St"),
                SampleDataUtil.getTagSet("Colleagues"), getSubjectSet("Science"),
                SampleDataUtil.getClassSet("2B"));

        Teacher teacher3 = new Teacher(new Name("Jane Doe"), new Gender("female"), new Phone("12345678"),
                new Email("jane.doe@example.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        // same object -> returns true
        assertTrue(teacher1.isSameTeacher(teacher1));

        // same name, different other attributes -> returns true
        assertTrue(teacher1.isSameTeacher(teacher2));

        // different name -> returns false
        assertFalse(teacher1.isSameTeacher(teacher3));
    }

    @Test
    public void equals() {
        Teacher teacher1 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        Teacher teacher2 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        Teacher teacher3 = new Teacher(new Name("Jane Doe"), new Gender("female"), new Phone("87654321"),
                new Email("jane.doe@example.com"), new Address("456 Elm St"),
                SampleDataUtil.getTagSet("Colleagues"), getSubjectSet("Science"),
                SampleDataUtil.getClassSet("2B"));

        // same values -> returns true
        assertTrue(teacher1.equals(teacher2));

        // same object -> returns true
        assertTrue(teacher1.equals(teacher1));

        // null -> returns false
        assertFalse(teacher1.equals(null));

        // different type -> returns false
        assertFalse(teacher1.equals(5));

        // different teacher -> returns false
        assertFalse(teacher1.equals(teacher3));
    }

    @Test
    public void hashCodeTest() {
        Teacher teacher1 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        Teacher teacher2 = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));

        Teacher teacher3 = new Teacher(new Name("Jane Doe"), new Gender("female"), new Phone("87654321"),
                new Email("jane.doe@example.com"), new Address("456 Elm St"),
                SampleDataUtil.getTagSet("Colleagues"), getSubjectSet("Science"),
                SampleDataUtil.getClassSet("2B"));

        // same values -> returns same hashcode
        assertTrue(teacher1.hashCode() == teacher2.hashCode());

        // different values -> returns different hashcode
        assertFalse(teacher1.hashCode() == teacher3.hashCode());
    }

    @Test
    public void getSubjectString_validSubject_returnsSubjectString() {
        Teacher teacher = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), SampleDataUtil.getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A"));
        assertEquals("[Math]", teacher.getSubjects().toString());
    }

    @Test
    public void getClassesString_validClasses_returnsClassesString() {
        Teacher teacher = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                SampleDataUtil.getTagSet("Friends"), SampleDataUtil.getSubjectSet("Math"),
                SampleDataUtil.getClassSet("1A", "2B"));
        assertEquals("1A, 2B", teacher.getClasses().toString());
    }

}
