package seedu.address.model.delivery;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

public class ItemNameContainsKeywordPredicate implements Predicate<Delivery> {
    private final List<String> keywords;

    public ItemNameContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Delivery delivery) {
        boolean result = false;
        for (ItemName i: delivery.getItems()) {
            result = result || keywords.stream().anyMatch(keyword -> StringUtil.containsIgnoreCase(i.value, keyword));
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof ItemNameContainsKeywordPredicate)) {
            return false;
        }

        ItemNameContainsKeywordPredicate otherItemNameContainsKeywordPredicate =
                (ItemNameContainsKeywordPredicate) other;
        return keywords.equals(otherItemNameContainsKeywordPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
