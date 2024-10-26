package seedu.edulog.commons.util;

import java.nio.file.Path;
import java.util.Optional;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.ReadOnlyGiftList;
import seedu.edulog.model.gift.Gift;
import seedu.edulog.storage.GiftListStorage;
import seedu.edulog.storage.JsonGiftListStorage;

public class GiftUtil {
    public static Gift getRandomGift() throws DataLoadingException {
        Optional<ReadOnlyGiftList> giftListOptional;
        ReadOnlyGiftList initialData;
        GiftListStorage storage = new JsonGiftListStorage(Path.of("src", "main", "data", "gifts.json"));

        giftListOptional = storage.readGiftList();
        initialData = giftListOptional.get();
        return initialData.getRandomGift();
    }
}