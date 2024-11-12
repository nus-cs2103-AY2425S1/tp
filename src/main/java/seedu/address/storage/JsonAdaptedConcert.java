package seedu.address.storage;

import static seedu.address.model.concert.ConcertDate.INPUT_DATE_FORMATTER;
import static seedu.address.model.concert.ConcertDate.OUTPUT_DATE_FORMATTER;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;

/**
 * Jackson-friendly version of {@link Concert}
 */
public class JsonAdaptedConcert {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Concert's %s field is missing!";

    private final String name;
    private final String address;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedConcert} with the given concert details.
     */
    @JsonCreator
    public JsonAdaptedConcert(@JsonProperty("name") String name,
            @JsonProperty("address") String address, @JsonProperty("date") String date) {
        this.name = name;
        this.address = address;
        this.date = date;
    }

    /**
     * Converts a given {@code Concert} into this class for Jackson use.
     */
    public JsonAdaptedConcert(Concert source) {
        name = source.getName().fullName;
        address = source.getAddress().value;
        // date is in D MMM YYYY HHmm format
        date = source.getDate().concertDate;
    }

    /**
     * Converts this Jackson-friendly adapted concert object into the model's {@code Concert} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *         concert.
     */
    public Concert toModelType() throws IllegalValueException {
        // verify name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class
                    .getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        // verify address
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // verify date
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ConcertDate.class.getSimpleName()));
        }

        if (!ConcertDate.isValidDate(date, OUTPUT_DATE_FORMATTER)) {
            throw new IllegalValueException(ConcertDate.MESSAGE_CONSTRAINTS);
        }
        // date is D MMM YYYY HHmm reverse the processDate when adding from json
        String formattedDate = ConcertDate.processDate(date, OUTPUT_DATE_FORMATTER,
                INPUT_DATE_FORMATTER);
        final ConcertDate modelDate = new ConcertDate(formattedDate);

        return new Concert(modelName, modelAddress, modelDate);
    }
}
