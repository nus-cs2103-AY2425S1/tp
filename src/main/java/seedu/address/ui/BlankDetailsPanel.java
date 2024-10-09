package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class BlankDetailsPanel extends UiPart<Region> {

    private static final String FXML = "BlankDetailsPanel.fxml";


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public BlankDetailsPanel() {
        super(FXML);
    }
}
