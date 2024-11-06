package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.PersonBuilder;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModelManager;

/**
 * Contains integration tests (interaction with the CommonModel) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private AddressBookModel model;

    @BeforeEach
    public void setUp() {
        model = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook());
        CommonModelManager.initialise(model, null);
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        AddressBookModel expectedModel = new AddressBookModelManager(model.getAddressBook());
        expectedModel.addPerson(validPerson);

        CommandTestUtil.assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel
        );
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        CommandTestUtil.assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON
        );
    }

}
