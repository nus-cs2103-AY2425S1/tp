package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_delete() throws Exception {
        Person person = new PersonBuilder().build();
        String name = person.getName().fullName;
        DeleteCommand command = (DeleteCommand) parser.parseCommand(DeleteCommand.COMMAND_WORD + " n/" + name);
        assertEquals(new DeleteCommand(name), command);
    }
}
