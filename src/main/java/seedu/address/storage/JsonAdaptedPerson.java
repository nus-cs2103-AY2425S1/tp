package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private static final String DEFAULT_PHONE = null;
    private static final String DEFAULT_EMAIL = null;
    private static final String DEFAULT_TELEGRAMHANDLE = null;
    private static final String DEFAULT_MODNAME = null;

    private final String name;
    private String phone;
    private String email;
    private String telegramHandle;
    private String moduleName;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String contactType;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("telegramHandle") String telegramHandle,
                             @JsonProperty("moduleName") String moduleName,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("contactType") String contactType) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.telegramHandle = telegramHandle;
        this.contactType = contactType;
        this.moduleName = moduleName;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().map(Phone::toString).orElse(null);
        email = source.getEmail().map(Email::toString).orElse(null);
        telegramHandle = source.getTelegramHandle().map(TelegramHandle::toString).orElse(null);
        contactType = source.getContactType().value.toString();
        moduleName = source.getModuleName().map(ModuleName::toString).orElse(null);
        remark = source.getRemark().map(Remark::toString).orElse(null);
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
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

        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        final Optional<Phone> modelPhone = (phone == null || phone.isEmpty())
                ? Optional.empty()
                : Optional.of(new Phone(phone));

        if (email != null && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }

        final Optional<Email> modelEmail = email == null || email.isEmpty()
                ? Optional.empty()
                : Optional.of(new Email(email));

        if (telegramHandle != null && !TelegramHandle.isValidTelegramHandle(telegramHandle)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final Optional<TelegramHandle> modelTelegramHandle = telegramHandle == null || telegramHandle.isEmpty()
                ? Optional.empty()
                : Optional.of(new TelegramHandle(telegramHandle));

        if (contactType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ContactType.class.getSimpleName()));
        }
        if (!ContactType.isValidContactType(contactType)) {
            throw new IllegalValueException(ContactType.MESSAGE_CONSTRAINTS);
        }
        final ContactType modelContactType = new ContactType(contactType);

        if (moduleName != null && !ModuleName.isValidModName(moduleName)) {
            throw new IllegalValueException(ModuleName.MESSAGE_CONSTRAINTS);
        }
        final Optional<ModuleName> modelModuleName = moduleName == null || moduleName.isEmpty()
                ? Optional.empty()
                : Optional.of(new ModuleName(moduleName));

        if (remark != null && !Remark.isValidRemark(remark)) {
            throw new IllegalValueException(TelegramHandle.MESSAGE_CONSTRAINTS);
        }
        final Optional<Remark> modelRemark = remark == null || remark.isEmpty()
                ? Optional.empty()
                : Optional.of(new Remark(remark));

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelContactType, modelName, modelPhone, modelEmail, modelTelegramHandle,
                modelModuleName, modelRemark, modelTags);
    }

    public boolean isValidPerson() {
        boolean hasValidContactInfo = phone != null && Phone.isValidPhone(phone)
                || telegramHandle != null && TelegramHandle.isValidTelegramHandle(telegramHandle)
                || email != null && Email.isValidEmail(email);
        return name != null && Name.isValidName(name)
                && hasValidContactInfo
                && contactType != null && ContactType.isValidContactType(contactType);
    }

    /**
     * Replaces invalid contact info with default values
     */
    public void clearInvalidInfo() {
        if (phone != null && !Phone.isValidPhone(phone)) {
            phone = DEFAULT_PHONE;
        }
        if (email != null && !Email.isValidEmail(email)) {
            email = DEFAULT_EMAIL;
        }
        if (telegramHandle != null && !TelegramHandle.isValidTelegramHandle(telegramHandle)) {
            telegramHandle = DEFAULT_TELEGRAMHANDLE;
        }
        if (moduleName != null && !ModuleName.isValidModName(moduleName)) {
            moduleName = DEFAULT_MODNAME;
        }
    }
}
