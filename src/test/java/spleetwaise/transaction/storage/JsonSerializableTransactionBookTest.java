package spleetwaise.transaction.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.util.JsonUtil;
import spleetwaise.transaction.model.TransactionBook;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class JsonSerializableTransactionBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonSerializableTransactionBookTest");
    private static final Path TYPICAL_TXN_FILE = TEST_DATA_FOLDER.resolve("typicalTransactionBook.json");
    private static final Path INVALID_TXN_FILE = TEST_DATA_FOLDER.resolve("invalidEntryTransactionBook.json");
    private static final Path DUPLICATE_TXN_FILE = TEST_DATA_FOLDER.resolve("duplicateEntryTransactionBook.json");
    private static final Path DUPLICATE_ID_TXN_FILE = TEST_DATA_FOLDER.resolve("duplicateIdTransactionBook.json");
    private static final Person[] TEST_PEOPLE = { TypicalPersons.CARL, TypicalPersons.DANIEL, TypicalPersons.BENSON,
                                                  TypicalPersons.ALICE, TypicalPersons.BOB };
    private AddressBookModel addressBookModel;


    @BeforeEach
    public void setUp() {
        addressBookModel = new AddressBookModelManager();
        for (Person p : TEST_PEOPLE) {
            addressBookModel.addPerson(p);
        }
    }

    @Test
    public void toModelType_typicalTransactionFile_success() throws Exception {
        JsonSerializableTransactionBook dataFromFile = JsonUtil.readJsonFile(
                TYPICAL_TXN_FILE,
                JsonSerializableTransactionBook.class
        ).get();
        TransactionBook transactionBookFromFile = dataFromFile.toModelType(addressBookModel);
        TransactionBook typicalTxnBook = TypicalTransactions.getTypicalTransactionBook();
        assertEquals(transactionBookFromFile, typicalTxnBook);
    }

    @Test
    public void toModelType_invalidTransactionFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_TXN_FILE,
                JsonSerializableTransactionBook.class
        ).get();
        TransactionBook tb = dataFromFile.toModelType(addressBookModel);
        assertEquals(1, tb.getTransactionList().size());
    }

    @Test
    public void toModelType_duplicateTransactions_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_TXN_FILE,
                JsonSerializableTransactionBook.class
        ).get();

        TransactionBook tb = dataFromFile.toModelType(addressBookModel);
        assertEquals(1, tb.getTransactionList().size());
    }

    @Test
    public void toModelType_duplicateIds_throwsIllegalValueException() throws Exception {
        JsonSerializableTransactionBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_ID_TXN_FILE,
                JsonSerializableTransactionBook.class
        ).get();

        TransactionBook tb = dataFromFile.toModelType(addressBookModel);
        assertEquals(1, tb.getTransactionList().size());
    }
}
