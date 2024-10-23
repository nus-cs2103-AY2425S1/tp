package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.association.Association;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Panel containing the vendor details and their assigned events.
 */
public class VendorDetailsPanel extends UiPart<Region> {
    private static final String FXML = "VendorDetailsPanel.fxml";
    private Vendor vendor;
    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(VendorDetailsPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label description;
    @FXML
    private Label noVendorMsg;
    @FXML
    private GridPane detailsHolder;
    @FXML
    private StackPane detailsChildrenPlaceholder;

    private ObservableList<Event> assignedEvents;

    /**
     * Creates a {@code VendorDetailsPanel} with the given {@code ObservableObjectValue<Vendor>} and {@code Logic}.
     */
    public VendorDetailsPanel(ObservableObjectValue<Vendor> vendor, Logic logic) {
        super(FXML);
        this.logic = logic;

        assignedEvents = FXCollections.observableArrayList();

        vendor.addListener((observable, oldValue, newValue) -> {
            showVendorDetails();
            setVendor(newValue);

            // Update assignedEvents when the vendor changes
            updateAssignedEvents();
        });

        // Listen for changes in associations and update assigned events accordingly
        logic.addAssociationChangeListener((ListChangeListener<? super Association>) change -> {
            updateAssignedEvents();
        });

        EventListPanel eventListPanel = new EventListPanel(assignedEvents, "Assigned Events");
        detailsChildrenPlaceholder.getChildren().add(eventListPanel.getRoot());
    }

    private void setVendor(Vendor vendor) {
        this.vendor = vendor;
        name.setText(vendor.getName().fullName);
        phone.setText(vendor.getPhone().value);
        description.setText(vendor.getDescription().value);
    }

    private void showVendorDetails() {
        detailsHolder.setVisible(true);
        noVendorMsg.setVisible(false);
    }

    /**
     * Updates the assigned events based on the current vendor.
     */
    private void updateAssignedEvents() {
        if (vendor == null) {
            assignedEvents.clear();
        } else {
            assignedEvents.setAll(logic.getAssociatedEvents(vendor));
        }
    }
}
