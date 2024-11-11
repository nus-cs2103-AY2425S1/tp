package keycontacts.model.student;

import java.util.function.Predicate;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;

/**
 * Tests that a {@code Student}'s {@code Name} matches all of the keywords
 * given.
 */
public class StudentDescriptorMatchesPredicate implements Predicate<Student> {
    private final FindStudentDescriptor descriptor;

    public StudentDescriptorMatchesPredicate(FindStudentDescriptor findStudentDescriptor) {
        this.descriptor = findStudentDescriptor;
    }

    @Override
    public boolean test(Student student) {
        return descriptor.matches(student);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentDescriptorMatchesPredicate)) {
            return false;
        }

        StudentDescriptorMatchesPredicate otherStudentDescriptorMatchesPredicate =
                (StudentDescriptorMatchesPredicate) other;
        return descriptor.equals(otherStudentDescriptorMatchesPredicate.descriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Find Student Descriptor", descriptor).toString();
    }
}
