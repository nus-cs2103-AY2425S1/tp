package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Year;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Comment EMPTY_COMMENT = new Comment("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new StudentId("A8743880E"), Email.makeEmail("e1234567@u.nus.edu"),
                Major.makeMajor("Business Analytics"),
                getTagSet("group 1"), Year.makeYear("1"), EMPTY_COMMENT),
            new Person(new Name("Bernice Yu"), new StudentId("A9272757L"), Email.makeEmail("e9999999@u.nus.edu"),
                Major.makeMajor("Computer Science"),
                getTagSet("group 1"), Year.makeYear("1"), EMPTY_COMMENT),
            new Person(new Name("Charlotte Oliveiro"), new StudentId("A9321028P"),
                    Email.makeEmail("e3456819@u.nus.edu"), Major.makeMajor("Political Science"),
                getTagSet("group 2"), Year.makeYear("1"), EMPTY_COMMENT),
            new Person(new Name("David Li"), new StudentId("A9103128E"), Email.makeEmail("e0000001@u.nus.edu"),
                Major.makeMajor("Business Administration"),
                getTagSet("group 2"), Year.makeYear("1"), EMPTY_COMMENT),
            new Person(new Name("Irfan Ibrahim"), new StudentId("A2492021T"), Email.makeEmail("e3456718@u.nus.edu"),
                Major.makeMajor("Chemistry"),
                getTagSet("group 3"), Year.makeYear("1"), EMPTY_COMMENT),
            new Person(new Name("Roy Balakrishnan"), new StudentId("A9262441K"), Email.makeEmail("e5739264@u.nus.edu"),
                Major.makeMajor("Mechanical Engineering"),
                getTagSet("group 3"), Year.makeYear("1"), EMPTY_COMMENT)
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
    public static Set<Group> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Group::new)
                .collect(Collectors.toSet());
    }

}
