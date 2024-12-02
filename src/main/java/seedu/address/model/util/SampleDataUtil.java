package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
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
            new Person(new Name("Campus Asset Management"), getPhone("+65 6601 7878 (24 hours)"),
                getEmail("maintenance@nus.edu.sg"),
                getAddress("CAM Office 8 Kent Ridge Drive #01-01 Singapore 119246"),
                getTagSet("school"), getEmptyMap(),
                getDescription("For issues relating to cleanliness, leaks, repairs, aircon etc.")
                ),
            new Person(new Name("Campus Emergency and Security"), getPhone("+65 6874 1616 (24 hours)"),
                getEmail("ces@nus.edu.sg"),
                getAddress("Campus Emergency and Security Office 17 & 18 Prince George’s Park Singapore 118417"),
                getTagSet("school", "security", "emergency"), getEmptyMap(),
                getDescription("For emergencies only")),
            new Person(new Name("Clinical Services"), getPhone("+65 6601 5035"),
                getEmail("uhc_health@nus.edu.sg"),
                Optional.empty(),
                getTagSet("school", "clinic", "health"), getEmptyMap(),
                getDescription("For appointment booking, medical consultation etc.")),
            new Person(new Name("Lifeline NUS"), getPhone("+65 6516 7777"),
                Optional.empty(),
                getAddress("NUS Kent Ridge Campus"),
                getTagSet("school", "health", "emergency"), getEmptyMap(),
                getDescription("(24 hours psychological support for students and staff)")),
            new Person(new Name("NUS Office of Finance"), getPhone("+65 6516 1177 (All student finance matters)"),
                Optional.empty(),
                getAddress("Student Service Centre (SSC) @ Kent Ridge Campus, "
                    + "University Sports Centre, USC-03-07, "
                    + "2 Sports Drive 1, "
                    + "Singapore 117561"),
                getTagSet("school", "finance", "office"), getEmptyMap(),
                getDescription("For email enquiry, visit http://www.askstudentservice.nus.edu.sg/")),
            new Person(new Name("Boyd Anderson"), getPhone("660 17900"), getEmail("boyd@comp.nus.edu.sg"),
                getAddress("COM3-02-46"),
                getTagSet("favorite"), getMap("CS1101S", RoleType.PROFESSOR),
                getDescription("Lecturer in the Department of Computer Science at the School of Computing"
                    + " at the National University of Singapore (NUS).")),
            new Person(new Name("Martin Henz"), getPhone("651 66632"), getEmail("henz@comp.nus.edu.sg"),
                getAddress("COM3-02-05"),
                new HashSet<>(), getMap("CS1101S", RoleType.PROFESSOR),
                getDescription("Likes to set difficult questions to his students!")),
            new Person(new Name("Aaron Tan"), getPhone("651 62906"), getEmail("tantc@comp.nus.edu.sg"),
                getAddress("COM1-03-12"),
                new HashSet<>(), getMap("CS1231S", RoleType.PROFESSOR),
                getEmptyDescription()),
            new Person(new Name("Mao Xiongkai"), getPhone("91031282"), getEmail("mxk@u.nus.edu"),
                Optional.empty(),
                getTagSet("friends"), getMap("CS2030S", RoleType.TUTOR),
                getDescription("Respond very quickly and does pretty solid jobs")),
            new Person(new Name("Zhu Tianyi"), getPhone("92492021"), getEmail("zty@u.nus.edu"),
                Optional.empty(),
                getTagSet("friends"), getMap("CS1231S", RoleType.TUTOR),
                getDescription("A very good TA who takes responsibility in teaching,"
                    + " anything not sure can go ask him.")),
            new Person(new Name("Huang Jiaxi"), getPhone("92624417"), getEmail("hjx@u.nus.edu"),
                Optional.empty(),
                getTagSet("classmates"), getMultipleRolesMap(),
                getDescription("Teaching many modules within the same semester, a very solid TA."))
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
     * Returns an {@code Optional<Address>} containing the address given.
     */
    public static Optional<Address> getAddress(String string) {
        return Optional.of(new Address(string));
    }

    /**
     * Returns an {@code Optional<Phone>} containing the phone given.
     */
    public static Optional<Phone> getPhone(String string) {
        return Optional.of(new Phone(string));
    }

    /**
     * Returns an {@code Optional<Email>} containing the address given.
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
        hashMap.put(new ModuleCode("CS2103T"), RoleType.TUTOR);
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

    /**
     * Returns an {@code Optional<Description>} containing the description given.
     */
    public static Optional<Description> getDescription(String description) {
        return Optional.of(new Description(description));
    }

    /**
     * Returns an empty description.
     */
    public static Optional<Description> getEmptyDescription() {
        return Optional.empty();
    }
}
