package spleetwaise.transaction.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.person.NameContainsKeywordsPredicate;
import spleetwaise.address.model.person.Person;
import spleetwaise.address.model.person.Phone;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.parser.BaseParserUtil;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.testutil.Assert;
import spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_AMOUNT = "+123.456";
    private static final String VALID_AMOUNT = "-123.45";

    private static final String INVALID_DESCRIPTION = "";
    private static final String VALID_DESCRIPTION = "description";

    private static final String INVALID_DATE = "112024";
    private static final String VALID_DATE = "01012024";

    private static final String INVALID_STATUS = "invalid";
    private static final String INVALID_AMTSIGN = "invalid";

    private static final Phone TEST_PHONE = TypicalPersons.ALICE.getPhone();
    private static final Index TEST_INDEX = Index.fromOneBased(1);

    @BeforeEach
    void setUp() {
        assertTrue(BaseParserUtil.class.isAssignableFrom(ParserUtil.class));
    }

    @Test
    public void parseIndex_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIndex(null));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, ParserUtil.MESSAGE_INVALID_INDEX, ()
                -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(
                TypicalIndexes.INDEX_FIRST_TRANSACTION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseAmount_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAmount(null));
    }

    @Test
    public void parseAmount_invalidAmount_exceptionThrown() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAmount(INVALID_AMOUNT));
    }

    @Test
    public void parseAmount_validAmount_success() {
        Amount result = assertDoesNotThrow(() -> ParserUtil.parseAmount(VALID_AMOUNT));
        assertEquals(new Amount("-123.45"), result);
    }

    @Test
    public void parseDescription_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parseDescription_invalidDescription_exceptionThrown() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validDescription_success() {
        Description result = assertDoesNotThrow(() -> ParserUtil.parseDescription(VALID_DESCRIPTION));
        assertEquals(new Description("description"), result);
    }

    @Test
    public void parseDate_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidDate_exceptionThrown() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validDate_success() {
        Date result = assertDoesNotThrow(() -> ParserUtil.parseDate(VALID_DATE));
        assertEquals(new Date("01012024"), result);
    }

    @Test
    public void parseStatus_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));
    }

    @Test
    public void parseStatus_invalidStatus_exceptionThrown() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseStatus(INVALID_STATUS));
    }

    @Test
    public void parseStatus_validStatus_success() {
        Status result = assertDoesNotThrow(() -> ParserUtil.parseStatus(Status.DONE_STATUS));
        assertEquals(new Status(Status.DONE_STATUS), result);
        assertEquals(new Status(true), result);

        result = assertDoesNotThrow(() -> ParserUtil.parseStatus(Status.NOT_DONE_STATUS));
        assertEquals(new Status(Status.NOT_DONE_STATUS), result);
        assertEquals(new Status(false), result);
    }

    @Test
    public void parseAmountSign_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAmountSign(null));
    }

    @Test
    public void parseAmountSign_invalidStatus_exceptionThrown() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAmountSign(INVALID_AMTSIGN));
    }

    @Test
    public void parseAmountSign_validStatus_success() {
        AmountSignFilterPredicate result = assertDoesNotThrow(() ->
                ParserUtil.parseAmountSign(AmountSignFilterPredicate.POSITIVE_SIGN));
        assertEquals(new AmountSignFilterPredicate(AmountSignFilterPredicate.POSITIVE_SIGN), result);

        result = assertDoesNotThrow(() -> ParserUtil.parseAmountSign(AmountSignFilterPredicate.NEGATIVE_SIGN));
        assertEquals(new AmountSignFilterPredicate(AmountSignFilterPredicate.NEGATIVE_SIGN), result);
    }

    @Test
    public void getPersonFromPhone_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.getPersonFromPhone(null));
    }

    @Test
    public void getPersonFromPhone_personNotFound_exceptionThrown() {
        AddressBookModel abModel = new AddressBookModelManager();
        CommonModelManager.initialise(abModel, null);

        Assert.assertThrows(ParseException.class, () -> ParserUtil.getPersonFromPhone(TEST_PHONE));
    }

    @Test
    public void getPersonFromPhone_personFound_success() {
        AddressBookModel abModel = new AddressBookModelManager();
        CommonModelManager.initialise(abModel, null);
        abModel.addPerson(TypicalPersons.ALICE);

        Person testPerson = assertDoesNotThrow(() -> ParserUtil.getPersonFromPhone(TEST_PHONE));
        assertEquals(TypicalPersons.ALICE, testPerson);
    }


    @Test
    public void getPersonByFilteredPersonListIndex_null_exceptionThrown() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.getPersonByFilteredPersonListIndex(null));
    }

    @Test
    public void getPersonByFilteredPersonListIndex_personNotFound_exceptionThrown() {
        AddressBookModel abModel = new AddressBookModelManager();
        CommonModelManager.initialise(abModel, null);

        Assert.assertThrows(ParseException.class, () -> ParserUtil.getPersonByFilteredPersonListIndex(TEST_INDEX));
    }

    @Test
    public void getPersonByFilteredPersonListIndex_personFound_success() {
        AddressBookModel abModel = new AddressBookModelManager();
        CommonModelManager.initialise(abModel, null);
        abModel.addPerson(TypicalPersons.ALICE);

        Person testPerson = assertDoesNotThrow(() -> ParserUtil.getPersonByFilteredPersonListIndex(TEST_INDEX));
        assertEquals(TypicalPersons.ALICE, testPerson);
    }

    @Test
    public void getPersonByFilteredPersonListIndexAfterFilter_personNotFound_exceptionThrown() {
        AddressBookModel abModel = new AddressBookModelManager();
        abModel.setAddressBook(TypicalPersons.getTypicalAddressBook());
        CommonModelManager.initialise(abModel, null);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("none"));

        CommonModelManager.getInstance().updateFilteredPersonList(predicate);

        Assert.assertThrows(ParseException.class, () -> ParserUtil.getPersonByFilteredPersonListIndex(TEST_INDEX));
    }

    @Test
    public void getPersonByFilteredPersonListIndexAfterFilter_personFound_success() {
        AddressBookModel abModel = new AddressBookModelManager();
        abModel.setAddressBook(TypicalPersons.getTypicalAddressBook());
        CommonModelManager.initialise(abModel, null);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Elle"));

        CommonModelManager.getInstance().updateFilteredPersonList(predicate);

        Person testPerson = assertDoesNotThrow(() -> ParserUtil.getPersonByFilteredPersonListIndex(TEST_INDEX));
        assertEquals(TypicalPersons.ELLE, testPerson);
    }
}
