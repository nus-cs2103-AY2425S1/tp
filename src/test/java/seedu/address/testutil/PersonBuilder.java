package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_ID = "P24689";
    public static final String DEFAULT_WARD = "A1";
    public static final String DEFAULT_DIAGNOSIS = "Celiac Disease";
    public static final String DEFAULT_MEDICATION = "gluten-free diet";
    public static final String DEFAULT_NOTES = "";
    /*
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
     */

    private Id id;
    private Name name;
    private Ward ward;
    private Diagnosis diagnosis;
    private Medication medication;
    private Notes notes;
    private Appointment appointment;
    /*
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
     */

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new Id(DEFAULT_ID);
        ward = new Ward(DEFAULT_WARD);
        diagnosis = new Diagnosis(DEFAULT_DIAGNOSIS);
        medication = new Medication(DEFAULT_MEDICATION);
        notes = new Notes(DEFAULT_NOTES);
        appointment = null;
        /*
        diagnosis = new Diagnosis("temp");
        medication = new Medication("temp");
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
         */
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        id = personToCopy.getId();
        ward = personToCopy.getWard();
        diagnosis = personToCopy.getDiagnosis();
        medication = personToCopy.getMedication();
        notes = personToCopy.getNotes();
        appointment = personToCopy.getAppointment();
        /*
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());

         */
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Person} that we are building.
     */
    public PersonBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Ward} of the {@code Person} that we are building.
     */
    public PersonBuilder withWard(String ward) {
        this.ward = new Ward(ward);
        return this;
    }

    /**
     * Sets the {@code Diagnosis} of the {@code Person} that we are building.
     */
    public PersonBuilder withDiagnosis(String diagnosis) {
        this.diagnosis = new Diagnosis(diagnosis);
        return this;
    }

    /**
     * Sets the {@code Diagnosis} of the {@code Person} that we are building.
     */
    public PersonBuilder withMedication(String medication) {
        this.medication = new Medication(medication);
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Person} that we are building.
     */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(String description, LocalDateTime start, LocalDateTime end) {
        this.appointment = new Appointment(description, start, end);
        return this;
    }

    /*
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.

    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.

    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.

    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.

    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }
    */

    public Person build() {
        return new Person(name, id, ward, diagnosis, medication, notes, appointment);
    }

}
