package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Field}'s content matches any of the keywords given.
 */
public class FieldContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String field;

    /**
     * @param keywords A list of keywords to search by
     * @param field The field to be searched
     */
    public FieldContainsKeywordsPredicate(List<String> keywords, String field) {
        requireNonNull(field);
        requireNonNull(keywords);
        this.keywords = keywords;
        this.field = field;
    }

    @Override
    public boolean test(Person person) {
        return switch (this.field) {
        case "name" -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().toString(), keyword));
        case "id" -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getId().toString(), keyword));
        case "ward" -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getWard().toString(), keyword));
        case "diagnosis" -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getDiagnosis().toString(), keyword));
        case "medication" -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMedication().toString(), keyword));
        default -> false;
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FieldContainsKeywordsPredicate)) {
            return false;
        }

        FieldContainsKeywordsPredicate otherFieldContainsKeywordsPredicate = (FieldContainsKeywordsPredicate) other;
        return keywords.equals(otherFieldContainsKeywordsPredicate.keywords)
                && field.equals(otherFieldContainsKeywordsPredicate.field);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("field", field)
                .add("keywords", keywords)
                .toString();
    }
}
