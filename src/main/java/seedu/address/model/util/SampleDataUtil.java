package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

/**
 * Contains utility methods for populating {@code CampusConnect} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getTagSet("colleagues"))
        };
    }

    // Schedule for deletion
    /*
    public static ReadOnlyCampusConnect getSampleCampusConnect() {
        CampusConnect sampleAb = new CampusConnect();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }*/

    /**
     * Returns a tag set containing the list of name strings given.
     * @param names Varargs array containing the names of the tags.
     * @return Set containing the tags.
     */
    public static Set<Tag> getTagSet(String... names) {
        return Arrays.stream(names)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing tags with the names and categories specified.
     * @param names Array of names for the tags.
     * @param categories Array of categories to be attached to the respective names.
     * @return Set containing the tags.
     * @throws IllegalValueException thrown when a tag category is not valid.
     */
    public static Set<Tag> getTagSet(String[] names, String[] categories) throws IllegalValueException {
        List<Tag> resultTags = new ArrayList<>();
        TagCategory tagCat;

        // assume both arrays same length, since this is a helper for test functions
        // so no need to test for inconsistent length
        for (int i = 0; i < names.length; i++) {
            tagCat = TagCategory.fromString(categories[i]);
            resultTags.add(new Tag(names[i], tagCat));
        }
        return resultTags.stream().collect(Collectors.toSet());
    }
}
