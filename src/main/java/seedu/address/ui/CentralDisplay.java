package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    private Logic logic;
    private PersonListPanel personListPanel;
    private SessionLogPanel sessionLogPanel;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane sessionLogPanelPlaceholder;

    public CentralDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        // Initialize the panels
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());

        // Add them to the placeholders
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Gets the personListPanel
     */
    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public void showPersonListPanel() {
        personListPanelPlaceholder.setVisible(true);
        sessionLogPanelPlaceholder.setVisible(false);
    }

    public void showSessionLogPanel() {
        personListPanelPlaceholder.setVisible(false);
        sessionLogPanelPlaceholder.setVisible(true);
    }

    public void handleLog(int personIndex) {
        System.out.println(personIndex);
        sessionLogPanel = new SessionLogPanel(logic.getSessionLog(personIndex));

        sessionLogPanelPlaceholder.getChildren().add(sessionLogPanel.getRoot());
        showSessionLogPanel();
    }
}