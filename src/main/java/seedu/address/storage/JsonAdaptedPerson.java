package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String dateOfBirth;
    private final String gender;
    private final String nric;
    private final List<JsonAdaptedMedCon> medCons = new ArrayList<>();
    private final List<JsonAdaptedAllergy> allergies = new ArrayList<>();
    private final String priority;
    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("dateOfBirth") String dateOfBirth,
            @JsonProperty("gender") String gender,
            @JsonProperty("nric") String nric,
            @JsonProperty("allergies") List<JsonAdaptedAllergy> allergies,
            @JsonProperty("priority") String priority,
            @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments,
            @JsonProperty("medCons") List<JsonAdaptedMedCon> medCons) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.priority = priority;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nric = nric;
        if (medCons != null) {
            this.medCons.addAll(medCons);
        }
        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        if (appointments != null) {
            this.appointments.addAll(appointments);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        dateOfBirth = source.getDateOfBirth().value;
        gender = source.getGender().value;
        nric = source.getNric().value;

        // Use helper function to convert and sort medical conditions, allergies, and appointments
        medCons.addAll(convertToSortedList(source.getMedCons(), JsonAdaptedMedCon::new));
        allergies.addAll(convertToSortedList(source.getAllergies(), JsonAdaptedAllergy::new));
        appointments.addAll(convertToSortedList(source.getAppointments(), JsonAdaptedAppointment::new));

        priority = source.getPriority().priority;
    }

    /**
     * Helper function to convert a set to a sorted list of adapted JSON objects.
     *
     * @param source The set of items to be converted and sorted.
     * @param mapper A function to map the item to its adapted form.
     * @param <T> The type of items in the set.
     * @param <R> The type of the adapted JSON item.
     * @return A sorted list of adapted items.
     */
    private <T extends Comparable<T>, R> List<R> convertToSortedList(Set<T> source, Function<T, R> mapper) {
        return source.stream()
                .sorted()
                .map(mapper)
                .toList();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Allergy> personAllergies = new ArrayList<>();
        final List<Appointment> personAppointments = new ArrayList<>();
        final List<MedCon> personMedCons = new ArrayList<>();

        for (JsonAdaptedAppointment appointment : appointments) {
            personAppointments.add(appointment.toModelType());
        }

        for (JsonAdaptedAllergy allergy : allergies) {
            personAllergies.add(allergy.toModelType());
        }

        for (JsonAdaptedMedCon medCon : medCons) {
            personMedCons.add(medCon.toModelType());
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

        if (dateOfBirth == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateOfBirth.class.getSimpleName()));
        }
        if (!DateUtil.isValidDate(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS_WRONG_FORMAT);
        }
        if (!DateOfBirth.isValidDateOfBirth(dateOfBirth)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS_FUTURE_DATE);
        }
        final DateOfBirth modelDateOfBirth = new DateOfBirth(dateOfBirth);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        final Set<Allergy> modelAllergies = new HashSet<>(personAllergies);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        final Priority modelPriority = new Priority(priority);

        final Set<Appointment> modelAppointments = new HashSet<>(personAppointments);

        final Set<MedCon> modelMedCons = new HashSet<>(personMedCons);

        return new Person(modelName, modelPhone, modelEmail, modelNric, modelAddress, modelDateOfBirth,
                modelGender, modelAllergies, modelPriority, modelAppointments, modelMedCons);

    }

}
