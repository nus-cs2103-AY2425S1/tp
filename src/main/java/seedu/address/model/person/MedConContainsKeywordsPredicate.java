package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code MedCon} matches any of the keywords given.
 */
public class MedConContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public MedConContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getMedCons().stream()
                                  .anyMatch(medCon -> keywords.stream()
                                  .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medCon.medConName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MedConContainsKeywordsPredicate)) {
            return false;
        }

        MedConContainsKeywordsPredicate otherMedConContainsKeywordsPredicate = (MedConContainsKeywordsPredicate) other;
        return keywords.equals(otherMedConContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
