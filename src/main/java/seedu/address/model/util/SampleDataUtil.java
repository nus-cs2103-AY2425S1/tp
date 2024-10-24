package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
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

    // Example histories for demonstration purposes
    public static final History HISTORY_JOHN_DOE = History.addActivity(
            new History(LocalDate.of(2024, 1, 5)),
            LocalDate.of(2024, 2, 15), "Meeting about 3-bedroom condo");

    public static final History HISTORY_JANE_SMITH = History.addActivity(
            History.addActivity(
                    new History(LocalDate.of(2024, 1, 3)),
                    LocalDate.of(2024, 2, 20), "Viewed house at Clementi"),
            LocalDate.of(2024, 3, 10), "Follow-up discussion about financing");

    public static final History HISTORY_BOB_JONES = History.addActivity(
            new History(LocalDate.of(2024, 1, 10)),
            LocalDate.of(2024, 2, 25), "First-time buyer inquiry");

    public static final History HISTORY_ANNA_LIM = History.addActivity(
            new History(LocalDate.of(2024, 1, 12)),
            LocalDate.of(2024, 3, 5), "Discussion about selling property");

    public static final History HISTORY_TOM_WONG = History.addActivity(
            History.addActivity(
                    new History(LocalDate.of(2024, 1, 15)),
                    LocalDate.of(2024, 2, 28), "Interested in rental properties"),
            LocalDate.of(2024, 3, 20), "Meeting about investment options");

    public static final History HISTORY_JACK_TAN = History.addActivity(
            new History(LocalDate.of(2024, 2, 2)),
            LocalDate.of(2024, 3, 12), "Inquired about mortgage rates");

    public static final History HISTORY_SARAH_NG = History.addActivity(
            new History(LocalDate.of(2024, 1, 6)),
            LocalDate.of(2024, 2, 14), "Initial consultation about selling HDB flat");

    public static final History HISTORY_WILLIAM_GO = History.addActivity(
            History.addActivity(
                    new History(LocalDate.of(2024, 1, 22)),
                    LocalDate.of(2024, 2, 5), "Negotiated condo deal"),
            LocalDate.of(2024, 3, 3), "Follow-up on condo purchase");

    public static final History HISTORY_AMY_WEE = History.addActivity(
            new History(LocalDate.of(2024, 1, 15)),
            LocalDate.of(2024, 2, 25), "Discussed long-term investment plans");

    public static final History HISTORY_ALAN_CHEW = History.addActivity(
            new History(LocalDate.of(2024, 2, 1)),
            LocalDate.of(2024, 3, 5), "Viewed resale HDB flat");
    public static final History HISTORY_SIGMA_RIZZLER = History.addActivity(
            new History(LocalDate.of(2024, 2, 1)),
            LocalDate.of(2024, 3, 5), "Rizzling at National University of Skibidi");

    @SuppressWarnings("checkstyle:Indentation")
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("John Doe"), new Phone("87431234"), new Email("john.doe@example.com"),
                        new Address("Blk 123 Clementi Ave 3, #12-34"),
                        new Remark("Looking for a 3-bedroom condo"),
                        new Birthday("1985-05-12"),
                        getTagSet("buyer", "longTerm"),
                        new DateOfCreation(LocalDate.of(2024, 1, 5)),
                        HISTORY_JOHN_DOE),

            new Person(new Name("Jane Smith"), new Phone("91234567"), new Email("jane.smith@example.com"),
                        new Address("Blk 45 Queenstown Road, #08-19"),
                        new Remark("Looking for a family home"),
                        new Birthday("1990-08-15"),
                        getTagSet("potentialBuyer"),
                        new DateOfCreation(LocalDate.of(2024, 1, 3)),
                        HISTORY_JANE_SMITH),

            new Person(new Name("Bob Jones"), new Phone("92345678"), new Email("bob.jones@example.com"),
                        new Address("Blk 78 Toa Payoh Central, #03-45"),
                        new Remark("First-time buyer, looking for affordable options"),
                        new Birthday("1995-07-20"),
                        getTagSet("firstTimeBuyer"),
                        new DateOfCreation(LocalDate.of(2024, 1, 10)),
                        HISTORY_BOB_JONES),

            new Person(new Name("Anna Lim"), new Phone("98765432"), new Email("anna.lim@example.com"),
                        new Address("Blk 123 Bukit Timah Road, #04-12"),
                        new Remark("Selling 5-room flat, wants a quick sale"),
                        new Birthday("1988-11-25"),
                        getTagSet("seller", "family"),
                        new DateOfCreation(LocalDate.of(2024, 1, 12)),
                        HISTORY_ANNA_LIM),

            new Person(new Name("Tom Wong"), new Phone("93456789"), new Email("tom.wong@example.com"),
                        new Address("Blk 88 Tanjong Pagar, #10-15"),
                        new Remark("Interested in rental properties and investments"),
                        new Birthday("1970-02-18"),
                        getTagSet("investor", "longTermClient"),
                        new DateOfCreation(LocalDate.of(2024, 1, 15)),
                        HISTORY_TOM_WONG),

            new Person(new Name("Jack Tan"), new Phone("85434523"), new Email("jack.tan@example.com"),
                        new Address("Blk 12 Boon Lay Drive, #05-67"),
                        new Remark("Interested in property financing"),
                        new Birthday("1982-01-15"),
                        getTagSet("buyer", "financial"),
                        new DateOfCreation(LocalDate.of(2024, 2, 2)),
                        HISTORY_JACK_TAN),

            new Person(new Name("Sarah Ng"), new Phone("91344521"), new Email("sarah.ng@example.com"),
                        new Address("Blk 55 Serangoon Ave 3, #14-78"),
                        new Remark("Planning to sell HDB flat"),
                        new Birthday("1987-03-05"),
                        getTagSet("seller"),
                        new DateOfCreation(LocalDate.of(2024, 1, 6)),
                        HISTORY_SARAH_NG),

            new Person(new Name("William Go"), new Phone("96341234"), new Email("william.go@example.com"),
                        new Address("Blk 34 Bishan St 23, #07-45"),
                        new Remark("Looking to purchase condo"),
                        new Birthday("1975-12-30"),
                        getTagSet("buyer"),
                        new DateOfCreation(LocalDate.of(2024, 1, 22)),
                        HISTORY_WILLIAM_GO),

            new Person(new Name("Amy Wee"), new Phone("97345678"), new Email("amy.wee@example.com"),
                        new Address("Blk 123 Yishun Ring Road, #02-45"),
                        new Remark("Interested in long-term investments"),
                        new Birthday("1980-05-12"),
                        getTagSet("investor"),
                        new DateOfCreation(LocalDate.of(2024, 1, 15)),
                        HISTORY_AMY_WEE),
            new Person(new Name("Alan Chew"), new Phone("82345671"), new Email("alan.chew@example.com"),
                        new Address("Blk 45 Bedok South Ave 2, #12-34"),
                        new Remark("Viewing resale HDB flats"),
                        new Birthday("1978-07-15"),
                        getTagSet("buyer", "resale"),
                        new DateOfCreation(LocalDate.of(2024, 2, 1)),
                        HISTORY_ALAN_CHEW),
            new Person(new Name("Sigma Rizzler"), new Phone("69420"), new Email("skibidi@dop.com"),
                        new Address("Based Estate"), new Remark("Cool dude"), new Birthday("2000-06-09"),
                        getTagSet("favourite"),
                        new DateOfCreation(LocalDate.of(2000, 1, 1)),
                        HISTORY_SIGMA_RIZZLER)
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
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}
