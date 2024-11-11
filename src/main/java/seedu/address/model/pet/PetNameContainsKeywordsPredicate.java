package seedu.address.model.pet;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Pet}'s {@code Name} matches any of the keywords given.
 */
public class PetNameContainsKeywordsPredicate implements Predicate<Pet> {
    private final List<String> keywords;

    public PetNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Pet pet) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsTextIgnoreCase(pet.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetNameContainsKeywordsPredicate)) {
            return false;
        }

        PetNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (PetNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
