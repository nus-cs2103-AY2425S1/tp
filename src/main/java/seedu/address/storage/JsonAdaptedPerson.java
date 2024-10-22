package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String nric;
    private final List<Nric> caregivers = new ArrayList<>();
    private final List<Nric> patients = new ArrayList<>();
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();
    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details with notes.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("nric") String nric,
            @JsonProperty("phone") String phone, @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("roles") List<JsonAdaptedRole> roles,
            @JsonProperty("caregivers") List<Nric> caregivers,
            @JsonProperty("patients") List<Nric> patients,
            @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
            @JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }

        if (roles != null) {
            this.roles.addAll(roles);
        }
        if (caregivers != null) {
            this.caregivers.addAll(caregivers);
        }
        if (patients != null) {
            this.patients.addAll(patients);
        }
        if (appointments != null) {
            this.appointments.addAll(appointments);
        }
        if (notes != null) {
            this.notes.addAll(notes);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        nric = source.getNric().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        roles.addAll(source.getRoles().stream().map(JsonAdaptedRole::new).collect(Collectors.toList()));
        patients.addAll(source.getPatients());
        caregivers.addAll(source.getCaregivers());
        List<JsonAdaptedAppointment> adaptedAppointments = source.getAppointments().stream()
                .map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList());
        appointments.addAll(adaptedAppointments);
        notes.addAll(source.getNotes().stream().map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's
     * {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Nric"));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        final List<Role> roleList = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            roleList.add(role.toModelType());
        }

        final Set<Nric> caregiverNrics = caregivers.stream()
                .collect(Collectors.toSet());

        final Set<Nric> patientNrics = patients.stream()
                .collect(Collectors.toSet());

        final List<Appointment> appointmentList = new ArrayList<>();
        for (JsonAdaptedAppointment appointment : appointments) {
            appointmentList.add(appointment.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Set<Role> modelRoles = new HashSet<>(roleList);
        final Set<Nric> modelCaregivers = new HashSet<>(caregiverNrics);
        final Set<Nric> modelPatients = new HashSet<>(patientNrics);

        Person person = new Person(modelName, modelNric, modelPhone, modelEmail, modelAddress, modelTags, modelRoles,
                modelCaregivers, modelPatients);
        for (Appointment appointment : appointmentList) {
            person.addAppointment(appointment);
        }
        for (JsonAdaptedNote note : notes) {
            person.addNote(note.toModelType());
        }
        return person;
    }

}
