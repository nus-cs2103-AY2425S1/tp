package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Age;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"), new Gender("M"),
                    new Age("19"), new Detail("To be assigned"), getStudyGroupTagSet("1A")),

            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"), new Gender("M"),
                    new Age("19"), new Detail(""), getStudyGroupTagSet("Control", "1A")),

            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"), new Gender("F"),
                    new Age("19"), new Detail(""), getStudyGroupTagSet("Treatment", "1B")),

            new Person(new Name("David Li"), new Email("lidavid@example.com"), new Gender("M"), new Age("19"),
                    new Detail("To be assigned"), getStudyGroupTagSet("1B")),

            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"), new Gender("M"),
                    new Age("19"), new Detail("Follow up on 19 Nov"), getStudyGroupTagSet("2A")),

            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"), new Gender("M"),
                    new Age("19"), new Detail("Follow up on 19 Nov"), getStudyGroupTagSet("2B"))
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
     * Returns a study group tag set containing the list of strings given.
     */
    public static Set<StudyGroupTag> getStudyGroupTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(StudyGroupTag::new)
                .collect(Collectors.toSet());
    }

}
