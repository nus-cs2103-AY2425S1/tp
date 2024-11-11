package seedu.address.model.person;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ModuleRoleMap} matches any of the keywords given.
 */
public class ModuleRoleContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<ModuleRolePair> moduleRoleKeywords;


    public ModuleRoleContainsKeywordsPredicate(Set<ModuleRolePair> moduleRoleKeywords) {
        this.moduleRoleKeywords = new LinkedHashSet<>(moduleRoleKeywords);
    }

    public ModuleRoleContainsKeywordsPredicate(List<ModuleRolePair> moduleRoleKeywords) {
        this.moduleRoleKeywords = new LinkedHashSet<>(moduleRoleKeywords);
    }


    /**
     * Test if keywords contained in {@code person}'s {@code ModuleRoleMap}.
     * @param person the input argument
     * @return true if any keyword is contained in {@code person}'s {@code ModuleRoleMap}, else false
     */
    public boolean test(Person person) {
        ModuleRoleMap personModuleRoleMap = person.getModuleRoleMap();
        return moduleRoleKeywords.stream().anyMatch(personModuleRoleMap::containsModuleRolePair);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleRoleContainsKeywordsPredicate otherModuleContainsKeywordsPredicate)) {
            return false;
        }

        return moduleRoleKeywords.equals(otherModuleContainsKeywordsPredicate.moduleRoleKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", moduleRoleKeywords).toString();
    }

    /**
     * Get module-role pairs of moduleRoleMapToSearch.
     *
     * @return a List of string of module-role pairs.
     */
    public List<String> getModuleRolePairs() {
        return moduleRoleKeywords.stream()
                .map(ModuleRolePair::toString)
                .collect(Collectors.toList());
    }
}
