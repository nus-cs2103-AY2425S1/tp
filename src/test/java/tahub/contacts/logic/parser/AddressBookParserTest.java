package tahub.contacts.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_COURSE_CODE;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_COURSE_NAME;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.commands.ClearCommand;
import tahub.contacts.logic.commands.ExitCommand;
import tahub.contacts.logic.commands.HelpCommand;
import tahub.contacts.logic.commands.ListCommand;
import tahub.contacts.logic.commands.course.CourseDeleteCommand;
import tahub.contacts.logic.commands.course.CourseEditCommand;
import tahub.contacts.logic.commands.person.PersonAddCommand;
import tahub.contacts.logic.commands.person.PersonDeleteCommand;
import tahub.contacts.logic.commands.person.PersonEditCommand;
import tahub.contacts.logic.commands.person.PersonEditCommand.EditPersonDescriptor;
import tahub.contacts.logic.commands.person.PersonFindCommand;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.person.NameContainsKeywordsPredicate;
import tahub.contacts.model.person.Person;
import tahub.contacts.testutil.EditCourseDescriptorBuilder;
import tahub.contacts.testutil.EditPersonDescriptorBuilder;
import tahub.contacts.testutil.PersonBuilder;
import tahub.contacts.testutil.PersonUtil;
import tahub.contacts.testutil.TypicalPersons;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        PersonAddCommand command = (PersonAddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new PersonAddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Person firstPerson = TypicalPersons.getTypicalPersons().get(0);
        PersonDeleteCommand command = (PersonDeleteCommand) parser.parseCommand(
                PersonDeleteCommand.COMMAND_WORD + " " + PREFIX_MATRICULATION_NUMBER + firstPerson.getMatricNumber());
        assertEquals(new PersonDeleteCommand(firstPerson.getMatricNumber()), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person firstPerson = TypicalPersons.getTypicalPersons().get(0);
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        PersonEditCommand command = (PersonEditCommand) parser.parseCommand(PersonEditCommand.COMMAND_WORD + " "
                + PREFIX_MATRICULATION_NUMBER + firstPerson.getMatricNumber()
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new PersonEditCommand(firstPerson.getMatricNumber(), descriptor), command);
    }

    @Test
    public void parseCommand_editCourse() throws Exception {
        CourseCode courseCode = new CourseCode(VALID_COURSE_CODE);
        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
                .withCourseName(VALID_COURSE_NAME).build();
        CourseEditCommand command = (CourseEditCommand) parser
                .parseCommand(CourseEditCommand.COMMAND_WORD + " c/" + courseCode + COURSE_NAME_DESC);
        assertEquals(new CourseEditCommand(courseCode, descriptor), command);
    }

    @Test
    public void parseCommand_deleteCourse() throws Exception {
        CourseCode courseCode = new CourseCode(VALID_COURSE_CODE);
        CourseDeleteCommand command = (CourseDeleteCommand) parser.parseCommand(CourseDeleteCommand
                .COMMAND_WORD + " c/" + courseCode);
        assertEquals(new CourseDeleteCommand(courseCode), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        PersonFindCommand command = (PersonFindCommand) parser.parseCommand(
                PersonFindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new PersonFindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
