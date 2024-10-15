package seedu.address.model.skill;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Skills} matches any of the keywords given.
 */
public class SkillsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SkillsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // Skill matches any of the keywords given
        return keywords.stream()
                .anyMatch(keyword -> person.getSkills().stream()
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
