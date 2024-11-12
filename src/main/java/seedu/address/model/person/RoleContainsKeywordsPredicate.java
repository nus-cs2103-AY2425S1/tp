package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that a {@code Person}'s {@code Role} matches any of the keywords given.
 */
public class RoleContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a RoleContainsKeywordsPredicate with the given wedding
     *
     * @param keywords The keywords to check against
     */
    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a given person has role containing some keywords.
     * Returns true only if some keywords are found in the person's role.
     *
     * @param person The person to test
     * @return true if some keywords are found in the person's role, false otherwise
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getRole()
                        .map(role -> role.roleName.equalsIgnoreCase(keyword))
                        .orElse(false));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleContainsKeywordsPredicate)) {
            return false;
        }

        RoleContainsKeywordsPredicate otherRoleContainsKeywordsPredicate = (RoleContainsKeywordsPredicate) other;
        return keywords.equals(otherRoleContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
