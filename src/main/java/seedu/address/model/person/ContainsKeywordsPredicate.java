package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.function.Predicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class ContainsKeywordsPredicate implements Predicate<Person> {

    private final ArgumentMultimap keywords;

    public ContainsKeywordsPredicate(ArgumentMultimap keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Person person) {
        return (keywords.getValue(PREFIX_ROLE).isPresent() && testRole(keywords.getAllValues(PREFIX_ROLE), person))
                || (keywords.getValue(PREFIX_NAME).isPresent() && testName(keywords.getAllValues(PREFIX_NAME), person))
                || (keywords.getValue(PREFIX_TAG).isPresent() && testTags(keywords.getAllValues(PREFIX_TAG), person))
                || (keywords.getValue(PREFIX_NRIC).isPresent() && testNric(keywords.getAllValues(PREFIX_NRIC), person));
    }

    private boolean testTags(List<String> allValues, Person person) {
        return allValues.stream()
                .anyMatch(tag -> person.hasTag(new Tag(tag)));
    }

    private boolean testNric(List<String> allValues, Person person) {
        return allValues.stream()
                .anyMatch(nric -> person.hasNric(new Nric(nric)));
    }

    private boolean testRole(List<String> allValues, Person person) {
        return allValues.stream()
                .anyMatch(role -> person.hasRole(Role.valueOf(role.toUpperCase())));
    }

    private boolean testName(List<String> allValues, Person person) {
        return allValues.stream()
                .anyMatch(name -> person.hasName(new Name(name)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherContainsKeywordsPredicate = (ContainsKeywordsPredicate) other;
        return keywords.equals(otherContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
