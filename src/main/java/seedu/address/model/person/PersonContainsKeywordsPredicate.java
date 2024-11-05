package seedu.address.model.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests that a {@code Person} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private boolean isSearchQueryEmpty = false;

    // Identity fields
    private final NameContainsKeywordsPredicate namePredicate;
    private final PhoneContainsKeywordsPredicate phonePredicate;
    private final EmailContainsKeywordsPredicate emailPredicate;

    // Data predicates
    private final AddressContainsKeywordsPredicate addressPredicate;
    private final TagContainsKeywordsPredicate tagsPredicate;

    // TODO: Missing keywords lists for subject and classes

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate}
     *
     * @param searchQuery A list representing the search or filter query by the user
     * @throws ParseException If a non-empty search query was given that didn't fit the categories
     */
    public PersonContainsKeywordsPredicate(List<String> searchQuery) throws ParseException {
        if (searchQuery.get(0).isEmpty()) {
            isSearchQueryEmpty = true;
            namePredicate = new NameContainsKeywordsPredicate(new ArrayList<>());
            phonePredicate = new PhoneContainsKeywordsPredicate(new ArrayList<>());
            emailPredicate = new EmailContainsKeywordsPredicate(new ArrayList<>());
            addressPredicate = new AddressContainsKeywordsPredicate(new ArrayList<>());
            tagsPredicate = new TagContainsKeywordsPredicate(new ArrayList<>());
            return;
        }

        // Initialize keyword lists
        List<String> nameKeywords = new ArrayList<>();
        List<String> phoneKeywords = new ArrayList<>();
        List<String> emailKeywords = new ArrayList<>();
        List<String> addressKeywords = new ArrayList<>();
        List<String> tagsKeywords = new ArrayList<>();

        Map<Prefix, List<String>> keywordMap = new HashMap<>();
        keywordMap.put(PREFIX_NAME, nameKeywords);
        keywordMap.put(PREFIX_PHONE, phoneKeywords);
        keywordMap.put(PREFIX_EMAIL, emailKeywords);
        keywordMap.put(PREFIX_ADDRESS, addressKeywords);
        keywordMap.put(PREFIX_TAG, tagsKeywords);

        List<String> currentList = null;

        for (String keyword : searchQuery) {
            Prefix prefix = new Prefix(keyword);

            if (keywordMap.containsKey(prefix)) {
                // If switching `currentList` without adding anything in yet
                if (currentList != null && currentList.isEmpty()) {
                    throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
                }

                currentList = keywordMap.get(prefix);
            } else if (currentList != null) {
                currentList.add(keyword);
            } else {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
        }

        // Final check if last `currentList` is empty
        if (currentList != null && currentList.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        phonePredicate = new PhoneContainsKeywordsPredicate(phoneKeywords);
        emailPredicate = new EmailContainsKeywordsPredicate(emailKeywords);
        addressPredicate = new AddressContainsKeywordsPredicate(addressKeywords);
        tagsPredicate = new TagContainsKeywordsPredicate(tagsKeywords);
    }

    @Override
    public boolean test(Person person) {
        if (isSearchQueryEmpty) {
            return true;
        }

        assert person != null : "Person object cannot be null";

        boolean hasMatchingName = namePredicate.test(person);
        boolean hasMatchingPhone = phonePredicate.test(person);
        boolean hasMatchingEmail = emailPredicate.test(person);
        boolean hasMatchingAddress = addressPredicate.test(person);
        boolean hasMatchingTags = tagsPredicate.test(person);

        return hasMatchingName || hasMatchingPhone || hasMatchingEmail || hasMatchingAddress || hasMatchingTags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPersonContainsKeywordPredicate = (PersonContainsKeywordsPredicate) other;
        return namePredicate.equals(otherPersonContainsKeywordPredicate.namePredicate)
                && phonePredicate.equals(otherPersonContainsKeywordPredicate.phonePredicate)
                && emailPredicate.equals(otherPersonContainsKeywordPredicate.emailPredicate)
                && addressPredicate.equals(otherPersonContainsKeywordPredicate.addressPredicate)
                && tagsPredicate.equals(otherPersonContainsKeywordPredicate.tagsPredicate);
    }
}
