package seedu.address.model.delivery;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Predicate;

public class ItemNameContainsKeywordPredicate implements Predicate<Delivery> {
    private final List<String> keywords;

    public ItemNameContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Delivery delivery) {
        return keywords.stream()
                .anyMatch(keyword -> true);
    }
}
