package keycontacts.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;
import keycontacts.model.student.Student;

/**
 * The calendar view
 */
public class CalendarView extends UiPart<Region> {
    private static final String FXML = "CalendarView.fxml";

    private static final int NUM_ROWS = 7;
    private static final int NUM_MINUTES = 24 * 60;

    private static final List<Color> CALENDAR_COLORS = List.of(
            Color.DARKBLUE,
            Color.DARKRED,
            Color.SLATEBLUE,
            Color.SLATEGREY,
            Color.MEDIUMSLATEBLUE,
            Color.GOLDENROD,
            Color.OLIVEDRAB,
            Color.TEAL,
            Color.CORNFLOWERBLUE,
            Color.INDIANRED,
            Color.STEELBLUE,
            Color.ORANGERED
    );

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

    private final ObservableList<Student> students;
    private Date startDate;
    private Date endDate;

    /**
     * Default constructor to initialize the {@code CalendarView}
     */
    public CalendarView(ObservableList<Student> students) {
        super(FXML);
        addHBoxes();
        generateGrid();

        this.students = students;
        // view schedule for today by default
        update(new Date(LocalDate.now()));

        // when student data changes, update calendar based on student data
        students.addListener((ListChangeListener<Student>) unused -> update(startDate));
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

    private void createBlock(Day day, Time startTime, Time endTime, Color color, String name) {
        assert endTime.isAfter(startTime);

        TimeBlock timeBlock = new TimeBlock(startTime, endTime);
        int dayValue = day.value.getValue() - 1;

        List<TimeBlock> currentDayBlocks = timeBlocks[dayValue];

        TimeBlock previousTimeBlock = new TimeBlock(0, 0);
        for (int i = 0; i <= currentDayBlocks.size(); i++) {
            if (i == currentDayBlocks.size()) {
                // attach
                dayVBoxes.get(dayValue).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayVBoxes.get(dayValue).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color, name));
                currentDayBlocks.add(i, timeBlock);

                return;
            }

            TimeBlock currentTimeBlock = currentDayBlocks.get(i);
            assert !currentTimeBlock.isIntersecting(timeBlock);
            if (currentTimeBlock.isAfter(timeBlock)) {
                // attach
                dayVBoxes.get(dayValue).getChildren().add(i * 2,
                        timeBlock.createPadding(previousTimeBlock));
                dayVBoxes.get(dayValue).getChildren().add(i * 2 + 1,
                        timeBlock.createBlock(color, name));
                currentDayBlocks.add(i, timeBlock);

                // fix padding
                currentTimeBlock.subtractPaddingWidth(timeBlock.getTotalWidth());

                return;
            }
            previousTimeBlock = currentTimeBlock;
        }
    }

    private void clearBlocks() {
        for (int i = 0; i < NUM_ROWS; i++) {
            dayVBoxes.get(i).getChildren().clear();
            timeBlocks[i].clear();
        }
    }

    /**
     * Updates the calendar view for a new date.
     * Generates the schedule for the week of the date based on current student data.
     */
    public void update(Date date) {
        startDate = date.getFirstDayOfWeek();
        endDate = date.getLastDayOfWeek();

        // update header
        calendarHeader.setText(startDate.toDisplay() + " - " + endDate.toDisplay());

        clearBlocks();

        ArrayList<Student> uniqueGroupStudents = new ArrayList<>();
        for (Student student : students) {
            boolean groupPresent = false;
            for (Student uniqueGroupStudent: uniqueGroupStudents) {
                if (student.getGroup().isSameGroup(uniqueGroupStudent.getGroup())) {
                    groupPresent = true;
                }
            }

            if (!groupPresent) {
                uniqueGroupStudents.add(student);
            }
        }

        for (int i = 0; i < uniqueGroupStudents.size(); i++) {
            Color groupColor = CALENDAR_COLORS.get(i % CALENDAR_COLORS.size());

            Student student = uniqueGroupStudents.get(i);
            String name = student.getGroup().isNoGroup() ? student.getName().fullName : student.getGroup().groupName;

            RegularLesson regularLesson = student.getRegularLesson();
            if (regularLesson != null) {
                // only show the regular lesson if it is not cancelled
                Date regularLessonDate = regularLesson.getDateForWeek(startDate);
                if (!student.getCancelledLessons().contains(new CancelledLesson(regularLessonDate))) {
                    createBlock(regularLesson.getLessonDay(), regularLesson.getStartTime(), regularLesson.getEndTime(),
                            groupColor, name);
                }
            }


            for (MakeupLesson makeupLesson: student.getMakeupLessons()) {
                if (makeupLesson.getLessonDate().value.isAfter(endDate.value)
                        || makeupLesson.getLessonDate().value.isBefore(startDate.value)) {
                    continue;
                }

                createBlock(makeupLesson.getLessonDate().convertToDay(), makeupLesson.getStartTime(),
                        makeupLesson.getEndTime(), groupColor, name);
            }
        }
    }

    class TimeBlock {

        private final int startTimeMinutes;
        private final int endTimeMinutes;

        private Pane block;
        private Pane padding;

        private int paddingWidth;
        private int blockWidth;

        public TimeBlock(Time startTime, Time endTime) {
            this(startTime.value.getHour() * 60 + startTime.value.getMinute(),
                    endTime.value.getHour() * 60 + endTime.value.getMinute());
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
            return startTimeMinutes < other.endTimeMinutes && other.startTimeMinutes < endTimeMinutes;
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


