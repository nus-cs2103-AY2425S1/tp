package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalProperties.PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS;
import static seedu.address.testutil.TypicalProperties.PROPERTY_CONDO_WITH_MULTIPLE_TAGS;
import static seedu.address.testutil.TypicalProperties.PROPERTY_CONDO_WITH_TAG;
import static seedu.address.testutil.TypicalProperties.PROPERTY_HDB;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class BuyPropertyContainsKeywordsPredicateTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicate1 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        BuyPropertyContainsKeywordsPredicate predicate2 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        BuyPropertyContainsKeywordsPredicate predicate1 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword1"));
        BuyPropertyContainsKeywordsPredicate predicate2 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword2"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentPredicateType_returnsFalse() {
        BuyPropertyContainsKeywordsPredicate predicate1 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        SellPropertyContainsKeywordsPredicate predicate2 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_buyPropertyContainsKeyword_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("hdb"));
        Person person = new PersonBuilder().withBuyProperty(
                PROPERTY_HDB).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_buyPropertyDoesNotContainKeyword_returnsFalse() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("condo"));
        Person person = new PersonBuilder().withBuyProperty(
                PROPERTY_HDB).build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_buyPropertyContainsKeywordInTags_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("spacious"));
        Person person = new PersonBuilder().withBuyProperty(
                PROPERTY_CONDO_WITH_MULTIPLE_TAGS).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_buyPropertyContainsKeywordIgnoreCase_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicateHdb =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("HdB"));
        BuyPropertyContainsKeywordsPredicate predicateCondo =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("CoNdO"));
        Person personHdb = new PersonBuilder().withBuyProperty(
                PROPERTY_HDB).build();
        Person personCondo = new PersonBuilder().withBuyProperty(
                PROPERTY_CONDO_WITH_TAG).build();
        assertTrue(predicateHdb.test(personHdb));
        assertTrue(predicateCondo.test(personCondo));
    }

    @Test
    public void test_personContainsMultipleKeywords_returnsTrue() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Arrays.asList("apartment", "14-21"));
        Person person = new PersonBuilder().withBuyProperty(
                PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personDoesNotContainAnyKeywords_returnsFalse() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Arrays.asList("NonExistentTag1", "NonExistentTag2"));
        Person person = new PersonBuilder().withBuyProperty(
                PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS).build();
        assertFalse(predicate.test(person));
    }
}
