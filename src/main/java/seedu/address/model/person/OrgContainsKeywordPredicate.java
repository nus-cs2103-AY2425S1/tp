package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

public class OrgContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public OrgContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getOrg(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OrgContainsKeywordsPredicate)) {
            return false;
        }

        OrgContainsKeywordsPredicate otherOrgContainsKeywordsPredicate = (OrgContainsKeywordsPredicate) other;
        return keywords.equals(otherOrgContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

