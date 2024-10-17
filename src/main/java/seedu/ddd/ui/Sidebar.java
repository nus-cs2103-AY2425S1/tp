package seedu.ddd.ui;

import javafx.scene.layout.HBox;
/**
 * An UI component that displays information of a {@code Person}.
 */
public class Sidebar extends UiPart<HBox> {

    private static final String FXML = "Sidebar.fxml";

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public Sidebar() {
        super(FXML);
    }
}
