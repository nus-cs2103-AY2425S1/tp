package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of call history.
 */
public class HistoryListPanel extends UiPart<Region> {
    private static final String FXML = "HistoryListPanel.fxml";

    @FXML
    private ListView<ContactRecord> callHistoryView;
    @FXML
    private VBox profileView;
    private ObservableList<ContactRecord> items;

    /**
     * Creates a {@code HistoryListPanel}.
     */
    public HistoryListPanel() {
        super(FXML);
        items = FXCollections.observableArrayList();
        callHistoryView.setItems(items);
        callHistoryView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ContactRecord contactRecord, boolean empty) {
                super.updateItem(contactRecord, empty);
                if (empty || contactRecord == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    int index = getIndex() + 1;
                    HistoryCard card = new HistoryCard(contactRecord, index);
                    setGraphic(card.getRoot());
                }
            }
        });
    }

    /**
     * Initializes the call history panel with the given {@code ContactRecordList}.
     */
    public void initializeCallHistory(ObservableList<ContactRecord> history) {
        items.clear();
        items.addAll(FXCollections.observableArrayList(history));
    }

    /**
     * Sets the profile view to display the profile of the given {@code Person}.
     */
    public void setProfile(Person person) {
        profileView.getChildren().clear();
        ProfileView newProfileView = new ProfileView(person);
        profileView.getChildren().add(newProfileView.getRoot());
    }
}
