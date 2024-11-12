package seedu.address.storage;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
class JsonAdaptedWedding {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

    private final String name;
    private final JsonAdaptedPerson client;
    private final String date;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(
            @JsonProperty("name") String name,
            @JsonProperty("client") JsonAdaptedPerson client,
            @JsonProperty("date") String date,
            @JsonProperty("venue") String venue) {
        this.name = name;
        this.client = client;
        this.date = date;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        name = source.getName().fullName;
        if (source.getClient() == null) {
            client = null;
        } else {
            client = new JsonAdaptedPerson(source.getClient().getPerson());
        }
        date = source.getDate() == null ? null : source.getDate().toString();
        venue = source.getVenue() == null ? null : source.getVenue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType(List<Wedding> weddingList) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (client == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Client.class.getSimpleName()));
        }

        Client finalClient = new Client(client.toModelType(weddingList));

        Date modelDate = null;
        if (date != null) {
            if (!Date.isValidDate(date)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            modelDate = new Date(date);
        }

        Venue modelVenue = null;
        if (venue != null) {
            if (!Venue.isValidVenue(venue)) {
                throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
            }
            modelVenue = new Venue(venue);
        }

        return new Wedding(modelName, finalClient, modelDate, modelVenue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedWedding)) {
            return false;
        }

        JsonAdaptedWedding otherWedding = (JsonAdaptedWedding) other;
        return Objects.equals(name, otherWedding.name)
                && Objects.equals(client, otherWedding.client)
                && Objects.equals(date, otherWedding.date)
                && Objects.equals(venue, otherWedding.venue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, client, date, venue);
    }
}
