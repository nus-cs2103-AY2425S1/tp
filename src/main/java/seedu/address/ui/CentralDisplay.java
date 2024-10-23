package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

/**
 * The Central Display. Provides the functionality to change to different panels
 * based on commands.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";

    private Logic logic;
    private PersonListPanel personListPanel;
    private SessionLogPanel sessionLogPanel;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane sessionLogPanelPlaceholder;

    /**
     * Creates a {@code CentralDisplay} with the given {@code Stage} and {@code Logic}
     */
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

    /**
     * Shows the person list panel while hiding the session log panel.
     */
    public void showPersonListPanel() {
        personListPanelPlaceholder.setVisible(true);
        sessionLogPanelPlaceholder.setVisible(false);
    }

    /**
     * Shows the session log panel while hiding the person list panel.
     */
    public void showSessionLogPanel() {
        personListPanelPlaceholder.setVisible(false);
        sessionLogPanelPlaceholder.setVisible(true);
    }


    /**
     * Injects the logs of the current person identified with their index in the address book
     * to the sessionLogPanel.
     */
    public void handleLog(int personIndex) {
        requireNonNull(personIndex);
        assert personIndex > -1 : "Person index retrieved is less than 0";

        System.out.println(personIndex);
        sessionLogPanel = new SessionLogPanel(logic.getSessionLog(personIndex));

        sessionLogPanelPlaceholder.getChildren().add(sessionLogPanel.getRoot());
        showSessionLogPanel();
    }
}
