package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.contactrecord.ContactRecord;

/**
 * Panel containing the list of call history.
 */
public class HistoryListPanel extends UiPart<Region> {
    private static final String FXML = "HistoryListPanel.fxml";

    @FXML
    private ListView<ContactRecord> callHistoryView;
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
}
