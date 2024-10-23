package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.commands.AddPropertyToSellCommand;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToSellCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindBuyCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.logic.commands.FindSellCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SoldPropertyCommand;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.BuyPropertyContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneNumberContainsKeywordPredicate;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.SellPropertyContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditPersonPropertyToBuyDescriptorBuilder;
import seedu.address.testutil.EditPersonPropertyToSellDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PropertyToBuyBuilder;


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
    public void parseCommand_findName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPhoneNumber() throws Exception {
        List<String> keywords = Arrays.asList("99122112", "88118811", "90019000");
        FindPhoneNumberCommand command = (FindPhoneNumberCommand) parser.parseCommand(
                FindPhoneNumberCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPhoneNumberCommand(new PhoneNumberContainsKeywordPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findBuyProperty() throws ParseException {
        List<String> keywords = Arrays.asList("522522", "10-09", "hdb");
        FindBuyCommand command = (FindBuyCommand) parser.parseCommand(
                FindBuyCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindBuyCommand(new BuyPropertyContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findSellProperty() throws ParseException {
        List<String> keywords = Arrays.asList("522522", "10-09", "hdb");
        FindSellCommand command = (FindSellCommand) parser.parseCommand(
                FindSellCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindSellCommand(new SellPropertyContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_addSell() throws Exception {
        Index index = Index.fromOneBased(1);
        Property property = new PropertyToBuyBuilder().withHousingType("c").build();
        AddPropertyToSellCommand command =
                (AddPropertyToSellCommand) parser.parseCommand("addSell 1 ht/c sp/1500000 pc/123456 un/10-01");
        assertEquals(new AddPropertyToSellCommand(index, property), command);
    }

    @Test
    public void parseCommand_addBuy() throws Exception {
        Index index = Index.fromOneBased(1);
        Property property = new PropertyToBuyBuilder().withHousingType("c").build();
        AddPropertyToBuyCommand command =
                (AddPropertyToBuyCommand) parser.parseCommand("addBuy 1 ht/c bp/1500000 pc/123456 un/10-01");
        assertEquals(new AddPropertyToBuyCommand(index, property), command);
    }

    @Test
    public void parseCommand_delSell() throws Exception {
        Index personIndex = Index.fromOneBased(1);
        Index propertyIndex = Index.fromOneBased(1);
        DeletePropertyToSellCommand.EditPersonPropertyToSellDescriptor descriptor =
                new EditPersonPropertyToSellDescriptorBuilder().build();

        DeletePropertyToSellCommand expectedCommand = new DeletePropertyToSellCommand(personIndex,
                propertyIndex, descriptor);
        DeletePropertyToSellCommand command =
                (DeletePropertyToSellCommand) parser.parseCommand("delSell 1 1");
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_delBuy() throws Exception {
        Index personIndex = Index.fromOneBased(1);
        Index propertyIndex = Index.fromOneBased(1);
        DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor descriptor =
                new EditPersonPropertyToBuyDescriptorBuilder().build();


        DeletePropertyToBuyCommand expectedCommand = new DeletePropertyToBuyCommand(personIndex,
                propertyIndex, descriptor);
        DeletePropertyToBuyCommand command =
                (DeletePropertyToBuyCommand) parser.parseCommand("delBuy 1 1");
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_bought() throws Exception {
        Index personIndex = Index.fromOneBased(1);
        Index propertyIndex = Index.fromOneBased(1);
        String priceString = "2000000";
        Optional<Price> actualPrice = Optional.of(new Price(priceString));

        String input = "bought "
                + personIndex.getOneBased()
                + " "
                + propertyIndex.getOneBased()
                + " ap/" + priceString;

        BoughtPropertyCommand expectedCommand = new BoughtPropertyCommand(
                personIndex,
                propertyIndex,
                actualPrice);

        BoughtPropertyCommand command =
                (BoughtPropertyCommand) parser.parseCommand(input);

        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_sold() throws Exception {
        Index personIndex = Index.fromOneBased(1);
        Index propertyIndex = Index.fromOneBased(1);
        String priceString = "2000000";
        Optional<Price> actualPrice = Optional.of(new Price(priceString));

        String input = "sold "
                + personIndex.getOneBased()
                + " "
                + propertyIndex.getOneBased()
                + " ap/" + priceString;

        SoldPropertyCommand expectedCommand = new SoldPropertyCommand(
                personIndex,
                propertyIndex,
                actualPrice);

        SoldPropertyCommand command =
                (SoldPropertyCommand) parser.parseCommand(input);

        assertEquals(command, expectedCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
    @Test
    public void parseCommand_sortIndividual() throws Exception {
        Index index = Index.fromOneBased(1);
        SortIndividualCommand command = (SortIndividualCommand) parser.parseCommand("sorti 1 f/Price o/L");
        assertNotEquals(new SortIndividualCommand(index, "Price", "L"), command);
    }
}
