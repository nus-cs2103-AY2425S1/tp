package seedu.edulog.model;

import java.util.List;

import seedu.edulog.model.gift.Gift;

/**
 * Unmodifiable view of a gift list.
 */
public interface ReadOnlyGiftList {

    /**
     * Returns an unmodifiable view of the gifts list.
     */
    List<Gift> getGiftList();

    /**
     * Gets a random gift from the gift list.
     */
    Gift getRandomGift();

}
