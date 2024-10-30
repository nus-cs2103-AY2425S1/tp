package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Speciality;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.shared.Date;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    // Create sample doctors and patients for the address book
    private static Doctor doctor1 = new Doctor(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
            new Speciality("General"), getTagSet("Permanent"));

    private static Doctor doctor2 = new Doctor(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            new Speciality("General"), getTagSet("Locum"));

    private static Doctor doctor3 = new Doctor(new Name("Charlotte Oliveiro"), new Phone("93210283"),
            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            new Speciality("General"), getTagSet("Locum"));

    private static Patient patient1 = new Patient(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            new DateOfBirth("12-12-1998"), new Gender("M"), getTagSet("Frequent"));

    private static Patient patient2 = new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
            new DateOfBirth("10-04-1982"), new Gender("M"), getTagSet("CHASHolder"));

    private static Patient patient3 = new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
            new DateOfBirth("01-03-1990"), new Gender("M"), getTagSet("Frequent"));

    /**
     * Returns an array of sample persons. (patients and doctors)
     */
    public static Person[] getSamplePersons() {
        return new Person[] {
            doctor1, doctor2, doctor3, patient1, patient2, patient3
        };
    }

    /**
     * Returns an array of sample appointments, using sample doctors and patients.
     */
    public static Appointment[] getSampleAppointments() {
        Appointment appointment1 = new Appointment(doctor1, patient1,
                new Date("28-10-2024"), new Time("1000"));

        Appointment appointment2 = new Appointment(doctor1, patient2,
                new Date("28-10-2024"), new Time("1200"));

        Appointment appointment3 = new Appointment(doctor2, patient3,
                new Date("28-11-2024"), new Time("0900"));

        return new Appointment[] {appointment1, appointment2, appointment3};
    }

    /**
     * Returns a sample address book containing sample persons and sample appointments.
     */
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Appointment sampleAppointment : getSampleAppointments()) {
            sampleAb.addAppointment(sampleAppointment);
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
