package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEL_APPT_AMY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDCON_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDCON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAllergyCommand;
import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMedConCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DelAllergyCommand;
import seedu.address.logic.commands.DelMedConCommand;
import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindMedConCommand;
import seedu.address.logic.commands.FindNricCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListPrioCommand;
import seedu.address.logic.commands.SetPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AppointmentExistsPredicate;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.MedConContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;
import seedu.address.model.person.Priority;
import seedu.address.model.person.PriorityMatchesPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private static final Nric nric = new Nric("T1234567A");

    private final AddressBookParser parser = new AddressBookParser();

    private void testCommandInsensitiveCase(String usageMessage, String commandWord) {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, usageMessage), () ->
                parser.parseCommand(commandWord.toUpperCase()));
    }

    @Test
    public void parseCommand_insensitivity() throws ParseException {
        // test if the uppercase version of command still works
        // thrown error should indicate command was correctly parsed as valid but wrong format, with appropriate msg
        testCommandInsensitiveCase(AddCommand.MESSAGE_USAGE, AddCommand.COMMAND_WORD);
        testCommandInsensitiveCase(EditCommand.MESSAGE_USAGE, EditCommand.COMMAND_WORD);
        testCommandInsensitiveCase(DeleteCommand.MESSAGE_USAGE, DeleteCommand.COMMAND_WORD);
        testCommandInsensitiveCase(FindCommand.MESSAGE_USAGE, FindCommand.COMMAND_WORD);
        testCommandInsensitiveCase(FindNricCommand.MESSAGE_USAGE, FindNricCommand.COMMAND_WORD);
        testCommandInsensitiveCase(SetPriorityCommand.MESSAGE_USAGE, SetPriorityCommand.COMMAND_WORD);
        testCommandInsensitiveCase(AddApptCommand.MESSAGE_USAGE, AddApptCommand.COMMAND_WORD);
        testCommandInsensitiveCase(DeleteApptCommand.MESSAGE_USAGE, DeleteApptCommand.COMMAND_WORD);
        testCommandInsensitiveCase(AddMedConCommand.MESSAGE_USAGE, AddMedConCommand.COMMAND_WORD);
        testCommandInsensitiveCase(DelMedConCommand.MESSAGE_USAGE, DelMedConCommand.COMMAND_WORD);
        testCommandInsensitiveCase(FindMedConCommand.MESSAGE_USAGE, FindMedConCommand.COMMAND_WORD);
        testCommandInsensitiveCase(AddAllergyCommand.MESSAGE_USAGE, AddAllergyCommand.COMMAND_WORD);
        testCommandInsensitiveCase(DelAllergyCommand.MESSAGE_USAGE, DelAllergyCommand.COMMAND_WORD);
        testCommandInsensitiveCase(ListPrioCommand.MESSAGE_USAGE, ListPrioCommand.COMMAND_WORD);

        // these don't fail format, so test if success
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

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
                DeleteCommand.COMMAND_WORD + " " + nric);
        assertEquals(new DeleteCommand(new NricMatchesPredicate(nric)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + VALID_NRIC_AMY + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(new NricMatchesPredicate(new Nric(VALID_NRIC_AMY)), descriptor), command);
    }

    @Test
    public void parseCommand_addAllergy() throws Exception {
        String commandInput = AddAllergyCommand.COMMAND_WORD + " " + NRIC_DESC_AMY
                + " " + PREFIX_ALLERGY + "Insulin";
        AddAllergyCommand command = (AddAllergyCommand) parser.parseCommand(commandInput);
        Set<Allergy> allergies = new HashSet<Allergy>();
        allergies.add(new Allergy("Insulin"));
        assertEquals(new AddAllergyCommand(new Nric(VALID_NRIC_AMY), allergies), command);
    }

    @Test
    public void parseCommand_addMedCon() throws Exception {
        String commandInput = AddMedConCommand.COMMAND_WORD + " " + NRIC_DESC_AMY
                              + " " + PREFIX_MEDCON + VALID_MEDCON_AMY;
        AddMedConCommand command = (AddMedConCommand) parser.parseCommand(commandInput);
        Set<MedCon> medcons = new HashSet<MedCon>();
        medcons.add(new MedCon(VALID_MEDCON_AMY));
        assertEquals(new AddMedConCommand(new Nric(VALID_NRIC_AMY), medcons), command);
    }

    @Test
    public void parseCommand_addAppt() throws Exception {
        Person person = new PersonBuilder().withNric(VALID_NRIC_AMY).build();
        AddApptCommand command = (AddApptCommand) parser.parseCommand(AddApptCommand.COMMAND_WORD + " "
                                                                      + APPOINTMENT_DESC_AMY + NRIC_DESC_AMY);
        assertEquals(new AddApptCommand(new NricMatchesPredicate(new Nric(VALID_NRIC_AMY)),
                                        VALID_APPOINTMENT_NAME_AMY,
                                        VALID_APPOINTMENT_DATE_AMY,
                                        VALID_APPOINTMENT_TIMEPERIOD_AMY), command);
    }

    @Test
    public void parseCommand_delAppt() throws Exception {
        Person person = new PersonBuilder().withNric(VALID_NRIC_AMY).build();
        DeleteApptCommand command = (DeleteApptCommand) parser.parseCommand(DeleteApptCommand.COMMAND_WORD + " "
                                                                            + VALID_DEL_APPT_AMY_DESC + NRIC_DESC_AMY);
        NricMatchesPredicate nric = new NricMatchesPredicate(new Nric(VALID_NRIC_AMY));
        AppointmentExistsPredicate appt = new AppointmentExistsPredicate(VALID_APPOINTMENT_DATE_AMY,
                                                                         VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertEquals(new DeleteApptCommand(nric, appt), command);
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
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listPrio() throws Exception {
        ListPrioCommand command = (ListPrioCommand) parser.parseCommand(
                ListPrioCommand.COMMAND_WORD + " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY);
        assertEquals(new ListPrioCommand(new PriorityMatchesPredicate(new Priority(VALID_PRIORITY_AMY))), command);
    }

    @Test
    public void parseCommand_findNric() throws Exception {
        FindNricCommand command = (FindNricCommand) parser.parseCommand(
                FindNricCommand.COMMAND_WORD + " " + VALID_NRIC_AMY);
        assertEquals(new FindNricCommand(new NricMatchesPredicate(AMY.getNric())), command);
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
    public void parseCommand_findMedCon() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindMedConCommand command = (FindMedConCommand) parser.parseCommand(
                FindMedConCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindMedConCommand(new MedConContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_delAllergy() throws Exception {
        String commandInput = DelAllergyCommand.COMMAND_WORD + " " + NRIC_DESC_AMY
                + " " + PREFIX_ALLERGY + "Insulin";
        DelAllergyCommand command = (DelAllergyCommand) parser.parseCommand(commandInput);
        Set<Allergy> allergies = new HashSet<Allergy>();
        allergies.add(new Allergy("Insulin"));
        assertEquals(new DelAllergyCommand(new Nric(VALID_NRIC_AMY), allergies), command);
    }

    @Test
    public void parseCommand_delMedCon() throws Exception {
        String commandInput = DelMedConCommand.COMMAND_WORD + " " + NRIC_DESC_AMY
                              + " " + PREFIX_MEDCON + VALID_MEDCON_AMY;
        DelMedConCommand command = (DelMedConCommand) parser.parseCommand(commandInput);
        Set<MedCon> medcons = new HashSet<MedCon>();
        medcons.add(new MedCon(VALID_MEDCON_AMY));
        assertEquals(new DelMedConCommand(new Nric(VALID_NRIC_AMY), medcons), command);
    }
}
