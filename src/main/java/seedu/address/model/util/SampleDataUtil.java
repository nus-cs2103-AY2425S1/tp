package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new ContactType("WORK"), new Name("Alex Yeoh"), Optional.of(new Phone("87438807")),
                    Optional.of(new Email("alexyeoh@example.com")), Optional.of(new TelegramHandle("@Alexyeoh")),
                    Optional.of(new ModuleName("CS1101S")), Optional.of(new Remark("likes cats")),
                    getTagSet("friends")),
            new Person(new ContactType("WORK"), new Name("Bernice Yu"), Optional.of(new Phone("99272758")),
                    Optional.of(new Email("berniceyu@example.com")), Optional.of(new TelegramHandle("@Berner")),
                    Optional.of(new ModuleName("CS1231S")), Optional.of(new Remark("Head TA")),
                    getTagSet("colleagues", "friends")),
            new Person(new ContactType("WORK"), new Name("Charlotte Oliveiro"), Optional.of(new Phone("93210283")),
                    Optional.of(new Email("charlotte@example.com")), Optional.of(new TelegramHandle("@Charlotte")),
                    Optional.of(new ModuleName("MA1522")), Optional.of(new Remark("doesn't like math")),
                    getTagSet("neighbours")),
            new Person(new ContactType("WORK"), new Name("David Li"), Optional.of(new Phone("91031282")),
                    Optional.of(new Email("lidavid@example.com")), Optional.of(new TelegramHandle("@David2")),
                    Optional.of(new ModuleName("CS2030")), Optional.of(new Remark("lives and breathes cs2030")),
                    getTagSet("family")),
            new Person(new ContactType("WORK"), new Name("Irfan Ibrahim"), Optional.of(new Phone("92492021")),
                    Optional.of(new Email("irfan@example.com")), Optional.of(new TelegramHandle("@Irfan2")),
                    Optional.of(new ModuleName("CS2040")), Optional.of(new Remark("algorithm god")),
                    getTagSet("classmates")),
            new Person(new ContactType("WORK"), new Name("Roy Balakrishnan"), Optional.of(new Phone("92624417")),
                    Optional.of(new Email("royb@example.com")), Optional.of(new TelegramHandle("@RoyBala")),
                    Optional.of(new ModuleName("ES2660")), Optional.of(new Remark("professional yapper")),
                    getTagSet("colleagues"))
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

}
