package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    public RightPanel(DetailPanel detailPanel, ResultDisplay resultDisplay, CommandBox commandBox) {
        super(FXML);
        this.detailPanel = detailPanel;
        this.resultDisplay = resultDisplay;
        this.commandBox = commandBox;

        fillPlaceholders();
    }

    private void fillPlaceholders() {
        detailPanelPlaceholder.getChildren().add(detailPanel.getRoot());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }


}
