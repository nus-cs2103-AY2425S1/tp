//package seedu.address.model.util;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.person.Person;
//
//public class SampleDataUtilTest {
//    @Test
//    void getSamplePersons_returnsNonEmptyArray() {
//        assertTrue(SampleDataUtil.getSamplePersons().length > 0);
//    }
//
//    @Test
//    void getSamplePersons_containsExpectedPerson() {
//        Person[] samplePersons = SampleDataUtil.getSamplePersons();
//        Person expectedPerson = new Person(new Name("Alex Yeoh"), new Phone("87438807"),
//                new Email("alexyeoh@example.com"),
//                SampleDataUtil.getEventSet("IFG"),
//                SampleDataUtil.getRoleSet("Athlete - COM - Volleyball Women, Tennis"));
//        assertTrue(Arrays.asList(samplePersons).contains(expectedPerson));
//    }
//
//    @Test
//    void getSampleAddressBook_containsSamplePersons() {
//        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();
//        Person[] samplePersons = SampleDataUtil.getSamplePersons();
//        for (Person samplePerson : samplePersons) {
//            assertTrue(sampleAddressBook.getPersonList().contains(samplePerson));
//        }
//    }
//}
