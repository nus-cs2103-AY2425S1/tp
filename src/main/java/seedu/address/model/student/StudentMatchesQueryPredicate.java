package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s fields matches any of the keywords given
 */
public class StudentMatchesQueryPredicate implements Predicate<Student> {

    private final List<String> keywords;
    private static final String noGroupKey = "!nogroup";

    public StudentMatchesQueryPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        String groupName = student.getGroupName().isPresent()
            ? student.getGroupName().get().getGroupName().toLowerCase() : noGroupKey;
        return
            keywords.stream().anyMatch(keyword -> {
                String key = keyword.toLowerCase();
                String studentNumber = student.getStudentNumber().getStudentNumber().toLowerCase();
                String name = student.getName().getFullName().toLowerCase();
                String email = student.getEmail().getEmail().toLowerCase();
                return studentNumber.contains(key)
                    || name.contains(key)
                    || email.contains(key)
                    || student.getTags().stream().anyMatch(tag -> tag.getTagName().toLowerCase().contains(key))
                    || groupName.contains(key);
            });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentMatchesQueryPredicate)) {
            return false;
        }

        StudentMatchesQueryPredicate otherPredicate = (StudentMatchesQueryPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("keyword", keywords)
            .toString();
    }
}
