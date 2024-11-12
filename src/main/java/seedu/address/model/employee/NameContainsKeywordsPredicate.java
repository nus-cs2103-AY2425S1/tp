package seedu.address.model.employee;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Employee}'s {@code EmployeeName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Employee> {
    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate}.
     *
     * @param keywords A valid list of names.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Employee employee) {
        // Null objects should not have been added to addressbook
        assert employee != null;

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(employee.getName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
