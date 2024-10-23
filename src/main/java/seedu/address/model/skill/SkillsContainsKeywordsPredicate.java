package seedu.address.model.skill;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Skills} matches any of the keywords given.
 */
public class SkillsContainsKeywordsPredicate implements Predicate<Person> {
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
    public boolean test(Person person) {
        // Null objects should not have been added to addressbook
        assert person != null;

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
