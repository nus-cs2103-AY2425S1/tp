package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import seedu.address.model.contactdate.ContactDate;
import seedu.address.model.contactdate.ContactDateList;

/**
 * Panel containing the list of call history.
 */
public class CallHistoryPanel extends UiPart<VBox> {
    private static final String FXML = "CallHistoryPanel.fxml";

    @FXML
    private ListView<ContactDate> callHistoryView;
    private ObservableList<ContactDate> items;

    /**
     * Creates a {@code CallHistoryPanel}.
     */
    public CallHistoryPanel() {
        super(FXML);
        items = FXCollections.observableArrayList();
        callHistoryView.setItems(items);
        callHistoryView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ContactDate contactDate, boolean empty) {
                super.updateItem(contactDate, empty);
                if (empty || contactDate == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    int index = getIndex() + 1;
                    CallHistoryCard card = new CallHistoryCard(contactDate, index);
                    setGraphic(card.getRoot());
                }
            }
        });
    }

    /**
     * Initializes the call history panel with the given {@code ContactDateList}.
     */
    public void initializeCallHistory(ContactDateList history) {
        System.out.println("Initializing call history...");
        items.clear();
        items.addAll(FXCollections.observableArrayList(history));

        callHistoryView.refresh();
    }
}
