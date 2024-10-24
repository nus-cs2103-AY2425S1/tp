package keycontacts.ui;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The calendar view
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "Calendar.fxml";

    private static final int NUM_ROWS = 7;
    private static final int NUM_MINUTES = 24 * 60;

    @SuppressWarnings("unchecked")
    private final List<TimeBlock>[] timeBlocks = (ArrayList<TimeBlock>[]) new ArrayList[NUM_ROWS];

    private final List<HBox> dayHBoxes = new ArrayList<>();

    @FXML
    private GridPane grid;

    @FXML
    private HBox mon;

    @FXML
    private HBox tue;

    @FXML
    private HBox wed;

    @FXML
    private HBox thu;

    @FXML
    private HBox fri;

    @FXML
    private HBox sat;

    @FXML
    private HBox sun;

    /**
     * Default constructor to initialize the {@code CalendarView}
     */
    public CalendarView() {
        super(FXML);
        addHBoxes();
        generateGrid();
        update();

    }

    private void addHBoxes() {
        dayHBoxes.add(mon);
        dayHBoxes.add(tue);
        dayHBoxes.add(wed);
        dayHBoxes.add(thu);
        dayHBoxes.add(fri);
        dayHBoxes.add(sat);
        dayHBoxes.add(sun);
    }

    private void generateGrid() {
        for (int i = 0; i < NUM_ROWS; i++) {
            timeBlocks[i] = new ArrayList<>();
        }
    }

    private void createBlock(int day, LocalTime startTime, LocalTime endTime, Color color) {
        assert endTime.isAfter(startTime);

        TimeBlock timeBlock = new TimeBlock(startTime, endTime);

        List<TimeBlock> currentDayBlocks = timeBlocks[day];

        TimeBlock previousTimeBlock = new TimeBlock(0, 0);
        for (int i = 0; i <= currentDayBlocks.size(); i++) {
            if (i == currentDayBlocks.size()) {
                // attach
                dayHBoxes.get(day).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayHBoxes.get(day).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color));
                currentDayBlocks.add(i, timeBlock);

                return;
            }

            TimeBlock currentTimeBlock = currentDayBlocks.get(i);
            assert !currentTimeBlock.isIntersecting(timeBlock);
            if (currentTimeBlock.isAfter(timeBlock)) {
                // attach
                dayHBoxes.get(day).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayHBoxes.get(day).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color));
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
                Color.DARKBLUE);
        createBlock(2, LocalTime.of(6, 0, 0), LocalTime.of(8, 0, 0),
                Color.DARKRED);
        createBlock(0, LocalTime.of(3, 45, 0), LocalTime.of(5, 45, 0),
                Color.SLATEBLUE);
        createBlock(0, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
        createBlock(1, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
        createBlock(3, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
        createBlock(4, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
        createBlock(5, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
        createBlock(6, LocalTime.of(2, 0, 0), LocalTime.of(3, 30, 0),
                Color.SLATEGRAY);
    }

    class TimeBlock {

        private final int startTimeMinutes;
        private final int endTimeMinutes;

        private Rectangle block;
        private Rectangle padding;

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
            this.padding.widthProperty().bind(grid.widthProperty().multiply((double) paddingWidth / NUM_MINUTES));
            this.padding.heightProperty().bind(grid.heightProperty().divide(NUM_ROWS));
        }

        /**
         * Updates the binding to the new block width
         */
        private void updateBlockBinding() {
            this.block.widthProperty().bind(grid.widthProperty().multiply((double) blockWidth / NUM_MINUTES));
            this.block.heightProperty().bind(grid.heightProperty().divide(NUM_ROWS));
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
            this.padding = new Rectangle();
            this.padding.setStyle("-fx-opacity: 0");
            updatePaddingBinding();
            return this.padding;
        }

        /**
         * Returns a block rectangle based on the duration of this {@code TimeBlock} with the provided color
         */
        public Node createBlock(Color color) {
            this.blockWidth = getDuration();
            this.block = new Rectangle();
            this.block.setFill(color);
            updateBlockBinding();
            return this.block;
        }

    }
}


