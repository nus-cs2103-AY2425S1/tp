package seedu.eventtory.ui;

/**
 * Represents the current state of the UI.
 */
public enum UiState {
    DEFAULT, VENDOR_LIST, VENDOR_DETAILS, EVENT_LIST, EVENT_DETAILS;

    public boolean isState(UiState state) {
        return this == state;
    }

    public boolean isDefault() {
        return isState(DEFAULT);
    }

    public boolean isVendorList() {
        return isState(VENDOR_LIST);
    }

    public boolean isVendorDetails() {
        return isState(VENDOR_DETAILS);
    }

    public boolean isEventList() {
        return isState(EVENT_LIST);
    }

    public boolean isEventDetails() {
        return isState(EVENT_DETAILS);
    }
}
