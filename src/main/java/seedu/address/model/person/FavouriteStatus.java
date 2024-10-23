package seedu.address.model.person;

/**
 * Represents the favourite status of a contact.
 * <p>
 * This enum can be used to indicate whether a contact is classified
 * as a favourite or not. The two possible values are:
 * </p>
 * <ul>
 *     <li>{@link #FAVOURITE} - Indicates that the contact is a favourite.</li>
 *     <li>{@link #NOT_FAVOURITE} - Indicates that the contact is not a favourite.</li>
 * </ul>
 */
public enum FavouriteStatus {
    FAVOURITE,
    NOT_FAVOURITE;

    public static final String MESSAGE_CONSTRAINTS = "Favourite status should either be FAVOURITE or NOT_FAVOURITE";

    public static boolean isValidFavouriteStatus(String status) {
        return status.equals(FAVOURITE.toString()) || status.equals(NOT_FAVOURITE.toString());
    }
}
