package seedu.address.testutil;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.Email;
import seedu.address.model.person.HealthRecord;
import seedu.address.model.person.HealthRisk;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setNric(person.getNric());
        descriptor.setBirthDate(person.getBirthdate());
        descriptor.setSex(person.getSex());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setBloodType(person.getBloodType());
        descriptor.setNokName(person.getNokName());
        descriptor.setNokPhone(person.getNokPhone());
        descriptor.setAllergy(person.getAllergy());
        descriptor.setHealthRisk(person.getHealthRisk());
        descriptor.setHealthRecord(person.getHealthRecord());
        descriptor.setAppts(person.getAppts());
        descriptor.setNote(person.getNote());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code BirthDate} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthDate(String birthDate) {
        descriptor.setBirthDate(new Birthdate(birthDate));
        return this;
    }

    /**
     * Sets the {@code Sex} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSex(String sex) {
        descriptor.setSex(new Sex(sex));
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code bloodType} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBloodType(String bloodType) {
        descriptor.setBloodType(new BloodType(bloodType));
        return this;
    }

    /**
     * Sets the {@code nokName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNokName(String nokName) {
        descriptor.setNokName(new Name(nokName));
        return this;
    }

    /**
     * Sets the {@code nokPhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNokPhone(String nokPhone) {
        descriptor.setNokPhone(new Phone(nokPhone));
        return this;
    }

    /**
     * Sets the {@code Allergy} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAllergy(String allergy) {
        descriptor.setAllergy(new Allergy(allergy));
        return this;
    }

    /**
     * Sets the {@code healthRisk} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHealthRisk(String healthRisk) {
        descriptor.setHealthRisk(new HealthRisk(healthRisk));
        return this;
    }

    /**
     * Sets the {@code healthRecord} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHealthRecord(String healthRecord) {
        descriptor.setHealthRecord(new HealthRecord(healthRecord));
        return this;
    }

    /**
     * Parses the {@code Appts} into a {@code List<Appt>}
     * and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withAppts(String... appts) {
        List<Appt> appointmentSet = Stream.of(appts)
                .map(apptString -> LocalDateTime.parse(apptString, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .map(Appt::new).collect(Collectors.toList());
        descriptor.setAppts(appointmentSet);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }
}
