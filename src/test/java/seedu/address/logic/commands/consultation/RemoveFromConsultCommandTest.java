package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

public class RemoveFromConsultCommandTest {

    private final Index validIndex = Index.fromOneBased(1);
    private final List<Name> studentNames = List.of(new Name("Alex Yeoh"), new Name("Harry Ng"));

    @Test
    public void execute_removeStudentsFromConsult_success() throws Exception {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        RemoveFromConsultCommand command = new RemoveFromConsultCommand(validIndex, studentNames);

        CommandResult result = command.execute(modelStub);

        Consultation consultation = modelStub.getFilteredConsultationList().get(0);
        // Use consultation's date and time to format the success message
        String expectedMessage = String.format(RemoveFromConsultCommand.MESSAGE_REMOVE_FROM_CONSULT_SUCCESS,
            consultation.getDate().getValue(), consultation.getTime().getValue());

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidConsultationIndex_throwsCommandException() {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        Index invalidIndex = Index.fromOneBased(5); // Out of bound index
        RemoveFromConsultCommand command = new RemoveFromConsultCommand(invalidIndex, studentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        List<Name> invalidStudentNames = List.of(new Name("Non Existent"));

        RemoveFromConsultCommand command = new RemoveFromConsultCommand(validIndex, invalidStudentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub),
            RemoveFromConsultCommand.MESSAGE_STUDENT_NOT_FOUND);
    }

    // Model stub that contains a consultation and can return students by name
    private class ModelStubWithConsultation extends ModelStub {
        private final Consultation consultation = new Consultation(
                new seedu.address.model.consultation.Date("2024-10-20"),
                new seedu.address.model.consultation.Time("14:00"),
                FXCollections.observableArrayList(
                        new StudentBuilder().withName("Alex Yeoh").build(),
                        new StudentBuilder().withName("Harry Ng").build()));

        private ArrayList<Consultation> consults;

        ModelStubWithConsultation() {
            this.consults = new ArrayList<>();
            this.consults.add(consultation);
        }

        @Override
        public void setConsult(Consultation target, Consultation editedConsult) {
            int index = this.consults.indexOf(target);
            this.consults.set(index, editedConsult);
        }

        @Override
        public ObservableList<Consultation> getFilteredConsultationList() {
            return FXCollections.observableArrayList(consults);
        }

        @Override
        public java.util.Optional<Student> findStudentByName(Name name) {
            for (Student student : consultation.getStudents()) {
                if (student.getName().equals(name)) {
                    return java.util.Optional.of(student);
                }
            }
            return java.util.Optional.empty();
        }
    }
}
