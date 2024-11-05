package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddBuyersToListingCommand;
import seedu.address.logic.commands.AddListingCommand;
import seedu.address.logic.commands.ClearListingCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteListingCommand;
import seedu.address.logic.commands.EditListingCommand;
import seedu.address.logic.commands.FindListingCommand;
import seedu.address.logic.commands.RemoveBuyersFromListingCommand;
import seedu.address.logic.commands.ShowListingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListingParserTest {

    private ListingParser parser = new ListingParser();

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
                parser.parseCommand("editListing Kent Ridge Condo n/Kent Ridge HDB");
        assertEquals(EditListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_deleteListing() throws Exception {
        Command command = parser.parseCommand("deleteListing n/Wartion House");
        assertEquals(DeleteListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_clearListing() throws Exception {
        Command command = parser.parseCommand("clearListings");
        assertEquals(ClearListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_findListing() throws Exception {
        Command command = parser.parseCommand("findListings n/Warton Riverdale");
        assertEquals(FindListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_showListings() throws Exception {
        Command command = parser.parseCommand("showlistings");
        assertEquals(ShowListingsCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_addBuyersToListing() throws Exception {
        Command command =
                parser.parseCommand("addBuyersToListing Warton House buyer/John Doe");
        assertEquals(AddBuyersToListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_removeBuyersFromListing() throws Exception {
        Command command =
                parser.parseCommand("removeBuyersFromListing Warton House buyer/John Doe");
        assertEquals(RemoveBuyersFromListingCommand.class, command.getClass());
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand("unknownCommand"),
                MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void parseCommand_invalidFormat_throwsParseException() {
        String invalidCommand = "editListing";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parseCommand(invalidCommand));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
