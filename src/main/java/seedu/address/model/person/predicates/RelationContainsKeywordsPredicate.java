package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Guest}'s {@code Relation} matches any of the keywords given.
 */
public class RelationContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RelationContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Guest)) {
            return false;
        }
        Guest guest = (Guest) person;
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(guest.getRelation().relation, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RelationContainsKeywordsPredicate)) {
            return false;
        }

        RelationContainsKeywordsPredicate otherRelationContainsKeywordsPredicate =
                (RelationContainsKeywordsPredicate) other;
        return keywords.equals(otherRelationContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
