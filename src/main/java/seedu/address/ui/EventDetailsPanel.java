package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
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
    @FXML
    private FlowPane tags;

    private ObservableList<Vendor> assignedVendors;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public EventDetailsPanel(ObservableObjectValue<Event> event, ObservableSet<Pair<Vendor, Event>> associations) {
        super(FXML);

        assignedVendors = FXCollections.observableArrayList();

        event.addListener((observable, oldValue, newValue) -> {
            showEventDetails();
            setEvent(newValue);

            // Update assignedVendors when event is changed
            updateAssignedVendors(associations);
        });

        associations.addListener((SetChangeListener.Change<? extends Pair<Vendor, Event>> change) -> {
            updateAssignedVendors(associations);
        });

        VendorListPanel vendorListPanel = new VendorListPanel(assignedVendors, "Assigned Vendors");
        detailsChildrenPlaceholder.getChildren().add(vendorListPanel.getRoot());

    }

    private void setEvent(Event event) {
        this.event = event;
        name.setText(event.getName().fullName);
        date.setText(event.getDate().toString());
        event.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void showEventDetails() {
        detailsHolder.setVisible(true);
        noEventMsg.setVisible(false);
    }

    private void updateAssignedVendors(ObservableSet<Pair<Vendor, Event>> associations) {
        assignedVendors.clear();
        assignedVendors
                .addAll(associations.stream().filter(pair -> pair.getValue().equals(event)).map(Pair::getKey).toList());
    }

}
