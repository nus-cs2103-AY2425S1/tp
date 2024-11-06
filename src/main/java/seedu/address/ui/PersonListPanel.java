package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";

    // Save the instance of the scrollListener
    // So that it can be removed and re-added
    private ChangeListener<Number> scrollListener;
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private final DoubleProperty maxContentWidth = new SimpleDoubleProperty(0);

    @FXML
    private ListView<Person> personListView;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox placeholderContainer;

    @FXML
    private ImageView placeholderImageView;

    @FXML
    private Label placeholderLabel;

    @FXML
    private VBox personListContainer;

    @FXML
    private ScrollBar horizontalSb;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, String title) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell()); // map each item to a display cell
        titleLabel.setText(title);

        // Allow label to expand to fill all available width in parent container
        // Then, center it
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);

        // Initial visibility toggle
        togglePlaceholder(personList.isEmpty());

        // Subsequent visibility toggle handler
        personList.addListener((ListChangeListener<Person>) change -> {
            togglePlaceholder(personList.isEmpty());
        });

        // Wait for the personListView to be initialized
        Platform.runLater(() -> {
            assert(personListView != null);
            logger.info("Setting up auto scroll mechanism");
            setupAutoScroll(personList);

            // Initial highlight
            int currentSize = personListView.getItems().size();
            if (currentSize > 0) {
                personListView.getSelectionModel().select(0);
            }

            // Horizontal scroll mechanism
            registerMaxContentWidthListener();
            registerResizeListener();

            // Disable buttons for vertical scroll bar
            disableScrollbarButtons();
        });
    }


    private void registerMaxContentWidthListener() {
        maxContentWidth.addListener((observable, oldValue, newValue) -> {
            updateScrollTranslation();
        });
    }

    private void updateScrollTranslation() {
        // If the listener is already set, remove it first
        if (scrollListener != null) {
            horizontalSb.valueProperty().removeListener(scrollListener);
        }

        // Create the new listener
        scrollListener = (observable, oldValue, newValue) -> {
            // The new value of the scrollbar, which ranges from 0 to 100
            // Convert down to a 0 to 1 range
            double scrollValue = newValue.doubleValue() / 100.0;

            for (Node node : personListView.lookupAll(".list-cell")) {
                if (node instanceof ListCell<?>) {
                    ListCell<?> cell = (ListCell<?>) node;
                    ScrollPane contentContainer = (ScrollPane) cell.lookup("#contentContainer");
                    if (contentContainer != null) {
                        // Get the visible area
                        // If total area (represented by max content width) exceeds visible area
                        // Then perform the scroll mechanism
                        double containerWidth = contentContainer.getViewportBounds().getWidth();
                        if (maxContentWidth.get() > containerWidth) {
                            double translation = -scrollValue * (maxContentWidth.get() - containerWidth);
                            contentContainer.getContent().setTranslateX(translation);
                        } else {
                            // Scroll by an arbitrary and small amount
                            // This is honestly to just give the scrollbar a purpose when there is no overflow
                            contentContainer.getContent().setTranslateX(-scrollValue * 5.5);
                        }
                    }
                }
            }
        };

        // Add the new listener to the ScrollBar's valueProperty
        horizontalSb.valueProperty().addListener(scrollListener);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        // Cache each person card
        // Only update the person card if a person is created or changed
        private PersonListCard cachedPersonCard;

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                cachedPersonCard = null;
                return;
            }

            if (cachedPersonCard == null || !cachedPersonCard.person.equals(person)) {
                cachedPersonCard = new PersonListCard(person, getIndex() + 1, personListView);
                setGraphic(cachedPersonCard.getRoot());
            }

            // After every update, recalculate the max content width (includes non-visible area)
            // Based on the cells that are filled (ie: cells that have a person entry in it!)
            Platform.runLater(PersonListPanel.this::recalculateMaxContentWidthWithDelay);
        }
    }

    private void recalculateMaxContentWidthWithDelay() {
        // Use Timeline to delay the recalculation
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            recalculateMaxContentWidth();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void recalculateMaxContentWidth() {
        personListView.requestLayout();
        Platform.runLater(() -> {

            double currentMaxWidth = 0;

            for (Node node : personListView.lookupAll(".list-cell")) {
                if (node instanceof ListCell<?>) {
                    ListCell<?> cell = (ListCell<?>) node;
                    ScrollPane contentContainer = (ScrollPane) cell.lookup("#contentContainer");
                    if (contentContainer != null) {
                        double totalWidth = contentContainer.getContent().getBoundsInParent().getWidth();
                        currentMaxWidth = Math.max(currentMaxWidth, totalWidth);
                    }
                }
            }
            // Recalculate and set
            // This in turn triggers the registered listener on maxContentWidth
            maxContentWidth.set(currentMaxWidth);
        });
    }

    private void registerResizeListener() {
        // On resize, reset both the translation and the horizontal scroll bar value
        personListView.widthProperty().addListener((observable, oldValue, newValue) -> {
            for (Node node : personListView.lookupAll(".list-cell")) {
                if (node instanceof ListCell<?>) {
                    ListCell<?> cell = (ListCell<?>) node;
                    ScrollPane contentContainer = (ScrollPane) cell.lookup("#contentContainer");
                    if (contentContainer != null) {
                        contentContainer.getContent().setTranslateX(0);
                    }
                }
            }
            horizontalSb.setValue(0);
        });
    }

    private void setupAutoScroll(ObservableList<Person> personList) {
        personListView.getItems().addListener((ListChangeListener<Person>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    /*
                     * Initially (when the app first opens), when adding either a guest or vendor,
                     * three changes are picked up here.
                     * The change involving adding the new person.
                     * The change involving adding the entire current guests.
                     * The change involving adding the entire current vendors.
                     * Only auto-scroll for the first change.
                     * Hence, we use the below if check to enforce this.
                     */
                    if (change.getAddedSize() == 1) {
                        Platform.runLater(() -> {
                            personListView.scrollTo(change.getTo());
                            personListView.getSelectionModel().select(change.getTo() - 1);
                        });
                        break;
                    } else {
                        continue;
                    }
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> {
                        int removalIndex = change.getFrom();
                        if (removalIndex >= personList.size()) {
                            removalIndex = Math.max(0, personList.size() - 1);
                        }
                        personListView.scrollTo(removalIndex);
                        personListView.getSelectionModel().select(removalIndex);
                    });
                    break;
                }

                if (change.wasUpdated()) {
                    Platform.runLater(() -> {
                        int changeIndex = change.getFrom();
                        personListView.scrollTo(changeIndex);
                        personListView.getSelectionModel().select(changeIndex);
                    });
                    break;
                }
            }
        });
    }

    private void disableScrollbarButtons() {
        for (Node node : personListView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar scrollBar) {
                scrollBar.setDisable(true);
            }
        }
    }

    private void togglePlaceholder(boolean isEmpty) {
        if (isEmpty) {
            placeholderContainer.setVisible(true);
            placeholderContainer.setManaged(true);

            personListContainer.setVisible(false);
            personListContainer.setManaged(false);
        } else {
            placeholderContainer.setVisible(false);
            placeholderContainer.setManaged(false);

            personListContainer.setVisible(true);
            personListContainer.setManaged(true);
        }
    }
}
