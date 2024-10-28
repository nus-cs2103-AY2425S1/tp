package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        // Try matching the combined phrase first
        String combinedPhrase = String.join(" ", keywords).toLowerCase();
        if (personClientTypes.stream().anyMatch(clientType ->
                clientType.clientTypeName.toLowerCase().contains(combinedPhrase))) {
            return true;
        }

        // Split each client type into individual words and flatten into a single stream of words
        Set<String> clientTypeWords = personClientTypes.stream()
                .map(clientType -> clientType.clientTypeName.toLowerCase().split("\\s+"))
                .flatMap(Arrays::stream)
                .collect(Collectors.toSet());

        // Check if all keywords match at least one of the client type words
        return keywords.stream()
                .allMatch(keyword -> clientTypeWords.stream()
                        .anyMatch(word -> word.startsWith(keyword.toLowerCase())));



    //        // If combined phrase does not match, try matching individual keywords
    //        return keywords.stream()
    //                .allMatch(keyword -> personClientTypes.stream().anyMatch(clientType ->
    //                        clientType.clientTypeName.toLowerCase().startsWith(keyword.toLowerCase()))
    //                );
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
