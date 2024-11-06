package seedu.address.logic.commands.controller;

/**
 * Interface of confirmation.
 */
public interface ConfirmationController {
    /**
     * Checks if the user confirmed the specified action.
     *
     * @param title The dialog title.
     * @param contentText The content of the confirmation message.
     * @return true if the user confirms action, false otherwise.
     */
    public boolean isConfirmed(String title, String contentText);
}
