package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoleType;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), getPhone("87438807"), getEmail("alexyeoh@example.com"),
                getAddress("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), getMap("CS2040S", RoleType.PROFESSOR)),
            new Person(new Name("Bernice Yu"), getPhone("99272758"), getEmail("berniceyu@example.com"),
                getAddress("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getEmptyMap()),
            new Person(new Name("Charlotte Oliveiro"), getPhone("93210283"), getEmail("charlotte@example.com"),
                getAddress("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getMultipleRolesMap()),
            new Person(new Name("David Li"), getPhone("91031282"), getEmail("lidavid@example.com"),
                getAddress("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getEmptyMap()),
            new Person(new Name("Irfan Ibrahim"), getPhone("92492021"), getEmail("irfan@example.com"),
                getAddress("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getMap("MA1522", RoleType.TUTOR)),
            new Person(new Name("Roy Balakrishnan"), getPhone("92624417"), getEmail("royb@example.com"),
                getAddress("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getMultipleRolesMap())
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an {@Code Optional<Address>} containing the address given.
     */
    public static Optional<Address> getAddress(String string) {
        return Optional.of(new Address(string));
    }

    /**
     * Returns an {@Code Optional<Phone>} containing the phone given.
     */
    public static Optional<Phone> getPhone(String string) {
        return Optional.of(new Phone(string));
    }

    /**
     * Returns an {@Code Optional<Email>} containing the address given.
     */
    public static Optional<Email> getEmail(String string) {
        return Optional.of(new Email(string));
    }

    /**
     * Returns a ModuleRoleMap containing the given module code and role.
     */
    public static ModuleRoleMap getMap(String moduleCode, RoleType roleType) {
        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();
        hashMap.put(new ModuleCode(moduleCode), roleType);
        ModuleRoleMap map = new ModuleRoleMap(hashMap);
        return map;
    }

    /**
     * Returns a ModuleRoleMap containing multiple roles.
     */
    public static ModuleRoleMap getMultipleRolesMap() {
        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();
        hashMap.put(new ModuleCode("CS2103T"), RoleType.STUDENT);
        hashMap.put(new ModuleCode("CS1101S"), RoleType.TUTOR);
        hashMap.put(new ModuleCode("MA1521"), RoleType.STUDENT);
        ModuleRoleMap map = new ModuleRoleMap(hashMap);
        return map;
    }

    /**
     * Returns an empty ModuleRoleMap.
     */
    public static ModuleRoleMap getEmptyMap() {
        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();
        return new ModuleRoleMap(hashMap);
    }

}
