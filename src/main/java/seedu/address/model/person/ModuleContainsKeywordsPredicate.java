package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Module} matches the keyword given.
 */
public class ModuleContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public ModuleContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getModules().stream()
                .anyMatch(module -> module.toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleContainsKeywordsPredicate)) {
            return false;
        }

        ModuleContainsKeywordsPredicate otherModulePredicate = (ModuleContainsKeywordsPredicate) other;
        return keyword.equals(otherModulePredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keyword).toString();
    }
}
