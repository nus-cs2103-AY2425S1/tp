package seedu.edulog.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.edulog.commons.exceptions.IllegalValueException;
import seedu.edulog.model.GiftList;
import seedu.edulog.model.ReadOnlyGiftList;
import seedu.edulog.model.gift.Gift;

/**
 * An Immutable GiftList that is serializable to JSON format.
 */
@JsonRootName(value = "giftlist")
class JsonSerializableGiftList {

    private final List<JsonAdaptedGift> gifts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGiftList} with the given students and lessons.
     */
    @JsonCreator
    public JsonSerializableGiftList(@JsonProperty("gifts") List<JsonAdaptedGift> gifts) {
        this.gifts.addAll(gifts);
    }

    /**
     * Converts a given {@code ReadOnlyGiftList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGiftList}.
     */
    public JsonSerializableGiftList(ReadOnlyGiftList source) {
        gifts.addAll(source.getGiftList().stream().map(JsonAdaptedGift::new).collect(Collectors.toList()));
    }

    /**
     * Converts this giftlist book into the model's {@code GiftList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GiftList toModelType() throws IllegalValueException {
        GiftList giftList = new GiftList();
        for (JsonAdaptedGift jsonAdaptedGift : gifts) {
            Gift gift = jsonAdaptedGift.toModelType();
            giftList.addGift(gift);
        }

        return giftList;
    }

}
