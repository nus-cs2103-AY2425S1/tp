package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code ModuleRoleMap} matches any of the keywords given.
 */
public class ModuleRoleContainsKeywordsPredicate implements Predicate<Person> {
    private final ModuleRoleMap moduleRoleMapKeywords;


    public ModuleRoleContainsKeywordsPredicate(ModuleRoleMap moduleRoleMapKeywords) {
        this.moduleRoleMapKeywords = moduleRoleMapKeywords;
    }


    /**
     * Test if keywords contained in person's module role map.
     * @param person the input argument
     * @return true if keywords contained else false
     */
    public boolean test(Person person) {
        ModuleRoleMap personModuleRoleMap = person.getModuleRoleMap();
        return moduleRoleMapKeywords.getRoles().entrySet().stream()
                .anyMatch(entry -> entry.getValue().equals(personModuleRoleMap.getRoles().get(entry.getKey())));
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
        return moduleRoleMapKeywords.getData()
                .stream()
                .map(ModuleRolePair::toString)
                .collect(Collectors.toList());
    }
}
