package spleetwaise.transaction.storage.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.storage.JsonAdaptedPerson;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.exceptions.IllegalValueException;
import spleetwaise.commons.util.IdUtil;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.DateUtil;
import spleetwaise.transaction.testutil.TransactionBuilder;

public class JsonAdaptedTransactionTest {

    private static final String VALID_DESCRIPTION = "description";
    private static final String INVALID_DESCRIPTION = "a".repeat(Description.MAX_LENGTH + 1);
    private static final Person[] TEST_PEOPLE = { TypicalPersons.BENSON };
    private static final JsonAdaptedAmount VALID_AMT = new JsonAdaptedAmount("-10.00");
    private static final JsonAdaptedCategory VALID_CAT = new JsonAdaptedCategory("FOOD");
    private static final JsonAdaptedCategory INVALID_CAT = new JsonAdaptedCategory(" FOOD");
    private static final List<JsonAdaptedCategory> VALID_CATS = new ArrayList<>(List.of(VALID_CAT));
    private static final boolean VALID_STAT = false;
    private AddressBookModel addressBookModel;


    @BeforeEach
    public void setUp() {
        addressBookModel = new AddressBookModelManager();
        for (Person p : TEST_PEOPLE) {
            addressBookModel.addPerson(p);
        }
    }

    /**
     * Test the constructor of a JsonAdaptedTransaction with valid values.
     */
    @Test
    public void testConstructor() throws IllegalValueException {
        // Test Json constructor
        JsonAdaptedPerson jPerson = new JsonAdaptedPerson(TypicalPersons.BENSON);
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        Transaction t = new Transaction(jTrans.getId(), jPerson.toModelType(), VALID_AMT.toModelType(),
                new Description(VALID_DESCRIPTION),
                new Date(DateUtil.VALID_DATE),
                TransactionBuilder.DEFAULT_CATEGORY_SET,
                new Status(VALID_STAT)
        );

        assertEquals(t, jTrans.toModelType(addressBookModel));

        // Test transaction constructor
        jTrans = new JsonAdaptedTransaction(t);

        assertEquals(t, jTrans.toModelType(addressBookModel));
        assertEquals(VALID_AMT.getAmount(), jTrans.getAmount().getAmount());
        assertEquals(VALID_DESCRIPTION, jTrans.getDescription());
        assertEquals(DateUtil.VALID_DATE, jTrans.getDate());
        assertEquals(VALID_STAT, jTrans.getIsDone());
    }

    @Test
    public void testConstructor_nullId() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(null, TypicalPersons.BENSON.getId(), VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidIdFormat() {
        // Test an invalid ID format scenario
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction("invalid-id", TypicalPersons.BENSON.getId(),
                VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidPerson() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), null, VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidPersonId() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.ALICE.getId(),
                VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidAmount() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                null,
                VALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_nullDescription() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                null,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_nullDate() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                VALID_DESCRIPTION,
                null,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidDate() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                VALID_DESCRIPTION,
                DateUtil.INVALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void testConstructor_invalidDescription() {
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                INVALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                VALID_CATS
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }

    @Test
    public void toModelType_invalidCats_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCats = new ArrayList<>(VALID_CATS);
        invalidCats.add(INVALID_CAT);
        JsonAdaptedTransaction jTrans = new JsonAdaptedTransaction(IdUtil.getId(), TypicalPersons.BENSON.getId(),
                VALID_AMT,
                INVALID_DESCRIPTION,
                DateUtil.VALID_DATE,
                VALID_STAT,
                invalidCats
        );
        assertThrows(IllegalValueException.class, () -> jTrans.toModelType(addressBookModel));
    }
}
