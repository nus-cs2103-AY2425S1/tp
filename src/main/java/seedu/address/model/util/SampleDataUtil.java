package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Role;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        // TODO: change sample persons to match our CCA context
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Telegram("alexYeoh"),
                getRoleSet("Member"),
                new HashSet<>(), FavouriteStatus.NOT_FAVOURITE),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Telegram("berniceYu"),
                getRoleSet("Member", "Vice-Captain"),
                new HashSet<>(), FavouriteStatus.NOT_FAVOURITE),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Telegram("charlotte"),
                getRoleSet("Member"),
                new HashSet<>(), FavouriteStatus.NOT_FAVOURITE),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Telegram("davidLi"),
                getRoleSet("Jersey Vendor"),
                new HashSet<>(), FavouriteStatus.NOT_FAVOURITE),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Telegram("irfanIbrahim"),
                getRoleSet("Member", "Treasurer"),
                new HashSet<>(), FavouriteStatus.NOT_FAVOURITE),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Telegram("royBala"),
                getRoleSet("Member", "Welfare-IC"),
                new HashSet<>(), FavouriteStatus.FAVOURITE)
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
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

}
