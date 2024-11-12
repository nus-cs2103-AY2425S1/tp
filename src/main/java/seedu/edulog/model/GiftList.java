package seedu.edulog.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Random;

import seedu.edulog.model.gift.Gift;

/**
 * Wraps all data for GiftList
 */
public class GiftList implements ReadOnlyGiftList {
    private final List<Gift> gifts;
    private Random random = new Random();

    /**
     * Generates a GiftList based on an existing list of gifts that have already been initialized.
     * @param gifts the List of existing gifts.
     */
    public GiftList(List<Gift> gifts) {
        requireNonNull(gifts);
        this.gifts = gifts;
    }

    @Override
    public List<Gift> getGiftList() {
        return gifts;
    }

    /**
     * Gets a random gift from the gift list.
     */
    @Override
    public Gift getRandomGift() {
        return gifts.get(random.nextInt(gifts.size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GiftList)) {
            return false;
        }

        GiftList otherGiftList = (GiftList) other;
        return gifts.equals(otherGiftList.gifts);
    }

    @Override
    public int hashCode() {
        return gifts.hashCode();
    }
}
