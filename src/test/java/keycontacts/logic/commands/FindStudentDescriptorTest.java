package keycontacts.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Group;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;

public class FindStudentDescriptorTest {

    @Test
    public void testDefaultConstructor() {
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        assertEquals("", descriptor.getName());
        assertEquals("", descriptor.getAddress());
        assertEquals("", descriptor.getPhone());
        assertEquals("", descriptor.getGradeLevel());
    }

    @Test
    public void testParameterizedConstructor() {
        FindStudentDescriptor descriptor = new FindStudentDescriptor("John", "123 Street", "12345678", "ABRSM 5");
        assertEquals("John", descriptor.getName());
        assertEquals("123 Street", descriptor.getAddress());
        assertEquals("12345678", descriptor.getPhone());
        assertEquals("ABRSM 5", descriptor.getGradeLevel());
    }

    @Test
    public void testCopyConstructor() {
        FindStudentDescriptor original = new FindStudentDescriptor("John", "123 Street", "12345678", "ABRSM 10");
        FindStudentDescriptor copy = new FindStudentDescriptor(original);
        assertEquals(original, copy);
    }

    @Test
    public void testSettersAndGetters() {
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setName("John");
        descriptor.setAddress("123 Street");
        descriptor.setPhone("12345678");
        descriptor.setGradeLevel("Trinity 10");

        assertEquals("John", descriptor.getName());
        assertEquals("123 Street", descriptor.getAddress());
        assertEquals("12345678", descriptor.getPhone());
        assertEquals("Trinity 10", descriptor.getGradeLevel());
    }

    @Test
    public void testMatches() {
        FindStudentDescriptor descriptor = new FindStudentDescriptor("John", "", "", "");
        Student student = new Student(new Name("John"), new Phone("12345678"), new Address("123 Street"),
                new GradeLevel("ABRSM 3"), new Group(""));

        assertTrue(descriptor.matches(student));

        descriptor = new FindStudentDescriptor("", "123 Street", "", "");
        assertTrue(descriptor.matches(student));

        descriptor = new FindStudentDescriptor("", "", "12345678", "");
        assertTrue(descriptor.matches(student));

        descriptor = new FindStudentDescriptor("", "", "", "ABRSM 3");
        assertTrue(descriptor.matches(student));

        descriptor = new FindStudentDescriptor("Jane", "", "", "");
        assertFalse(descriptor.matches(student));
    }

    @Test
    public void testEquals() {
        FindStudentDescriptor descriptor1 = new FindStudentDescriptor("John", "123 Street", "12345678", "Grade 10");
        FindStudentDescriptor descriptor2 = new FindStudentDescriptor("John", "123 Street", "12345678", "Grade 10");
        FindStudentDescriptor descriptor3 = new FindStudentDescriptor("Jane", "456 Avenue", "87654321", "Grade 11");

        assertTrue(descriptor1.equals(descriptor2));
        assertFalse(descriptor1.equals(descriptor3));
    }
}
