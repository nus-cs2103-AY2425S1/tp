package seedu.address.model.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name, Nric, Role or Tag} matches any of the keywords given.
 * Each keyword is prepended with their respective prefixes
 *
 */
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
                .anyMatch(keyword -> person.hasName(new Name(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherPredicate = (ContainsKeywordsPredicate) other;

        // Compare the keyword maps for equality.
        return this.keywords.equals(otherPredicate.keywords);
    }
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        // Loop through all prefixes in the ArgumentMultimap and add them to the ToStringBuilder
        for (Prefix prefix : keywords.getPrefixes()) {
            List<String> values = keywords.getAllValues(prefix);
            if (!values.isEmpty()) {
                toStringBuilder.add(prefix.getPrefix(), values);
            }
        }
        return toStringBuilder.toString();
    }


}
