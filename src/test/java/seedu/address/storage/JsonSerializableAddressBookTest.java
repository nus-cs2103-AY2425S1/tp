package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalWeddings;
import seedu.address.testutil.WeddingBuilder;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path INVALID_WEDDING_FILE = TEST_DATA_FOLDER.resolve("invalidWeddingAddressBook.json");
    private static final Path DUPLICATE_WEDDING_FILE = TEST_DATA_FOLDER.resolve("duplicateWeddingAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidWedding_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_WEDDING_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateWeddings_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_WEDDING_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_WEDDING,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_emptyAddressBook_success() throws Exception {
        JsonSerializableAddressBook addressBook = new JsonSerializableAddressBook(
                Collections.emptyList(), Collections.emptyList());
        assertEquals(new AddressBook(), addressBook.toModelType());
    }

    @Test
    public void toModelType_validWeddingWithClients_success() throws Exception {
        // Create test data
        Person client = TypicalPersons.ALICE;
        Wedding wedding = new WeddingBuilder().withClient(client)
                .withDate("2022-01-01").withVenue("Test Venue").build();
        client.setOwnWedding(wedding);

        // Create JSON adapted versions
        List<JsonAdaptedPerson> persons = new ArrayList<>();
        persons.add(new JsonAdaptedPerson(client));
        List<JsonAdaptedWedding> weddings = new ArrayList<>();
        weddings.add(new JsonAdaptedWedding(wedding));

        // Create and convert address book
        JsonSerializableAddressBook addressBook = new JsonSerializableAddressBook(persons, weddings);
        AddressBook modelAddressBook = addressBook.toModelType();

        // Verify the conversion
        assertNotNull(modelAddressBook);
        assertEquals(1, modelAddressBook.getPersonList().size());
        assertEquals(1, modelAddressBook.getWeddingList().size());

        // Verify the wedding-client relationship
        Wedding modelWedding = modelAddressBook.getWeddingList().get(0);
        Person modelClient = modelAddressBook.getPersonList().get(0);
        assertEquals(modelWedding, modelClient.getOwnWedding());
        assertEquals(modelClient, modelWedding.getClient().getPerson());
    }

    @Test
    public void constructor_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JsonSerializableAddressBook(null));
    }
}
