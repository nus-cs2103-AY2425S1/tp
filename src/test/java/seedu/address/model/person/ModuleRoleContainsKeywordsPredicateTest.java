package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonBuilder;

public class ModuleRoleContainsKeywordsPredicateTest {
    private static final ModuleCode DEFAULT_MODULE_CODE = new ModuleCode("CS1101S");
    private static final ModuleCode DEFAULT_MODULE_CODE_2 = new ModuleCode("CS2103T");
    private static final RoleType DEFAULT_ROLE_TYPE = RoleType.STUDENT;
    private static final RoleType DEFAULT_ROLE_TYPE_2 = RoleType.PROFESSOR;

    @Test
    public void equals() throws ParseException {
        List<ModuleRolePair> firstModuleRolePairs =
                ParserUtil.parseModuleRolePairs(Collections.singletonList("CS1101S-Student"));

        List<ModuleRolePair> secondModuleRolePairs =
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS2103T-Prof", "CS1101S-Student"));

        ModuleRoleContainsKeywordsPredicate firstPredicate =
                new ModuleRoleContainsKeywordsPredicate(firstModuleRolePairs);
        ModuleRoleContainsKeywordsPredicate secondPredicate =
                new ModuleRoleContainsKeywordsPredicate(secondModuleRolePairs);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values, same order -> returns true
        ModuleRoleContainsKeywordsPredicate secondPredicateCopy =
                new ModuleRoleContainsKeywordsPredicate(secondModuleRolePairs);
        assertTrue(secondPredicate.equals(secondPredicateCopy));

        // same values, different order -> returns true
        List<ModuleRolePair> secondModuleRolePairsDifferentOrder =
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Student", "CS2103T-Prof"));
        ModuleRoleContainsKeywordsPredicate secondPredicateDifferentOrder =
                new ModuleRoleContainsKeywordsPredicate(secondModuleRolePairsDifferentOrder);
        assertTrue(secondPredicate.equals(secondPredicateDifferentOrder));

        // same values, duplicate module-role pairs -> returns true
        List<ModuleRolePair> secondModuleRolePairsDuplicate =
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS2103T-Prof", "CS1101S-Student", "CS1101S-Student"));
        ModuleRoleContainsKeywordsPredicate secondPredicateDuplicate =
                new ModuleRoleContainsKeywordsPredicate(secondModuleRolePairsDuplicate);
        assertTrue(secondPredicate.equals(secondPredicateDuplicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different module-role pairs -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_moduleRoleContainsKeywords_returnsTrue() throws ParseException {
        // One keyword
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Collections.singletonList("CS1101S-Student")));
        Person person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();
        assertTrue(predicate.test(person));

        // Multiple keywords, different modules
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Student", "CS2103T-Prof")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE_2},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE_2}).build();
        assertTrue(predicate.test(person));

        // Multiple keywords, same module
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Student", "CS1101S-Prof")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE_2}).build();
        assertTrue(predicate.test(person));

        // Multiple keywords, same module and role
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Student", "CS1101S-Student")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE}).build();
        assertTrue(predicate.test(person));

        // Only one matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Student", "CS2103T-Prof")));
        person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();
        assertTrue(predicate.test(person));

        // Mixed-case keywords
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("cS1101S-student", "CS2103T-prof")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE_2},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE_2}).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_moduleRoleDoesNotContainKeywords_returnsFalse() throws ParseException {
        Person person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();

        // Zero keywords
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                List.of());
        assertFalse(predicate.test(person));

        // Non-matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Collections.singletonList("CS2103T-Prof")));
        assertFalse(predicate.test(person));

        // Keywords match module and role, but not together
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRolePairs(Arrays.asList("CS1101S-Prof")));
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() throws ParseException {
        List<ModuleRolePair> moduleRolePairs = ParserUtil.parseModuleRolePairs(
                Arrays.asList("CS1101S-Student", "CS2103T-Prof"));
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(moduleRolePairs);

        String expected =
                ModuleRoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + moduleRolePairs + "}";
        assertEquals(expected, predicate.toString());
    }
}
