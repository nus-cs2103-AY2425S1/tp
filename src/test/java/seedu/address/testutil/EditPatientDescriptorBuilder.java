package seedu.address.testutil;

import java.util.LinkedHashSet;

import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Allergy;
import seedu.address.model.patient.AllergyList;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.ExistingCondition;
import seedu.address.model.patient.HealthRisk;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Note;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditPatientDescriptorBuilder {

    private EditPatientDescriptor descriptor;

    public EditPatientDescriptorBuilder() {
        descriptor = new EditPatientDescriptor();
    }

    public EditPatientDescriptorBuilder(EditPatientDescriptor descriptor) {
        this.descriptor = new EditPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPatientDescriptor} with fields containing {@code patient}'s details
     */
    public EditPatientDescriptorBuilder(Patient patient) {
        descriptor = new EditPatientDescriptor();
        descriptor.setName(patient.getName());
        descriptor.setNric(patient.getNric());
        descriptor.setBirthDate(patient.getBirthdate());
        descriptor.setSex(patient.getSex());
        descriptor.setPhone(patient.getPhone());
        descriptor.setEmail(patient.getEmail());
        descriptor.setAddress(patient.getAddress());
        descriptor.setBloodType(patient.getBloodType());
        descriptor.setNokName(patient.getNokName());
        descriptor.setNokPhone(patient.getNokPhone());
        descriptor.setAllergiesToAdd(new AllergyList(new LinkedHashSet<>(patient.getAllergies())));
        descriptor.setAllergiesToRemove(new AllergyList(new LinkedHashSet<>(patient.getAllergies())));
        descriptor.setHealthRisk(patient.getHealthRisk());
        descriptor.setExistingCondition(patient.getExistingCondition());
        descriptor.setNote(patient.getNote());
    }

    /**
     * Sets the {@code Name} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code BirthDate} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withBirthDate(String birthDate) {
        descriptor.setBirthDate(new Birthdate(birthDate));
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withSex(String sex) {
        descriptor.setSex(new Sex(sex));
        return this;
    }

    public EditPatientDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code bloodType} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Sets the {@code nokName} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withNokName(String nokName) {
        descriptor.setNokName(new Name(nokName));
        return this;
    }

    /**
     * Sets the {@code nokPhone} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withNokPhone(String nokPhone) {
        descriptor.setNokPhone(new Phone(nokPhone));
        return this;
    }

    /**
     * Sets the {@code allergiesToAdd} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withAllergiesToAdd(String... allergies) {
        AllergyList allergyListToAdd = new AllergyList();
        for (String allergy : allergies) {
            String trimmedAllergy = allergy.trim();
            allergyListToAdd.addAllergy(new Allergy(trimmedAllergy));
        }
        descriptor.setAllergiesToAdd(allergyListToAdd);
        return this;
    }

    /**
     * Sets the {@code allergiesToRemove} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withAllergiesToRemove(String... allergies) {
        AllergyList allergyListToRemove = new AllergyList();
        for (String allergy : allergies) {
            String trimmedAllergy = allergy.trim();
            allergyListToRemove.addAllergy(new Allergy(trimmedAllergy));
        }
        descriptor.setAllergiesToRemove(allergyListToRemove);
        return this;
    }

    /**
     * Sets the {@code healthRisk} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withHealthRisk(String healthRisk) {
        descriptor.setHealthRisk(new HealthRisk(healthRisk));
        return this;
    }

    /**
     * Sets the {@code existingCondition} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withExistingCondition(String existingCondition) {
        descriptor.setExistingCondition(new ExistingCondition(existingCondition));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }
}
