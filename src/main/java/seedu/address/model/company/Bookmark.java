package seedu.address.model.company;

import java.util.Objects;

/**
 * Represents the bookmark status of a company.
 */
public class Bookmark {

    public final boolean isBookmark;

    /**
     * Construct a new {@code Bookmark}
     *
     * @param value The boolean value of whether a company is bookmarked.
     */
    public Bookmark(boolean value) {
        this.isBookmark = value;
    }

    public Bookmark() {
        this.isBookmark = false;
    }

    public boolean getIsBookmarkValue() {
        return this.isBookmark;
    }

    /**
     * This method is used to return a String to be used for the UI on indicating if the company
     * is bookmarked or not.
     */
    public String prettyString() {
        return isBookmark ? "Bookmarked" : "Not bookmarked";
    }

    @Override
    public String toString() {
        return isBookmark ? "true" : "false";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Bookmark)) {
            return false;
        }

        Bookmark otherBookmark = (Bookmark) other;
        return Objects.equals(isBookmark, otherBookmark.isBookmark);
    }
}
