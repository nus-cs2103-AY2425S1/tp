package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Labeled;

/**
 * Handle for the status bar UI component in the application. This class provides methods to interact with and test the
 * state of the status bar, such as tracking changes in the save location status.
 */
public class StatusBarHandle extends NodeHandle<Node> {
    /** CSS selector for the status bar placeholder node. */
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";
    /** CSS selector for the save location status node. */
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";
    /** Node displaying the current save location. */
    private final Labeled saveLocationNode;
    /** The last remembered save location status, used to detect changes. */
    private String lastRememberedSaveLocation;

    /**
     * Creates a {@code StatusBarHandle} for the given status bar footer node.
     *
     * @param statusBarNode The root node of the status bar.
     */
    public StatusBarHandle(Node statusBarNode) {
        super(statusBarNode);
        saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
    }

    /**
     * Returns the current save location displayed on the status bar.
     *
     * @return A string representing the save location.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Stores the current save location status to track changes. This remembered value can be used to compare with
     * future values.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Checks if the save location has changed since the last remembered value.
     *
     * @return {@code true} if the save location has changed, {@code false} otherwise.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }
}
