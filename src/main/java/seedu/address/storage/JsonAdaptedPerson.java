package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.UpdatedAt;
import seedu.address.model.scheme.Scheme;
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
    private final String priority;
    private final String dateOfBirth;
    private final Double income;
    private final Integer familySize;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    private final List<JsonAdaptedScheme> schemes = new ArrayList<>();
    private final String updatedAt;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("priority") String priority,
            @JsonProperty("dateOfBirth") String dateOfBirth,
            @JsonProperty("income") Double income,
            @JsonProperty("familySize") Integer familySize,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("schemes") ArrayList<JsonAdaptedScheme> schemes,
            @JsonProperty("updatedAt") String updatedAt) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.priority = priority;
        this.dateOfBirth = dateOfBirth;
        this.income = income;
        this.familySize = familySize;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (schemes != null) {
            this.schemes.addAll(schemes);
        }
        this.updatedAt = updatedAt;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        priority = source.getPriority().name();
        dateOfBirth = source.getDateOfBirth().getValue().toString();
        income = source.getIncome().getValue();
        familySize = source.getFamilySize().getValue();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());
        updatedAt = source.getUpdatedAt().getValue().toString();
        schemes.addAll(source.getSchemes().stream()
                .map(JsonAdaptedScheme::new)
                .toList());
    }

    private Person toModelType(boolean isArchived) throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        final List<Scheme> personSchemes = new ArrayList<>();
        for (JsonAdaptedScheme scheme : schemes) {
            personSchemes.add(scheme.toModelType());
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

        if (priority == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName()));
        }

        final Priority modelPriority;
        try {
            modelPriority = Priority.valueOf(priority);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
        }

        if (dateOfBirth == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName()));
        }

        final DateOfBirth modelDateOfBirth;
        final LocalDate dateOfBirthLocalDate;

        try {
            dateOfBirthLocalDate = LocalDate.parse(dateOfBirth);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }

        if (!DateOfBirth.isValidDate(dateOfBirthLocalDate)) {
            throw new IllegalValueException(DateOfBirth.MESSAGE_CONSTRAINTS);
        }
        modelDateOfBirth = new DateOfBirth(dateOfBirthLocalDate);

        final Income modelIncome;
        if (income == null) {
            modelIncome = new Income(0);
        } else if (Income.isValidIncome(income)) {
            modelIncome = new Income(income);
        } else {
            throw new IllegalValueException(Income.MESSAGE_CONSTRAINTS);
        }

        final FamilySize modelFamilySize;
        if (familySize == null) {
            modelFamilySize = new FamilySize(1);
        } else if (FamilySize.isValidFamilySize(familySize)) {
            modelFamilySize = new FamilySize(familySize);
        } else {
            throw new IllegalValueException(FamilySize.MESSAGE_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final ArrayList<Scheme> modelSchemes = new ArrayList<>(personSchemes);

        final UpdatedAt modelUpdatedAt = Optional.ofNullable(updatedAt)
                .flatMap(JsonAdaptedPerson::parseDateTime)
                .map(UpdatedAt::new)
                .orElseGet(UpdatedAt::now);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelPriority, modelDateOfBirth,
                modelIncome, modelFamilySize, modelTags, modelSchemes, modelUpdatedAt, isArchived);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        return toModelType(false);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's archived {@code Person} object
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toArchivedModelType() throws IllegalValueException {
        return toModelType(true);
    }

    private static Optional<LocalDateTime> parseDateTime(String datetime) {
        try {
            return Optional.of(LocalDateTime.parse(datetime));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
