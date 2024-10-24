package seedu.address.logic.parser;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ABSENT_REASON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ECNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_SCORE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEcNameCommand;
import seedu.address.logic.commands.AddEcNumberCommand;
import seedu.address.logic.commands.AddExamCommand;
import seedu.address.logic.commands.AddExamScoreCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exam.Exam;
import seedu.address.model.person.AbsentDate;
import seedu.address.model.person.AbsentReason;
import seedu.address.model.person.EcName;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> names = Arrays.asList("foo", "bar");
        List<String> phones = Arrays.asList("123", "456");

        String commandInput = FilterCommand.COMMAND_WORD + " " + "n/foo n/bar " + "p/123 p/456";

        FilterCommand command = (FilterCommand) parser.parseCommand(commandInput);
        FilterCommand expectedCommand = new FilterCommand(new PersonPredicate(names, phones, emptyList(),
                emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()));

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
    public void parseCommand_addEcName() throws Exception {

        final String ecName = "Jane Doe";

        AddEcNameCommand expected = new AddEcNameCommand(
                INDEX_FIRST_PERSON, new EcName(ecName));

        AddEcNameCommand command = (AddEcNameCommand) parser.parseCommand(
                AddEcNameCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_ECNAME + ecName);

        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_addEcNumber() throws Exception {

        final String ecNumber = "91234567";

        AddEcNumberCommand expected = new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(ecNumber));

        AddEcNumberCommand command = (AddEcNumberCommand) parser.parseCommand(
                AddEcNumberCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_ECNUMBER + ecNumber);

        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_addAttendance() throws Exception {
        final String absentDate = "20-10-2024";
        final String absentReason = "Sick";

        AddAttendanceCommand expected = new AddAttendanceCommand(
                INDEX_FIRST_PERSON,
                new AbsentDate(absentDate),
                new AbsentReason(absentReason));

        AddAttendanceCommand command = (AddAttendanceCommand) parser.parseCommand(
                AddAttendanceCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_ABSENT_DATE + absentDate + " "
                        + PREFIX_ABSENT_REASON + absentReason);

        // Assert that the expected command matches the parsed command
        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_addExam() throws Exception {

        final String exam = "Midterm";

        AddExamCommand expected = new AddExamCommand(new Exam(exam));

        AddExamCommand command = (AddExamCommand) parser.parseCommand(
                AddExamCommand.COMMAND_WORD + " "
                        + PREFIX_EXAM + exam);

        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_addExamScore() throws Exception {

        final String exam = "Midterm";
        final String score = "85";

        AddExamScoreCommand expected = new AddExamScoreCommand(INDEX_FIRST_PERSON, new Exam(exam), score);
        AddExamScoreCommand command = (AddExamScoreCommand) parser.parseCommand(
                AddExamScoreCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_EXAM + exam + " "
                        + PREFIX_EXAM_SCORE + score);

        assertEquals(expected, command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }
}
