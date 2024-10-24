package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Listings;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.listing.Listing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Listings that is serializable to JSON format.
 */
@JsonRootName(value = "listings")
public class JsonSerializableListings {
    public static final String MESSAGE_DUPLICATE_LISTING = "Listings contains duplicate listing(s).";

    private final List<JsonAdaptedListing> listings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableListings} with the given listings.
     */
    @JsonCreator
    public JsonSerializableListings(@JsonProperty("listings") List<JsonAdaptedListing> listings) {
        this.listings.addAll(listings);
    }

    /**
     * Converts a given {@code ReadOnlyListings} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableListings}.
     */
    public JsonSerializableListings(ReadOnlyListings source) {
        listings.addAll(source.getListingList().stream().map(JsonAdaptedListing::new).collect(Collectors.toList()));
    }

    /**
     * Converts the listings into the model's {@code Listings} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Listings toModelType() throws IllegalValueException {
        Listings newListings = new Listings();
        for (JsonAdaptedListing jsonAdaptedListing : listings) {
            Listing listing = jsonAdaptedListing.toModelType();
            if (newListings.hasListing(listing)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LISTING);
            }
            newListings.addListing(listing);
        }
        return newListings;
    }
}
