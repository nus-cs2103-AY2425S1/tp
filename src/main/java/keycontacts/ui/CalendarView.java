package keycontacts.ui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The calendar view
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";

    private static final int NUM_ROWS = 7;
    private static final int NUM_MINUTES = 24 * 60;

    @SuppressWarnings("unchecked")
    private final List<TimeBlock>[] timeBlocks = (ArrayList<TimeBlock>[]) new ArrayList[NUM_ROWS];

    private final List<VBox> dayVBoxes = new ArrayList<>();

    @FXML
    private GridPane grid;

    @FXML
    private VBox mon;

    @FXML
    private VBox tue;

    @FXML
    private VBox wed;

    @FXML
    private VBox thu;

    @FXML
    private VBox fri;

    @FXML
    private VBox sat;

    @FXML
    private VBox sun;

    @FXML
    private Label calendarHeader;

    /**
     * Default constructor to initialize the {@code CalendarView}
     */
    public CalendarView() {
        super(FXML);
        addHBoxes();
        generateGrid();

        // TODO remove
        update();
    }

    private void addHBoxes() {
        dayVBoxes.add(mon);
        dayVBoxes.add(tue);
        dayVBoxes.add(wed);
        dayVBoxes.add(thu);
        dayVBoxes.add(fri);
        dayVBoxes.add(sat);
        dayVBoxes.add(sun);
    }

    private void generateGrid() {
        for (int i = 0; i < NUM_ROWS; i++) {
            timeBlocks[i] = new ArrayList<>();
        }
    }

    private void createBlock(int day, LocalTime startTime, LocalTime endTime, Color color, String name) {
        assert endTime.isAfter(startTime);

        TimeBlock timeBlock = new TimeBlock(startTime, endTime);

        List<TimeBlock> currentDayBlocks = timeBlocks[day];

        TimeBlock previousTimeBlock = new TimeBlock(0, 0);
        for (int i = 0; i <= currentDayBlocks.size(); i++) {
            if (i == currentDayBlocks.size()) {
                // attach
                dayVBoxes.get(day).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayVBoxes.get(day).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color, name));
                currentDayBlocks.add(i, timeBlock);

                return;
            }

            TimeBlock currentTimeBlock = currentDayBlocks.get(i);
            assert !currentTimeBlock.isIntersecting(timeBlock);
            if (currentTimeBlock.isAfter(timeBlock)) {
                // attach
                dayVBoxes.get(day).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayVBoxes.get(day).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color, name));
                currentDayBlocks.add(i, timeBlock);

                // fix padding
                currentTimeBlock.subtractPaddingWidth(timeBlock.getTotalWidth());

                return;
            }
            previousTimeBlock = currentTimeBlock;
        }
    }

    /**
     * Updates the calendar view with pre-inputted data
     * TODO change this method to take some data as input to update the calendar
     */
    public void update() {
        createBlock(2, LocalTime.of(1, 0, 0), LocalTime.of(3, 0, 0),
                Color.DARKBLUE, "Poppy Ulys");
        createBlock(2, LocalTime.of(6, 0, 0), LocalTime.of(8, 0, 0),
                Color.DARKRED, "Ben Atis");
        createBlock(0, LocalTime.of(3, 45, 0), LocalTime.of(5, 45, 0),
                Color.SLATEBLUE, "Lye Pistro");
        createBlock(0, LocalTime.of(7, 0, 0), LocalTime.of(8, 30, 0),
                Color.MEDIUMSLATEBLUE, "Diene Slich");
        createBlock(1, LocalTime.of(5, 0, 0), LocalTime.of(6, 30, 0),
                Color.SLATEGRAY, "Vin Polaris");
        createBlock(3, LocalTime.of(4, 0, 0), LocalTime.of(5, 30, 0),
                Color.SLATEGRAY, "Ches Lia");
        createBlock(4, LocalTime.of(12, 0, 0), LocalTime.of(23, 59, 0),
                Color.DARKGOLDENROD, "Easen Lee");
        createBlock(5, LocalTime.of(2, 0, 0), LocalTime.of(3, 0, 0),
                Color.SLATEGRAY, "Britz Yela");
        createBlock(6, LocalTime.of(0, 0, 0), LocalTime.of(23, 59, 0),
                Color.SLATEBLUE, "Feri Mona");
    }

    class TimeBlock {

        private final int startTimeMinutes;
        private final int endTimeMinutes;

        private Pane block;
        private Pane padding;

        private int paddingWidth;
        private int blockWidth;

        public TimeBlock(LocalTime startTime, LocalTime endTime) {
            this(startTime.getHour() * 60 + startTime.getMinute(), endTime.getHour() * 60 + endTime.getMinute());
        }

        private TimeBlock(int startTimeMinutes, int endTimeMinutes) {
            this.startTimeMinutes = startTimeMinutes;
            this.endTimeMinutes = endTimeMinutes;
        }

        /**
         * Returns the duration of the time block
         */
        public int getDuration() {
            return endTimeMinutes - startTimeMinutes;
        }

        /**
         * Returns the distance between this time block and the other
         */
        public int getDistance(TimeBlock other) {
            if (isIntersecting(other)) {
                return 0;
            }

            if (isAfter(other)) {
                return startTimeMinutes - other.endTimeMinutes;
            }

            return other.startTimeMinutes - endTimeMinutes;
        }

        /**
         * Returns true if the given time block starts at or after this time block's end
         */
        public boolean isAfter(TimeBlock other) {
            return startTimeMinutes >= other.startTimeMinutes;
        }

        /**
         * Returns true if the given time block intersects with this time block
         */
        public boolean isIntersecting(TimeBlock other) {
            return startTimeMinutes <= other.endTimeMinutes && other.startTimeMinutes <= endTimeMinutes;
        }

        /**
         * Subtracts {@code width} from the padding width
         */
        public void subtractPaddingWidth(int width) {
            this.paddingWidth -= width;
            updatePaddingBinding();
        }

        /**
         * Updates the binding to the new padding width
         */
        private void updatePaddingBinding() {
            this.padding.prefHeightProperty().bind(grid.heightProperty().multiply((double) paddingWidth / NUM_MINUTES));
            this.padding.prefWidthProperty().bind(grid.widthProperty().divide(NUM_ROWS));
        }

        /**
         * Updates the binding to the new block width
         */
        private void updateBlockBinding() {
            this.block.prefHeightProperty().bind(grid.heightProperty().multiply((double) blockWidth / NUM_MINUTES));
            this.block.prefWidthProperty().bind(grid.widthProperty().divide(NUM_ROWS));
        }

        /**
         * Returns the total width this block takes, including padding
         */
        public int getTotalWidth() {
            return paddingWidth + blockWidth;
        }

        /**
         * Returns a padding rectangle based on distance to the {@code previousTimeBlock}
         */
        public Node createPadding(TimeBlock previousTimeBlock) {
            this.paddingWidth = getDistance(previousTimeBlock);
            this.padding = new Pane();
            this.padding.setStyle("-fx-opacity: 0");
            updatePaddingBinding();
            return this.padding;
        }

        /**
         * Returns a block rectangle based on the duration of this {@code TimeBlock} with the provided color
         */
        public Node createBlock(Color color, String name) {
            this.blockWidth = getDuration();
            this.block = new Pane();
            this.block.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0))));

            Label label = new Label(name);
            label.getStyleClass().add("label-calendar-block");
            label.prefWidthProperty().bind(block.widthProperty());
            label.setAlignment(Pos.CENTER);
            this.block.getChildren().add(label);

            updateBlockBinding();
            return this.block;
        }

    }
}


