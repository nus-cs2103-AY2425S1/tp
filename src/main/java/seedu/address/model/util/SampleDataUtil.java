package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
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
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
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
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
            loadTags(sampleAb, samplePerson);
            loadTasks(sampleAb, samplePerson);
            loadWeddings(sampleAb, samplePerson);
        }
        return sampleAb;
    }

    /**
     * Adds tags to model that were in person but not in model, and makes model and
     * person store the same tags
     */
    private static void loadTags(AddressBook addressBook, Person person) {
        Set<Tag> tagList = person.getTags();
        Set<Tag> newTagList = new HashSet<>();
        for (Tag tag : tagList) {
            // Adds the tag to the model
            if (!addressBook.hasTag(tag)) {
                addressBook.addTag(tag);
            }

            newTagList.add(addressBook.getTag(tag));
            addressBook.getTag(tag).increaseTaggedCount();
        }
        person.setTags(newTagList);
    }

    /**
     * Adds weddings to model that were in person but not in model, and makes model and
     * person store the same weddings
     */
    private static void loadWeddings(AddressBook addressBook, Person person) {
        Set<Wedding> weddingList = person.getWeddings();
        Set<Wedding> newWeddingList = new HashSet<>();
        for (Wedding wedding : weddingList) {
            // Adds the wedding to the model
            if (!addressBook.hasWedding(wedding)) {
                addressBook.addWedding(wedding);
            }

            // If the wedding does not contain the person, add them to the guest list of the wedding by default
            if (!addressBook.getWedding(wedding).hasPerson(person)) {
                addressBook.getWedding(wedding).addToGuestList(addressBook.getPerson(person));
            }

            newWeddingList.add(addressBook.getWedding(wedding));
        }
        person.setWeddings(newWeddingList);
    }

    /**
     * Adds tasks to model that were in person but not in model, and makes model and
     * person store the same tasks
     */
    private static void loadTasks(AddressBook addressBook, Person person) {
        Set<Task> taskList = person.getTasks();
        for (Task task : taskList) {
            if (addressBook.hasTask(task)) {
                continue;
            }
            addressBook.addTask(task);
        }
    }


    /**
     * Returns a tag set containing the list of tags given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(TagName::new)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a task set containing the list of tasks given as strings.
     * Strings should follow a format like: "todo: Buy cake", "deadline: Submit report by Monday", "event: Meeting"
     */
    public static Set<Task> getTaskSet(String... strings) {
        return Arrays.stream(strings)
                .map(SampleDataUtil::parseTaskFromString)
                .collect(Collectors.toSet());
    }

    /**
     * Parses a string to return a specific Task instance (e.g., Todo, Deadline, or Event).
     */
    private static Task parseTaskFromString(String taskString) {
        if (taskString.startsWith("todo:")) {
            return new Todo(taskString.substring(5).trim());
        } else if (taskString.startsWith("deadline:")) {
            return new Deadline(taskString.substring(9).trim(), "2022-12-22");
        } else if (taskString.startsWith("event:")) {
            return new Event(taskString.substring(6).trim(), "2022-12-22", "2022-12-23");
        } else {
            throw new IllegalArgumentException("Unknown task type in string: " + taskString);
        }
    }



    /**
     * Returns a wedding set containing the list of weddings given.
     */
    public static Set<Wedding> getWeddingSet(String... strings) {
        return Arrays.stream(strings)
                .map(WeddingName::new)
                .map(Wedding::new)
                .collect(Collectors.toSet());
    }

}
