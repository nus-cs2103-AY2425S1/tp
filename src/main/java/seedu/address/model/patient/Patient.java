package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.filteredappointment.FilteredAppointment;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    private final Nric nric;
    private final Birthdate birthdate;
    private final Sex sex;

    // Data fields
    private Address address;
    private AllergyList allergies = new AllergyList();
    private BloodType bloodType;
    private HealthRisk healthRisk;
    private ExistingCondition existingCondition;
    private Note note;
    private Name nokName;
    private Phone nokPhone;
    private ApptList appts = new ApptList();

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Nric nric, Birthdate birthdate, Sex sex, Phone phone) {
        requireAllNonNull(name, nric, birthdate, sex, phone);
        this.name = name;
        this.nric = nric;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.email = null;
        this.address = null;
        this.bloodType = null;
        this.healthRisk = null;
        this.existingCondition = null;
        this.note = null;
        this.nokName = null;
        this.nokPhone = null;
    }

    /**
     * Only Name, NRIC, Sex, BirthDate, HealthServices field need to be present.
     * The other fields can be null
     */
    public Patient(Name name, Nric nric, Birthdate birthdate, Sex sex, Phone phone,
            Email email, Address address, AllergyList allergies, BloodType bloodType, HealthRisk healthRisk,
            ExistingCondition existingCondition, Note note, Name nokName, Phone nokPhone, ApptList appts) {
        requireAllNonNull(name, nric, birthdate, sex);
        this.name = name;
        this.nric = nric;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.allergies = allergies;
        this.bloodType = bloodType;
        this.healthRisk = healthRisk;
        this.existingCondition = existingCondition;
        this.note = note;
        this.nokName = nokName;
        this.nokPhone = nokPhone;
        this.appts = appts;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public Sex getSex() {
        return sex;
    }

    public Birthdate getBirthdate() {
        return birthdate;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Name getNokName() {
        return nokName;
    }

    public Phone getNokPhone() {
        return nokPhone;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public HealthRisk getHealthRisk() {
        return healthRisk;
    }

    public Note getNote() {
        return note;
    }

    public ExistingCondition getExistingCondition() {
        return existingCondition;
    }

    public Address getAddress() {
        return address;
    }

    public ApptList getAppts() {
        return appts;
    }

    // ApptList access functions
    /**
     * Returns the most recent past appointment.
     * @return Appt
     * @see ApptList#getMostRecentPastAppt()
     */
    public Appt getMostRecentPastAppt() {
        return appts.getMostRecentPastAppt();
    }

    /**
     * Returns the most recent future appointment.
     * @return Appt
     * @see ApptList#getMostRecentFutureAppt()
     */
    public Appt getMostRecentFutureAppt() {
        return appts.getMostRecentFutureAppt();
    }

    /**
     * Adds an appointment to the patient's list of appointments.
     * The appointments will be sorted by date and time.
     */
    public void addAppt(Appt appt) {
        appts.addAppt(appt);
    }

    /**
     * Returns an immutable list of appointments.
     * This list will not contain any duplicate appointments.
     *
     * @return List of appointments.
     */
    public List<Appt> getImmutableApptList() {
        return appts.getImmutableApptList();
    }

    /**
     * Returns a string representation of the appointments
     * in the form of a list of strings.
     *
     * @return String representation of the appointments.
     */
    public String getApptsString() {
        return appts.toString();
    }

    /**
     * Deletes an appointment from the patient's list of appointments.
     */
    public void deleteAppt(Appt appt) {
        appts.deleteAppt(appt);
    }

    // AllergyList access functions

    /**
     * Adds an allergy to the patient's set of allergies.
     */
    public void addAllergy(Allergy allergy) {
        allergies.addAllergy(allergy);
    }

    /**
     * Returns an immutable list of allergies.
     * This list will not contain any duplicate allergies.
     *
     * @return List of allergies.
     */
    public List<Allergy> getAllergies() {
        return allergies.getAllergies();
    }

    /**
     * Returns a string representation of all allergies of the patient, sorted in alphabetical order.
     *
     * @return String representation of the allergies.
     */
    public String getAllergiesString() {
        return allergies.toString();
    }

    /**
     * Deletes an allergy from the patient's set of allergies.
     */
    public void deleteAllergy(Allergy allergy) {
        allergies.deleteAllergy(allergy);
    }

    /**
     * Checks if an allergy is present for removal in the patient's set of allergies.
     */
    public void checkAllergyPresentForRemoval(Allergy allergy) {
        allergies.checkAllergyPresentForRemoval(allergy);
    }

    /**
     * Checks if an allergy is present in the patient's set of allergies.
     */
    public void checkAllergyAlreadyExists(Allergy allergy) {
        allergies.checkAllergyAlreadyExists(allergy);
    }

    public Stream<FilteredAppointment> getFilteredAppointments(AppointmentDateFilter dateFilter) {
        return this.appts.filterAppts(dateFilter).map(appt -> new FilteredAppointment(appt, this));
    }

    /**
     * Returns true if both patients have the same NRIC.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getNric().equals(getNric());
    }

    public int compareTo(Patient other) {
        return this.getName().compareTo(other.getName());
    }

    // public void showDetails() {

    // }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return this.isSamePatient(otherPatient);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, nric, birthdate, sex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("sex", sex)
                .add("birthdate", birthdate)
                .add("phone", phone)
                .toString();
    }

}
