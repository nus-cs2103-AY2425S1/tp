package seedu.address.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.PresentDates;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.tut.TutDate;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new PresentDates(getDateList("20/02/2024"))),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"),
                    new PresentDates(getDateList("20/02/2024", "22/02/2024"))),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new PresentDates(getDateList("20/02/2024"))),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new PresentDates(getDateList("20/02/2024"))),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new PresentDates(getDateList("20/02/2024"))),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new PresentDates(getDateList("20/02/2024")))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
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

    public static ArrayList<TutDate> getDateList(String... strings) {
        return Arrays.stream(strings)
                .map(s -> {
                    try {
                        return parseDate(s);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Parses a date string in the format "dd/MM/yyyy" and converts it to a {@link TutDate} object.
     * If the input string does not match the expected format, a {@link ParseException} is thrown.
     *
     * @param date The date string in the format "dd/MM/yyyy".
     * @return A {@link TutDate} object representing the parsed date.
     * @throws ParseException If the date string cannot be parsed due to an invalid format.
     */
    public static TutDate parseDate(String date) throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return new TutDate(d);
    }
}
