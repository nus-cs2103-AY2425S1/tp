package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;


/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a {@code PhoneContainsKeywordsPredicate}.
     *
     * @param keywords A list of keywords to check against the person's phone number.
     */
    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Evaluates this predicate on the given {@code Person}.
     *
     * @param person The person whose phone number is being tested.
     * @return {@code true} if any of the keywords match any part of the person's phone number.
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword ->
                person.getPhone().toString().contains(keyword)
        );
    }

    /**
     * Compares this {@code PhoneContainsKeywordsPredicate} to another object.
     *
     * @param other The object to compare to.
     * @return {@code true} if this is the same object or if the other object is an instance of
     *         {@code PhoneContainsKeywordsPredicate} and both predicates contain the same keywords.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPhoneContainsKeywordsPredicate = (PhoneContainsKeywordsPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordsPredicate.keywords);
    }
}
