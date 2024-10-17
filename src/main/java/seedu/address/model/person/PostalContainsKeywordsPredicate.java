package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tests that a {@code Person}'s {@code Address} contains any of the given postal code keywords.
 */
public class PostalContainsKeywordsPredicate implements Predicate<Person> {

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("S\\d{6}");
    private final List<String> keywords;

    public PostalContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String addressValue = person.getAddress().value;
        Matcher matcher = POSTAL_CODE_PATTERN.matcher(addressValue);

        if (matcher.find()) {
            String postalCode = matcher.group();
            return keywords.stream().anyMatch(postalCode::contains);
        }
        return false;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PostalContainsKeywordsPredicate)) {
            return false;
        }

        PostalContainsKeywordsPredicate otherPredicate = (PostalContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}
