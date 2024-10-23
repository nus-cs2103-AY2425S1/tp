package seedu.address.model.predicate;

import static seedu.address.logic.Messages.MESSAGE_INVALID_WITH_SPACES;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.FindCommand;

/**
 * A predicate that tests whether a specified field of type {@code T} contains a given keyword.
 * The predicate can operate with either a single keyword or multiple keywords, enabling greater flexibility.
 *
 * <p>This class is a generic implementation that allows you to create predicates for various fields
 * in a Person object, such as an email or phone number. It utilises a function to extract the desired field's
 * string representation from the object. </p>
 *
 * @param <T> the type of the objects being tested
 */
public class FieldContainsKeywordsPredicate<T> implements Predicate<T> {
    private final List<String> keywords;
    private final Function<T, String> fieldExtractor;
    private final boolean isMultipleKeywords; // Flag to control multiple keyword behavior

    /**
     * Constructs a {@code FieldContainsKeywordsPredicate} with a list of keywords.
     *
     * @param keywords the list of keywords to search for
     * @param fieldExtractor a function to extract the field's string representation from the object
     */
    public FieldContainsKeywordsPredicate(List<String> keywords,
                                          Function<T, String> fieldExtractor,
                                          boolean isMultipleKeywords) throws IllegalArgumentException {
        if (!isMultipleKeywords && keywords.size() > 1) {
            throw new IllegalArgumentException(
                    String.format(MESSAGE_INVALID_WITH_SPACES, FindCommand.MESSAGE_USAGE));
        }
        this.keywords = keywords;
        this.fieldExtractor = fieldExtractor;
        this.isMultipleKeywords = isMultipleKeywords;
    }

    @Override
    public boolean test(T t) {
        String fieldValue = this.fieldExtractor.apply(t);

        if (!isMultipleKeywords) {
            assert this.keywords.size() == 1 : "Size of the list of keywords for single keyword must be 1!";
            return StringUtil.isWordPresentIgnoreCase(fieldValue, this.keywords.get(0));
        }

        assert !this.keywords.isEmpty() : "Size of the list of keywords cannot be empty!";
        return this.keywords.stream()
                .anyMatch(keyword -> StringUtil.isWordInSentenceIgnoreCase(fieldValue, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FieldContainsKeywordsPredicate<?>)) {
            return false;
        }

        FieldContainsKeywordsPredicate<?> otherFieldContainsKeywordPredicate =
                (FieldContainsKeywordsPredicate<?>) other;
        return this.keywords.equals(otherFieldContainsKeywordPredicate.keywords)
                && this.fieldExtractor.equals(otherFieldContainsKeywordPredicate.fieldExtractor)
                && this.isMultipleKeywords == otherFieldContainsKeywordPredicate.isMultipleKeywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", this.keywords).toString();
    }

}
