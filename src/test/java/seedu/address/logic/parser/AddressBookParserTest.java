package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RENTAL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.AddRentalCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteClientCommand;
import seedu.address.logic.commands.DeleteRentalCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditClientCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NextCommandHistoryCommand;
import seedu.address.logic.commands.PreviousCommandHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.RentalInformationBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_cadd() throws Exception {
        Client client = new PersonBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(PersonUtil.getAddCommand(client));
        assertEquals(new AddClientCommand(client), command);
    }

    @Test
    public void parseCommand_radd() throws Exception {
        AddRentalCommand command = (AddRentalCommand) parser.parseCommand(AddRentalCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_ADDRESS
                + RentalInformationBuilder.DEFAULT_ADDRESS + " " + PREFIX_RENTAL_START_DATE
                + RentalInformationBuilder.DEFAULT_RENTAL_START_DATE + " " + PREFIX_RENTAL_END_DATE
                + RentalInformationBuilder.DEFAULT_RENTAL_START_DATE + " " + PREFIX_RENT_DUE_DATE
                + RentalInformationBuilder.DEFAULT_RENT_DUE_DATE + " " + PREFIX_MONTHLY_RENT
                + RentalInformationBuilder.DEFAULT_MONTHLY_RENT + " " + PREFIX_DEPOSIT
                + RentalInformationBuilder.DEFAULT_DEPOSIT + " " + PREFIX_CUSTOMER_LIST
                + RentalInformationBuilder.DEFAULT_CUSTOMER_LIST);
        assertEquals(new AddRentalCommand(INDEX_FIRST_PERSON, new RentalInformationBuilder().build()), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_cdelete() throws Exception {
        DeleteClientCommand command = (DeleteClientCommand) parser.parseCommand(
                DeleteClientCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteClientCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_rdelete() throws Exception {
        String userInput = DeleteRentalCommand.COMMAND_WORD + " "
                + PREFIX_CLIENT_INDEX + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_RENTAL_INDEX + INDEX_FIRST_RENTAL.getOneBased();
        DeleteRentalCommand command = (DeleteRentalCommand) parser.parseCommand(userInput);
        assertEquals(new DeleteRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL), command);
    }

    @Test
    public void parseCommand_cedit() throws Exception {
        Client client = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(client).build();
        EditClientCommand command = (EditClientCommand) parser.parseCommand(EditClientCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditClientCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    //TODO: parseCommand_find()

    @Test
    public void parsePrevCommandHistory() throws ParseException {
        PreviousCommandHistoryCommand previousCommandHistoryCommand =
                (PreviousCommandHistoryCommand) parser.parseCommand("upCommand");
        assertEquals(new PreviousCommandHistoryCommand(), previousCommandHistoryCommand);
    }
    @Test
    public void parseNextCommandHistory() throws ParseException {
        NextCommandHistoryCommand nextCommandHistoryCommand =
                (NextCommandHistoryCommand) parser.parseCommand("downCommand");
        assertEquals(new NextCommandHistoryCommand(), nextCommandHistoryCommand);
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
