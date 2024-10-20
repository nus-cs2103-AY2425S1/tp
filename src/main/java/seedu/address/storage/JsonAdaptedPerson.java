package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.PaymentStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProjectStatus;
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
    private final String projectStatus;
    private final String paymentStatus;
    private final String clientStatus;
    private final String deadline;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("projectStatus") String projectStatus,
                             @JsonProperty("paymentStatus") String paymentStatus,
                             @JsonProperty("clientStatus") String clientStatus,
                             @JsonProperty("deadline") String deadline,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.projectStatus = projectStatus;
        this.paymentStatus = paymentStatus;
        this.clientStatus = clientStatus;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.deadline = deadline;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        projectStatus = source.getProjectStatus().toString();
        paymentStatus = source.getPaymentStatus().toString();
        clientStatus = source.getClientStatus().toString();
        deadline = source.getDeadline().value.format(Deadline.INPUT_FORMATTER);
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

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (projectStatus == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, ProjectStatus.class.getSimpleName()));
        }
        if (!ProjectStatus.isValidProjectStatus(projectStatus)) {
            throw new IllegalValueException(ProjectStatus.MESSAGE_CONSTRAINTS);
        }
        final ProjectStatus modelProjectStatus = new ProjectStatus(projectStatus);

        if (paymentStatus == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, PaymentStatus.class.getSimpleName()));
        }
        if (!PaymentStatus.isValidPaymentStatus(paymentStatus)) {
            throw new IllegalValueException(PaymentStatus.MESSAGE_CONSTRAINTS);
        }
        final PaymentStatus modelPaymentStatus = new PaymentStatus(paymentStatus);

        if (clientStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ClientStatus.class.getSimpleName()));
        }
        if (!ClientStatus.isValidClientStatus(clientStatus)) {
            throw new IllegalValueException(ClientStatus.MESSAGE_CONSTRAINTS);
        }
        final ClientStatus modelClientStatus = new ClientStatus(clientStatus);

        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }

        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }

        final Deadline modelDeadline = new Deadline(deadline);

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                modelProjectStatus, modelPaymentStatus, modelClientStatus, modelDeadline);
    }
}
