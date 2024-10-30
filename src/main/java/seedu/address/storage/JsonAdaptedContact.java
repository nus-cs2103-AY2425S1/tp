package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.StudentStatus;
import seedu.address.model.contact.TelegramHandle;
import seedu.address.model.tag.Nickname;
import seedu.address.model.tag.Role;

/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String name;
    private final String telegramHandle;
    private final String email;
    private final String studentStatus;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();
    private final String nickname;

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("name") String name, @JsonProperty("telegramHandle") String telegramHandle,
                              @JsonProperty("email") String email, @JsonProperty("studentStatus") String studentStatus,
                              @JsonProperty("roles") List<JsonAdaptedRole> roles,
                              @JsonProperty("nickname") String nickname) {
        this.name = name;
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.studentStatus = studentStatus;
        if (roles != null) {
            this.roles.addAll(roles);
        }
        this.nickname = nickname;
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        name = source.getName().fullName;
        telegramHandle = source.getTelegramHandle().value;
        email = source.getEmail().value;
        studentStatus = source.getStudentStatus().value;
        roles.addAll(source.getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
        nickname = source.getNickname().value;
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Role> contactRoles = new ArrayList<>();
        for (JsonAdaptedRole role : roles) {
            contactRoles.add(role.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (telegramHandle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TelegramHandle.class.getSimpleName()));
        }
        if (!TelegramHandle.isValidTelegramHandle(telegramHandle)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final TelegramHandle modelTelegramHandle = new TelegramHandle(telegramHandle);

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
        final Set<Role> modelRoles = new HashSet<>(contactRoles);
        final Nickname modelNickname = new Nickname(nickname); // Nickname can be anything
        return new Contact(modelName, modelTelegramHandle, modelEmail, modelstudentStatus, modelRoles, modelNickname);
    }

}
