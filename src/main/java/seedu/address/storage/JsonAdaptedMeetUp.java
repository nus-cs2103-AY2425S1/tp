package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.MeetUp;

/**
 * Jackson-friendly version of {@link MeetUp}.
 */
class JsonAdaptedMeetUp {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meet up's %s field is missing!";

    private final String name;
    private final String info;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a {@code JsonAdaptedMeetUp} with the given meet up details.
     */
    @JsonCreator
    public JsonAdaptedMeetUp(@JsonProperty("name") String name, @JsonProperty("info") String info,
            @JsonProperty("from") LocalDateTime from, @JsonProperty("to") LocalDateTime to) {
        this.name = name;
        this.info = info;
        this.from = from;
        this.to = to;
    }

    /**
     * Converts a given {@code MeetUp} into this class for Jackson use.
     */
    public JsonAdaptedMeetUp(MeetUp source) {
        name = source.getName();
        info = source.getInfo();
        from = source.getFrom();
        to = source.getTo();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code MeetUp} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meet up.
     */
    public MeetUp toModelType() throws IllegalValueException {
        // This can only be implemented after model is refactored
        // if (name == null) {
        //     throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        // }
        // if (!Name.isValidName(name)) {
        //     throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        // }
        // final Name modelName = new Name(name);

        // if (phone == null) {
        //     throw new IllegalValueException(
        // String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        // }
        // if (!Phone.isValidPhone(phone)) {
        //     throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        // }
        // final Phone modelPhone = new Phone(phone);

        // if (email == null) {
        //     throw new IllegalValueException(
        // String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        // }
        // if (!Email.isValidEmail(email)) {
        //     throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        // }
        // final Email modelEmail = new Email(email);

        // final Address modelAddress = new Address(address);
        // final PersonType modelPersonType = new PersonType(personType);

        // Placeholder code
        LocalDateTime from = LocalDateTime.of(2023, 10, 14, 15, 30, 45);
        LocalDateTime to = LocalDateTime.of(2023, 10, 14, 15, 30, 45);
        return new MeetUp("placeholder", "placeholder", from, to);
    }

}
