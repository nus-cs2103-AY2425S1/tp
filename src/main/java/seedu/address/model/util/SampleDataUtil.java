package seedu.address.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateOfCreation;
import seedu.address.model.person.Email;
import seedu.address.model.person.History;
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
    public static final Birthday EMPTY_BIRTHDAY = new Birthday("");

    @SuppressWarnings("checkstyle:Indentation")
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("John Doe"), new Phone("81234567"), new Email("john.doe@example.com"),
                    new Address("Blk 101 Clementi Street 12, #02-05"),
                    new Remark("Looking for a 3-room HDB in Clementi"),
                    new Birthday("1985-04-12"),
                    getTagSet("potential buyer", "friends"),
                    new DateOfCreation(LocalDate.of(2024, 1, 7)),
                    createHistory(new String[][]{
                        {"2024-02-02", "Inquired about Clementi flat"}
                    })),

            new Person(new Name("Jane Smith"), new Phone("92345678"), new Email("jane.smith@example.com"),
                    new Address("Blk 20 Queenstown Avenue, #12-34"),
                    new Remark("Looking for a 4-room condo in Queenstown"),
                    new Birthday("1990-07-21"),
                    getTagSet("potential buyer"),
                    new DateOfCreation(LocalDate.of(2024, 1, 8)),
                    createHistory()), // Empty history

            new Person(new Name("Samuel Tan"), new Phone("94561234"), new Email("samuel.tan@example.com"),
                    new Address("Blk 54 Punggol Field, #09-12"),
                    new Remark("First-time buyer, needs advice on financing"),
                    new Birthday("1992-03-15"),
                    getTagSet("first-time buyer", "finance"),
                    new DateOfCreation(LocalDate.of(2024, 1, 9)),
                    createHistory(new String[][]{
                        {"2024-10-17", "Discussed loan options"},
                        {"2024-10-17", "Attended meeting"}
                    })),

            new Person(new Name("Emily Lim"), new Phone("98765432"), new Email("emily.lim@example.com"),
                    new Address("Blk 123 Bukit Timah Road, #03-45"),
                    new Remark("Selling 5-room flat, prefers high floor"),
                    new Birthday("1988-10-22"),
                    getTagSet("seller", "family"),
                    new DateOfCreation(LocalDate.of(2024, 1, 10)),
                    createHistory()), // Empty history

            new Person(new Name("Michael Ong"), new Phone("95678901"), new Email("michael.ong@example.com"),
                    new Address("Blk 88 Tanjong Pagar Street 10, #08-16"),
                    new Remark("Investing in rental properties"),
                    new Birthday("1975-12-30"),
                    getTagSet("investor", "long-term client"),
                    new DateOfCreation(LocalDate.of(2024, 1, 11)),
                    createHistory()), // Empty history

            new Person(new Name("Sarah Lee"), new Phone("96234125"), new Email("sarah.lee@example.com"),
                    new Address("Blk 9 Tiong Bahru Road, #04-01"),
                    new Remark("Relocating, looking for a 2-bedroom condo"),
                    new Birthday("1983-09-02"),
                    getTagSet("expat", "condo seeker"),
                    new DateOfCreation(LocalDate.of(2024, 1, 12)),
                    createHistory(new String[][]{
                        {"2024-10-17", "Event 1"},
                        {"2024-10-17", "Event 2"}
                    }))
        };
    }

    /**
     * Creates a new {@code History} object with the provided entries.
     * Each entry is represented by a date and an array of activities for that date.
     *
     * @param entries A 2D string array where each element is a date followed by a list of activities.
     * @return A new {@code History} object with the provided entries.
     */
    public static History createHistory(String[][] entries) {
        TreeMap<LocalDate, ArrayList<String>> historyMap = new TreeMap<>();

        for (String[] entry : entries) {
            LocalDate date = LocalDate.parse(entry[0]);
            ArrayList<String> activities = new ArrayList<>(Arrays.asList(entry).subList(1, entry.length));
            historyMap.put(date, activities);
        }

        return new History(historyMap, LocalDate.now()); // Use current date as the creation date
    }

    /**
     * Creates an empty {@code History} object.
     *
     * @return A new empty {@code History} object.
     */
    public static History createHistory() {
        return new History(new TreeMap<>(), LocalDate.now());
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
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
