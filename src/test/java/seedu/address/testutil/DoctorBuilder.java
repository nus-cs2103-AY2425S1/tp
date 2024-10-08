package seedu.address.testutil;

import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.Speciality;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder extends PersonBuilder {

    public static final String DEFAULT_SPECIALITY = "General";

    private Speciality speciality;

    /**
     * Creates a {@code DoctorBuilder} with the default details.
     */
    public DoctorBuilder() {
        super();
        speciality = new Speciality(DEFAULT_SPECIALITY);
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code doctorToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        super(doctorToCopy);
        speciality = doctorToCopy.getSpeciality();
    }

    /**
     * Sets the {@code Speciality} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withSpeciality(String speciality) {
        this.speciality = new Speciality(speciality);
        return this;
    }

    @Override
    public DoctorBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public DoctorBuilder withTags(String ... tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public DoctorBuilder withAddress(String address) {
        super.withAddress(address);
        return this;
    }

    @Override
    public DoctorBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    @Override
    public DoctorBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    @Override
    public Doctor build() {
        Person person = super.build();
        return new Doctor(
            person.getName(),
            person.getPhone(),
            person.getEmail(),
            person.getAddress(),
            speciality,
            person.getTags()
        );
    }

}
