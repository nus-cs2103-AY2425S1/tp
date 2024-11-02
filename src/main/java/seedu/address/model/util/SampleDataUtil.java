package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Guest(new Name("Tony Stark"), new Phone("83238717"), new Email("tony.stark@starkindustries.com"),
                    new Address("10880 Malibu Point, Malibu"),
                    getTagSet("leader", "genius"), new Rsvp("P"), new Relation("H")),
            new Guest(new Name("Steve Rogers"), new Phone("99311258"), new Email("steverogers@avengers.com"),
                    new Address("Brooklyn, New York"),
                    getTagSet("captain", "hero"), new Rsvp("A"), new Relation("W")),
            new Guest(new Name("Natasha Romanoff"), new Phone("93212323"), new Email("natasha.romanoff@shield.com"),
                    new Address("Red Room, Moscow"),
                    getTagSet("spy", "agent"), new Rsvp("D"), new Relation("U")),
            new Vendor(new Name("Bruce Banner"), new Phone("87134653"), new Email("bruce.banner@avengers.com"),
                    new Address("Culver University, Virginia"),
                    getTagSet("scientist", "hulk"), new Company("Banner Labs"), new Budget("100000")),
            new Vendor(new Name("Thor Odinson"), new Phone("90783445"), new Email("thor@asgard.com"),
                    new Address("Asgard, Realm Eternal"),
                    getTagSet("god", "warrior"), new Company("Asgardian Enterprises"), new Budget("500000")),
            new Vendor(new Name("Clint Barton"), new Phone("83129340"), new Email("clint.barton@shield.com"),
                    new Address("Waverly, Iowa"),
                    getTagSet("archer", "agent"), new Company("Barton Security"), new Budget("50000"))
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
