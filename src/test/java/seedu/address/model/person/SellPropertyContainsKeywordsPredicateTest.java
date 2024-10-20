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

public class SellPropertyContainsKeywordsPredicateTest {

    @Test
    public void equals_sameObject_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicate1 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        SellPropertyContainsKeywordsPredicate predicate2 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        SellPropertyContainsKeywordsPredicate predicate1 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword1"));
        SellPropertyContainsKeywordsPredicate predicate2 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword2"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentPredicateType_returnsFalse() {
        SellPropertyContainsKeywordsPredicate predicate1 =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        BuyPropertyContainsKeywordsPredicate predicate2 =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("keyword"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_sellPropertyContainsKeyword_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("hdb"));
        Person person = new PersonBuilder().withSellProperty(
                PROPERTY_HDB).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_sellPropertyDoesNotContainKeyword_returnsFalse() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("condo"));
        Person person = new PersonBuilder().withSellProperty(
                PROPERTY_HDB).build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void test_sellPropertyContainsKeywordInTags_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("spacious"));
        Person person = new PersonBuilder().withSellProperty(
                PROPERTY_CONDO_WITH_MULTIPLE_TAGS).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_sellPropertyContainsKeywordIgnoreCase_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicateHdb =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("HdB"));
        SellPropertyContainsKeywordsPredicate predicateCondo =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("CoNdO"));
        Person personHdb = new PersonBuilder().withSellProperty(
                PROPERTY_HDB).build();
        Person personCondo = new PersonBuilder().withSellProperty(
                PROPERTY_CONDO_WITH_TAG).build();
        assertTrue(predicateHdb.test(personHdb));
        assertTrue(predicateCondo.test(personCondo));
    }

    @Test
    public void test_personContainsMultipleKeywords_returnsTrue() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Arrays.asList("apartment", "14-21"));
        Person person = new PersonBuilder().withSellProperty(
                PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_personDoesNotContainAnyKeywords_returnsFalse() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Arrays.asList("NonExistentTag1", "NonExistentTag2"));
        Person person = new PersonBuilder().withSellProperty(
                PROPERTY_APARTMENT_WITH_MULTIPLE_TAGS).build();
        assertFalse(predicate.test(person));
    }
}
