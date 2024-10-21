package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.clienttype.ClientType;

/**
 * Tests that a {@code Person}'s {@code Client_Type} matches any of the keywords given.
 */
public class ClientTypeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ClientTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<ClientType> personClientTypes = person.getClientTypes();

        // If the client type list is empty, return false -> no client type to match (Invalid Client Type)
        if (this.keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .allMatch(keyword -> personClientTypes.stream().anyMatch(clientType ->
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
