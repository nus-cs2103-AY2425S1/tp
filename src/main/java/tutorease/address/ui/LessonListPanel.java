package tutorease.address.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.lesson.Lesson;

/**
 * Panel containing the list of lessons.
 */
public class LessonListPanel extends UiPart<Region> {
    private static final String FXML = "LessonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LessonListPanel.class);

    @FXML
    private ListView<Lesson> lessonListView;

    /**
     * Creates a {@code LessonListPanel} with the given {@code ObservableList}.
     *
     * @param lessonList The list of lessons.
     */
    public LessonListPanel(ObservableList<Lesson> lessonList) {
        super(FXML);

        logger.log(Level.INFO, "Creating LessonListPanel with lessonList: " + lessonList);
        lessonListView.setItems(lessonList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
        logger.log(Level.INFO, "Created LessonListPanel with lessonList");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Lesson} using a {@code LessonCard}.
     */
    class LessonListViewCell extends ListCell<Lesson> {
        @Override
        protected void updateItem(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);

            logger.log(Level.INFO, "Updating LessonListViewCell with lesson: " + lesson);
            if (empty || lesson == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LessonCard(lesson, getIndex() + 1).getRoot());
            }
        }
    }
}
