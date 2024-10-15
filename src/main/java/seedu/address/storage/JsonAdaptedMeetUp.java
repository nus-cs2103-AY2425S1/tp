package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;

/**
 * Jackson-friendly version of {@link MeetUp}.
 */
class JsonAdaptedMeetUp {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meet up's %s field is missing!";

    private final Name name;
    private final Info info;
    private final From from;
    private final To to;

    /**
     * Constructs a {@code JsonAdaptedMeetUp} with the given meet up details.
     */
    @JsonCreator
    public JsonAdaptedMeetUp(@JsonProperty("name") Name name, @JsonProperty("info") Info info,
                             @JsonProperty("from") From from, @JsonProperty("to") To to) {
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
        From start = new From("2024-09-15 12:00");
        To end = new To("2024-09-15 14:00");
        return new MeetUp(name, info, start, end);
    }

}
