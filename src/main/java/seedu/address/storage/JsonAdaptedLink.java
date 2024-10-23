package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.link.Link;
import seedu.address.model.owner.Owner;
import seedu.address.model.pet.Pet;

/**
 * Jackson-friendly version of {@link Pet}.
 */
class JsonAdaptedLink {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Link's %s field is missing!";
    public static final String INVALID_LINK_MESSAGE_FORMAT = "Cannot find owner or pet with the given ID %s";

    private final String from;
    private final String to;

    /**
     * Constructs a {@code JsonAdaptedLink} with the given link details.
     */
    @JsonCreator
    public JsonAdaptedLink(@JsonProperty("from") String fromId, @JsonProperty("to") String toId) {
        this.from = fromId;
        this.to = toId;
    }

    /**
     * Converts a given {@code Link} into this class for Jackson use.
     */
    public JsonAdaptedLink(Link source) {
        this.from = source.getFrom().getUniqueID();
        this.to = source.getTo().getUniqueID();
    }

    /**
     * Converts this Jackson-friendly adapted link object into the model's {@code Link} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Link.
     */
    public Link toModelType(ObservableList<Owner> owners, ObservableList<Pet> pets) throws IllegalValueException {
        FilteredList<Owner> filteredOwners = owners.filtered(owner -> owner.getUniqueID().equals(from));
        if (filteredOwners.size() == 0) {
            throw new IllegalValueException(String.format(INVALID_LINK_MESSAGE_FORMAT, from));
        }

        FilteredList<Pet> filteredPets = pets.filtered(pet -> pet.getUniqueID().equals(to));
        if (filteredPets.size() == 0) {
            throw new IllegalValueException(String.format(INVALID_LINK_MESSAGE_FORMAT, to));
        }

        // Indices have been checked and should not have out of bound errors
        return new Link(filteredOwners.get(0), filteredPets.get(0));
    }
}
