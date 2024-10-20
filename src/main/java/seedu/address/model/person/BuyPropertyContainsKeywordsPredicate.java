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
        return person.getListOfBuyingProperties().stream().anyMatch(property ->
            keywords.stream().anyMatch(keyword ->
                StringUtil.containsNumericWithOptionalHyphen(property.toString(), keyword)
                // returns true if keyword is housing type
                || property.getClass().getSimpleName().toUpperCase().contains(keyword.toUpperCase())
                // returns true if keyword is in any of the property tags
                || property.getTags().stream().anyMatch(tag -> tag.toString().contains(keyword))
            )
        );
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
