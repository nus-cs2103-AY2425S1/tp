package seedu.internbuddy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_STATUS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.internbuddy.testutil.Assert.assertThrows;
import static seedu.internbuddy.testutil.TypicalApplications.SWE_APPLICATION;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.commands.AddCommand;
import seedu.internbuddy.logic.commands.ApplyCommand;
import seedu.internbuddy.logic.commands.ClearCommand;
import seedu.internbuddy.logic.commands.DeleteCommand;
import seedu.internbuddy.logic.commands.EditCommand;
import seedu.internbuddy.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.internbuddy.logic.commands.ExitCommand;
import seedu.internbuddy.logic.commands.FindCommand;
import seedu.internbuddy.logic.commands.HelpCommand;
import seedu.internbuddy.logic.commands.ListCommand;
import seedu.internbuddy.logic.commands.UpdateCommand;
import seedu.internbuddy.logic.parser.exceptions.ParseException;
import seedu.internbuddy.model.application.AppStatus;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.NameContainsKeywordsPredicate;
import seedu.internbuddy.testutil.CompanyBuilder;
import seedu.internbuddy.testutil.CompanyUtil;
import seedu.internbuddy.testutil.EditCompanyDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Company company = new CompanyBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(CompanyUtil.getAddCommand(company));
        assertEquals(new AddCommand(company), command);
    }

    @Test
    public void parseCommand_apply() throws Exception {
        ApplyCommand command = (ApplyCommand) parser.parseCommand(ApplyCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COMPANY.getOneBased() + " " + PREFIX_NAME + "Full Stack Engineer" + " "
                + PREFIX_DESCRIPTION + "Requires: ReactJS and ExpressJS");
        assertEquals(new ApplyCommand(INDEX_FIRST_COMPANY, SWE_APPLICATION), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_COMPANY.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_COMPANY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Company company = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(company).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_COMPANY.getOneBased() + " " + CompanyUtil.getEditCompanyDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_COMPANY, descriptor), command);
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
    public void parseCommand_update() throws Exception {
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + PREFIX_COMPANY_INDEX + INDEX_FIRST_COMPANY.getOneBased() + " "
                + PREFIX_APP_INDEX + INDEX_FIRST_COMPANY.getOneBased() + " "
                + PREFIX_APP_STATUS + "OA");
        AppStatus appStatus = new AppStatus("OA");
        assertEquals(new UpdateCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_COMPANY, appStatus), command);
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
