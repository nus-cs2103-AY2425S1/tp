package seedu.address.model.client;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Client}'s {@code Address} contains any of the given keywords.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Client> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddressContainsKeywordsPredicate
                && keywords.equals(((AddressContainsKeywordsPredicate) other).keywords));
    }
}
