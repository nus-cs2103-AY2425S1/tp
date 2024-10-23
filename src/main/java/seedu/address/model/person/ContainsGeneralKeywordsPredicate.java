package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.FindCommand;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContainsGeneralKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsGeneralKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> testPerson(person, keyword));
    }

    /**
     * Tests if there is a match between a {@code Person}'s fields and the individual keyword
     * @param person The individual getting tested
     * @param keyword The specific keyword the person should posess
     * @return True if individual posesses a field exactly matching the keyword, false otherwise
     */
    private boolean testPerson(Person person, String keyword) {
        assert keyword != FindCommand.VALIDATION_REGEX : "Keyword cannot be an empty value";
        if (StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)) {
            return true; // Returns true if names match
        } else if (StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)) {
            return true; // Returns true if address match
        } else if (StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)) {
            return true; // Returns true if phone number matches
        } else if (StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)) {
            return true; // Returns true if email matches
        } else if (person.getTags().stream().anyMatch(tag ->
                StringUtil.containsWordIgnoreCase(tag.tagName, keyword))) {
            return true; // Returns true if tags match
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsGeneralKeywordsPredicate)) {
            return false;
        }

        ContainsGeneralKeywordsPredicate otherContainsKeywordsPredicate = (ContainsGeneralKeywordsPredicate) other;
        return keywords.equals(otherContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
