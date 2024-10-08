package seedu.address.testutil;

import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final String DEFAULT_DATE_OF_BIRTH = "01-01-2000";
    public static final String DEFAULT_GENDER = "M";

    private DateOfBirth dateOfBirth;
    private Gender gender;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        super();
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        gender = new Gender(DEFAULT_GENDER);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        dateOfBirth = patientToCopy.getDateOfBirth();
        gender = patientToCopy.getGender();
    }

    /**
     * Sets the {@code DateOfBirth} of the {@code Patient} that we are building.
     */
    public PatientBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Gender} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    @Override
    public PatientBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    @Override
    public PatientBuilder withTags(String ... tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public PatientBuilder withAddress(String address) {
        super.withAddress(address);
        return this;
    }

    @Override
    public PatientBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    @Override
    public PatientBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    @Override
    public Patient build() {
        Person person = super.build();
        return new Patient(
            person.getName(),
            person.getPhone(),
            person.getEmail(),
            person.getAddress(),
            dateOfBirth,
            gender,
            person.getTags()
        );
    }

}
