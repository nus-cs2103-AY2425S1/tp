package seedu.address.model.util;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.model.util.SampleDataUtil.getTaskSet;
import static seedu.address.model.util.SampleDataUtil.getWeddingSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class SampleDataUtilTest {
    @Test
    public void checkSampleDataCorrect() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBook();

        // Check people

        Person[] expectedPersonList = new Person[] {
            new Vendor(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("hotel manager"),
                    getWeddingSet("Casey's Wedding"),
                    getTaskSet("todo: Finalize Catering Menu")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("florist", "guest"),
                    getWeddingSet(),
                    getTaskSet()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"),
                    getWeddingSet("Wedding August 2029", "Wedding 2"),
                    getTaskSet()),
            new Vendor(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("makeup artist"),
                    getWeddingSet("Wedding August 2025", "Tom's Wedding"),
                    getTaskSet("todo: Send invitations")),
            new Vendor(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("photographer", "guest"),
                    getWeddingSet("Casey's Wedding"),
                    getTaskSet("deadline: Schedule Hair and Makeup Trials")),
            new Vendor(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("hairstylist"),
                    getWeddingSet("Tom's Wedding"),
                    getTaskSet())
        };

        ObservableList<Person> persons = sampleAddressBook.getPersonList();
        assertTrue(persons.containsAll(of(expectedPersonList)));

        // Check weddings

        List<Wedding> expectedWeddings = List.of(
                new Wedding(new WeddingName("Tom's Wedding"), null, null,
                        new ArrayList<>(Arrays.asList(expectedPersonList[5], expectedPersonList[3])),
                        null, ""),
                new Wedding(new WeddingName("Casey's Wedding"), null, null,
                        new ArrayList<>(Arrays.asList(expectedPersonList[0], expectedPersonList[4])),
                        null, ""),
                new Wedding(new WeddingName("Wedding August 2029"), null, null,
                        new ArrayList<>(Collections.singletonList(expectedPersonList[2])),
                        null, ""),
                new Wedding(new WeddingName("Wedding 2"), null, null,
                        new ArrayList<>(Collections.singletonList(expectedPersonList[2])),
                        null, ""),
                new Wedding(new WeddingName("Wedding August 2025"), null, null,
                        new ArrayList<>(Collections.singletonList(expectedPersonList[3])),
                        null, "")
        );

        ObservableList<Wedding> weddings = sampleAddressBook.getWeddingList();
        assertTrue(weddings.containsAll(expectedWeddings));

        // Check tags

        List<Tag> expectedTags = List.of(
                new Tag(new TagName("hotel manager"), 1),
                new Tag(new TagName("florist"), 2),
                new Tag(new TagName("guest"), 2),
                new Tag(new TagName("neighbours"), 1),
                new Tag(new TagName("makeup artist"), 1),
                new Tag(new TagName("photographer"), 1),
                new Tag(new TagName("hairstylist"), 1)
        );

        ObservableList<Tag> tags = sampleAddressBook.getTagList();
        assertTrue(tags.containsAll(expectedTags));

        // Check tasks

        List<Task> expectedTasks = List.of(
                new Task("Finalize Catering Menu"),
                new Task("Send invitations"),
                new Deadline("Schedule Hair and Makeup Trials", "2022-12-22")
        );

        ObservableList<Task> tasks = sampleAddressBook.getTaskList();
        assertTrue(tasks.containsAll(expectedTasks));
    }
}
