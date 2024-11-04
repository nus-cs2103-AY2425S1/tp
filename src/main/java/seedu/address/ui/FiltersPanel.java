package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;


/**
 * Panel containing the list of current filters.
 */
public class FiltersPanel extends UiPart<Region> {
    private static final String FXML = "FiltersPanel.fxml";
    @FXML
    private FlowPane filterListFlowPane;

    /**
     *
     */
    public FiltersPanel(ObservableList<Tag> tagFilters, ObservableList<RsvpStatus> statusFilters) {
        super(FXML);
        initialiseLabel();
        updateFiltersDisplay(tagFilters, statusFilters);
        setUpListener(tagFilters, statusFilters);
    }

    private void initialiseLabel() {
        Label filtersTitleLabel = new Label("Current Filters: ");
        filtersTitleLabel.getStyleClass().add("tags-title-label");
        filterListFlowPane.getChildren().add(filtersTitleLabel);
    }

    private void updateFiltersDisplay(ObservableList<Tag> tagFilters, ObservableList<RsvpStatus> statusFilters) {
        filterListFlowPane.getChildren().clear();
        initialiseLabel();

        tagFilters.stream()
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.getTagName());
                    tagLabel.getStyleClass().add("tag-label");

                    Color colour = TagColourManager.getColourForTag(tag.getTagName());
                    tagLabel.setStyle(String.format("-fx-background-color: #%02x%02x%02x;",
                            (int) (colour.getRed() * 255),
                            (int) (colour.getGreen() * 255),
                            (int) (colour.getBlue() * 255)));

                    filterListFlowPane.getChildren().add(tagLabel);
                });

        statusFilters.stream()
                .forEach(status -> {
                    Label statusLabel = new Label(status.getStatus());

                    statusLabel.setStyle(StatusColourManager.getStatusStyle(status));
                    statusLabel.getStyleClass().add("rsvp-filter-label");

                    filterListFlowPane.getChildren().add(statusLabel);
                });


        filterListFlowPane.requestLayout();

    }

    private void setUpListener(ObservableList<Tag> tags, ObservableList<RsvpStatus> statuses) {
        tags.addListener((ListChangeListener<Tag>) c -> {
            updateFiltersDisplay(tags, statuses);
        });

        statuses.addListener((ListChangeListener<RsvpStatus>) c -> {
            updateFiltersDisplay(tags, statuses);
        });
    }
}
