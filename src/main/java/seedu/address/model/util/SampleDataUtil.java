package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getEmergencyContactSet(
                        new EmergencyContact(
                                new Name("Sarah Lim"),
                                new Phone("98761234"),
                                new Relationship("Daughter")),
                        new EmergencyContact(
                                new Name("Peter Yeoh"),
                                new Phone("97645132"),
                                new Relationship("Son"))),
                new Doctor(new DoctorName("Tan Wei Ming"), new Phone("99119919"), new Email("drtan@gmail.com")),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getEmergencyContactSet(
                        new EmergencyContact(
                                new Name("Kevin Goh"),
                                new Phone("98764123"),
                                new Relationship("Husband")),
                        new EmergencyContact(
                                new Name("Brad Goh"),
                                new Phone("98764142"),
                                new Relationship("Cousin")
                        )),
                new Doctor(new DoctorName("Lim Heng Seng"), new Phone("80987123"), new Email("drlim@gmail.com")),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getEmergencyContactSet(
                        new EmergencyContact(
                                new Name("Haziq Bin Abudllah"),
                                new Phone("98763412"),
                                new Relationship("Brother")),
                        new EmergencyContact(
                                new Name("Ahmad Bin Ahman"),
                                new Phone("98763448"),
                                new Relationship("Grandson"))
                ),
                new Doctor(new DoctorName("Robert Lim"), new Phone("91919191"), new Email("robertlim@hotmail.com")),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getEmergencyContactSet(
                        new EmergencyContact(new Name("Amanda Lee"), new Phone("98762341"), new Relationship("Cousin"))
                ),
                new Doctor(new DoctorName("Jessica Loh"), new Phone("99119919"), new Email("jloh@gmail.com")),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getEmergencyContactSet(
                        new EmergencyContact(
                                new Name("Nurul Ain"),
                                new Phone("98761243"),
                                new Relationship("Daughter")),
                        new EmergencyContact(
                                new Name("Izzudin Aiman"),
                                new Phone("94673215"),
                                new Relationship("Son")
                        ),
                        new EmergencyContact(
                                new Name("Khairul Anwar"),
                                new Phone("94673185"),
                                new Relationship("Relative")
                        )
                ), new Doctor(new DoctorName("Zhou Jie Lun"), new Phone("88888888"), new Email("zhoujl@hotmail.com")),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getEmergencyContactSet(
                        new EmergencyContact(
                                new Name("Anjali Devi"),
                                new Phone("98763124"),
                                new Relationship("Daughter")),
                        new EmergencyContact(
                                new Name("Belle Choy"),
                                new Phone("98763187"),
                                new Relationship("Granddaughter"))
                ),
                new Doctor(new DoctorName("Ed Sheeran"), new Phone("95114320"), new Email("edsheeran@gmail.com")),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a EmergencyContact set containing the list of emergency contacts given.
     */
    public static Set<EmergencyContact> getEmergencyContactSet(EmergencyContact... emergencyContacts) {
        return Arrays.stream(emergencyContacts).collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
