package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.findcommand.FindAddressCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.findcommand.FindEmailCommand;
import seedu.address.logic.commands.findcommand.FindNameCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.findcommand.FindTagCommand;
import seedu.address.logic.commands.findcommand.FindTaskCommand;
import seedu.address.logic.commands.findcommand.FindWeddingCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.tag.CreateTagCommand;
import seedu.address.logic.commands.tag.DeleteTagCommand;
import seedu.address.logic.commands.tag.ListTagsCommand;
import seedu.address.logic.commands.tag.TagCommand;
import seedu.address.logic.commands.tag.UntagCommand;
import seedu.address.logic.commands.task.CreateTaskCommand;
import seedu.address.logic.commands.task.DeleteTaskCommand;
import seedu.address.logic.commands.task.ListTasksCommand;
import seedu.address.logic.commands.vendor.AddVendorCommand;
import seedu.address.logic.commands.vendor.AssignVendorCommand;
import seedu.address.logic.commands.vendor.UnassignVendorCommand;
import seedu.address.logic.commands.wedding.AssignWeddingCommand;
import seedu.address.logic.commands.wedding.CreateWeddingCommand;
import seedu.address.logic.commands.wedding.DeleteWeddingCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand;
import seedu.address.logic.commands.wedding.ListWeddingsCommand;
import seedu.address.logic.commands.wedding.UnassignWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TagContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.TaskContainsKeywordsPredicate;
import seedu.address.model.person.keywordspredicate.WeddingContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalTasks;
import seedu.address.testutil.VendorBuilder;


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
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/ foo");
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultipleName() throws Exception {
        List<String> keywords = Arrays.asList("Amy", "Bob", "Clarissa");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " n/Amy n/Bob n/Clarissa");
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findAddress() throws Exception {
        List<String> keywords = List.of("Jurong West Street");
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " a/Jurong West Street");
        assertEquals(new FindAddressCommand(new AddressContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultipleAddress() throws Exception {
        List<String> keywords = Arrays.asList("Jurong West Street", "Tampines East");
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " a/Jurong West Street a/Tampines East");
        assertEquals(new FindAddressCommand(new AddressContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEmail() throws Exception {
        List<String> keywords = Arrays.asList("sally@gmail.com");
        FindEmailCommand command = (FindEmailCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " e/sally@gmail.com");
        assertEquals(new FindEmailCommand(new EmailContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultipleEmails() throws Exception {
        List<String> keywords = Arrays.asList("sally@gmail.com", "bob@example.com");
        FindEmailCommand command = (FindEmailCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " e/sally@gmail.com e/bob@example.com");
        assertEquals(new FindEmailCommand(new EmailContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        List<String> keywords = List.of("99394835");
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " p/99394835");
        assertEquals(new FindPhoneCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultiplePhone() throws Exception {
        List<String> keywords = Arrays.asList("99394835", "283384");
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " p/99394835 p/283384");
        assertEquals(new FindPhoneCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = List.of("florist");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/florist");
        assertEquals(new FindTagCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultipleTags() throws Exception {
        List<String> keywords = Arrays.asList("florist", "photographer");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " t/florist t/photographer");
        assertEquals(new FindTagCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findWedding() throws Exception {
        List<String> keywords = Arrays.asList("Snoopy's wedding");
        FindWeddingCommand command = (FindWeddingCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " w/ Snoopy's wedding");
        assertEquals(new FindWeddingCommand(new WeddingContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findMultipleWeddings() throws Exception {
        List<String> keywords = Arrays.asList("Snoopy's wedding", "Wedding 2029");
        FindWeddingCommand command = (FindWeddingCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " w/Snoopy's wedding w/Wedding 2029");
        assertEquals(new FindWeddingCommand(new WeddingContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findTask() throws Exception {
        List<String> keywords = Arrays.asList("Order wedding cake");
        FindTaskCommand command = (FindTaskCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " tk/Order wedding cake");
        assertEquals(new FindTaskCommand(new TaskContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_tag() throws Exception {
        HashSet<Tag> tagsToAdd = new HashSet<>(Arrays.asList(new Tag(new TagName("colleague")),
                new Tag(new TagName("gym"))));
        String userInput = TagCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " t/gym t/colleague";
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST, tagsToAdd);

        TagCommand command = (TagCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_untag() throws Exception {
        HashSet<Tag> tagsToRemove = new HashSet<>(Arrays.asList(new Tag(new TagName("colleague")),
                new Tag(new TagName("gym"))));
        String userInput = UntagCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " t/gym t/colleague";
        UntagCommand expectedCommand = new UntagCommand(INDEX_FIRST, tagsToRemove);

        UntagCommand command = (UntagCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_createTag() throws Exception {
        Tag tagToCreate = new Tag(new TagName("colleague"));
        String userInput = CreateTagCommand.COMMAND_WORD + " t/colleague";
        CreateTagCommand expectedCommand = new CreateTagCommand(tagToCreate);

        CreateTagCommand command = (CreateTagCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        Tag tagToDelete = new Tag(new TagName("vendor"));
        String userInput = DeleteTagCommand.COMMAND_WORD + " t/vendor";
        DeleteTagCommand expectedCommand = new DeleteTagCommand(tagToDelete);

        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using delete tag keyword
        String userKeywordInput = DeleteTagCommand.COMMAND_KEYWORD + " t/vendor";
        DeleteTagCommand keywordCommand = (DeleteTagCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_createWedding() throws Exception {
        Wedding weddingToCreate = new Wedding(new WeddingName("Wedding 19"));
        String userInput = CreateWeddingCommand.COMMAND_WORD + " w/Wedding 19";
        CreateWeddingCommand expectedCommand = new CreateWeddingCommand(weddingToCreate);

        CreateWeddingCommand command = (CreateWeddingCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using create wedding keyword
        String userKeywordInput = CreateWeddingCommand.COMMAND_KEYWORD + " w/Wedding 19";
        CreateWeddingCommand keywordCommand = (CreateWeddingCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_deleteWedding() throws Exception {
        Wedding weddingToDelete = new Wedding(new WeddingName("Joe's Wedding"));
        String userInput = DeleteWeddingCommand.COMMAND_WORD + " w/Joe's Wedding";
        DeleteWeddingCommand expectedCommand = new DeleteWeddingCommand(weddingToDelete);

        DeleteWeddingCommand command = (DeleteWeddingCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using delete wedding keyword
        String userKeywordInput = DeleteWeddingCommand.COMMAND_KEYWORD + " w/Joe's Wedding";
        DeleteWeddingCommand keywordCommand = (DeleteWeddingCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_assignWedding() throws Exception {
        HashMap<Wedding, String> weddingsToAdd = new HashMap<>() {
            { put(new Wedding(new WeddingName("Wedding 19")), "g"); }
            { put(new Wedding(new WeddingName("Joe's Wedding")), "g"); }
        };
        String userInput = AssignWeddingCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                + " w/Wedding 19 w/Joe's Wedding";
        AssignWeddingCommand expectedCommand = new AssignWeddingCommand(INDEX_FIRST, weddingsToAdd, false);

        AssignWeddingCommand command = (AssignWeddingCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using assign wedding keyword
        String userKeywordInput = AssignWeddingCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased()
                + " w/Wedding 19 w/Joe's Wedding";
        AssignWeddingCommand keywordCommand = (AssignWeddingCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_unassignWedding() throws Exception {
        HashSet<Wedding> weddingsToRemove = new HashSet<>(Arrays.asList(new Wedding(new WeddingName("Wedding 19")),
                new Wedding(new WeddingName("Joe's Wedding"))));
        String userInput = UnassignWeddingCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                + " w/Wedding 19 w/Joe's Wedding";
        UnassignWeddingCommand expectedCommand = new UnassignWeddingCommand(INDEX_FIRST, weddingsToRemove);

        UnassignWeddingCommand command = (UnassignWeddingCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using unassign wedding keyword
        String userKeywordInput = UnassignWeddingCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased()
                + " w/Wedding 19 w/Joe's Wedding";
        UnassignWeddingCommand keywordCommand = (UnassignWeddingCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_editWedding() throws Exception {
        String newWeddingParameter = " w/New Wedding Name";
        String userInput = EditWeddingCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + newWeddingParameter;
        EditWeddingCommand.EditWeddingDescriptor editWeddingDescriptor = new EditWeddingCommand.EditWeddingDescriptor();
        editWeddingDescriptor.setWeddingName(new WeddingName("New Wedding Name"));
        EditWeddingCommand expectedCommand = new EditWeddingCommand(INDEX_FIRST, editWeddingDescriptor);

        EditWeddingCommand command = (EditWeddingCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        // Test using edit wedding keyword
        String userKeywordInput = EditWeddingCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased()
                + newWeddingParameter;
        EditWeddingCommand keywordCommand = (EditWeddingCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
    }

    @Test
    public void parseCommand_listWeddings() throws Exception {
        assertTrue(parser.parseCommand(ListWeddingsCommand.COMMAND_WORD) instanceof ListWeddingsCommand);
        assertTrue(parser.parseCommand(ListWeddingsCommand.COMMAND_WORD + " 3") instanceof ListWeddingsCommand);
        // Test using list weddings keyword
        assertTrue(parser.parseCommand(ListWeddingsCommand.COMMAND_KEYWORD + " 3") instanceof ListWeddingsCommand);
    }

    @Test
    public void parseCommand_listTasks() throws Exception {
        assertTrue(parser.parseCommand(ListTasksCommand.COMMAND_WORD) instanceof ListTasksCommand);
        assertTrue(parser.parseCommand(ListTasksCommand.COMMAND_WORD + " 3") instanceof ListTasksCommand);
        // Test using list tasks keyword
        assertTrue(parser.parseCommand(ListTasksCommand.COMMAND_KEYWORD + " 3") instanceof ListTasksCommand);
    }
    @Test
    public void parseCommand_listTags() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD + " 3") instanceof ListTagsCommand);
        // Test using list tags keyword
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_KEYWORD + " 3") instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST), command);

        // Test using delete task keyword
        DeleteTaskCommand keywordCommand = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST), keywordCommand);
    }

    @Test
    public void parseCommand_assignVendor() throws Exception {
        AssignVendorCommand command = (AssignVendorCommand) parser.parseCommand(
                AssignVendorCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new AssignVendorCommand(INDEX_FIRST), command);

        // Test using assign vendor keyword
        AssignVendorCommand keywordCommand = (AssignVendorCommand) parser.parseCommand(
                AssignVendorCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new AssignVendorCommand(INDEX_FIRST), keywordCommand);
    }

    @Test
    public void parseCommand_unassignVendor() throws Exception {
        UnassignVendorCommand command = (UnassignVendorCommand) parser.parseCommand(
                UnassignVendorCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new UnassignVendorCommand(INDEX_FIRST), command);

        // Test using unassign vendor keyword
        UnassignVendorCommand keywordCommand = (UnassignVendorCommand) parser.parseCommand(
                UnassignVendorCommand.COMMAND_KEYWORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new UnassignVendorCommand(INDEX_FIRST), keywordCommand);
    }

    @Test
    public void parseCommand_addVendor() throws Exception {
        Vendor vendor = new VendorBuilder().build();
        String userInput = AddVendorCommand.COMMAND_WORD + " n/Alison Longwood";
        AddVendorCommand command = (AddVendorCommand) parser.parseCommand(userInput);
        assertEquals(new AddVendorCommand(vendor), command);

        // Test using add vendor keyword
        String userKeywordInput = AddVendorCommand.COMMAND_KEYWORD + " n/Alison Longwood";
        AddVendorCommand keywordCommand = (AddVendorCommand) parser.parseCommand(userKeywordInput);
        assertEquals(new AddVendorCommand(vendor), keywordCommand);
    }

    @Test
    public void parseCommand_createTask() throws Exception {
        String userInput = CreateTaskCommand.COMMAND_WORD
                + " tk/Buy groceries tk/Submit report d/2024-12-31"
                + " tk/Project meeting d/2024-10-10 d/2024-10-11";
        HashSet<Task> tasksToAdd = new HashSet<>(TypicalTasks.getTypicalTasks());

        CreateTaskCommand expectedCommand = new CreateTaskCommand(tasksToAdd);
        CreateTaskCommand command = (CreateTaskCommand) parser.parseCommand(userInput);

        assertEquals(expectedCommand, command);

        // Test using create task keyword
        String userKeywordInput = CreateTaskCommand.COMMAND_KEYWORD
                + " tk/Buy groceries tk/Submit report d/2024-12-31"
                + " tk/Project meeting d/2024-10-10 d/2024-10-11";
        CreateTaskCommand keywordCommand = (CreateTaskCommand) parser.parseCommand(userKeywordInput);
        assertEquals(expectedCommand, keywordCommand);
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
