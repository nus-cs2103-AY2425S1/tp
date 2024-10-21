package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Notes EMPTY_NOTES = new Notes("");
    public static Person[] getSamplePersons() {
        return new Person[] {
            /*
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))

             */
            new Person(new Name("Alex Yeoh"), new Id("P12345"), new Ward("A1"),
                    new Diagnosis("A. fib (Atrial Fibrillation)"),
                    new Medication("Metoprolol"), EMPTY_NOTES),
            new Person(new Name("Bernice Yu"), new Id("P23456"), new Ward("B1"),
                    new Diagnosis("Celiac Disease/Gluten Sensitivity"),
                    new Medication("Corticosteroids"),
                    new Notes("patient is on a strict gluten free diet")),
            new Person(new Name("Charlotte Oliveiro"), new Id("P34567"), new Ward("C2"),
                    new Diagnosis("Major Depressive Disorder - Recurrent"),
                    new Medication("Fluoxetine (Prozac)"), EMPTY_NOTES),
            new Person(new Name("David Li"), new Id("P45678"), new Ward("A1"),
                    new Diagnosis("Hypertensive Heart Disease - Stage 2"),
                    new Medication("Angiotensin-Converting Enzyme (ACE) Inhibitors"),
                    new Notes("patient is prone to falling")),
            new Person(new Name("Irfan Ibrahim"), new Id("P56789"), new Ward("D1"),
                    new Diagnosis("Acute Respiratory Distress Syndrome (ARDS) / COVID-19"),
                    new Medication("Oxygen Therapy"), new Notes("patient required checkup every 4 hours")),
            new Person(new Name("Roy Balakrishnan"), new Id("P67890"), new Ward("B1"),
                    new Diagnosis("Streptococcus Pneumoniae"),
                    new Medication("Penicillin (Penicillin G or Penicillin V)"), EMPTY_NOTES)

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
