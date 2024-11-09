package spleetwaise.commons.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static spleetwaise.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static spleetwaise.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static spleetwaise.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.AddressBook;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.util.IdUtil;

public class CommandUtilTest {

    @BeforeEach
    void setUp() {
        // Initialize CommonModelManager with a typical address book for consistent test data
        CommonModelManager.initialise(new AddressBookModelManager(getTypicalAddressBook()), null);
        IdUtil.setDeterminate(true); // Ensures determinism in tests
    }

    @AfterEach
    void tearDown() {
        // Reset IdUtil determinism setting after each test
        IdUtil.setDeterminate(false);
    }

    @Test
    void getPersonAtIndex_validIndex_returnsPerson() throws CommandException {
        CommonModelManager model = CommonModelManager.getInstance();

        // Retrieve the expected person using the first typical index
        Person expectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Retrieve person using CommandUtil method
        Person personRetrieved = CommandUtil.getPersonByFilteredPersonListIndex(model, INDEX_FIRST_PERSON);

        // Verify that the retrieved person matches the expected person
        assertNotNull(personRetrieved);
        assertEquals(expectedPerson, personRetrieved);
    }

    @Test
    void getPersonAtIndex_secondValidIndex_returnsCorrectPerson() throws CommandException {
        CommonModelManager model = CommonModelManager.getInstance();

        // Retrieve the expected person using the second typical index
        Person expectedPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Retrieve person using CommandUtil method
        Person personRetrieved = CommandUtil.getPersonByFilteredPersonListIndex(model, INDEX_SECOND_PERSON);

        // Verify that the retrieved person matches the expected person
        assertNotNull(personRetrieved);
        assertEquals(expectedPerson, personRetrieved);
    }

    @Test
    void getPersonAtIndex_outOfBoundsIndex_throwsCommandException() {
        CommonModelManager model = CommonModelManager.getInstance();

        // Create an index that is out of bounds
        Index outOfBoundsIndex = Index.fromZeroBased(model.getFilteredPersonList().size());

        // Assert that CommandUtil throws a CommandException when an out-of-bounds index is provided
        CommandException exception = assertThrows(CommandException.class, () ->
                CommandUtil.getPersonByFilteredPersonListIndex(model, outOfBoundsIndex)
        );

        // Verify the exception message
        assertEquals(
                String.format(
                        Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        outOfBoundsIndex.getOneBased()
                ),
                exception.getMessage()
        );
    }

    @Test
    void getPersonAtIndex_emptyList_throwsCommandException() {
        // Reinitialize CommonModelManager with an empty address book
        CommonModelManager.initialise(new AddressBookModelManager(new AddressBook()), null);
        CommonModelManager model = CommonModelManager.getInstance();

        // Assert that CommandUtil throws a CommandException for an empty list
        CommandException exception = assertThrows(CommandException.class, () ->
                CommandUtil.getPersonByFilteredPersonListIndex(model, INDEX_FIRST_PERSON)
        );

        // Verify the exception message
        assertEquals(
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, INDEX_FIRST_PERSON.getOneBased()),
                exception.getMessage()
        );
    }
}
