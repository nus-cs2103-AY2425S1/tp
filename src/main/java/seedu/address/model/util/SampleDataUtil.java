package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.preferredtime.PreferredTime;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        Game sampleGame = new Game("LoL",
                new Username("LeagueGamer"), new SkillLevel("bad"), new Role("Support"), false);
        HashMap<String, Game> sample = new HashMap<String, Game>();
        sample.put("LoL", sampleGame);
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), sample,
                getPreferredTimeSet("2100-2200")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), getGameMap("LoL"),
                getPreferredTimeSet("2030-2130")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), getGameMap("LoL"),
                getPreferredTimeSet("1900-2100")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), getGameMap("LoL"),
                getPreferredTimeSet("2100-2130", "1900-2030")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), getGameMap("LoL"),
                getPreferredTimeSet("1800-2000")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), getGameMap("LoL"),
                getPreferredTimeSet("2100-2130"))
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
     * Returns a game set containing the list of strings given.
     */
    public static Map<String, Game> getGameMap(String... strings) {
        Map<String, Game> gameMap = new HashMap<>();
        Arrays.stream(strings)
                .forEach((gameName) -> gameMap.put(gameName, new Game(gameName, new Username("nil"),
                        new SkillLevel("nil"), new Role("nil"), false)));
        return gameMap;
    }

    /**
     * Returns a preferredTime set containing the list of strings given.
     */
    public static Set<PreferredTime> getPreferredTimeSet(String... strings) {
        return Arrays.stream(strings)
                .map(PreferredTime::new)
                .collect(Collectors.toSet());
    }

}
