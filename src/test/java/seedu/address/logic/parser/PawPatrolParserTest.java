package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PET;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddOwnerCommand;
import seedu.address.logic.commands.AddPetCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteOwnerCommand;
import seedu.address.logic.commands.DeletePetCommand;
import seedu.address.logic.commands.EditOwnerCommand;
import seedu.address.logic.commands.EditOwnerCommand.EditOwnerDescriptor;
import seedu.address.logic.commands.EditPetCommand;
import seedu.address.logic.commands.EditPetCommand.EditPetDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindOwnerCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.FindPetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOwnerCommand;
import seedu.address.logic.commands.ListPetCommand;
import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.pet.Pet;
import seedu.address.model.pet.PetNameContainsKeywordsPredicate;
import seedu.address.testutil.EditOwnerDescriptorBuilder;
import seedu.address.testutil.EditPetDescriptorBuilder;
import seedu.address.testutil.OwnerBuilder;
import seedu.address.testutil.OwnerUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PetBuilder;
import seedu.address.testutil.PetUtil;

public class PawPatrolParserTest {

    private final PawPatrolParser parser = new PawPatrolParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addOwner() throws Exception {
        Owner owner = new OwnerBuilder().build();
        AddOwnerCommand command = (AddOwnerCommand) parser.parseCommand(OwnerUtil.getAddOwnerCommand(owner));
        assertEquals(new AddOwnerCommand(owner), command);
    }

    @Test
    public void parseCommand_addPet() throws Exception {
        Pet pet = new PetBuilder().build();
        AddPetCommand command = (AddPetCommand) parser.parseCommand(PetUtil.getAddPetCommand(pet));
        assertEquals(new AddPetCommand(pet), command);
    }

    @Test
    public void parseCommand_link() throws Exception {
        LinkCommand command = (LinkCommand) parser.parseCommand(
            LinkCommand.COMMAND_WORD + " o" + INDEX_FIRST_OWNER.getOneBased() + " " + PREFIX_TO + "p"
            + INDEX_FIRST_PET.getOneBased());
        assertEquals(new LinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET))), command);
    }

    @Test
    public void parseCommand_unlink() throws Exception {
        UnlinkCommand command = (UnlinkCommand) parser.parseCommand(
                UnlinkCommand.COMMAND_WORD + " o" + INDEX_FIRST_OWNER.getOneBased() + " " + PREFIX_TO + "p"
                        + INDEX_FIRST_PET.getOneBased());
        assertEquals(new UnlinkCommand(INDEX_FIRST_OWNER, new HashSet<>(Arrays.asList(INDEX_FIRST_PET))), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteOwner() throws Exception {
        DeleteOwnerCommand command = (DeleteOwnerCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " o" + INDEX_FIRST_OWNER.getOneBased());
        assertEquals(new DeleteOwnerCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deletePet() throws Exception {
        DeletePetCommand command = (DeletePetCommand) parser.parseCommand(
                DeletePetCommand.COMMAND_WORD + " p" + INDEX_FIRST_PET.getOneBased());
        assertEquals(new DeletePetCommand(INDEX_FIRST_PET), command);
    }

    @Test
    public void parseCommand_editOwner() throws Exception {
        Owner owner = new OwnerBuilder().build();
        EditOwnerDescriptor descriptor = new EditOwnerDescriptorBuilder(owner).build();
        EditOwnerCommand command = (EditOwnerCommand) parser.parseCommand(EditOwnerCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + OwnerUtil.getEditOwnerDescriptorDetails(descriptor));
        assertEquals(new EditOwnerCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editPet() throws Exception {
        Pet pet = new PetBuilder().build();
        EditPetDescriptor descriptor = new EditPetDescriptorBuilder(pet).build();
        EditPetCommand command = (EditPetCommand) parser.parseCommand(EditPetCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PetUtil.getEditPetDescriptorDetails(descriptor));
        assertEquals(new EditPetCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
            FindPersonCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findOwner() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindOwnerCommand command = (FindOwnerCommand) parser.parseCommand(
            FindOwnerCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindOwnerCommand(new OwnerNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPet() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPetCommand command = (FindPetCommand) parser.parseCommand(
            FindPetCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPetCommand(new PetNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listOwners() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " owners") instanceof ListOwnerCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(ListCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_listPets() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " pets") instanceof ListPetCommand);
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(ListCommand.COMMAND_WORD));
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
