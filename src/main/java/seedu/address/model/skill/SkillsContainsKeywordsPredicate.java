package seedu.address.model.skill;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code Skills} matches any of the keywords given.
 */
public class SkillsContainsKeywordsPredicate implements Predicate<Employee> {
    private final Set<String> keywords;

    /**
     * Constructs a {@code SkillsContainsKeywordsPredicate}.
     *
     * @param keywords A valid list of skills.
     */
    public SkillsContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);
        this.keywords = new HashSet<String>(keywords);
    }

    @Override
    public boolean test(Employee employee) {
        // Null objects should not have been added to addressbook
        assert employee != null;

        // Skill matches any of the keywords given
        return keywords.stream()
                .anyMatch(keyword -> employee.getSkills().stream()
                .anyMatch(skill -> StringUtil.containsWordIgnoreCase(skill.skill, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SkillsContainsKeywordsPredicate)) {
            return false;
        }

        SkillsContainsKeywordsPredicate otherSkillsContainsKeywordsPredicate =
                (SkillsContainsKeywordsPredicate) other;
        return keywords.equals(otherSkillsContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
