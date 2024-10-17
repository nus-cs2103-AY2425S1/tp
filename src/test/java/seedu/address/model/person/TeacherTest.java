package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.util.SampleDataUtil.getClassSet;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
public class TeacherTest {

    @Test
    public void createTeacher_success() {

        Teacher teacher = new Teacher(new Name("John Doe"), new Gender("male"), new Phone("12345678"),
                new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                getTagSet("Friends"), new Subject("Math"),
                getClassSet("1A"));

        assertEquals("John Doe", teacher.getName().toString());
        assertEquals("male", teacher.getGender().toString());
        assertEquals("12345678", teacher.getPhone().toString());
        assertEquals("johndoe@hotmail.com", teacher.getEmail().toString());
        assertEquals("123 Main St", teacher.getAddress().toString());
        assertEquals("Math", teacher.getSubject().toString());
        assertEquals(1, teacher.getTags().size());
        assertEquals(1, teacher.getClasses().size());
    }

    @Test
    public void createTeacher_invalidName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teacher teacher = new Teacher(new Name(""), new Gender("M"), new Phone("12345678"),
                    new Email("johndoe@hotmail.com"), new Address("123 Main St"),
                    getTagSet("Friends"), new Subject("Math"),
                    getClassSet("1A"));
        });
    }

    @Test
    public void createTeacher_invalidEmail_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Teacher teacher = new Teacher(new Name("John Doe"), new Gender("M"), new Phone("12345678"),
                    new Email("invalid-email"), new Address("123 Main St"),
                    getTagSet("Friends"), new Subject("Math"),
                    getClassSet("1A"));
        });
    }

}
