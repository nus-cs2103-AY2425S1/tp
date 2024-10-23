package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWeddingBook;
import seedu.address.model.WeddingBook;
import seedu.address.model.wedding.Wedding;

/**
 * An Immutable WeddingBook that is serializable to JSON format.
 */
@JsonRootName(value = "weddingbook")
public class JsonSerializableWeddingBook {

    public static final String MESSAGE_DUPLICATE_WEDDING = "Wedding list contains duplicate wedding(s).";

    private final List<JsonAdaptedWedding> weddings = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWeddingBook} with the given weddings.
     */
    @JsonCreator
    public JsonSerializableWeddingBook(@JsonProperty("weddings") List<JsonAdaptedWedding> weddings) {
        this.weddings.addAll(weddings);
    }

    /**
     * Converts a given {@code ReadOnlyWeddingBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeddingBook}.
     */
    public JsonSerializableWeddingBook(ReadOnlyWeddingBook source) {
        weddings.addAll(source.getWeddingList().stream().map(JsonAdaptedWedding::new).collect(Collectors.toList()));
    }

    /**
     * Converts this wedding book into the model's {@code WeddingBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WeddingBook toModelType() throws IllegalValueException {
        WeddingBook weddingBook = new WeddingBook();
        for (JsonAdaptedWedding jsonAdaptedWedding : weddings) {
            Wedding wedding = jsonAdaptedWedding.toModelType();
            if (weddingBook.hasWedding(wedding)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WEDDING);
            }
            weddingBook.addWedding(wedding);
        }
        return weddingBook;
    }

}
