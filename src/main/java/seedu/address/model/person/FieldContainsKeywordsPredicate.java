package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Person}'s field matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate<T> implements Predicate<Person> {
    // T is the field type
    private final List<String> keywords;
    private final Function<Person, T> fieldExtractor;

    /**
     * Constructor for FieldContainsKeywordsPredicate
     * @param keywords list of keywords to search for
     * @param fieldExtractor function to extract the field from the person (e.g. name, phone, email)
     */
    public FieldContainsKeywordsPredicate(List<String> keywords, Function<Person, T> fieldExtractor) {
        this.keywords = keywords;
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public boolean test(Person person) {
        T fieldValue = fieldExtractor.apply(person);
        // check if field contains keyword
        return keywords.stream()
                .anyMatch(
                        keyword -> (fieldValue.toString().toLowerCase().contains(keyword.toLowerCase())
                                || StringUtil.containsWordIgnoreCase(fieldValue.toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }
        FieldContainsKeywordsPredicate<?> otherPredicate = (FieldContainsKeywordsPredicate<?>) other;
        return keywords.equals(otherPredicate.keywords)
                && testFieldExtractorEquality(fieldExtractor, otherPredicate.getFieldExtractor());
    }



    /**
     * Returns the field extractor function
     */
    public Function<Person, T> getFieldExtractor() {
        return fieldExtractor;
    }

    private boolean testFieldExtractorEquality(Function<Person, T> extractor1, Function<? super Person, ?> extractor2) {
        Person testPerson = new Person(new Name("test"), new Phone("12345678"),
                new Email("test@gmail.com"), new Address("test"), new TelegramUsername("tester12"), new HashSet<>());
        return Objects.equals(extractor1.apply(testPerson), extractor2.apply(testPerson));
    }






}
