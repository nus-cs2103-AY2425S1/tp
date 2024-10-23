package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class DuplicatePhoneTaggerTest {

    private static final String TEST_PHONE_ONE = "11111111";
    private static final String TEST_PHONE_TWO = "12345678";
    private static final String TEST_PHONE_THREE = "87654321";
    private static final String TEST_PHONE_FOUR = "23232323";
    private static final String TEST_PHONE_FIVE = "32323232";
    private static final String TEST_PHONE_SIX = "45454545";
    private static final String TEST_PHONE_SEVEN = "54545454";
    private static final Person AIKEN = new PersonBuilder().withName("Aiken").withPhone(TEST_PHONE_ONE)
            .withEmail("aiken@gmail.com").withAddress("Kent Ridge MRT").build();
    private static final Person ALEX = new PersonBuilder().withName("Alex").withPhone(TEST_PHONE_TWO)
            .withEmail("alex@yahoo.com").withAddress("Minecraft").build();
    private static final Person ALICE = new PersonBuilder().withName("Alice").withPhone(TEST_PHONE_THREE)
            .withEmail("alice@hotmail.com").withAddress("CAPT").build();
    private static final Person ALBERT = new PersonBuilder().withName("Albert").withPhone(TEST_PHONE_FOUR)
            .withEmail("albert@outlook.com").withAddress("PGP").build();
    private static final Person ADRIEL = new PersonBuilder().withName("Adriel").withPhone(TEST_PHONE_FIVE)
            .withEmail("adriel@gmail.com").withAddress("UTown").build();
    private static final Person TAN_AH_KOW = new PersonBuilder().withName("Tan Ah Kow").withPhone(TEST_PHONE_SIX)
            .withEmail("tak@hotmail.com").withAddress("Woodlands MRT").build();
    private static final Person BART_BILL = new PersonBuilder().withName("Bartholomew Billiams")
            .withPhone(TEST_PHONE_SEVEN).withEmail("barbill@gmail.com").withAddress("United Kingdom").build();

    private final DuplicatePhoneTagger duplicatePhoneTagger = new DuplicatePhoneTagger();
    private final AddressBookBuilder testAB = new AddressBookBuilder();
    private Model testModel;

    @BeforeEach
    public void setUp() {
        testAB.withPerson(AIKEN)
                .withPerson(ALEX)
                .withPerson(ALICE)
                .withPerson(ALBERT)
                .withPerson(ADRIEL)
                .withPerson(TAN_AH_KOW)
                .withPerson(BART_BILL);
    }
    // Test for identifying no duplicates
    @Test
    public void execute_isDuplicatePresent_noDuplicates() {
        testModel = new ModelManager(testAB.build(), new UserPrefs());

        List<Person> persons = testModel.getFilteredPersonList();
        duplicatePhoneTagger.updateFrequenciesOfPhones(persons);
        assertFalse(duplicatePhoneTagger.isDuplicatePresent());
    }
    // Test for identifying duplicates
    @Test
    public void execute_isDuplicatePresent_withDuplicates() {
        Person testPerson = new PersonBuilder().withName("Aaron").withPhone(TEST_PHONE_TWO)
                .withEmail("aaron@example.com").withAddress("Kent Ridge MRT").build();
        testAB.withPerson(testPerson);
        testModel = new ModelManager(testAB.build(), new UserPrefs());

        List<Person> persons = testModel.getFilteredPersonList();
        duplicatePhoneTagger.updateFrequenciesOfPhones(persons);
        assertTrue(duplicatePhoneTagger.isDuplicatePresent());
    }
    // Test for identifying correct duplicate
    @Test
    public void identify_correct_duplicate() {
        Person testPerson = new PersonBuilder().withName("Aaron").withPhone(TEST_PHONE_TWO)
                .withEmail("aiken@example.com").withAddress("Kent Ridge MRT").build();
        testAB.withPerson(testPerson);
        testModel = new ModelManager(testAB.build(), new UserPrefs());

        List<Person> persons = testModel.getFilteredPersonList();
        duplicatePhoneTagger.updateFrequenciesOfPhones(persons);
        for (Person person : persons) {
            if (person.getPhone().equals(new Phone(TEST_PHONE_TWO))) {
                assertTrue(duplicatePhoneTagger.isPhoneDuplicate(person.getPhone()));
            } else {
                assertFalse(duplicatePhoneTagger.isPhoneDuplicate(person.getPhone()));
            }
        }
    }
    @Test
    public void execute_tagPhoneDuplicates() {
        Person testPerson = new PersonBuilder().withName("Aaron").withPhone(TEST_PHONE_TWO)
                .withEmail("aaron@example.com").withAddress("Kent Ridge MRT").build();
        testAB.withPerson(testPerson);
        testModel = new ModelManager(testAB.build(), new UserPrefs());

        duplicatePhoneTagger.tagPhoneDuplicates(testModel);
        List<Person> persons = testModel.getFilteredPersonList();
        assertTrue(persons
                .stream()
                .anyMatch(person -> person
                        .getTags()
                        .stream()
                        .anyMatch(tag -> tag
                                .tagName
                                .equals(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME))));

        HashMap<Phone, Integer> phoneFrequencies = duplicatePhoneTagger.getPhoneFrequencies();
        for (Phone phoneNumber : phoneFrequencies.keySet()) {
            if (phoneNumber.equals(new Phone(TEST_PHONE_TWO))) {
                assertEquals(2, phoneFrequencies.get(phoneNumber));
            } else {
                assertEquals(1, phoneFrequencies.get(phoneNumber));
            }
        }
    }

    @Test
    public void execute_tagPhoneDuplicates_withNoDuplicates() {
        testModel = new ModelManager(testAB.build(), new UserPrefs());

        duplicatePhoneTagger.tagPhoneDuplicates(testModel);
        List<Person> persons = testModel.getFilteredPersonList();
        assertFalse(persons
                .stream()
                .anyMatch(person -> person
                        .getTags()
                        .stream()
                        .anyMatch(tag -> tag
                                .tagName
                                .equals(DuplicatePhoneTagger.DUPLICATE_PHONE_TAG_NAME))));

        HashMap<Phone, Integer> phoneFrequencies = duplicatePhoneTagger.getPhoneFrequencies();
        for (Phone phoneNumber : phoneFrequencies.keySet()) {
            assertEquals(1, phoneFrequencies.get(phoneNumber));
        }
    }

}
