package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ChatWindowCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.clientcommands.AddBuyerProfile;
import seedu.address.logic.commands.clientcommands.AddSellerProfile;
import seedu.address.logic.commands.clientcommands.DeleteClientProfileCommand;
import seedu.address.logic.commands.clientcommands.EditClientCommand;
import seedu.address.logic.commands.clientcommands.EditClientCommand.EditPersonDescriptor;
import seedu.address.logic.commands.clientcommands.FindClientCommand;
import seedu.address.logic.commands.clientcommands.ShowClientsCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.AddAppointmentCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.DeleteAppointmentCommand;
import seedu.address.logic.commands.clientcommands.appointmentcommands.TodayCommand;
import seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand;
import seedu.address.logic.commands.listingcommands.AddListingCommand;
import seedu.address.logic.commands.listingcommands.ClearListingCommand;
import seedu.address.logic.commands.listingcommands.DeleteListingCommand;
import seedu.address.logic.commands.listingcommands.EditListingCommand;
import seedu.address.logic.commands.listingcommands.FindListingCommand;
import seedu.address.logic.commands.listingcommands.RemoveBuyersFromListingCommand;
import seedu.address.logic.commands.listingcommands.ShowListingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class EzstatesParserTest {

    private final EzstatesParser parser = new EzstatesParser();

    @Test
    public void parseCommand_addBuyer() throws Exception {
        Buyer buyer = new PersonBuilder().buildBuyer();
        AddBuyerProfile command = (AddBuyerProfile) parser.parseCommand(PersonUtil.getAddCommand(buyer));
        assertEquals(new AddBuyerProfile(buyer), command);
    }

    @Test
    public void parseCommand_addSeller() throws Exception {
        Seller seller = (Seller) new PersonBuilder().buildSeller();
        AddSellerProfile command = (AddSellerProfile) parser.parseCommand(PersonUtil.getAddCommand(seller));
        assertEquals(new AddSellerProfile(seller), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_appointment() throws Exception {
        Person sean = new PersonBuilder().withName("Sean Kevin Dias")
                .withPhone("94351253").withEmail("sean@example.com").buildBuyer();
        Appointment appointment = new Appointment(
                new Date("01-01-24"),
                new From("0900"),
                new To("1000")
        );
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AddAppointmentCommand.COMMAND_WORD + " " + sean.getName()
                        + " " + PREFIX_DATE + appointment.getDate()
                        + " " + PREFIX_FROM + appointment.getFrom()
                        + " " + PREFIX_TO + appointment.getTo()
                );
        assertEquals(new AddAppointmentCommand(sean.getName(), appointment), command);
    }
    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_WORD + " " + ALICE.getName());
        assertEquals(new DeleteAppointmentCommand(ALICE.getName()), command);
    }
    @Test
    public void parseCommand_deleteClientProfile() throws Exception {
        DeleteClientProfileCommand command = (DeleteClientProfileCommand) parser.parseCommand(
                DeleteClientProfileCommand.COMMAND_WORD + " " + ALICE.getName());
        assertEquals(new DeleteClientProfileCommand(ALICE.getName()), command);
    }
    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().buildBuyer();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditClientCommand command = (EditClientCommand) parser.parseCommand(EditClientCommand.COMMAND_WORD + " "
                + ALICE.getName() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditClientCommand(ALICE.getName(), descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindClientCommand command = (FindClientCommand) parser.parseCommand(
                FindClientCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindClientCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    // appointment and listings commands
    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ShowClientsCommand.COMMAND_WORD) instanceof ShowClientsCommand);
        assertTrue(parser.parseCommand(ShowClientsCommand.COMMAND_WORD + " 3") instanceof ShowClientsCommand);
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

    // New Tests for Additional Commands

    @Test
    public void parseCommand_today() throws Exception {
        assertTrue(parser.parseCommand(TodayCommand.COMMAND_WORD) instanceof TodayCommand);
    }

    @Test
    public void parseCommand_showListings() throws Exception {
        assertTrue(parser.parseCommand(ShowListingsCommand.COMMAND_WORD) instanceof ShowListingsCommand);
    }

    @Test
    public void parseCommand_chatWindow() throws Exception {
        assertTrue(parser.parseCommand(ChatWindowCommand.COMMAND_WORD) instanceof ChatWindowCommand);
    }

    @Test
    public void parseCommand_addListing() throws Exception {
        Command command =
                parser.parseCommand("listing n/Pasir Ris Condo "
                        + "price/700000 "
                        + "area/75 "
                        + "address/543 Pasir Ris Street 11 "
                        + "p/2000000 "
                        + "region/EAST "
                        + "seller/ Benson");
        assertEquals(AddListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_editListing() throws Exception {
        Command command =
                parser.parseCommand("editlisting Kent Ridge Condo n/Kent Ridge HDB");
        assertEquals(EditListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_deleteListing() throws Exception {
        Command command = parser.parseCommand("deletelisting Wartion House");
        assertEquals(DeleteListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_clearListing() throws Exception {
        Command command = parser.parseCommand("clearlistings");
        assertEquals(ClearListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_findListing() throws Exception {
        Command command = parser.parseCommand("findlisting Warton Riverdale");
        assertEquals(FindListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_addBuyersToListing() throws Exception {
        Command command =
                parser.parseCommand("addlistingbuyers Warton House buyer/John Doe");
        assertEquals(AddBuyersToListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_removeBuyersFromListing() throws Exception {
        Command command =
                parser.parseCommand("removelistingbuyers Warton House buyer/John Doe");
        assertEquals(RemoveBuyersFromListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_invalidFormat_throwsParseException() {
        String invalidCommand = "editlisting";
        ParseException thrown = Assertions
                .assertThrows(ParseException.class, () -> parser.parseCommand(invalidCommand));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }

}
