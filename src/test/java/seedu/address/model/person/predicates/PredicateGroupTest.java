package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.FindUtil;
import seedu.address.testutil.PersonBuilder;

public class PredicateGroupTest {

    @Test
    public void equals() {
        Set<String> firstPredicateKeywordList = Set.of("first");
        Set<String> secondPredicateKeywordList = Set.of("first", "second");
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        PredicateGroup firstPredicateGroup = new PredicateGroup();
        firstPredicateGroup.add(firstPredicate);
        PredicateGroup secondPredicateGroup = new PredicateGroup();
        secondPredicateGroup.add(secondPredicate);

        // same object -> returns true
        assertTrue(firstPredicateGroup.equals(firstPredicateGroup));

        // same values -> returns true
        PredicateGroup firstPredicateGroupCopy = new PredicateGroup();
        firstPredicateGroupCopy.add(firstPredicate);
        assertTrue(firstPredicateGroup.equals(firstPredicateGroupCopy));

        // different types -> returns false
        assertFalse(firstPredicateGroup.equals(1));

        // null -> returns false
        assertFalse(firstPredicateGroup.equals(null));

        // different person -> returns false
        assertFalse(firstPredicateGroup.equals(secondPredicateGroup));
    }

    @Test
    public void test_personDoesNotPassAllPredicate_returnsFalse() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                Set.of("Kingsleigh", "Burton", "Lewis"));
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(
                Set.of("wonderland"));
        GenderMatchesKeywordsPredicate genderPredicate = new GenderMatchesKeywordsPredicate(
                Set.of("M"));
        AgeContainsKeywordsPredicate agePredicate = new AgeContainsKeywordsPredicate(
                Set.of("20", "21-23"));
        DetailContainsKeywordsPredicate detailsPredicate = new DetailContainsKeywordsPredicate(
                Set.of("wizard", "looking", "yellow"));
        StudyGroupsContainKeywordsPredicate studyGroupsPredicate = new StudyGroupsContainKeywordsPredicate(
                Set.of("H1", "G3", "G4"));

        // Fail all predicates
        Person person = new PersonBuilder().withName("Dorothy Gale").withEmail("dorothy@land.oz")
                .withGender("F").withAge("18").withDetail("Up the tornado")
                .withStudyGroupTags("O1", "O2", "O3").build();
        PredicateGroup predicateGroup = FindUtil.getPredicateGroup(namePredicate, emailPredicate,
                genderPredicate,
                agePredicate, detailsPredicate, studyGroupsPredicate);

        assertFalse(predicateGroup.test(person));

        // Fail one predicate (detail predicate will fail)
        person = new PersonBuilder().withName("Alice Kingsleigh").withEmail("alice@wonderland.com")
                .withGender("F").withAge("22").withDetail("Went through the unlooking Glass")
                .withStudyGroupTags("H1", "H2", "G1").build();

        assertFalse(predicateGroup.test(person));
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Set.of("keyword1", "keyword2"));
        PredicateGroup predicateGroup = FindUtil.getPredicateGroup(predicate);

        String expected = PredicateGroup.class.getCanonicalName() + "{predicates=[" + predicate + "]}";
        assertEquals(expected, predicateGroup.toString());
    }
}
