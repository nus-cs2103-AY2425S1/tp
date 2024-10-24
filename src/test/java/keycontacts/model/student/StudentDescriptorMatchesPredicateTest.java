package keycontacts.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.testutil.FindStudentDescriptorBuilder;
import keycontacts.testutil.StudentBuilder;

public class StudentDescriptorMatchesPredicateTest {

    @Test
    public void test_studentNameMatches_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setName("John Doe");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withName("John Doe").build();

        // Act & Assert
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_studentNameDoesNotMatch_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setName("Jane Doe");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withName("John Doe").build();

        // Act & Assert
        assertFalse(predicate.test(student));
    }

    @Test
    public void test_studentAddressMatches_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setAddress("Block 123, Bobby Street 3");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withAddress("Block 123, Bobby Street 3").build();

        // Act & Assert
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_studentAddressDoesNotMatch_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setAddress("Block 312, Amy Street 1");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withAddress("Block 123, Bobby Street 3").build();

        // Act & Assert
        assertFalse(predicate.test(student));
    }

    @Test
    public void test_studentPhoneMatches_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setPhone("11111111");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withPhone("11111111").build();

        // Act & Assert
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_studentPhoneDoesNotMatch_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setPhone("22222222");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withPhone("11111111").build();

        // Act & Assert
        assertFalse(predicate.test(student));
    }

    @Test
    public void test_studentGradeLevelMatches_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setGradeLevel("RSL 2");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withGradeLevel("RSL 2").build();

        // Act & Assert
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_studentGradeLevelDoesNotMatch_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setGradeLevel("ABRSM 3");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withGradeLevel("RSL 2").build();

        // Act & Assert
        assertFalse(predicate.test(student));
    }

    @Test
    public void test_studentMultipleFieldMatches_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setName("John Doe");
        descriptor.setAddress("Block 123, Bobby Street 3");
        descriptor.setPhone("11111111");
        descriptor.setGradeLevel("RSL 2");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withName("John Doe").withAddress("Block 123, Bobby Street 3")
                .withPhone("11111111").withGradeLevel("RSL 2").build();

        // Act & Assert
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_studentMultipleFieldDoesNotMatch_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        descriptor.setName("Jane Doe");
        descriptor.setAddress("Block 312, Amy Street 1");
        descriptor.setPhone("22222222");
        descriptor.setGradeLevel("ABRSM 3");
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        Student student = new StudentBuilder().withName("John Doe").withAddress("Block 123, Bobby Street 3")
                .withPhone("11111111").withGradeLevel("RSL 2").build();

        // Act & Assert
        assertFalse(predicate.test(student));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);

        // Act & Assert
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_differentObjectSameValues_returnsTrue() {
        // Arrange
        FindStudentDescriptor descriptor1 = new FindStudentDescriptorBuilder().withName("John Doe").build();
        FindStudentDescriptor descriptor2 = new FindStudentDescriptorBuilder().withName("John Doe").build();
        StudentDescriptorMatchesPredicate predicate1 = new StudentDescriptorMatchesPredicate(descriptor1);
        StudentDescriptorMatchesPredicate predicate2 = new StudentDescriptorMatchesPredicate(descriptor2);

        // Act & Assert
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentObjectDifferentValues_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor1 = new FindStudentDescriptor();
        descriptor1.setName("John Doe");
        FindStudentDescriptor descriptor2 = new FindStudentDescriptor();
        descriptor2.setName("Jane Doe");
        StudentDescriptorMatchesPredicate predicate1 = new StudentDescriptorMatchesPredicate(descriptor1);
        StudentDescriptorMatchesPredicate predicate2 = new StudentDescriptorMatchesPredicate(descriptor2);

        // Act & Assert
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // Arrange
        FindStudentDescriptor descriptor = new FindStudentDescriptor();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);

        // Act & Assert
        assertFalse(predicate.equals(null));
    }
}
