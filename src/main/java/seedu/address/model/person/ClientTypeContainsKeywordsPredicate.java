package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.clienttype.ClientType;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ClientTypeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ClientTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<ClientType> personClientTypes = person.getClientTypes();
        return keywords.stream()
                .anyMatch(keyword -> personClientTypes.stream().anyMatch(clientType ->
                        clientType.clientTypeName.toLowerCase().startsWith(keyword.toLowerCase()))
                );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientTypeContainsKeywordsPredicate)) {
            return false;
        }

        ClientTypeContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (ClientTypeContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
