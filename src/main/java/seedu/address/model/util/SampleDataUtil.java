package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.person.UpdatedAt;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alice Tan"), new Phone("81234567"), new Email("alice.tan@example.com"),
                    new Address("Blk 123 Bedok North Street 1, #02-45"), Priority.HIGH,
                    new Remark("Single mother with three children, needs financial aid and childcare support"),
                    new DateOfBirth(LocalDate.of(1985, 5, 15)), new Income(1500), new FamilySize(4),
                    getTagSet("financial", "childcare"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Benny Lim"), new Phone("81234568"), new Email("benny.lim@example.com"),
                    new Address("Blk 234 Clementi Avenue 3, #05-67"), Priority.MEDIUM,
                    new Remark("Elderly couple, needs medical assistance and home care"),
                    new DateOfBirth(LocalDate.of(1950, 8, 20)), new Income(800), new FamilySize(2),
                    getTagSet("medical", "homeCare"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Catherine Lee"), new Phone("81234569"), new Email("catherine.lee@example.com"),
                    new Address("Blk 345 Toa Payoh Lorong 6, #12-34"), Priority.LOW,
                    new Remark("Unemployed and living alone, requires job placement support"),
                    new DateOfBirth(LocalDate.of(1990, 2, 14)), new Income(0), new FamilySize(1),
                    getTagSet("job", "financial"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("David Wong"), new Phone("81234570"), new Email("david.wong@example.com"),
                    new Address("Blk 456 Jurong West Street 52, #08-56"), Priority.HIGH,
                    new Remark("Family with a special needs child, needs educational support and therapy services"),
                    new DateOfBirth(LocalDate.of(1980, 10, 10)), new Income(3000), new FamilySize(4),
                    getTagSet("educational", "therapy"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Evelyn Chua"), new Phone("81234571"), new Email("evelyn.chua@example.com"),
                    new Address("Blk 567 Ang Mo Kio Avenue 10, #10-78"), Priority.MEDIUM,
                    new Remark("Victim of domestic violence, requires legal aid and housing support"),
                    new DateOfBirth(LocalDate.of(1992, 3, 30)), new Income(1200), new FamilySize(2),
                    getTagSet("legal", "housing"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Felix Ng"), new Phone("81234572"), new Email("felix.ng@example.com"),
                    new Address("Blk 678 Punggol Drive, #04-23"), Priority.LOW,
                    new Remark("Recent immigrant, needs language classes and job placement support"),
                    new DateOfBirth(LocalDate.of(1988, 7, 5)), new Income(1800), new FamilySize(3),
                    getTagSet("language", "job"), new UpdatedAt(UPDATED_AT))
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
