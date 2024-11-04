package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s fields matches any of the keywords given
 */
public class StudentMatchesQueryPredicate implements Predicate<Student> {

    private final List<String> keywords;

    public StudentMatchesQueryPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Student student) {
        String groupName = student.getGroupName().isPresent()
            ? student.getGroupName().get().getGroupName() : "!nogroup";
        return
            keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(student.getStudentNumber().getStudentNumber(), keyword)
                    || StringUtil.containsWordIgnoreCase(student.getName().getFullName(), keyword)
                    || student.getTags().stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(),
                    keyword))
                    || StringUtil.containsWordIgnoreCase(student.getEmail().getEmail(), keyword)
                    || groupName.equals(keyword));
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
