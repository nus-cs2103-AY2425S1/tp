package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Nric("T0123456A"), new Birthdate("2001-05-30"),
                    new Sex("M")),
            new Patient(new Name("Bernice Yu"), new Nric("S0123456B"), new Birthdate("1965-08-09"),
                new Sex("F")),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("M1234457A"), new Birthdate("1990-12-20"),
                new Sex("F"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
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

    public static Set<HealthService> getHealthServiceSet(String... strings) {
        return Arrays.stream(strings)
                .map(HealthService::new)
                .collect(Collectors.toSet());
    }

}
