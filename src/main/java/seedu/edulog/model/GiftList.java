package seedu.edulog.model;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edulog.model.gift.Gift;

/**
 * Wraps all data for giftlist
 */
public class GiftList implements ReadOnlyGiftList {
    private final ObservableList<Gift> gifts;
    private Random random = new Random();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        gifts = FXCollections.observableArrayList();
    }

    public GiftList() {}

    @Override
    public ObservableList<Gift> getGiftList() {
        return gifts;
    }

    public void addGift(Gift gift) {
        gifts.add(gift);
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
