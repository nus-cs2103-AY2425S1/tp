package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FieldContainsKeyWordPredicate<T> implements Predicate<Person> {
    // T is the field type
    private final List<String> keywords;
    private final Function<Person, T> fieldExtractor;

    public FieldContainsKeyWordPredicate(List<String> keywords, Function<Person, T> fieldExtractor) {
        this.keywords = keywords;
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public boolean test(Person person) {
        T fieldValue = fieldExtractor.apply(person);
        // check if field contains keyword
        return keywords.stream()
                .anyMatch(
                        keyword -> StringUtil.containsWordIgnoreCase(fieldValue.toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FieldContainsKeyWordPredicate)) {
            return false;
        }

        FieldContainsKeyWordPredicate<?> otherPredicate = (FieldContainsKeyWordPredicate<?>) other;
        return keywords.equals(otherPredicate.keywords)
                && fieldExtractor.equals(otherPredicate.fieldExtractor);
    }
}
