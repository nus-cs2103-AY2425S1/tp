package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATCHINGPRICE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_DATE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETING_TITLE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_HDB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.DANIEL;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalMeetings.MEETING_ADMIRALTY;
import static seedu.address.testutil.TypicalProperty.ADMIRALTY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddBuyerCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.logic.commands.AddSellerCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteBuyerCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.commands.DeletePropertyCommand;
import seedu.address.logic.commands.DeleteSellerCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterClientCommand;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListBuyersCommand;
import seedu.address.logic.commands.ListClientsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListMeetingsCommand;
import seedu.address.logic.commands.ListPropertiesCommand;
import seedu.address.logic.commands.ListSellersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.MeetingBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PropertyBuilder;

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
    public void parseCommand_addBuyer() throws Exception {
        Buyer alice = new ClientBuilder(ALICE).withEmail(ALICE.getEmail().toString())
                .withPhone(ALICE.getPhone().toString()).buildBuyer();
        AddBuyerCommand command = (AddBuyerCommand) parser.parseCommand(
                AddBuyerCommand.COMMAND_WORD + " " + PREFIX_NAME + ALICE.getName() + " "
                + PREFIX_PHONE + ALICE.getPhone() + " " + PREFIX_EMAIL + ALICE.getEmail()
        );

        assertEquals(new AddBuyerCommand(alice), command);
    }

    @Test
    public void parseCommand_addSeller() throws Exception {
        Seller daniel = new ClientBuilder(DANIEL).withEmail(DANIEL.getEmail().toString())
                .withPhone(DANIEL.getPhone().toString()).buildSeller();
        AddSellerCommand command = (AddSellerCommand) parser.parseCommand(
                AddSellerCommand.COMMAND_WORD + " " + PREFIX_NAME + DANIEL.getName() + " "
                        + PREFIX_PHONE + DANIEL.getPhone() + " " + PREFIX_EMAIL + DANIEL.getEmail()
        );

        assertEquals(new AddSellerCommand(daniel), command);
    }

    @Test
    public void parseCommand_deleteBuyer() throws Exception {
        final String phoneNumber = "92345678";
        DeleteBuyerCommand command = (DeleteBuyerCommand) parser.parseCommand(
                DeleteBuyerCommand.COMMAND_WORD + " " + PREFIX_PHONE + phoneNumber);
        assertEquals(new DeleteBuyerCommand(new Phone(phoneNumber)), command);
    }

    @Test
    public void parseCommand_deleteSeller() throws Exception {
        final String phoneNumber = "92345678";
        DeleteSellerCommand command = (DeleteSellerCommand) parser.parseCommand(
                DeleteSellerCommand.COMMAND_WORD + " " + PREFIX_PHONE + phoneNumber);
        assertEquals(new DeleteSellerCommand(new Phone(phoneNumber)), command);
    }

    @Test
    public void parseCommand_addProperty() throws Exception {
        Property property = new PropertyBuilder(ADMIRALTY).build();
        AddPropertyCommand command = (AddPropertyCommand) parser.parseCommand(
                AddPropertyCommand.COMMAND_WORD + " " + PREFIX_POSTALCODE + ADMIRALTY.getPostalCode() + " "
                        + PREFIX_UNITNUMBER + ADMIRALTY.getUnit() + " " + PREFIX_TYPE + ADMIRALTY.getType()
                        + " " + PREFIX_ASK + ADMIRALTY.getAsk() + " " + PREFIX_BID + ADMIRALTY.getBid()
        );

        assertEquals(new AddPropertyCommand(property), command);
    }

    @Test
    public void parseCommand_filterProperty() throws Exception {
        FilterPropertyCommand command = (FilterPropertyCommand) parser.parseCommand(
                FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + VALID_TYPE_HDB
        );

        assertEquals(new FilterPropertyCommand(new Type(VALID_TYPE_HDB),
                new MatchingPrice(VALID_MATCHINGPRICE_ADMIRALTY),
                new MatchingPrice(VALID_MATCHINGPRICE_BEDOK)), command);
    }

    @Test
    public void parseCommand_deleteProperty() throws Exception {
        DeletePropertyCommand command = (DeletePropertyCommand) parser.parseCommand(
                DeletePropertyCommand.COMMAND_WORD + " " + PREFIX_POSTALCODE + VALID_POSTALCODE_ADMIRALTY
                        + " " + PREFIX_UNITNUMBER + VALID_UNIT_ADMIRALTY);
        PostalCode postalCode = new PostalCode(VALID_POSTALCODE_ADMIRALTY);
        Unit unitNumber = new Unit(VALID_UNIT_ADMIRALTY);
        assertEquals(new DeletePropertyCommand(postalCode, unitNumber), command);
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
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " k/buyers")
                instanceof ListBuyersCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " k/sellers")
                instanceof ListSellersCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " k/properties")
                instanceof ListPropertiesCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " k/clients")
                instanceof ListClientsCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " k/meetings")
                instanceof ListMeetingsCommand);
    }

    @Test
    public void parseCommand_filterClient() throws Exception {
        FilterClientCommand command = (FilterClientCommand) parser.parseCommand(FilterClientCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_NAME_AMY);
        assertEquals(new FilterClientCommand(new Name(VALID_NAME_AMY)), command);
    }

    @Test
    public void parseCommand_addMeeting() throws Exception {
        Meeting meeting = new MeetingBuilder(MEETING_ADMIRALTY).build();
        AddMeetingCommand command = (AddMeetingCommand) parser.parseCommand(
                AddMeetingCommand.COMMAND_WORD + " " + PREFIX_MEETING_TITLE + MEETING_ADMIRALTY.getMeetingTitle()
                        + " " + PREFIX_MEETING_DATE + MEETING_ADMIRALTY.getMeetingDate() + " " + PREFIX_BUYER_PHONE
                        + VALID_PHONE_AMY + " " + PREFIX_SELLER_PHONE + VALID_PHONE_BOB + " "
                        + PREFIX_TYPE + VALID_TYPE_CONDO
                        + " " + PREFIX_POSTALCODE + VALID_POSTALCODE_ADMIRALTY
        );

        assertEquals(new AddMeetingCommand(meeting), command);
    }

    @Test
    public void parseCommand_deleteMeeting() throws Exception {
        DeleteMeetingCommand command = (DeleteMeetingCommand) parser.parseCommand(
            DeleteMeetingCommand.COMMAND_WORD + " " + PREFIX_MEETING_TITLE
                    + VALID_MEETING_TITLE_ADMIRALTY + " " + PREFIX_MEETING_DATE + VALID_MEETING_DATE_ADMIRALTY);
        assertEquals(new DeleteMeetingCommand(
                new MeetingTitle(VALID_MEETING_TITLE_ADMIRALTY), new MeetingDate(VALID_MEETING_DATE_ADMIRALTY)
        ), command);
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
