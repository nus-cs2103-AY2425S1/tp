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
import javafx.scene.text.Text;
import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;

/**
 * Panel containing the list of vendors.
 */
public class VendorDetailsPanel extends UiPart<Region> {
    private static final String FXML = "VendorDetailsPanel.fxml";
    private Vendor vendor;
    private final Logger logger = LogsCenter.getLogger(VendorDetailsPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Text description;
    @FXML
    private Label noVendorMsg;
    @FXML
    private GridPane detailsHolder;
    @FXML
    private StackPane detailsChildrenPlaceholder;
    @FXML
    private FlowPane tags;

    private ObservableList<Event> assignedEvents;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public VendorDetailsPanel(ObservableObjectValue<Vendor> vendor, ObservableSet<Pair<Vendor, Event>> associations) {
        super(FXML);

        assignedEvents = FXCollections.observableArrayList();

        vendor.addListener((observable, oldValue, newValue) -> {
            showVendorDetails();
            setVendor(newValue);

            updateAssignedEvents(associations);
        });

        associations.addListener((SetChangeListener.Change<? extends Pair<Vendor, Event>> change) -> {
            updateAssignedEvents(associations);
        });

        EventListPanel eventListPanel = new EventListPanel(assignedEvents, "Assigned Events");
        detailsChildrenPlaceholder.getChildren().add(eventListPanel.getRoot());
    }

    private void setVendor(Vendor vendor) {
        this.vendor = vendor;
        name.setText(vendor.getName().fullName);
        phone.setText(vendor.getPhone().value);
        description.setText(vendor.getDescription().value);

        // Reset tags when updating vendor
        tags.getChildren().clear();
        vendor.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void showVendorDetails() {
        detailsHolder.setVisible(true);
        noVendorMsg.setVisible(false);
    }

    private void updateAssignedEvents(ObservableSet<Pair<Vendor, Event>> associations) {
        assignedEvents.clear();
        assignedEvents.addAll(
                associations.stream().filter(pair -> pair.getKey().equals(vendor)).map(Pair::getValue).toList());
    }

}
