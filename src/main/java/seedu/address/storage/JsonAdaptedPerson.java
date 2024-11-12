package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.role.Role;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String telegram;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final List<JsonAdaptedAttendance> attendance = new ArrayList<>();
    private final String favouriteStatus;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("telegram") String telegram,
            @JsonProperty("roles") List<JsonAdaptedRole> roles,
            @JsonProperty("attendance") List<JsonAdaptedAttendance> attendance,
                             @JsonProperty("favouriteStatus") String favouriteStatus) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegram = telegram;
        this.favouriteStatus = favouriteStatus;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        if (attendance != null) {
            this.attendance.addAll(attendance);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        telegram = source.getTelegram().value;
        favouriteStatus = source.getFavouriteStatus().toString();
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .toList());
        attendance.addAll(source.getAttendance().stream()
                .map(JsonAdaptedAttendance::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Role> personRoles = new ArrayList<>();
        final List<Attendance> personAttendance = new ArrayList<>();

        for (JsonAdaptedRole role : roles) {
            personRoles.add(role.toModelType());
        }

        for (JsonAdaptedAttendance session : attendance) {
            personAttendance.add(session.toModelType());
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

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        if (favouriteStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FavouriteStatus.class.getSimpleName()));
        }
        if (!FavouriteStatus.isValidFavouriteStatus(favouriteStatus)) {
            throw new IllegalValueException(FavouriteStatus.MESSAGE_CONSTRAINTS);
        }
        final FavouriteStatus modelFavouriteStatus = FavouriteStatus.valueOf(favouriteStatus);

        final Set<Role> modelRoles = new HashSet<>(personRoles);
        final Set<Attendance> modelAttendance = new HashSet<>(personAttendance);
        return new Person(modelName, modelPhone, modelEmail, modelTelegram, modelRoles,
                modelAttendance, modelFavouriteStatus);
    }

}
