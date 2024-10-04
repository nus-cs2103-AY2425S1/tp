package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Represents the right panel of the application's main interface.
 * This class extends {@code UiPart<VBox>} and is responsible for managing and displaying the detail panel, result display,
 * and command box in the right section of the main window.
 *
 * <p>The layout for this panel is defined in 'RightPanel.fxml', which includes placeholders for each of the subcomponents.
 * This class fills these placeholders with the respective UI components at runtime.
 */
public class RightPanel extends UiPart<VBox> {
    private static final String FXML = "RightPanel.fxml";
    @FXML
    private StackPane detailPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane commandBoxPlaceholder;

    private DetailPanel detailPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;

    /**
     * Constructs a new RightPanel with the specified detail panel, result display, and command box.
     * Initializes the components and fills the respective placeholders with these components.
     *
     * @param detailPanel   The {@code DetailPanel} to display detailed information.
     * @param resultDisplay The {@code ResultDisplay} to show feedback messages or results.
     * @param commandBox    The {@code CommandBox} for command input.
     */
    public RightPanel(DetailPanel detailPanel, ResultDisplay resultDisplay, CommandBox commandBox) {
        super(FXML);
        this.detailPanel = detailPanel;
        this.resultDisplay = resultDisplay;
        this.commandBox = commandBox;

        fillPlaceholders();
    }

    /**
     * Fills the placeholders in the FXML layout with the appropriate UI components.
     * This method adds each component to its designated placeholder, allowing for dynamic updates and display within the interface.
     */
    private void fillPlaceholders() {
        detailPanelPlaceholder.getChildren().add(detailPanel.getRoot());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }


}
