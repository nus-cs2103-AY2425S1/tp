package seedu.edulog.model;

import javafx.collections.ObservableList;
import seedu.edulog.model.gift.Gift;

/**
 * Unmodifiable view of a gift list.
 */
public interface ReadOnlyGiftList {

    /**
     * Returns an unmodifiable view of the gifts list.
     */
    ObservableList<Gift> getGiftList();

    /**
     * Gets a random gift from the gift list.
     */
    Gift getRandomGift();

}
