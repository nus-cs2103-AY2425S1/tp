package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Gender("female"),
                    getModuleSet("MA1522"), getTagSet("friend", "mentor", "leader"), EMPTY_REMARK),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Gender("male"),
                    getModuleSet("CS1101"), getTagSet("colleague", "project", "tutorial"), EMPTY_REMARK),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Gender("male"),
                    getModuleSet("LS1301"), getTagSet("neighbor", "debate"), EMPTY_REMARK),
            new Person(new Name("David Li"), new Phone("91031282"), new Gender("female"),
                    getModuleSet("EL1101"), getTagSet("family", "mentor", "honor"), EMPTY_REMARK),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Gender("male"),
                    getModuleSet("EL1101"), getTagSet("classmate", "tutor", "soccer"), EMPTY_REMARK),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Gender("male"),
                    getModuleSet("MA1522"), getTagSet("colleague", "project", "basketball"), EMPTY_REMARK),
            new Person(new Name("Evelyn Tan"), new Phone("81234567"), new Gender("female"),
                    getModuleSet("CS2103"), getTagSet("project", "tutorial", "software"), EMPTY_REMARK),
            new Person(new Name("Farah Ahmed"), new Phone("87987654"), new Gender("female"),
                    getModuleSet("MA1101"), getTagSet("classmate", "mentor", "honor"), EMPTY_REMARK),
            new Person(new Name("Gabriel Lim"), new Phone("89876543"), new Gender("male"),
                    getModuleSet("IS1103"), getTagSet("classmate", "project", "coding"), EMPTY_REMARK),
            new Person(new Name("Hannah Koh"), new Phone("89765432"), new Gender("female"),
                    getModuleSet("CS1231"), getTagSet("debate", "leader"), EMPTY_REMARK)
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
     * Returns a module set containing the list of strings given.
     */
    public static Set<Module> getModuleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Module::new)
                .collect(Collectors.toSet());
    }

}
