package seedu.edulog.commons.util;

import java.util.Arrays;

import seedu.edulog.commons.exceptions.DataLoadingException;
import seedu.edulog.model.GiftList;
import seedu.edulog.model.ReadOnlyGiftList;
import seedu.edulog.model.gift.Gift;

/**
 * Utility class for generating random gift ideas.
 */
public class GiftUtil {
    /**
     * Represents our own private gift list. v2.0 enhancement: affiliate links here.
     * This list should be not fully available to the users to represent commercial sensitivity of gift list.
     */
    public static final ReadOnlyGiftList AFFILIATE_GIFTS = new GiftList(
        Arrays.stream(new String[]{
            "Book of inspirational quotes",
            "Personalized pen",
            "Notebook with motivational quotes",
            "Mug with the school logo",
            "Calendar for the next school year",
            "Set of colorful markers",
            "Motivational bookmarks",
            "Learning poster for the classroom",
            "Personalized reading light",
            "Creative craft kit",
            "Puzzle with a historical theme",
            "Reusable water bottle",
            "Stationery set with notebooks and pens",
            "Art supplies kit",
            "Personalized keychain",
            "Educational board game",
            "Custom sticky notes with a positive message",
            "Desk organizer set",
            "Portable phone stand",
            "Mini succulent plant",
            "Bookstore gift card",
            "Personalized pencil case",
            "Puzzle book (crosswords, sudoku, etc.)",
            "Flashcards for vocabulary or math practice",
            "Mindfulness coloring book",
            "Set of science experiment kits",
            "Educational mobile app subscription",
            "Custom bookmarks with the student’s name",
            "Motivational poster for their room",
            "Portable notebook with to-do lists"
        }).map(Gift::new).toList());

    /**
     * Returns a random gift from the gift list.
     */
    public static Gift getRandomGift() throws DataLoadingException {
        return AFFILIATE_GIFTS.getRandomGift();
    }
}
