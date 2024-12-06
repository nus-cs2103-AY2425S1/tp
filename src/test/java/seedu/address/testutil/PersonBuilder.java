package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final int DEFAULT_ID = 12;
    public static final String DEFAULT_PATIENT_ROLE = "PATIENT";
    public static final String DEFAULT_DOCTOR_ROLE = "DOCTOR";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    private Name name;
    private int id;
    private String role;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        id = DEFAULT_ID;
        role = DEFAULT_PATIENT_ROLE;
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        role = personToCopy.getRole();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        id = personToCopy.getId();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Person build() {
        return new Person(name, role, phone, email, address, remark);
    }
    public Person buildWithDefaultId() {
        return new Person(name, id, role, phone, email, address, remark);
    }


    /**
     * Builds a patient class
     */
    public Person buildPatient() {
        return new Person(name, role, phone, email, address, remark);
    }

    /**
     * Builds a patient with a id and appointment added
     */
    public Person buildPatientWithChosenIdAndAppointment(int patientId, int doctorId, LocalDateTime appointmentTime,
                                                         String remarks) {
        Person newPerson = new Person(name, patientId, DEFAULT_PATIENT_ROLE, phone, email, address, remark);
        newPerson.addAppointment(appointmentTime, patientId, doctorId, remarks);
        return newPerson;
    }

    /**
     * Builds a doctor with a id and appointment added
     */
    public Person buildDoctorWithChosenIdAndAppointment(int patientId, int doctorId, LocalDateTime appointmentTime,
                                                         String remarks) {
        Person newPerson = new Person(name, doctorId, DEFAULT_DOCTOR_ROLE, phone, email, address, remark);
        newPerson.addAppointment(appointmentTime, patientId, doctorId, remarks);
        return newPerson;
    }

    /**
     * Builds a doctor class
     */
    public Person buildDoctor() {
        return new Person(name, DEFAULT_DOCTOR_ROLE, phone, email, address, remark);
    }

}
