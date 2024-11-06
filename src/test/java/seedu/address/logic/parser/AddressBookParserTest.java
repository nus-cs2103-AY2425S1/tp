package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.CSV_EXPORT_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.CSV_IMPORT_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_ASC;
import static seedu.address.logic.commands.CommandTestUtil.SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.WEEK_DESC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddGradeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GitHubCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.RemoveGradeCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.person.comparator.ComparatorManager;
import seedu.address.model.person.comparator.PersonComparator;
import seedu.address.model.person.comparator.SortField;
import seedu.address.model.person.comparator.SortOrder;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.NonFunctionalBrowser;
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
                DeleteCommand.COMMAND_WORD + NAME_DESC_AMY);
        assertEquals(new DeleteCommand(new Name(VALID_NAME_AMY)), command);
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_delete()
    //with minor modifications
    @Test
    public void parseCommand_delete_withShortForm() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_SHORT_FORM + NAME_DESC_AMY);
        assertEquals(new DeleteCommand(new Name(VALID_NAME_AMY)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_edit()
    //with minor modifications
    @Test
    public void parseCommand_edit_withShortForm() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD_SHORT_FORM + " "
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
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));

        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + TAG_DESC_FRIEND + TAG_DESC_HUSBAND);
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(tagSet)), command);
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_filter()
    //with minor modifications
    @Test
    public void parseCommand_filter_withShortForm() throws Exception {
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(new Tag(VALID_TAG_HUSBAND));
        tagSet.add(new Tag(VALID_TAG_FRIEND));

        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD_SHORT_FORM + TAG_DESC_FRIEND + TAG_DESC_HUSBAND);
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(tagSet)), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_find()
    //with minor modifications
    @Test
    public void parseCommand_find_withShortForm() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_SHORT_FORM + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        PersonComparator comparator = new ComparatorManager().getComparator(SortField.NAME, SortOrder.ASC);
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + VALID_FIELD_NAME + ORDER_DESC_ASC);
        assertEquals(new SortCommand(comparator), command);
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

    @Test
    public void parseCommand_addGrade() throws Exception {
        String arguments = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        AddGradeCommand command = (AddGradeCommand) parser.parseCommand(AddGradeCommand.COMMAND_WORD + arguments);
        assertEquals(command, new AddGradeCommand(new Name(VALID_NAME_AMY), VALID_SCORE, VALID_ASSIGNMENT_ONE));
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_addGrade()
    //with minor modifications
    @Test
    public void parseCommand_addGrade_withShortForm() throws Exception {
        String arguments = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE + SCORE_DESC;
        AddGradeCommand command = (AddGradeCommand) parser
                .parseCommand(AddGradeCommand.COMMAND_WORD_SHORT_FORM + arguments);
        assertEquals(command, new AddGradeCommand(new Name(VALID_NAME_AMY), VALID_SCORE, VALID_ASSIGNMENT_ONE));
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD + " n/Amy");
        assertEquals(command, new ViewCommand(new Name("Amy")));
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_view()
    //with minor modifications
    @Test
    public void parseCommand_view_withShortForm() throws Exception {
        ViewCommand command = (ViewCommand) parser
                .parseCommand(ViewCommand.COMMAND_WORD_SHORT_FORM + " n/Amy");
        assertEquals(command, new ViewCommand(new Name("Amy")));
    }

    @Test
    public void parseCommand_githubCommand() throws ParseException {
        NonFunctionalBrowser nonFunctionalBrowser = NonFunctionalBrowser.getDesktop();
        String arguments = NAME_DESC_AMY;
        GitHubCommand command = (GitHubCommand) parser.parseCommand(GitHubCommand.COMMAND_WORD + arguments);
        Name validName = new Name(VALID_NAME_AMY);
        assertEquals(command, new GitHubCommand(validName, nonFunctionalBrowser));
    }

    //@@author swaminathanViswa -reused
    //Reused from parseCommand_githubCommand()
    //with minor modifications
    @Test
    public void parseCommand_githubCommand_withShortForm() throws ParseException {
        NonFunctionalBrowser nonFunctionalBrowser = NonFunctionalBrowser.getDesktop();
        String arguments = NAME_DESC_AMY;
        GitHubCommand command = (GitHubCommand) parser.parseCommand(GitHubCommand.COMMAND_WORD_SHORT_FORM + arguments);
        Name validName = new Name(VALID_NAME_AMY);
        assertEquals(command, new GitHubCommand(validName, nonFunctionalBrowser));
    }
    @Test
    public void parseCommand_removeGrade() throws Exception {
        String arguments = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE;
        RemoveGradeCommand command = (RemoveGradeCommand)
                parser.parseCommand(RemoveGradeCommand.COMMAND_WORD + arguments);
        assertEquals(command, new RemoveGradeCommand(VALID_NAME_AMY, VALID_ASSIGNMENT_ONE));
    }

    @Test
    public void parseCommand_removeGrade_withShortForm() throws Exception {
        String arguments = NAME_DESC_AMY + ASSIGNMENT_DESC_ONE;
        RemoveGradeCommand command = (RemoveGradeCommand)
                parser.parseCommand(RemoveGradeCommand.COMMAND_WORD_SHORT_FORM + arguments);
        assertEquals(command, new RemoveGradeCommand(VALID_NAME_AMY, VALID_ASSIGNMENT_ONE));
    }

    @Test
    public void parseCommand_mark() throws Exception {
        String arguments = NAME_DESC_AMY + WEEK_DESC;
        MarkCommand command = (MarkCommand) parser.parseCommand(MarkCommand.COMMAND_WORD + " " + arguments);
        assertEquals(new MarkCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_mark_withShortForm() throws Exception {
        String arguments = NAME_DESC_AMY + WEEK_DESC;
        MarkCommand command = (MarkCommand) parser
                .parseCommand(MarkCommand.COMMAND_WORD_SHORT_FORM + " " + arguments);
        assertEquals(new MarkCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_unmark() throws Exception {
        String arguments = NAME_DESC_AMY + WEEK_DESC;
        UnmarkCommand command = (UnmarkCommand) parser.parseCommand(UnmarkCommand.COMMAND_WORD + " " + arguments);
        assertEquals(new UnmarkCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_unmark_withShortForm() throws Exception {
        String arguments = NAME_DESC_AMY + WEEK_DESC;
        UnmarkCommand command = (UnmarkCommand) parser
                .parseCommand(UnmarkCommand.COMMAND_WORD_SHORT_FORM + " " + arguments);
        assertEquals(new UnmarkCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_export() throws Exception {
        String arguments = CSV_EXPORT_DESC_VALID;
        ExportCommand command = (ExportCommand) parser.parseCommand(ExportCommand.COMMAND_WORD + " " + arguments);
        assertEquals(new ExportCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_export_withShortForm() throws Exception {
        String arguments = CSV_EXPORT_DESC_VALID;
        ExportCommand command = (ExportCommand) parser
                .parseCommand(ExportCommand.COMMAND_WORD_SHORT_FORM + " " + arguments);
        assertEquals(new ExportCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_import() throws Exception {
        String arguments = CSV_IMPORT_DESC_VALID;
        ImportCommand command = (ImportCommand) parser.parseCommand(ImportCommand.COMMAND_WORD + " " + arguments);
        assertEquals(new ImportCommandParser().parse(arguments), command);
    }

    @Test
    public void parseCommand_import_withShortForm() throws Exception {
        String arguments = CSV_IMPORT_DESC_VALID;
        ImportCommand command = (ImportCommand) parser
                .parseCommand(ImportCommand.COMMAND_WORD_SHORT_FORM + " " + arguments);
        assertEquals(new ImportCommandParser().parse(arguments), command);
    }
}
