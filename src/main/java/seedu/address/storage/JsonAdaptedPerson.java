package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String telegram;
    private final String email;
    private final String studentStatus;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String nickname;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("telegram") String telegram,
                             @JsonProperty("email") String email, @JsonProperty("studentStatus") String studentStatus,
                             @JsonProperty("roles") List<JsonAdaptedRole> roles,
                             @JsonProperty("nickname") String nickname) {
        this.name = name;
        this.telegram = telegram;
        this.email = email;
        this.studentStatus = studentStatus;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.nickname = nickname;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        telegram = source.getTelegram().value;
        email = source.getEmail().value;
        studentStatus = source.getStudentStatus().value;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        nickname = source.getNickname().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Role> personRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            personRoles.add(role.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (studentStatus == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentStatus.class.getSimpleName()));
        }
        if (!StudentStatus.isValidStudentStatus(studentStatus)) {
            throw new IllegalValueException(StudentStatus.MESSAGE_CONSTRAINTS);
        }
        final StudentStatus modelstudentStatus = new StudentStatus(studentStatus);
        final Set<Role> modelRoles = new HashSet<>(personRoles);
        final Nickname modelNickname = new Nickname(nickname); // Nickname can be anything
        return new Person(modelName, modelTelegram, modelEmail, modelstudentStatus, modelRoles, modelNickname);
    }

}
