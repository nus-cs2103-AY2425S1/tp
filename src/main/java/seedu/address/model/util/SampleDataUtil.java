package seedu.address.model.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Tutorial;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new StudentId("E0000001"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                getTagSet("friends"), getTutorialMap()),
            new Person(new Name("Bernice Yu"), new StudentId("E0000002"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends"), getTutorialMap()),
            new Person(new Name("Charlotte Oliveiro"), new StudentId("E0000003"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                getTagSet("neighbours"), getTutorialMap()),
            new Person(new Name("David Li"), new StudentId("E0000004"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                getTagSet("family"), getTutorialMap()),
            new Person(new Name("Irfan Ibrahim"), new StudentId("E0000005"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                getTagSet("classmates"), getTutorialMap()),
            new Person(new Name("Roy Balakrishnan"), new StudentId("E0000006"), new Phone("92624417"),
                    new Email("royb@example.com"),
                getTagSet("colleagues"), getTutorialMap())
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
     * Returns a tutorial set containing the list of strings given and all are defaulted to not taken place.
     */
    public static Map<Tutorial, AttendanceStatus> getTutorialMap() {
        String[] strings = new String[Person.MAXIMUM_TUTORIALS];
        for (int i = 0; i < Person.MAXIMUM_TUTORIALS; i++) {
            strings[i] = String.valueOf(i + 1);
        }
        AttendanceStatus[] attendance = new AttendanceStatus[Person.MAXIMUM_TUTORIALS];
        Arrays.fill(attendance, AttendanceStatus.NOT_TAKEN_PLACE);
        return getTutorialMap(strings, attendance);
    }

    /**
     * Generates a {@code Map<Tutorial, AttendanceStatus} based on the given strings by calling the overloaded method
     * and defaulting attendance to not taken place.
     *
     * @param strings Array of tutorial indexes.
     * @return A map with containing each tutorial mapped to its attendance.
     */
    public static Map<Tutorial, AttendanceStatus> getTutorialMap(String ... strings) {
        AttendanceStatus[] attendance = new AttendanceStatus[strings.length];
        Arrays.fill(attendance, AttendanceStatus.NOT_TAKEN_PLACE);
        return getTutorialMap(strings, attendance);
    }

    /**
     * Generates a {@code Map<Tutorial, AttendanceStatus} based on the given arrays.
     *
     * @param strings Array of tutorial indexes.
     * @param attendance Array of attendance corresponding to each tutorial.
     * @return A map with containing each tutorial mapped to its attendance.
     */
    public static Map<Tutorial, AttendanceStatus> getTutorialMap(String[] strings, AttendanceStatus[] attendance) {
        Map<Tutorial, AttendanceStatus> tutorialMap = new LinkedHashMap<>();
        for (int i = 0; i < strings.length; i++) {
            tutorialMap.put(new Tutorial(strings[i]), attendance[i]);
        }
        return tutorialMap;
    }

}
