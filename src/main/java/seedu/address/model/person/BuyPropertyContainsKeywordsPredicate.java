package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code buyingProperties} list has a property that matches any of the keywords given.
 */
public class BuyPropertyContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a BuyPropertyContainsKeywordsPredicate to filter buying properties based on the provided keywords.
     *
     * @param keywords the list of keywords to match against buying properties
     */
    public BuyPropertyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // Returns true if any of the keywords match any of the buying properties fields
        for (String keyword : keywords) {
            if (person.getListOfBuyingProperties().stream()
                    .anyMatch(property -> StringUtil.containsNumericWithOptionalHyphen(property.toString(), keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyPropertyContainsKeywordsPredicate otherBuyPropertyContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherBuyPropertyContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
