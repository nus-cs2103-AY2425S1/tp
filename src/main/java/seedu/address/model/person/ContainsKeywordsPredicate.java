package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.criteria.SearchCriteria;

/**
 * Tests that a {@code Person}'s {@code Name, Nric, Role or Tag} matches any of the keywords given.
 * Each keyword is prepended with their respective prefixes
 *
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {

    private final List<SearchCriteria> keywords;

    public ContainsKeywordsPredicate(List<SearchCriteria> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(searchCriteria -> searchCriteria.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherPredicate = (ContainsKeywordsPredicate) other;

        // Compare the keyword maps for equality.
        return this.keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
