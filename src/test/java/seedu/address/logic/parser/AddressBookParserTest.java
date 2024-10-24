package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalProjects.ALPHA;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearProjectCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.list_commands.ListPersonCommand;
import seedu.address.logic.commands.list_commands.ListProjectCommand;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.model.skill.SkillsContainsKeywordsPredicate;
import seedu.address.model.tag.TagsContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.ProjectUtil;

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
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        List<String> skillsKeywords = keywords.subList(0, 1);
        List<String> tagsKeywords = keywords.subList(1, 3);

        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " "
                + skillsKeywords.stream().collect(Collectors.joining(
                        " " + PREFIX_SKILL.getPrefix(), PREFIX_SKILL.getPrefix(), " "))
                + tagsKeywords.stream().collect(Collectors.joining(
                        " " + PREFIX_TAG.getPrefix(), PREFIX_TAG.getPrefix(), " ")));

        assertEquals(new FilterCommand(new SkillsContainsKeywordsPredicate(skillsKeywords),
                new TagsContainsKeywordsPredicate(tagsKeywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);
    }

    @Test
    public void parseCommand_addProject() throws Exception {
        Project project = new ProjectBuilder().build();
        AddProjectCommand command = (AddProjectCommand) parser.parseCommand(ProjectUtil.getAddProjectCommand(project));
        assertEquals(new AddProjectCommand(project), command);
    }

    @Test
    public void parseCommand_deleteProject() throws Exception {
        DeleteProjectCommand command = (DeleteProjectCommand) parser.parseCommand(
                DeleteProjectCommand.COMMAND_WORD + " " + INDEX_FIRST_PROJECT.getOneBased());
        assertEquals(new DeleteProjectCommand(INDEX_FIRST_PROJECT), command);
    }

    @Test
    public void parseCommand_clearProject() throws Exception {
        assertTrue(parser.parseCommand(ClearProjectCommand.COMMAND_WORD) instanceof ClearProjectCommand);
        assertTrue(parser.parseCommand(ClearProjectCommand.COMMAND_WORD + " 3") instanceof ClearProjectCommand);
    }

    @Test
    public void parseCommand_findProject() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindProjectCommand command = (FindProjectCommand) parser.parseCommand(
                FindProjectCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listProject() throws Exception {
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD) instanceof ListProjectCommand);
        assertTrue(parser.parseCommand(ListProjectCommand.COMMAND_WORD + " 3") instanceof ListProjectCommand);
    }

    @Test
    public void parseCommand_unassign() throws Exception {
        assertTrue(parser.parseCommand(UnassignCommand.COMMAND_WORD
                + " aid/" + ALICE_ALPHA.getAssignmentId()) instanceof UnassignCommand);
        assertTrue(parser.parseCommand(UnassignCommand.COMMAND_WORD
                + " id/" + ALICE.getEmployeeId() + " pid/" + ALPHA.getId()) instanceof UnassignCommand);
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
