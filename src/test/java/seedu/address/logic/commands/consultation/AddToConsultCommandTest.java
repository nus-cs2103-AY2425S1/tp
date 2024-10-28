package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.logic.commands.consultation.AddToConsultCommand.MESSAGE_ADD_TO_CONSULT_SUCCESS;
import static seedu.address.logic.commands.consultation.AddToConsultCommand.MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_INDEX;
import static seedu.address.logic.commands.consultation.AddToConsultCommand.MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_NAME;
import static seedu.address.logic.commands.consultation.AddToConsultCommand.MESSAGE_STUDENT_NOT_FOUND;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.BOB;
import static seedu.address.testutil.TypicalStudents.CARL;
import static seedu.address.testutil.TypicalStudents.DANIEL;

import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
public class AddToConsultCommandTest {

    private Model model;


    private final Index validConsultIndex1WithAlice = Index.fromOneBased(1);
    private final Index validConsultIndex5 = Index.fromOneBased(5);
    private final ObservableList<Name> allInvalidStudentNames = FXCollections.observableArrayList(
            new Name(AMY.getName().fullName), new Name(BOB.getName().fullName));
    private final ObservableList<Name> someInvalidStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(BOB.getName().fullName));
    private final ObservableList<Name> validStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(DANIEL.getName().fullName));
    private final ObservableList<Name> duplicateStudentNames = FXCollections.observableArrayList(
            new Name(CARL.getName().fullName), new Name(CARL.getName().fullName));
    private final ObservableList<Index> validStudentIndices = FXCollections.observableArrayList(
            INDEX_FIRST_STUDENT, INDEX_SECOND_STUDENT); //contains Alice and Bob
    private ObservableList<Index> invalidIndices;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        invalidIndices = FXCollections.observableArrayList(
                Index.fromOneBased(model.getFilteredStudentList().size() + 1));
    }

    @Test
    public void execute_addStudentsToConsultNamesAndIndices_success() throws Exception {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                validStudentNames, validStudentIndices);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandResult result = command.execute(model);

        Consultation targetConsultation = expectedModel.getFilteredConsultationList()
                .get(validConsultIndex5.getZeroBased());
        Consultation editedConsultation = new Consultation(targetConsultation);

        editedConsultation.addStudent(CARL);
        editedConsultation.addStudent(DANIEL);
        editedConsultation.addStudent(ALICE);
        editedConsultation.addStudent(BENSON);
        expectedModel.setConsult(targetConsultation, editedConsultation);

        String expectedMessage = String.format(MESSAGE_ADD_TO_CONSULT_SUCCESS, Messages.format(editedConsultation));

        assertEquals(result.getFeedbackToUser(), expectedMessage);
        assertEquals(expectedModel.getFilteredConsultationList().get(validConsultIndex5.getZeroBased()),
                model.getFilteredConsultationList().get(validConsultIndex5.getZeroBased()));
    }

    @Test
    public void execute_addStudentsToConsultIndicesOnly_success() throws Exception {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                FXCollections.observableArrayList(), validStudentIndices);

        CommandResult result = command.execute(model);
        Consultation consultation = model.getFilteredConsultationList().get(validConsultIndex5.getZeroBased());

        assertEquals(2, consultation.getStudents().size()); // 2 from indices
    }

    @Test
    public void execute_addStudentsToConsultNamesOnly_success() throws Exception {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                validStudentNames, FXCollections.observableArrayList());

        CommandResult result = command.execute(model);
        Consultation consultation = model.getFilteredConsultationList().get(validConsultIndex5.getZeroBased());

        assertEquals(2, consultation.getStudents().size()); // 2 from names
    }

    @Test
    public void execute_invalidConsultationIndex_throwsCommandException() {
        Index invalidConsultIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        AddToConsultCommand command = new AddToConsultCommand(invalidConsultIndex,
                validStudentNames, validStudentIndices);

        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX,
                invalidConsultIndex.getOneBased()));
    }

    @Test
    public void execute_invalidStudentIndices_throwsCommandException() {

        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                validStudentNames, invalidIndices);

        String formattedOutOfBoundIndices = invalidIndices.stream()
                .map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(", "));

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_invalidStudentNames_throwsCommandException() {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                allInvalidStudentNames, validStudentIndices);

        String studentNotFoundMessage = String.format(MESSAGE_STUDENT_NOT_FOUND , AMY.getName());

        assertCommandFailure(command, model, studentNotFoundMessage);
    }

    @Test
    public void execute_someOfManyInvalidIndex_throwsCommandException() {
        ObservableList<Index> someInvalidIndices = FXCollections.observableArrayList(
                Index.fromOneBased(model.getFilteredStudentList().size() + 1), INDEX_FIRST_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(
                validConsultIndex5, validStudentNames, someInvalidIndices);

        String formattedOutOfBoundIndices = String.valueOf(model.getFilteredStudentList().size() + 1);

        assertCommandFailure(command, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someOfManyInvalidNames_throwsCommandException() {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                someInvalidStudentNames, validStudentIndices);

        String studentNotFoundMessage = String.format(MESSAGE_STUDENT_NOT_FOUND , BOB.getName());

        assertCommandFailure(command, model, studentNotFoundMessage);
    }

    @Test
    public void execute_duplicateStudentByIndex_throwsCommandException() {
        ObservableList<Index> duplicateIndices = FXCollections.observableArrayList(
                INDEX_FIRST_STUDENT, INDEX_FIRST_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                validStudentNames, duplicateIndices);

        String error = String.format(MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_INDEX, ALICE.getName().fullName,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_duplicateStudentInExistingAndIndex_throwsCommandException() {
        ObservableList<Index> validIndicesWithAlice = FXCollections.observableArrayList(
                INDEX_FIRST_STUDENT, INDEX_SECOND_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex1WithAlice,
                validStudentNames, validIndicesWithAlice);

        String error = String.format(MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_INDEX, ALICE.getName().fullName,
                INDEX_FIRST_STUDENT.getOneBased());

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_duplicateStudentInExistingAndName_throwsCommandException() {
        ObservableList<Name> validNamesWithAlice = FXCollections.observableArrayList(
                new Name(ALICE.getName().fullName), new Name(CARL.getName().fullName));
        ObservableList<Index> validIndicesWithoutAlice = FXCollections.observableArrayList(
                INDEX_SECOND_STUDENT);
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex1WithAlice,
                validNamesWithAlice, validIndicesWithoutAlice);

        String duplicateStudentInConsultError = String.format(
                MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_NAME, ALICE.getName());

        assertCommandFailure(command, model, duplicateStudentInConsultError);
    }

    @Test
    public void execute_duplicateStudentByName_throwsCommandException() {
        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                duplicateStudentNames, validStudentIndices);

        String error = String.format(MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_NAME, CARL.getName().fullName);

        assertCommandFailure(command, model, error);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        ObservableList<Index> validIndex = FXCollections.observableArrayList(
                INDEX_FIRST_STUDENT);

        AddToConsultCommand command = new AddToConsultCommand(validConsultIndex5,
                FXCollections.observableArrayList(), validIndex);

        CommandResult result = command.execute(model);
        Consultation consultation = model.getFilteredConsultationList().get(validConsultIndex5.getZeroBased());

        assertEquals(1, consultation.getStudents().size()); // 1 existing
    }
}
