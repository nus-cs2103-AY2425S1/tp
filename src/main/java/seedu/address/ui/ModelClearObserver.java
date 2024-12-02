package seedu.address.ui;

/**
 * Observes changes to the model.
 * Implementers of this interface receives a notification when the Udders list is cleared.
 *
 */
public interface ModelClearObserver {
    /**
     * Called when the Udders list has been cleared.
     * Implementations of this method should handle updating the related UI components.
     */
    void uddersCleared();
}
