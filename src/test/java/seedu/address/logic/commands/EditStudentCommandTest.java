package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INVALID_INDEX_STUDENT;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditStudentCommand;
import seedu.address.logic.commands.editcommands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;


public class EditStudentCommandTest {
    private Model model;
    private Student studentToEdit;
    private Student editedStudent;
    private EditPersonDescriptor editPersonDescriptorDuplicate;
    private EditPersonDescriptor validDescriptor;

    private final StudentNumber validStudentNumber = new StudentNumber("A0123456P");
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        studentToEdit = new Student(new Name("Amy"),
            new Email("amy@u.nus.edu"),
            new HashSet<Tag>(),
            validStudentNumber);
        model.addPerson(studentToEdit);
        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("GoodAtUI"));

        editPersonDescriptorDuplicate = new EditPersonDescriptor();
        editPersonDescriptorDuplicate.setName(new Name("Bob"));
        editPersonDescriptorDuplicate.setTags(tags);
        editPersonDescriptorDuplicate.setEmail(new Email("bob@u.nus.edu"));
        validDescriptor = new EditPersonDescriptor();
        validDescriptor.setName(new Name("NONE"));
        editedStudent = new Student(new Name("NONE"),
            new Email("amy@u.nus.edu"),
            new HashSet<Tag>(),
            validStudentNumber);
    }
    @Test
    public void execute_editStudent_success() throws CommandException {
        EditStudentCommand command = new EditStudentCommand(INDEX_FIRST_PERSON, validDescriptor);
        CommandResult expectedResult = command.execute(model);
        assertEquals(expectedResult.getFeedbackToUser(), String.format(EditStudentCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            Messages.format(editedStudent)));
    }

    @Test
    public void execute_duplicatedStudentEdited_throwsCommandException() {
        EditStudentCommand command = new EditStudentCommand(INDEX_FIRST_PERSON, editPersonDescriptorDuplicate);
        assertThrows(CommandException.class,  ()->command.execute(model), EditStudentCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        EditStudentCommand command = new EditStudentCommand(INVALID_INDEX_STUDENT, editPersonDescriptorDuplicate);
        assertThrows(CommandException.class, ()->command.execute(model), EditStudentCommand.MESSAGE_STUDENT_NOT_FOUND);
    }
    @Test
    public void equals() {
        StudentNumber studentNumberOne = new StudentNumber("A0123456P");
        StudentNumber studentNumberTwo = new StudentNumber("A0654321P");
        EditPersonDescriptor descriptorOne = new EditPersonDescriptor();
        descriptorOne.setName(new Name("John Doe"));
        descriptorOne.setEmail(new Email("e0000000@u.nus.edu"));
        EditPersonDescriptor descriptorTwo = new EditPersonDescriptor();
        descriptorTwo.setName(new Name("John Doe"));
        descriptorTwo.setEmail(new Email("e1111111@u.nus.edu"));

        EditStudentCommand commandOne = new EditStudentCommand(INDEX_FIRST_PERSON, descriptorOne);
        EditStudentCommand commandTwo = new EditStudentCommand(INDEX_SECOND_PERSON, descriptorTwo);
        EditStudentCommand commandOneCopy = new EditStudentCommand(INDEX_FIRST_PERSON, descriptorOne);
        assertTrue(commandOne.equals(commandOne));
        assertTrue(commandOne.equals(commandOneCopy));
        assertFalse(commandTwo.equals(commandOne));
        assertFalse(commandOne.equals(null));
        assertFalse(commandOne.equals(2));
    }
}
