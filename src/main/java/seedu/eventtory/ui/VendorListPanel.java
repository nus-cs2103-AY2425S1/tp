package seedu.eventtory.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.eventtory.commons.core.LogsCenter;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Panel containing the list of vendors.
 */
public class VendorListPanel extends UiPart<Region> {
    private static final String FXML = "VendorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(VendorListPanel.class);

    @FXML
    private Label header;
    @FXML
    private ListView<Vendor> vendorListView;

    /**
     * Creates a {@code VendorListPanel} with the given {@code ObservableList}.
     */
    public VendorListPanel(ObservableList<Vendor> vendorList, String headerText,
        ObservableIntegerValue indexOffset) {
        super(FXML);
        vendorListView.setItems(vendorList);
        vendorListView.setCellFactory(listView -> new VendorListViewCell(indexOffset));
        header.setText(headerText);

        // Required to fix the issue of last item not being shown when scrolled to the end
        vendorListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                vendorListView.scrollTo(vendorListView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    /**
     * Alternate constructor for {@code VendorListPanel} with a default index offset of 1.
     */
    public VendorListPanel(ObservableList<Vendor> vendorList, String headerText) {
        this(vendorList, headerText, new SimpleIntegerProperty(1));
    }

    /**
     * Set the header of the vendor list panel.
     */
    public void setHeader(String headerText) {
        header.setText(headerText);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Vendor} using a {@code VendorCard}.
     */
    class VendorListViewCell extends ListCell<Vendor> {
        private final ObservableIntegerValue indexOffset;

        public VendorListViewCell(ObservableIntegerValue indexOffset) {
            super();
            this.indexOffset = indexOffset;
        }

        @Override
        protected void updateItem(Vendor vendor, boolean empty) {
            super.updateItem(vendor, empty);

            if (empty || vendor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VendorCard(vendor, getIndex() + indexOffset.get()).getRoot());
            }
        }
    }

}
