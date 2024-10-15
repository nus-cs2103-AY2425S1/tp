package seedu.address.model.util;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentType;
import seedu.address.model.appointment.Medicine;
import seedu.address.model.appointment.Sickness;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final Logger logger = LogsCenter.getLogger(Model.class);

    public static PersonDescriptor[] getSamplePersons() {
        return new PersonDescriptor[]{
            new PersonDescriptor(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new PersonDescriptor(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new PersonDescriptor(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new PersonDescriptor(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new PersonDescriptor(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new PersonDescriptor(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Appointment[] getSampleAppointments() {
        return new Appointment[]{
            new Appointment(new AppointmentType("Consultation"), LocalDateTime.of(2024,
                    10, 20, 9, 30), 1, new Sickness("Flu"),
                    new Medicine("Antiviral")),
            new Appointment(new AppointmentType("Follow-up"), LocalDateTime.of(2024,
                    10, 22, 14, 0), 2, new Sickness("Diabetes"),
                    new Medicine("Insulin")),
            new Appointment(new AppointmentType("Emergency"), LocalDateTime.of(2024,
                    10, 23, 18, 45), 3, new Sickness("Asthma"),
                    new Medicine("Inhaler")),
            new Appointment(new AppointmentType("Check-up"), LocalDateTime.of(2024,
                    10, 25, 11, 15), 4, new Sickness("Hypertension"),
                    new Medicine("Beta-blocker")),
            new Appointment(new AppointmentType("Consultation"), LocalDateTime.of(2024,
                    10, 28, 16, 30), 5, new Sickness("Migraine"),
                    new Medicine("Pain reliever")),
            new Appointment(new AppointmentType("Vaccination"), LocalDateTime.of(2024,
                    11, 1, 10, 0), 6, new Sickness("Preventative Care"),
                    new Medicine("Vaccine"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (PersonDescriptor samplePerson : getSamplePersons()) {
            int id = sampleAb.addPerson(samplePerson);
            logger.finer("Added: " + id);
        }
        return sampleAb;
    }

    public static ReadOnlyAppointmentBook getSampleAppointmentBook() {
        AppointmentBook sampleAppb = new AppointmentBook();
        for (Appointment sampleAppointment : getSampleAppointments()) {
            String app = sampleAppb.addAppointment(sampleAppointment);
            logger.finer("Added: " + app);
        }
        return sampleAppb;
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
