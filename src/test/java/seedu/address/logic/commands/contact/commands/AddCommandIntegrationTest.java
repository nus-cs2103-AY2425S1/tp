package seedu.address.logic.commands.contact.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventManager(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                String.format(AddCommand.MESSAGE_DUPLICATE_PERSON, "phone", personInList.getPhone().value));
    }

    @Test
    public void execute_duplicatePersonSameEmail_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(1);
        Person personWithSameEmail = new PersonBuilder().withPhone("90123144")
                .withEmail(personInList.getEmail().value).build();
        assertCommandFailure(new AddCommand(personWithSameEmail), model,
                String.format(AddCommand.MESSAGE_DUPLICATE_PERSON, "email", personInList.getEmail().value));
    }

    @Test
    public void execute_duplicatePersonSameTelegram_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(2);
        Person personWithSameTelegram = new PersonBuilder().withPhone("90123144")
                .withTelegramUsername(personInList.getTelegramUsername().telegramUsername).build();
        assertCommandFailure(new AddCommand(personWithSameTelegram), model,
                String.format(AddCommand.MESSAGE_DUPLICATE_PERSON,
                        "telegram", personInList.getTelegramUsername().telegramUsername));
    }
}
