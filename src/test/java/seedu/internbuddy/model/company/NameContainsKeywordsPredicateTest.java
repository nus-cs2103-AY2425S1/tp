package seedu.internbuddy.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.model.application.Application;
import seedu.internbuddy.testutil.ApplicationBuilder;
import seedu.internbuddy.testutil.CompanyBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different company -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("TechCorp"));
        assertTrue(predicate.test(new CompanyBuilder().withName("TechCorp Solutions").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("TechCorp", "Solutions"));
        assertTrue(predicate.test(new CompanyBuilder().withName("TechCorp Solutions").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Solutions", "Innovations"));
        assertTrue(predicate.test(new CompanyBuilder().withName("TechCorp Innovations").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("tEchCorp", "sOLutions"));
        assertTrue(predicate.test(new CompanyBuilder().withName("TechCorp Solutions").build()));
    }

    @Test
    public void test_tagsContainsKeywords_returnsTrue() {
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("EV"));
        assertTrue(predicate.test(new CompanyBuilder().withTags("Elon, Musk, EV").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Elon", "Musk"));
        assertTrue(predicate.test(new CompanyBuilder().withTags("Elon, Musk, EV").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Zuckerberg", "Elon"));
        assertTrue(predicate.test(new CompanyBuilder().withTags("Elon, Musk, EV").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("mUsK", "eV"));
        assertTrue(predicate.test(new CompanyBuilder().withTags("Elon, Musk, EV").build()));

        // Matches both name and tag
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Musk", "EV", "Tesla"));
        assertTrue(predicate.test(new CompanyBuilder().withName("Tesla").withTags("Elon, Musk, EV").build()));
    }

    @Test
    public void test_appNameContainsKeywords_returnsTrue() {
        Application sweApplication = new ApplicationBuilder().withName("SWE").build();
        Application aiApplication = new ApplicationBuilder().withName("AI").build();
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("SWE"));
        assertTrue(predicate.test(new CompanyBuilder().withApplications(sweApplication, aiApplication).build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("SWE", "AI"));
        assertTrue(predicate.test(new CompanyBuilder().withApplications(sweApplication, aiApplication).build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("AI", "Elon"));
        assertTrue(predicate.test(new CompanyBuilder().withApplications(sweApplication, aiApplication).build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("sWe", "aI"));
        assertTrue(predicate.test(new CompanyBuilder().withApplications(sweApplication, aiApplication).build()));
    }

    @Test
    public void test_appDescriptionContainsKeywords_returnsTrue() {
        Application experienceApplication = new ApplicationBuilder()
                .withDescription("Minimum 10 years experience").build();
        Application noExperienceApplication = new ApplicationBuilder()
                .withDescription("No job experience required").build();

        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("years"));
        assertTrue(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("years", "experience"));
        assertTrue(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("AI", "required"));
        assertTrue(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("reQuiRed", "jOB"));
        assertTrue(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CompanyBuilder().withTags("EV").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Innovations"));
        assertFalse(predicate.test(new CompanyBuilder().withTags("AI").build()));

        // Keywords match phone, email and address, but does not match tags
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "contact@techcorp.com", "Main", "Street"));
        assertFalse(predicate.test(new CompanyBuilder().withPhone("12345")
                .withEmail("contact@techcorp.com").withAddress("Main Street").withTags("AI").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CompanyBuilder().withName("TechCorp").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Innovations"));
        assertFalse(predicate.test(new CompanyBuilder().withName("TechCorp Solutions").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "contact@techcorp.com", "Main", "Street"));
        assertFalse(predicate.test(new CompanyBuilder().withName("TechCorp").withPhone("12345")
                .withEmail("contact@techcorp.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_appNameDoesNotContainKeywords_returnsFalse() {
        Application pgpApplication = new ApplicationBuilder().withName("PGP").build();
        Application sgApplication = new ApplicationBuilder().withName("SG").build();

        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CompanyBuilder().withApplications(pgpApplication, sgApplication).build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Innovations"));
        assertFalse(predicate.test(new CompanyBuilder().withApplications(pgpApplication, sgApplication).build()));

        // Keywords match phone, email and address, but does not match application name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "contact@techcorp.com", "Main", "Street"));
        assertFalse(predicate.test(new CompanyBuilder().withPhone("12345").withEmail("contact@techcorp.com")
                .withAddress("Main Street").withApplications(pgpApplication).build()));
    }

    @Test
    public void test_appDescriptionDoesNotContainKeywords_returnsFalse() {
        Application experienceApplication = new ApplicationBuilder()
                .withDescription("Minimum 10 years experience").build();
        Application noExperienceApplication = new ApplicationBuilder()
                .withDescription("No job experience required").build();

        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Innovations"));
        assertFalse(predicate.test(new CompanyBuilder()
                .withApplications(experienceApplication, noExperienceApplication).build()));

        // Keywords match phone, email and address, but does not match application name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "contact@techcorp.com", "Main", "Street"));
        assertFalse(predicate.test(new CompanyBuilder().withPhone("12345").withEmail("contact@techcorp.com")
                .withAddress("Main Street").withApplications(experienceApplication).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
