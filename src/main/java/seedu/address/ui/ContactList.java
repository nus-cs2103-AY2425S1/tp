package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

public class ContactList extends UiPart<Region> {

    private static final String FXML = "ContactList.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;

    // FXML componenets
    @FXML
    private StackPane personListPanelPlaceholder;

    public ContactList(Logic logic) {
        super(FXML);

        this.logic = logic;

        this.initaliseComponents();
    }

    private void initaliseComponents() {

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }
}
