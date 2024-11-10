package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ModuleRoleMap} matches any of the keywords given.
 */
public class ModuleRoleContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<ModuleRolePair> moduleRoleMapKeywords;


    public ModuleRoleContainsKeywordsPredicate(Set<ModuleRolePair> moduleRoleMapKeywords) {
        this.moduleRoleMapKeywords = moduleRoleMapKeywords;
    }

    public ModuleRoleContainsKeywordsPredicate(List<ModuleRolePair> moduleRoleMapKeywords) {
        this.moduleRoleMapKeywords = new HashSet<>(moduleRoleMapKeywords);
    }


    /**
     * Test if keywords contained in {@code person}'s {@code ModuleRoleMap}.
     * @param person the input argument
     * @return true if any keyword is contained in {@code person}'s {@code ModuleRoleMap}, else false
     */
    public boolean test(Person person) {
        ModuleRoleMap personModuleRoleMap = person.getModuleRoleMap();
        return moduleRoleMapKeywords.stream().anyMatch(personModuleRoleMap::containsModuleRolePair);
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

        return moduleRoleMapKeywords.equals(otherModuleContainsKeywordsPredicate.moduleRoleMapKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", moduleRoleMapKeywords).toString();
    }

    /**
     * Get module-role pairs of moduleRoleMapToSearch.
     *
     * @return a List of string of module-role pairs.
     */
    public List<String> getModuleRolePairs() {
        return moduleRoleMapKeywords.stream()
                .map(ModuleRolePair::toString)
                .collect(Collectors.toList());
    }
}
