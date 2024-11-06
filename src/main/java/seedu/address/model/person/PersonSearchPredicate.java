package seedu.address.model.person;
import java.util.List;import java.util.function.Predicate;
import java.util.stream.Collectors;
import seedu.address.logic.commands.util.FieldQuery;import seedu.address.logic.commands.util.SearchField;
import seedu.address.model.tag.Tag;
/**
 * Predicate for search function. * Tests a person for whether they meet the search conditions represented by the keywords.
 */public class PersonSearchPredicate implements Predicate<Person> {
    private final List<FieldQuery> fieldQueries;
    public PersonSearchPredicate(List<FieldQuery> fieldQueries) {
        this.fieldQueries = fieldQueries;
    }
    @Override    public boolean test(Person person) {
        return fieldQueries.stream().allMatch(query -> matchesQuery(person, query));
    }
    private boolean matchesQuery(Person person, FieldQuery query) {
        List<String> fieldValues = getFieldValues(person, query.getField());
        if (query.getField() == SearchField.TAG) {
            return fieldValues.stream().anyMatch(fieldValue ->
                    fieldValue != null && fieldValue.toLowerCase()
                            .equals(query.getKeyword().toLowerCase()));
        } else {
            return fieldValues.stream().anyMatch(fieldValue ->
                    fieldValue != null && fieldValue.toLowerCase()
                            .contains(query.getKeyword().toLowerCase()));
        }
    }
    private List<String> getFieldValues(Person person, SearchField field) {
        return switch (field) {
        case NAME -> List.of(person.getName().fullName);
        case PHONE -> List.of(person.getPhone().value);
        case EMAIL -> List.of(person.getEmail().value);
        case LOCATION -> List.of(person.getAddress().value);
        case REMARK -> List.of(person.getRemark().value);
        case TAG -> person.getTags().stream().map(Tag::getTagName).toList();
    };    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PersonSearchPredicate)) {
            return false;
        }
        PersonSearchPredicate otherNameContainsKeywordsPredicate = (PersonSearchPredicate) other;
        return fieldQueries.equals(otherNameContainsKeywordsPredicate.fieldQueries);
    }
    @Override
    public String toString() {
        return this.fieldQueries.stream().map(FieldQuery::toString).collect(Collectors.joining(" "));
    }
}