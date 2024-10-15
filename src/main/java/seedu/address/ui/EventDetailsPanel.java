package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Panel containing the list of vendors.
 */
public class EventDetailsPanel extends UiPart<Region> {
    private static final String FXML = "EventDetailsPanel.fxml";
    private Event event;
    private final Logger logger = LogsCenter.getLogger(VendorDetailsPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label noEventMsg;
    @FXML
    private GridPane detailsHolder;
    @FXML
    private StackPane detailsChildrenPlaceholder;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public EventDetailsPanel(ObservableObjectValue<Event> event, ObservableList<Vendor> assignedVendors) {
        super(FXML);

        event.addListener((observable, oldValue, newValue) -> {
            showEventDetails();
            setEvent(newValue);
        });

        VendorListPanel vendorListPanel = new VendorListPanel(assignedVendors);
        detailsChildrenPlaceholder.getChildren().add(vendorListPanel.getRoot());

    }

    private void setEvent(Event event) {
        this.event = event;
        name.setText(event.getName().fullName);
        date.setText(event.getDate().toString());
    }

    private void showEventDetails() {
        detailsHolder.setVisible(true);
        noEventMsg.setVisible(false);
    }

}
