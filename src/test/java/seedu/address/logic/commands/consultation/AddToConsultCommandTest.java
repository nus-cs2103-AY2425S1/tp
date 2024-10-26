package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalConsultations.getTypicalConsultations;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

public class AddToConsultCommandTest {

    private Model model;

    private final String index1 = "1";
    private final Index validConsultIndex = Index.fromOneBased(Integer.parseInt(index1));
    private final ObservableList<Name> studentNames = FXCollections.observableArrayList(
            new Name(AMY.getName().fullName),
            new Name(BOB.getName().fullName));

    private final ObservableList<Name> duplicateStudentNames = FXCollections.observableArrayList(
            new Name(AMY.getName().fullName),
            new Name(AMY.getName().fullName));

    private final ObservableList<Index> studentIndices = FXCollections.observableArrayList(
            INDEX_FIRST_STUDENT,
            INDEX_SECOND_STUDENT);

    private final ObservableList<Index> duplicateStudentIndices = FXCollections.observableArrayList(
            INDEX_FIRST_STUDENT,
            INDEX_FIRST_STUDENT);

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addStudentsToConsult_success() throws Exception {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex, studentNames, studentIndices);

        CommandResult result = command.execute(model);
        Consultation consultation = model.getFilteredConsultationList().get(validConsultIndex.getZeroBased());

        assertEquals(5, consultation.getStudents().size()); // 2 from names, 2 from indices, and 1 existing student
    }

    @Test
    public void execute_invalidConsultationIndex_throwsCommandException() {
        Index invalidConsultIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        AddToConsultCommand command = new AddToConsultCommand(invalidConsultIndex, studentNames, studentIndices);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_invalidStudentIndices_throwsCommandException() {
        ObservableList<Index> invalidIndices = FXCollections.observableArrayList(Index.fromOneBased(model.getFilteredStudentList().size() + 1));
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex, studentNames, invalidIndices);

        String formattedOutOfBoundIndices = invalidIndices.stream()
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someOfManyInvalidIndexUnfilteredList_throwsCommandException() {
        ObservableList<Index> someInvalidIndices = FXCollections.observableArrayList(Index.fromOneBased(model.getFilteredStudentList().size() + 1), INDEX_FIRST_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex, studentNames, someInvalidIndices);

        String formattedOutOfBoundIndices = String.valueOf(model.getFilteredStudentList().size() + 1);

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
    }

    @Test
    public void execute_duplicateStudentByIndex_throwsCommandException() throws Exception {
        ObservableList<Index> duplicateIndices = FXCollections.observableArrayList(INDEX_FIRST_STUDENT, INDEX_FIRST_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex, FXCollections.observableArrayList(), duplicateIndices);

        assertThrows(CommandException.class, () -> command.execute(model), "Expected duplicate student exception.");
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex, FXCollections.observableArrayList(), studentIndices);

        CommandResult result = command.execute(model);
        Consultation consultation = model.getFilteredConsultationList().get(validConsultIndex.getZeroBased());

        assertEquals(3, consultation.getStudents().size()); // 1 existing + 2 added by index
    }

    // Additional utility to match typical setup for filtered list views
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);
        assertEquals(0, model.getFilteredStudentList().size());
    }
}
