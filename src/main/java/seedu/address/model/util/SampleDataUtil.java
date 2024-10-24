package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.Address;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RegisterNumber;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StudentClass;
import seedu.address.model.submission.Submission;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final EcName EMPTY_ECNAME = new EcName("");
    public static final EcNumber EMPTY_ECNUMBER = new EcNumber("");
    public static final HashMap<AbsentDate, AbsentReason> EMPTY_ATTENDANCE = new HashMap<>();
    public static final Set<Exam> EMPTY_EXAMS = Collections.emptySet();
    public static final Set<Submission> EMPTY_SUBMISSIONS = Collections.emptySet();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new RegisterNumber("1"), new Sex("M"),
                new StudentClass("1A"), EMPTY_ECNAME, EMPTY_ECNUMBER, EMPTY_EXAMS, getTagSet("friends"),
                    EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new RegisterNumber("2"), new Sex("F"),
                new StudentClass("2A"), new EcName("John Do"), EMPTY_ECNUMBER, EMPTY_EXAMS,
                    getTagSet("colleagues", "friends"), EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new RegisterNumber("3"), new Sex("F"),
                new StudentClass("1A"), new EcName("Rose Jackson"), EMPTY_ECNUMBER, EMPTY_EXAMS,
                    getTagSet("neighbours"), EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new RegisterNumber("4"), new Sex("M"),
                new StudentClass("4C"), new EcName("Jack Fincher"), EMPTY_ECNUMBER, EMPTY_EXAMS,
                    getTagSet("family"), EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new RegisterNumber("5"), new Sex("M"),
                new StudentClass("2B"), new EcName("Peter Ibrahim"), EMPTY_ECNUMBER, EMPTY_EXAMS,
                    getTagSet("classmates"), EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new RegisterNumber("6"), new Sex("M"),
                new StudentClass("2B"), new EcName("Marjorie Roy"), EMPTY_ECNUMBER, EMPTY_EXAMS,
                    getTagSet("colleagues"), EMPTY_ATTENDANCE, EMPTY_SUBMISSIONS)
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
     * Returns a submission set containing the list of strings given.
     */
    public static Set<Submission> getSubmissionSet(String... strings) {
        return Arrays.stream(strings)
                .map(Submission::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an exam set containing the list of strings given.
     */
    public static Set<Exam> getExamSet(String... strings) {
        return Arrays.stream(strings)
                .map(Exam::new)
                .collect(Collectors.toSet());
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
