package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"),
                       new Phone("87438807"),
                       new Email("alexyeoh@example.com"),
                       new Nric("S1234567A"),
                       new Address("Blk 30 Geylang Street 29,#06-40"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Peanuts"),
                       new Priority(),
                       getAppointmentSet("Dental:2024-12-25:1235-1300"),
                       getMedConSet("tooth decay")
            ),
            new Person(new Name("Bernice Yu"),
                       new Phone("99272758"),
                       new Email("berniceyu@example.com"),
                       new Nric("S1234567B"),
                       new Address("Blk 30 Lorong 3 Serangoon Gardens,#07-18"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Peanuts", "Pollen"),
                       new Priority(),
                       getAppointmentSet("Orthopedic:2024-12-01:1200-1300", "Physio:2024-12-01:1500-1600"),
                    getMedConSet("arthritis", "scoliosis")
            ),
            new Person(new Name("Charlotte Oliveiro"),
                       new Phone("93210283"),
                       new Email("charlotte@example.com"),
                       new Nric("S1234567C"),
                       new Address("Blk 11 Ang Mo Kio Street 74,#11-04"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Peanuts"),
                       new Priority(),
                       Collections.emptySet(),
                       getMedConSet("skill issue")
            ),
            new Person(new Name("David Li"),
                       new Phone("91031282"),
                       new Email("lidavid@example.com"),
                       new Nric("S1234567D"),
                       new Address("Blk 436 Serangoon Gardens Street 26,#16-43"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Peanuts"),
                       new Priority(),
                       getAppointmentSet("OT:2025-01-12:1000-1300",
                                         "PT:2025-02-02:1200-1300",
                                         "Consult:2025-02-20:1400-1430"),
                       getMedConSet("presley syndrome", "lung cancer", "arthritis")
            ),
            new Person(new Name("Irfan Ibrahim"),
                       new Phone("92492021"),
                       new Email("irfan@example.com"),
                       new Nric("S1234567E"),
                       new Address("Blk 47 Tampines Street 20,#17-35"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Pollen"),
                       new Priority(),
                       getAppointmentSet("Dental:2024-10-25:1235-1300"),
                       getMedConSet("cavity")
            ),
            new Person(new Name("Roy Balakrishnan"),
                       new Phone("92624417"),
                       new Email("royb@example.com"),
                       new Nric("S1234567F"),
                       new Address("Blk 47 Tampines Street 20,#17-35"),
                       new DateOfBirth("1990-01-01"),
                       new Gender("M"),
                       getAllergiesSet("Pollen"),
                       new Priority(),
                       Collections.emptySet(),
                       Collections.emptySet()
            )
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
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergiesSet(String... strings) {
        Set<Allergy> collect = Arrays.stream(strings).map(Allergy::new).collect(Collectors.toSet());
        return collect;
    }

    /**
     * Returns a MedCon set containing the list of strings given.
     */
    public static Set<MedCon> getMedConSet(String... strings) {
        Set<MedCon> collect = Arrays.stream(strings).map(MedCon::new).collect(Collectors.toSet());
        return collect;
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Appointment> getAppointmentSet(String... strings) {
        return Arrays.stream(strings)
                     .map(s -> {
                         String[] slice = s.split(":");
                         try {
                             return new Appointment(slice[0], slice[1], slice[2]);
                         } catch (IllegalValueException e) {
                             return null;
                         }
                     })
                     .collect(Collectors.toSet());
    }

}
