package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONSULT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.consultation.AddConsultCommand;
import seedu.address.logic.commands.consultation.AddToConsultCommand;
import seedu.address.logic.commands.consultation.DeleteConsultCommand;
import seedu.address.logic.commands.consultation.ExportConsultCommand;
import seedu.address.logic.commands.consultation.ImportConsultCommand;
import seedu.address.logic.commands.consultation.ListConsultsCommand;
import seedu.address.logic.commands.consultation.RemoveFromConsultCommand;
import seedu.address.logic.commands.lesson.AddLessonCommand;
import seedu.address.logic.commands.lesson.DeleteLessonCommand;
import seedu.address.logic.commands.lesson.ListLessonsCommand;
import seedu.address.logic.commands.lesson.MarkLessonAttendanceCommand;
import seedu.address.logic.commands.lesson.MarkLessonParticipationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.IsStudentOfCoursePredicate;
import seedu.address.model.student.Name;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.testutil.ConsultationBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;
import seedu.address.testutil.TypicalStudents;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_addConsult() throws Exception {
        Consultation consult = new ConsultationBuilder().build();
        AddConsultCommand command = (AddConsultCommand) parser.parseCommand(
                AddConsultCommand.COMMAND_WORD + " d/" + consult.getDate() + " t/" + consult.getTime());
        assertEquals(new AddConsultCommand(consult), command);
    }

    @Test
    public void addConsult_sortsByDate() {
        AddressBook addressBook = new AddressBook();

        // Create consultations with different dates
        Consultation consult1 = new Consultation(new Date("2024-10-20"), new Time("14:00"), List.of());
        Consultation consult2 = new Consultation(new Date("2024-09-15"), new Time("10:00"), List.of());
        Consultation consult3 = new Consultation(new Date("2024-11-05"), new Time("16:00"), List.of());

        // Add consultations to the address book
        addressBook.addConsult(consult1);
        addressBook.addConsult(consult2);
        addressBook.addConsult(consult3);

        // Ensure consultations are sorted by date
        ObservableList<Consultation> consultations = addressBook.getConsultList();
        assertEquals(consult2, consultations.get(0)); // Oldest date first
        assertEquals(consult1, consultations.get(1));
        assertEquals(consult3, consultations.get(2)); // Newest date last
    }

    @Test
    public void addConsult_consultationsSortedByDateAndTime() {
        AddressBook addressBook = new AddressBook();

        // Consultations with different dates and times
        Consultation consult1 = new Consultation(new Date("2024-07-20"), new Time("14:00"), List.of());
        Consultation consult2 = new Consultation(new Date("2024-07-20"), new Time("09:00"), List.of());
        Consultation consult3 = new Consultation(new Date("2024-07-19"), new Time("15:00"), List.of());

        // Add consultations
        addressBook.addConsult(consult1);
        addressBook.addConsult(consult2);
        addressBook.addConsult(consult3);

        // Ensure consultations are sorted by date, and by time within the same date
        ObservableList<Consultation> sortedConsults = addressBook.getConsultList();
        assertEquals(sortedConsults.get(0), consult3); // Earliest date first
        assertEquals(sortedConsults.get(1), consult2); // Same date, earlier time
        assertEquals(sortedConsults.get(2), consult1); // Same date, later time
    }

    @Test
    public void parseCommand_addToConsult() throws Exception {
        // Construct the input arguments for the AddToConsultCommand
        String index1 = "1";
        Index index = Index.fromOneBased(Integer.parseInt(index1));
        String name1 = "John Doe";
        String name2 = "Harry Ng";

        String input = AddToConsultCommand.COMMAND_WORD + " " + index.getOneBased()
                        + SPACED_PREFIX_NAME + name1 + SPACED_PREFIX_NAME + name2
                + SPACED_PREFIX_INDEX + index1;

        AddToConsultCommand command = (AddToConsultCommand) parser.parseCommand(input);

        // Construct the expected command
        List<Name> expectedNames = List.of(new Name(name1), new Name(name2));
        List<Index> expectedIndices = List.of(index);
        AddToConsultCommand expectedCommand = new AddToConsultCommand(index, expectedNames, expectedIndices);

        // Assert that the parsed command is equal to the expected command
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_listconsults() throws Exception {
        // Ensure the parser returns an instance of ListConsultsCommand when
        // "listconsults" is input
        assertTrue(parser.parseCommand(ListConsultsCommand.COMMAND_WORD) instanceof ListConsultsCommand);
        assertTrue(parser.parseCommand(ListConsultsCommand.COMMAND_WORD + " extraArgs") instanceof ListConsultsCommand);
    }

    @Test
    public void parseCommand_removeFromConsult() throws Exception {
        Index index = Index.fromOneBased(1);
        String input = " " + index.getOneBased() + " n/Alex Yeoh n/Harry Ng";

        RemoveFromConsultCommand command = (RemoveFromConsultCommand) parser.parseCommand(
                RemoveFromConsultCommand.COMMAND_WORD + input);

        List<Name> expectedNames = List.of(new Name("Alex Yeoh"), new Name("Harry Ng"));

        assertEquals(new RemoveFromConsultCommand(index, expectedNames), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_STUDENT);
        assertEquals(new DeleteCommand(firstIndexSet), command);
    }

    @Test
    public void parseCommand_deleteConsult() throws Exception {
        DeleteConsultCommand command = (DeleteConsultCommand) parser.parseCommand(
                DeleteConsultCommand.COMMAND_WORD + " " + INDEX_FIRST_CONSULT.getOneBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(INDEX_FIRST_CONSULT);
        assertEquals(new DeleteConsultCommand(firstIndexSet), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/foo c/cs2101 n/bar c/cs2102 n/baz");
        FindCommand expectedCommand = new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of("foo")),
                new NameContainsKeywordsPredicate(List.of("bar")),
                new NameContainsKeywordsPredicate(List.of("baz")),
                new IsStudentOfCoursePredicate(List.of("CS2101")),
                new IsStudentOfCoursePredicate(List.of("CS2102"))));
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_export() throws Exception {
        String fileName = "export";
        ExportCommand command = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ExportCommand(fileName, false), command);
    }

    @Test
    public void parseCommand_import() throws Exception {
        String fileName = "import.csv";
        ImportCommand command = (ImportCommand) parser.parseCommand(
                ImportCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ImportCommand(fileName), command);
    }

    @Test
    public void parseCommand_exportConsult() throws Exception {
        String fileName = "consultations";
        ExportConsultCommand command = (ExportConsultCommand) parser.parseCommand(
                ExportConsultCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new ExportConsultCommand(fileName, false), command);

        // Test with force flag
        ExportConsultCommand forceCommand = (ExportConsultCommand) parser.parseCommand(
                ExportConsultCommand.COMMAND_WORD + " -f " + fileName);
        assertEquals(new ExportConsultCommand(fileName, true), forceCommand);
    }

    @Test
    public void parseCommand_importConsult() throws Exception {
        String filePath = "consultations.csv";
        ImportConsultCommand command = (ImportConsultCommand) parser.parseCommand(
                ImportConsultCommand.COMMAND_WORD + " " + filePath);
        assertEquals(new ImportConsultCommand(filePath), command);

        // Test with home directory path
        String homeFilePath = "~/Documents/consultations.csv";
        ImportConsultCommand homeCommand = (ImportConsultCommand) parser.parseCommand(
                ImportConsultCommand.COMMAND_WORD + " " + homeFilePath);
        assertEquals(new ImportConsultCommand(homeFilePath), homeCommand);
    }

    @Test
    public void parseCommand_addLesson() throws Exception {
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand command = (AddLessonCommand) parser.parseCommand(
                AddLessonCommand.COMMAND_WORD + " d/" + lesson.getDate() + " t/" + lesson.getTime());
        assertEquals(new AddLessonCommand(lesson), command);
    }

    @Test
    public void parseCommand_deleteLesson() throws Exception {
        String arguments = "1"; // Assuming 1 is the index for the lesson to delete
        DeleteLessonCommand command = (DeleteLessonCommand) parser.parseCommand(
                DeleteLessonCommand.COMMAND_WORD + " " + arguments);

        // Create the expected DeleteLessonCommand object with the first index
        Set<Index> indexSet = new HashSet<>();
        indexSet.add(Index.fromOneBased(1));
        DeleteLessonCommand expectedCommand = new DeleteLessonCommand(indexSet);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_listLessons() throws Exception {
        assertTrue(parser.parseCommand(ListLessonsCommand.COMMAND_WORD) instanceof ListLessonsCommand);
        assertTrue(parser.parseCommand(ListLessonsCommand.COMMAND_WORD + " extraArgs") instanceof ListLessonsCommand);
    }

    @Test
    public void parseCommand_markLessonAttendance() throws Exception {
        MarkLessonAttendanceCommand expectedCommand = new MarkLessonAttendanceCommand(
                Index.fromOneBased(1),
                List.of(TypicalStudents.ALICE.getName(), TypicalStudents.BENSON.getName()),
                true);
        assertEquals(expectedCommand,
                parser.parseCommand(MarkLessonAttendanceCommand.COMMAND_WORD
                        + " 1 n/Alice Pauline n/Benson Meier a/y"));
    }

    @Test
    public void parseCommand_markLessonParticipation() throws Exception {
        MarkLessonParticipationCommand expectedCommand = new MarkLessonParticipationCommand(
                Index.fromOneBased(1),
                List.of(TypicalStudents.ALICE.getName(), TypicalStudents.BENSON.getName()),
                3);
        assertEquals(expectedCommand,
                parser.parseCommand(MarkLessonParticipationCommand.COMMAND_WORD
                        + " 1 n/Alice Pauline n/Benson Meier pt/3"));
    }
}
