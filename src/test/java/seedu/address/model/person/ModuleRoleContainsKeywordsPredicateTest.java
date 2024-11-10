package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

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
        ModuleRoleMap firstModuleRoleMap =
                ParserUtil.parseModuleRoleMap(Collections.singletonList("CS1101S-Student"));
        ModuleRoleMap secondModuleRoleMap =
                ParserUtil.parseModuleRoleMap(Arrays.asList("CS2103T-Prof", "CS1101S-Student"));

        ModuleRoleContainsKeywordsPredicate firstPredicate =
                new ModuleRoleContainsKeywordsPredicate(firstModuleRoleMap);
        ModuleRoleContainsKeywordsPredicate secondPredicate =
                new ModuleRoleContainsKeywordsPredicate(secondModuleRoleMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ModuleRoleContainsKeywordsPredicate firstPredicateCopy =
                new ModuleRoleContainsKeywordsPredicate(firstModuleRoleMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

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
                ParserUtil.parseModuleRoleMap(Collections.singletonList("CS1101S-Student")));
        Person person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();
        assertTrue(predicate.test(person));

        // Multiple keywords
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRoleMap(Arrays.asList("CS1101S-Student", "CS2103T-Prof")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE_2},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE_2}).build();
        assertTrue(predicate.test(person));

        // Only one matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRoleMap(Arrays.asList("CS1101S-Student", "CS2103T-Prof")));
        person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();
        assertTrue(predicate.test(person));

        // Mixed-case keywords
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRoleMap(Arrays.asList("cS1101S-student", "CS2103T-prof")));
        person = new PersonBuilder().withModuleRoleMap(new ModuleCode[]{DEFAULT_MODULE_CODE, DEFAULT_MODULE_CODE_2},
                new RoleType[]{DEFAULT_ROLE_TYPE, DEFAULT_ROLE_TYPE_2}).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_moduleRoleDoesNotContainKeywords_returnsFalse() throws ParseException {
        Person person = new PersonBuilder().withModuleRoleMap(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE).build();

        // Zero keywords
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(
                new ModuleRoleMap(new ModuleCode[]{}, new RoleType[]{}));
        assertFalse(predicate.test(person));

        // Non-matching keyword
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRoleMap(Collections.singletonList("CS2103T-Prof")));
        assertFalse(predicate.test(person));

        // Keywords match module and role, but not together
        predicate = new ModuleRoleContainsKeywordsPredicate(
                ParserUtil.parseModuleRoleMap(Arrays.asList("CS1101S-Prof")));
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() throws ParseException {
        ModuleRoleMap moduleRoleMap = ParserUtil.parseModuleRoleMap(Arrays.asList("CS1101S-Student", "CS2103T-Prof"));
        ModuleRoleContainsKeywordsPredicate predicate = new ModuleRoleContainsKeywordsPredicate(moduleRoleMap);

        String expected =
                ModuleRoleContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + moduleRoleMap + "}";
        assertEquals(expected, predicate.toString());
    }
}
