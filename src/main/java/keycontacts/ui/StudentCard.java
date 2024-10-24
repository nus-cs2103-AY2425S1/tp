package keycontacts.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved
     * keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The
     *      issue on AddressBook level 4</a>
     */

    public final Student student;
    public final int displayedIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label gradeLevel;
    @FXML
    private Label regularLesson;
    @FXML
    private Label cancelledLessons;
    @FXML
    private Label pianoPieces;
    @FXML
    private Label makeupLessons;
    @FXML
    private VBox groupChipHolder;

    /**
     * Creates a {@code StudentCode} with the given {@code Student} and index to
     * display.
     */
    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        this.displayedIndex = displayedIndex;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        phone.setText(student.getPhone().value);
        address.setText(student.getAddress().value);
        gradeLevel.setText(student.getGradeLevel().value);
        regularLesson.setText(student.getRegularLessonDisplay());
        pianoPieces.setText(formatPianoPieces(student));
        cancelledLessons.setText(formatCancelledLessons(student));
        makeupLessons.setText(formatMakeupLessons(student));
        // TODO Change this to take the student's group
        Label groupChip = new Label("Group " + displayedIndex);
        groupChip.getStyleClass().add("group-chip");
        groupChipHolder.getChildren().add(groupChip);
    }

    /**
     * Formats the pieces with index and piece name.
     */
    private String formatPianoPieces(Student student) {
        List<PianoPiece> sortedPieces = student.getPianoPieces().stream()
                .sorted(Comparator.comparing(PianoPiece::toString))
                .collect(Collectors.toList());

        if (sortedPieces.isEmpty()) {
            return "No Piano pieces assigned";
        }

        return IntStream.range(0, sortedPieces.size())
                .mapToObj(i -> String.format(
                        "%d. %s",
                        i + 1,
                        sortedPieces.get(i)))
                .collect(Collectors.joining("\n", "Piano pieces:\n", ""));
    }

    /**
     * Formats the cancelled lessons with index and date.
     */
    private String formatCancelledLessons(Student student) {
        List<CancelledLesson> sortedLessons = student.getCancelledLessons().stream()
                .sorted(Comparator.comparing(CancelledLesson::getLessonDate))
                .collect(Collectors.toList());

        if (sortedLessons.isEmpty()) {
            return "No Cancelled Lessons";
        }

        return IntStream.range(0, sortedLessons.size())
                .mapToObj(i -> String.format(
                        "%d. %s",
                        i + 1,
                        sortedLessons.get(i).getLessonDate().toDisplay()))
                .collect(Collectors.joining("\n", "Cancelled Lessons:\n", ""));
    }

    /**
     * Formats the makeup lessons with index, date, and time.
     */
    private String formatMakeupLessons(Student student) {
        List<MakeupLesson> sortedLessons = student.getMakeupLessons().stream()
                .sorted(Comparator.comparing(MakeupLesson::getLessonDate).thenComparing(Lesson::getStartTime))
                .collect(Collectors.toList());

        if (sortedLessons.isEmpty()) {
            return "No Makeup Lessons scheduled";
        }

        return IntStream.range(0, sortedLessons.size())
                .mapToObj(i -> String.format(
                        "%d. %s",
                        i + 1,
                        sortedLessons.get(i).toDisplay()))
                .collect(Collectors.joining("\n", "Makeup Lessons:\n", ""));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof StudentCard)) {
            return false;
        }

        StudentCard card = (StudentCard) object;
        return this.displayedIndex == card.displayedIndex && student.equals(card.student);
    }
}
