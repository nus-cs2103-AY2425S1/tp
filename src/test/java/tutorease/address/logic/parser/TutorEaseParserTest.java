package tutorease.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.CommandsUtil.getLessonAddCommand;
import static tutorease.address.testutil.CommandsUtil.getUpperCaseEditContactCommand;
import static tutorease.address.testutil.CommandsUtil.getUpperCaseFindContactCommand;
import static tutorease.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.commands.ClearCommand;
import tutorease.address.logic.commands.ContactCommand;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.commands.DeleteLessonCommand;
import tutorease.address.logic.commands.EditContactCommand;
import tutorease.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import tutorease.address.logic.commands.ExitCommand;
import tutorease.address.logic.commands.FindContactCommand;
import tutorease.address.logic.commands.FindLessonCommand;
import tutorease.address.logic.commands.HelpCommand;
import tutorease.address.logic.commands.LessonCommand;
import tutorease.address.logic.commands.ListContactCommand;
import tutorease.address.logic.commands.ListLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;
import tutorease.address.model.person.NameContainsKeywordsPredicate;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.CommandsUtil;
import tutorease.address.testutil.EditPersonDescriptorBuilder;
import tutorease.address.testutil.PersonUtil;
import tutorease.address.testutil.StudentBuilder;

public class TutorEaseParserTest {

    private final TutorEaseParser parser = new TutorEaseParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new StudentBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(PersonUtil.getAddContactCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
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

    @Test
    public void parseCommand_lesson_add() throws Exception {
        assertTrue(parser.parseCommand(getLessonAddCommand(AddLessonCommand.COMMAND_WORD))
                instanceof AddLessonCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindContactCommand command = (FindContactCommand) parser.parseCommand(
                ContactCommand.COMMAND_WORD + " " + FindContactCommand.COMMAND_WORD
                        + " " + String.join(" ", keywords));
        assertEquals(new FindContactCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new StudentBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(
                getUpperCaseEditContactCommand(EditContactCommand.COMMAND_WORD, descriptor, INDEX_FIRST_PERSON));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_upperCaseAdd() throws Exception {
        Person person = new StudentBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(
                CommandsUtil.getUpperCaseAddContactCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_upperCaseDelete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                CommandsUtil.getUpperCaseDeleteContactCommand(INDEX_FIRST_PERSON));
        assertEquals(new DeleteContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_upperCaseEdit() throws Exception {
        Person person = new StudentBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(
                getUpperCaseEditContactCommand(EditContactCommand.COMMAND_WORD.toUpperCase(),
                        descriptor, INDEX_FIRST_PERSON));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_upperCaseFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindContactCommand command = (FindContactCommand) parser.parseCommand(
                getUpperCaseFindContactCommand(String.join(" ", keywords)));
        assertEquals(new FindContactCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_upperCaseList() throws Exception {
        assertTrue(parser.parseCommand(ContactCommand.COMMAND_WORD.toUpperCase() + " "
                + "list") instanceof ListContactCommand);
    }

    @Test
    public void parseCommand_upperCaseHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.toUpperCase()) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_upperCaseExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.toUpperCase()) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_upperCaseClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD.toUpperCase()) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_upperCaseLessonAdd() throws Exception {
        assertTrue(parser.parseCommand(getLessonAddCommand(AddLessonCommand.COMMAND_WORD.toUpperCase()))
                instanceof AddLessonCommand);
    }

    @Test
    public void parseCommand_upperCaseLessonList() throws Exception {
        assertTrue(parser.parseCommand(LessonCommand.COMMAND_WORD.toUpperCase()
                + " " + ListContactCommand.COMMAND_WORD.toUpperCase())
                instanceof ListLessonCommand);
    }

    @Test
    public void parseCommand_upperCaseLessonDelete() throws Exception {
        assertTrue(parser.parseCommand(LessonCommand.COMMAND_WORD.toUpperCase() + " "
                + DeleteLessonCommand.COMMAND_WORD + " 1") instanceof DeleteLessonCommand);
    }

    @Test
    public void parseCommand_upperCaseLessonFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindLessonCommand command = (FindLessonCommand) parser.parseCommand(
                LessonCommand.COMMAND_WORD.toUpperCase() + " " + FindLessonCommand.COMMAND_WORD.toUpperCase()
                        + " " + String.join(" ", keywords));
        assertEquals(new FindLessonCommand(new LessonContainsNamesPredicate(keywords)), command);
    }
}
