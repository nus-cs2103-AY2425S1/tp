package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.vendor.Vendor;

/**
 * Panel containing the list of vendors.
 */
public class VendorDetailsPanel extends UiPart<Region> {
    private static final String FXML = "VendorDetailsPanel.fxml";
    public final Vendor vendor;
    private final Logger logger = LogsCenter.getLogger(VendorDetailsPanel.class);

    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label description;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public VendorDetailsPanel(Vendor vendor) {
        super(FXML);
        this.vendor = vendor;
        name.setText(vendor.getName().fullName);
        phone.setText(vendor.getPhone().value);
        description.setText(vendor.getDescription().value);

    }

}
