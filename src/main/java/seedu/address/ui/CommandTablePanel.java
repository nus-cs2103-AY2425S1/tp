package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of command help.
 */
public class CommandTablePanel extends UiPart<Region> {
    private static final String FXML = "CommandTablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandTablePanel.class);

    
    /**
     * Creates a {@code CommandTablePanel} with the given {@code ObservableList}.
     */
    public CommandTablePanel() {
        super(FXML);
        initializeTable();
    }

    /**
     * Fills up the table with the commands.
     */
    private void initializeTable() {
        logger.fine("Showing a few command format of the application.");
    }

}
