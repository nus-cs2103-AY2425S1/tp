package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
                new Person(new Name("Jeremy Ng"), new Phone("91224124"), new Email("jeremyng@gmail.com"),
                        new Address("53 Tuas South Ave 1, Singapore 637606"), new Job("Caterer(halal)"),
                        getTagSet()),
                new Person(new Name("Heather Teo"), new Phone("82212345"), new Email("h3ather@outlook.com"),
                        new Address("Blk 123, #12-34, Jurong West St 12, Singapore 123456"), new Job("Photographer"),
                        getTagSet("Ahmad & Esther", "Evan & Camillus")),
                new Person(new Name("Zachariah Lee"), new Phone("96541294"), new Email("zaccountant@hotmail.com"),
                        new Address("51 Changi Business Park Central 2, Singapore 486066"), new Job("Emcee"),
                        getTagSet()),
                new Person(new Name("Clarke Woodford"), new Phone("86321256"),
                        new Email("clarkymate@flowerpower.com.sg"),
                        new Address("Blk 456, #01-23, Serangoon North Ave 3, Singapore 456789"), new Job("Florist"),
                        getTagSet("Eric & Reyna")),
                new Person(new Name("Muhammad Amirul Bin Abdul Rahman"), new Phone("92451247"),
                        new Email("amirules@outlook.com"),
                        new Address("Blk 789, #11-22, Tampines St 21, Singapore 789012"), new Job("Lighting"),
                        getTagSet()),
                new Person(new Name("Judith Abigail Ho"), new Phone("97245670"), new Email("judithah@yahoo.com"),
                        new Address("464A Clementi Ave 1, Singapore 121464"), new Job("Makeup Artist"),
                        getTagSet()),
                new Person(new Name("Crisanto Adlawan"), new Phone("87543218"), new Email("cr7@gmail.com"),
                        new Address("151 Chin Swee Road, Singapore 169876"), new Job("Baker"),
                        getTagSet()),
                new Person(new Name("Chris Tan"), new Phone("97654431"), new Email("chirisshoots@gmail.com"),
                        new Address("112 Siglap Road, Singapore 234522"), new Job("Photographer"),
                        getTagSet("Karthigaran & Lavanya")),
                new Person(new Name("Abigail Low"), new Phone("83347765"), new Email("abby@hotmail.com"),
                        new Address("245 Admiralty Road, Singapore 345610"), new Job("Dj"),
                        getTagSet()),
                new Person(new Name("Nikhil Archrya"), new Phone("92311323"), new Email("Nik34@satsfoods.com"),
                        new Address("234 Pandan Loop, Singapore 128422"), new Job("Caterer"),
                        getTagSet()),
                new Person(new Name("Neville Leong"), new Phone("92257643"), new Email("Never3ver@photog.com"),
                        new Address("#02-04 Holland Road Shopping Centre, 211 Holland Avenue, Singapore 278967"),
                        new Job("Videographer"),
                        getTagSet("Terry & Soon Hian"))
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
     * Returns a set of participants containing the list of strings given.
     */
    public static Set<Person> getParticipantSet(Person... person) {
        return Arrays.stream(person)
                .collect(Collectors.toSet());
    }

    public static Wedding[] getSampleWeddings() {
        return new Wedding[] {
                new Wedding(new WeddingName("Karthigaran & Lavanya"),
                        new Venue("Hotel Fort Canning 11 Canning Walk, Singapore 178881"),
                        new Date("12/04/2025"), getParticipantSet(
                        new Person(new Name("Chris Tan"), new Phone("97654431"), new Email("chirisshoots@gmail.com"),
                                new Address("112 Siglap Road, Singapore 234522"), new Job("Photographer"),
                                getTagSet("Karthigaran & Lavanya"))
                )),
                new Wedding(new WeddingName("Terry & Soon Hian"),
                        new Venue("The Fullerton Hotel 1 Fullerton Square, Singapore 049178"),
                        new Date("12/02/2025"), getParticipantSet(
                        new Person(new Name("Neville Leong"), new Phone("92257643"), new Email("Never3ver@photog.com"),
                                new Address("#02-04 Holland Road Shopping Centre, 211 Holland Avenue, Singapore 278967"),
                                new Job("Videographer"), getTagSet("Terry & Soon Hian"))
                )),
                new Wedding(new WeddingName("Evan & Camillus"),
                        new Venue("Aloft 16 Ah Hood Road, Singapore 329982"),
                        new Date("12/12/2024"), getParticipantSet(
                        new Person(new Name("Heather Teo"), new Phone("82212345"), new Email("h3ather@outlook.com"),
                                new Address("Blk 123, #12-34, Jurong West St 12, Singapore 123456"),
                                new Job("Photographer"),
                                getTagSet("Ahmad & Esther", "Evan & Camillus"))
                )),
                new Wedding(new WeddingName("Connor & Aishah"),
                        new Venue("CHIJMES 30 Victoria St, Singapore 187996"),
                        new Date("07/04/2025"), getParticipantSet()),
                new Wedding(new WeddingName("Ahmad & Esther"),
                        new Venue("Shangri-La Hotel 22 Orange Grove Rd, Singapore 258350"),
                        new Date("13/02/2026"), getParticipantSet(
                        new Person(new Name("Heather Teo"), new Phone("82212345"), new Email("h3ather@outlook.com"),
                                new Address("Blk 123, #12-34, Jurong West St 12, Singapore 123456"),
                                new Job("Photographer"),
                                getTagSet("Ahmad & Esther", "Evan & Camillus"))
                )),
                new Wedding(new WeddingName("Eric & Reyna"),
                        new Venue("CHIJMES 30 Victoria St, Singapore 187996"),
                        new Date("11/10/2025"), getParticipantSet(
                        new Person(new Name("Clarke Woodford"), new Phone("86321256"),
                                new Email("clarkymate@flowerpower.com.sg"),
                                new Address("Blk 456, #01-23, Serangoon North Ave 3, Singapore 456789"),
                                new Job("Florist"),
                                getTagSet("Eric & Reyna"))
                ))
        };
    }

    public static ReadOnlyWeddingBook getSampleWeddingBook() {
        WeddingBook sampleWb = new WeddingBook();
        for (Wedding sampleWedding : getSampleWeddings()) {
            sampleWb.addWedding(sampleWedding);
        }
        return sampleWb;
    }

}
