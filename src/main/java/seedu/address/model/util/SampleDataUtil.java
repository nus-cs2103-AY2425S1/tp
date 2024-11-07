package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleHandler;
import seedu.address.model.role.exceptions.InvalidRoleException;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Agency for Science Technology and Really Cool Stuff A STAR"), new Phone("62112211"),
                    new Email("sponsorships@astar.com"), new Address("1 Fusionopolis Way, #20-10"),
                    new TelegramUsername("astar_support"),
                    getRoleSet("sponsor")),

            new Person(new Name("Boogle Singapore"), new Phone("64338807"),
                    new Email("sponsorships@boogle.com"), new Address("70 Pasir Panjang Road, #05-10"),
                    new TelegramUsername("boogle_sponsor"),
                    getRoleSet("sponsor")),

            new Person(new Name("Code a Cola Singapore"), new Phone("67771122"),
                    new Email("support@codeacola.sg"), new Address("15 Cantonment Road, #03-20"),
                    new TelegramUsername("codeacola"),
                    getRoleSet("sponsor", "vendor")),

            new Person(new Name("Deliveroo Delight"), new Phone("81112233"),
                    new Email("contact@deliveroodelight.com"), new Address("2 Anson Road, #04-02"),
                    new TelegramUsername("delightdeliv"),
                    getRoleSet("sponsor", "vendor")),

            new Person(new Name("Everest Bank"), new Phone("61234567"),
                    new Email("sponsors@everestbank.com"), new Address("8 Marina Boulevard, #20-10"),
                    new TelegramUsername("everest_support"),
                    getRoleSet("sponsor")),

            new Person(new Name("FizzCo Singapore"), new Phone("67334455"),
                    new Email("events@fizzco.com"), new Address("3 Changi Business Park Crescent, #04-12"),
                    new TelegramUsername("fizzco_sponsor"),
                    getRoleSet("sponsor", "vendor")),

            new Person(new Name("Glab"), new Phone("62334488"),
                    new Email("sponsorships@glab.com"), new Address("28 Ghim Moh Link, #10-01"),
                    new TelegramUsername("glab_support"),
                    getRoleSet("sponsor")),

            new Person(new Name("Haiwei"), new Phone("61119955"),
                    new Email("events@haiwei.com"), new Address("1 Kallang Avenue, #09-02"),
                    new TelegramUsername("haiwei_support"),
                    getRoleSet("sponsor")),

            new Person(new Name("Ikan Bilis Tech"), new Phone("67776655"),
                    new Email("partnerships@ikantech.com"), new Address("20 Jurong West Street 62, #08-15"),
                    new TelegramUsername("ikanbilistech"),
                    getRoleSet("sponsor", "vendor")),

            new Person(new Name("John Street"), new Phone("63334422"),
                    new Email("sponsorships@johnstreet.com"), new Address("5 Robinson Road, #12-09"),
                    new TelegramUsername("johnstreet"),
                    getRoleSet("sponsor")),

            new Person(new Name("KokoKrunch"), new Phone("66221144"),
                    new Email("events@kokokrunch.com"), new Address("10 Geylang Road, #06-10"),
                    new TelegramUsername("kokokrunch"),
                    getRoleSet("sponsor", "vendor")),

            new Person(new Name("Lee Harry"), new Phone("95651965"),
                    new Email("leeharry@example.com"), new Address("9 Chun Tin Road, #06-10"),
                    new TelegramUsername("leeharry"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Microhard"), new Phone("68887777"),
                    new Email("events@microhard.com"), new Address("12 Science Park Drive, #04-08"),
                    new TelegramUsername("microhard"),
                    getRoleSet("sponsor")),

            new Person(new Name("Nina Lim"), new Phone("98887127"),
                    new Email("ninalim@example.com"), new Address("46 Beach Road, #04-08"),
                    new TelegramUsername("ninalim"),
                    getRoleSet("volunteer")),

            new Person(new Name("Olivia Ong"), new Phone("81123456"),
                    new Email("olivia@example.com"), new Address("14 Alexandra Road, #04-09"),
                    new TelegramUsername("olivia_startup"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Peter Chua"), new Phone("88332211"),
                    new Email("peter@example.com"), new Address("22 Tanjong Pagar Road, #06-03"),
                    new TelegramUsername("peter_tech"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Quincy Tan"), new Phone("94448899"),
                    new Email("quincy@example.com"), new Address("13 Bukit Timah Road, #10-11"),
                    new TelegramUsername("quincy_network"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Rina Lim"), new Phone("91223344"),
                    new Email("rina@example.com"), new Address("8 Farrer Park Road, #03-02"),
                    new TelegramUsername("rina_biz"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Samuel Tan"), new Phone("97335566"),
                    new Email("samuel@example.com"), new Address("5 Geylang East Avenue, #02-04"),
                    new TelegramUsername("samueltan"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("TickTock"), new Phone("62224466"),
                    new Email("partners@ticktock.com"), new Address("1 Harbourfront Ave, #06-08"),
                    new TelegramUsername("ticktock"),
                    getRoleSet("sponsor")),

            new Person(new Name("Ursula Lim"), new Phone("97776655"),
                    new Email("ursula@example.com"), new Address("9 Changi Business Park, #04-05"),
                    new TelegramUsername("ursasa"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Victor Lee"), new Phone("94334477"),
                    new Email("victor@example.com"), new Address("6 Bayfront Avenue, #05-02"),
                    new TelegramUsername("victor_l"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Wendy Tan"), new Phone("84987799"),
                    new Email("wendy@example.com"), new Address("3 Marina Boulevard, #02-04"),
                    new TelegramUsername("wendyyy"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Xander Tan"), new Phone("87338855"),
                    new Email("xander@example.com"), new Address("18 Orchard Road, #10-03"),
                    new TelegramUsername("xander_data"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Yvonne Lim"), new Phone("93312299"),
                    new Email("yvonne@example.com"), new Address("10 Raffles Quay, #06-09"),
                    new TelegramUsername("yvonnee"),
                    getRoleSet("attendee", "volunteer")),

            new Person(new Name("Zachary Tan"), new Phone("89921122"),
                    new Email("zachary@example.com"), new Address("17 Sentosa Gateway, #02-07"),
                    new TelegramUsername("zachtan"),
                    getRoleSet("attendee", "volunteer"))
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
     * Returns a sample list of {@code Event} objects.
     */
    public static Event[] getSampleEvents() {
        Person[] samplePersons = getSamplePersons(); // Reuse sample persons for the event participants

        return new Event[] {
            new Event("NUS Student Life Fair 2024",
                    getPersonSet(samplePersons[20]),
                    getPersonSet(samplePersons[0]),
                    getPersonSet(samplePersons[5]),
                    getPersonSet(samplePersons[22])
            ),
            new Event("NUS Hackathon 2024",
                    getPersonSet(samplePersons[11], samplePersons[14]),
                    getPersonSet(samplePersons[2]),
                    getPersonSet(samplePersons[9], samplePersons[19]),
                    getPersonSet(samplePersons[11])
            )
        };
    }

    /**
     * Helper method that returns a set of persons.
     */
    public static Set<Person> getPersonSet(Person... persons) {
        Set<Person> personSet = new HashSet<>();
        for (Person person : persons) {
            personSet.add(person);
        }
        return personSet;
    }

    /**
     * Returns a sample {@code EventManager} populated with sample events.
     */
    public static ReadOnlyEventManager getSampleEventManager() {
        EventManager sampleEventManager = new EventManager();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEventManager.addEvent(sampleEvent);
        }
        return sampleEventManager;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        RoleHandler rh = new RoleHandler();
        return Arrays.stream(strings)
                .map(string -> tryGetRole(rh, string)) // Delegate exception handling to tryGetRole
                .flatMap(Optional::stream) // Flatten the Optional into a stream, skipping empty ones
                .collect(Collectors.toSet());
    }

    /**
     * Helper method that attempts to get a role and returns an Optional.
     */
    private static Optional<Role> tryGetRole(RoleHandler rh, String string) {
        try {
            return Optional.of(rh.getRole(string));
        } catch (InvalidRoleException e) {
            return Optional.empty();
        }
    }

}
