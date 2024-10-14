package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class BlankDetailsPanel extends UiPart<Region> {

    private static final String FXML = "BlankDetailsPanel.fxml";


    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to display.
     */
    public BlankDetailsPanel() {
        super(FXML);
    }
}
