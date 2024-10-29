package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.role.Attendee;
import seedu.address.model.role.Role;
import seedu.address.model.role.Sponsor;
import seedu.address.model.role.Vendor;
import seedu.address.model.role.Volunteer;
import seedu.address.testutil.PersonBuilder;

public class FieldContainsKeywordsPredicateTest {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TELEGRAM_USERNAME = "amybee";

    Person testPerson = new PersonBuilder().withRoles("attendee").build();

    @Test
    public void test_name() {
        List<String> keywords = Collections.singletonList("Amy");
        FieldContainsKeywordsPredicate<Name> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getName);
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_phone() {
        List<String> keywords = Collections.singletonList("85355255");
        FieldContainsKeywordsPredicate<Phone> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getPhone);
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_email() {
        List<String> keywords = Collections.singletonList("amy@gmail.com");
        FieldContainsKeywordsPredicate<Email> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getEmail);
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_address_numerical() {
        List<String> keywords = Collections.singletonList("123");
        String address = testPerson.getAddress().toString();
        FieldContainsKeywordsPredicate<Address> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getAddress);
        System.out.println(StringUtil.containsWordIgnoreCase("123, Jurong", "123"));
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_address_text() {
        List<String> keywords = Collections.singletonList("Jurong");
        String address = testPerson.getAddress().toString();
        FieldContainsKeywordsPredicate<Address> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getAddress);
        assertTrue(predicate.test(testPerson));
    }

    @Test
    public void test_address_partialWord() {
        List<String> keywords = Collections.singletonList("Juron");
        String address = testPerson.getAddress().toString();
        FieldContainsKeywordsPredicate<Address> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getAddress);
        assertTrue(predicate.test(testPerson));
    }
    @Test
    public void test_telegramUsername() {
        List<String> keywords = Collections.singletonList("amybee");
        FieldContainsKeywordsPredicate<TelegramUsername> predicate = new
                FieldContainsKeywordsPredicate<>(keywords, Person::getTelegramUsername);
        assertTrue(predicate.test(testPerson));
    }
}
