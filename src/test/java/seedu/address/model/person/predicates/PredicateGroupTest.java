package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PredicateGroupTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);

        PredicateGroup firstPredicateGroup = new PredicateGroup(firstPredicate);
        PredicateGroup secondPredicateGroup = new PredicateGroup(
                new NameContainsKeywordsPredicate(secondPredicateKeywordList));

        // same object -> returns true
        assertTrue(firstPredicateGroup.equals(firstPredicateGroup));

        // same values -> returns true
        PredicateGroup firstPredicateGroupCopy = new PredicateGroup(firstPredicate);
        assertTrue(firstPredicateGroup.equals(firstPredicateGroupCopy));

        // different types -> returns false
        assertFalse(firstPredicateGroup.equals(1));

        // null -> returns false
        assertFalse(firstPredicateGroup.equals(null));

        // different person -> returns false
        assertFalse(firstPredicateGroup.equals(secondPredicateGroup));
    }

    @Test
    public void add() {
        List<String> keywords = Arrays.asList("keyword1", "keyword2");
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(keywords);
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(keywords);
        GenderMatchesKeywordsPredicate genderPredicate = new GenderMatchesKeywordsPredicate(keywords);
        AgeContainsKeywordsPredicate agePredicate = new AgeContainsKeywordsPredicate(keywords);
        DetailsContainsKeywordsPredicate detailsPredicate = new DetailsContainsKeywordsPredicate(keywords);
        StudyGroupsContainKeywordsPredicate studyGroupsPredicate = new StudyGroupsContainKeywordsPredicate(keywords);

        PredicateGroup expectedPredicateGroup = new PredicateGroup(namePredicate, emailPredicate, genderPredicate,
                agePredicate, detailsPredicate, studyGroupsPredicate);

        PredicateGroup predicateGroup = new PredicateGroup();
        predicateGroup.add(namePredicate);
        predicateGroup.add(emailPredicate);
        predicateGroup.add(genderPredicate);
        predicateGroup.add(agePredicate);
        predicateGroup.add(detailsPredicate);
        predicateGroup.add(studyGroupsPredicate);
        assertEquals(expectedPredicateGroup, predicateGroup);
    }

    @Test
    public void test_personPassAllPredicate_returnsTrue() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                Arrays.asList("Kingsleigh", "Burton", "Lewis"));
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("wonderland"));
        GenderMatchesKeywordsPredicate genderPredicate = new GenderMatchesKeywordsPredicate(
                Collections.singletonList("F"));
        AgeContainsKeywordsPredicate agePredicate = new AgeContainsKeywordsPredicate(
                Arrays.asList("20", "21", "22", "23"));
        DetailsContainsKeywordsPredicate detailsPredicate = new DetailsContainsKeywordsPredicate(
                Arrays.asList("wizard", "looking", "yellow"));
        StudyGroupsContainKeywordsPredicate studyGroupsPredicate = new StudyGroupsContainKeywordsPredicate(
                Arrays.asList("H1", "G3", "G4"));

        Person person = new PersonBuilder().withName("Alice Kingsleigh").withEmail("alice@wonderland.com")
                .withGender("F").withAge("22").withDetail("Went through the Looking Glass")
                .withStudyGroupTags("H1", "H2", "G1").build();
        PredicateGroup predicateGroup = new PredicateGroup(namePredicate, emailPredicate, genderPredicate,
                agePredicate, detailsPredicate, studyGroupsPredicate);

        assertTrue(predicateGroup.test(person));
    }

    @Test
    public void test_personDoesNotPassAllPredicate_returnsFalse() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                Arrays.asList("Kingsleigh", "Burton", "Lewis"));
        EmailContainsKeywordsPredicate emailPredicate = new EmailContainsKeywordsPredicate(
                Collections.singletonList("wonderland"));
        GenderMatchesKeywordsPredicate genderPredicate = new GenderMatchesKeywordsPredicate(
                Collections.singletonList("M"));
        AgeContainsKeywordsPredicate agePredicate = new AgeContainsKeywordsPredicate(
                Arrays.asList("20", "21", "22", "23"));
        DetailsContainsKeywordsPredicate detailsPredicate = new DetailsContainsKeywordsPredicate(
                Arrays.asList("wizard", "looking", "yellow"));
        StudyGroupsContainKeywordsPredicate studyGroupsPredicate = new StudyGroupsContainKeywordsPredicate(
                Arrays.asList("H1", "G3", "G4"));

        // Fail all predicates
        Person person = new PersonBuilder().withName("Dorothy Gale").withEmail("dorothy@land.oz")
                .withGender("F").withAge("18").withDetail("Up the tornado")
                .withStudyGroupTags("O1", "O2", "O3").build();
        PredicateGroup predicateGroup = new PredicateGroup(namePredicate, emailPredicate, genderPredicate,
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
                Arrays.asList("keyword1", "keyword2"));
        PredicateGroup predicateGroup = new PredicateGroup(predicate);

        String expected = PredicateGroup.class.getCanonicalName() + "{predicates=[" + predicate + "]}";
        assertEquals(expected, predicateGroup.toString());
    }
}
