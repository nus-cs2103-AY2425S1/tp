package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Optional;

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

public class AddToConsultCommandTest {

    private final Index validIndex = Index.fromOneBased(1);
    private final ObservableList<Name> studentNames = FXCollections.observableArrayList(new Name("John Doe"),
        new Name("Harry Ng"));

    @Test
    public void execute_addStudentsToConsult_success() throws Exception {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        AddToConsultCommand command = new AddToConsultCommand(validIndex, studentNames);

        CommandResult result = command.execute(modelStub);
        Consultation consultation = modelStub.getFilteredConsultationList().get(0);

        // After execution, the consultation should have the students added
        assertEquals(2, consultation.getStudents().size());
        assertEquals("John Doe", consultation.getStudents().get(0).getName().fullName);
        assertEquals("Harry Ng", consultation.getStudents().get(1).getName().fullName);
    }

    @Test
    public void execute_invalidConsultationIndex_throwsCommandException() {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        Index invalidIndex = Index.fromOneBased(5);
        AddToConsultCommand command = new AddToConsultCommand(invalidIndex, studentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStubWithConsultation modelStub = new ModelStubWithConsultation();
        ObservableList<Name> invalidStudentNames = FXCollections.observableArrayList(new Name("Non Existent"));

        AddToConsultCommand command = new AddToConsultCommand(validIndex, invalidStudentNames);

        assertThrows(CommandException.class, () -> command.execute(modelStub));
    }

    // Model stub that contains a consultation and can return students by name
    private class ModelStubWithConsultation extends ModelStub {

        private final Consultation consultation = new Consultation(
                new seedu.address.model.consultation.Date("2024-10-20"),
                new seedu.address.model.consultation.Time("14:00"),
                FXCollections.observableArrayList());

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
        public Optional<Student> findStudentByName(Name name) {
            // Use StudentBuilder to create valid students
            Student johnDoe = new StudentBuilder()
                    .withName("John Doe")
                    .withPhone("91234567")
                    .withEmail("johndoe@example.com")
                    .withCourses("CS2103T")
                    .build();

            Student harryNg = new StudentBuilder()
                    .withName("Harry Ng")
                    .withPhone("98765432")
                    .withEmail("harryng@example.com")
                    .withCourses("CS2101")
                    .build();

            if (johnDoe.getName().equals(name)) {
                return Optional.of(johnDoe);
            } else if (harryNg.getName().equals(name)) {
                return Optional.of(harryNg);
            }

            return Optional.empty();
        }
    }
}
