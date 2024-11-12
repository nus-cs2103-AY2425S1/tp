package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_NRIC = "S6482983A";
    public static final String DEFAULT_CAREGIVER_NRIC = "S8765432Z";
    public static final String DEFAULT_PATIENT_NRIC = "S2345678H";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_ROLE = "PATIENT";
    public static final String DEFAULT_NOTE = "Note";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Role> roles;
    private Set<Nric> caregivers;
    private Set<Nric> patients;
    private Set<Appointment> appointments;
    private List<Note> notes;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        roles = SampleDataUtil.getRoleSet(DEFAULT_ROLE);
        caregivers = new HashSet<>();
        patients = new HashSet<>();
        appointments = new HashSet<>();
        notes = SampleDataUtil.getSampleNotes(DEFAULT_NOTE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        nric = personToCopy.getNric();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        roles = new HashSet<>(personToCopy.getRoles());
        patients = new HashSet<>(personToCopy.getPatients());
        caregivers = new HashSet<>(personToCopy.getCaregivers());
        appointments = new HashSet<>(personToCopy.getAppointments());
        notes = new ArrayList<Note>(personToCopy.getNotes());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String... role) {
        this.roles = SampleDataUtil.getRoleSet(role);
        return this;
    }


    /**
     * Sets the {@code Caregivers} of the {@code Person} that we are building.
     */
    public PersonBuilder withCaregivers(String... caregivers) {
        this.caregivers = SampleDataUtil.getCaregiverSet(caregivers);
        return this;
    }

    /**
     * Sets the {@code Patients} of the {@code Person} that we are building.
     */
    public PersonBuilder withPatients(String... patients) {
        this.patients = SampleDataUtil.getPatientSet(patients);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNotes(String ... notes) {
        this.notes = SampleDataUtil.getSampleNotes(notes);
        return this;
    }
    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     * A variable number of arguments can be provided.
     */
    public PersonBuilder withAppointments(Appointment... appointments) {
        for (Appointment appointment : appointments) {
            this.appointments.add(appointment);
        }
        return this;
    }

    /**
     * Returns a {@code Person} object created from the various fields previously set.
     *
     * @return a {@code Person} object created from the various fields previously set.
     */
    public Person build() {
        Person temp = new Person(name, nric, phone, email, address, tags, roles, caregivers, patients);
        for (Note note: notes) {
            temp.addNote(note);
        }

        return temp;
    }

}
