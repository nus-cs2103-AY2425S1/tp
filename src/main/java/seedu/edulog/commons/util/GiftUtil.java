package seedu.edulog.commons.util;

import java.nio.file.Path;
import java.util.Optional;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.ReadOnlyGiftList;
import seedu.edulog.model.gift.Gift;
import seedu.edulog.storage.GiftListStorage;
import seedu.edulog.storage.JsonGiftListStorage;

/**
 * Utility class for generating random gift ideas.
 */
public class GiftUtil {

    /**
     * Returns a random gift from the gift list.
     */
    public static Gift getRandomGift() throws DataLoadingException {
        Path path = Path.of("src", "main", "data", "gifts.json");
        GiftListStorage storage = new JsonGiftListStorage(path);

        Optional<ReadOnlyGiftList> giftListOptional = storage.readGiftList();
        if (giftListOptional.isEmpty()) {
            throw new DataLoadingException(new Exception("Gift list is empty"));
        }
        ReadOnlyGiftList initialData = giftListOptional.get();

        return initialData.getRandomGift();
    }
}
