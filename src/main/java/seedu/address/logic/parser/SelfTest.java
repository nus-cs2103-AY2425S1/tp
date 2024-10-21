package seedu.address.logic.parser;

import static seedu.address.model.person.RoleType.STUDENT;
import static seedu.address.model.util.SampleDataUtil.getTagSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoleType;

public class SelfTest {
    public static final ModuleCode DEFAULT_MODULE_CODE = new ModuleCode("CS1101S");
    public static final RoleType DEFAULT_ROLE_TYPE = RoleType.STUDENT;

    public static void main(String[] args) {

        HashMap<ModuleCode, RoleType> hashMap = new HashMap<>();

        // Put an entry into the HashMap
        hashMap.put(DEFAULT_MODULE_CODE, DEFAULT_ROLE_TYPE);

        Person[] persons = new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                        getTagSet("friends"), new ModuleRoleMap(hashMap)),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                        getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                        getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                        getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                        getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                        getTagSet("colleagues"))
        };

        System.out.println(Arrays.stream(persons).filter(person ->
                person.getModuleRoleMap().getRoles().containsKey(new ModuleCode("CS1101S"))
        ).toList());
    }
}
