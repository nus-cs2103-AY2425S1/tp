package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliverySortBy;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.SupplierSortBy;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_TIME = "20-10-2024444";
    private static final String INVALID_SUPPLIER_INDEX = "0";
    private static final String INVALID_PRODUCT = "@rice#$%";
    private static final String INVALID_QUANTITY = "200 MM";
    private static final String INVALID_COST = "-1";
    private static final String VALID_TIME = "18-06-2023 17:00";
    private static final String VALID_SUPPLIER_INDEX = "1";
    private static final String VALID_PRODUCT = "Rice";
    private static final String VALID_QUANTITY = "200 kg";
    private static final String VALID_COST = "2.15";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((String) null));
    }
    @Test
    public void parseDateTime_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_TIME));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("2024-10-22"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("0-0-0 : 0:0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("10-01-2022"));
    }
    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        DateTime expectedDateTime = new DateTime(VALID_TIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(VALID_TIME));
    }
    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        DateTime expectedDateTime = new DateTime(VALID_TIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(timeWithWhitespace));
    }
    @Test
    public void parseSupplierIndex_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSupplierIndex((String) null));
    }
    @Test
    public void parseSupplierIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex(INVALID_SUPPLIER_INDEX));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex("abc"));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex(""));
    }
    @Test
    public void parseSupplierIndex_validValueWithoutWhitespace_returnsSupplierIndex() throws Exception {
        SupplierIndex expectedSupplierIndex = new SupplierIndex(VALID_SUPPLIER_INDEX);
        assertEquals(expectedSupplierIndex, ParserUtil.parseSupplierIndex(VALID_SUPPLIER_INDEX));
    }

    @Test
    public void parseSupplierIndex_validValueWithWhitespace_returnsTrimmedSupplierIndex() throws Exception {
        String supplierIndexWithWhiteSpace = WHITESPACE + VALID_SUPPLIER_INDEX + WHITESPACE;
        SupplierIndex expectedSupplierIndex = new SupplierIndex(VALID_SUPPLIER_INDEX);
        assertEquals(expectedSupplierIndex, ParserUtil.parseSupplierIndex(supplierIndexWithWhiteSpace));
    }
    @Test
    public void parseProduct_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProduct((String) null));
    }
    @Test
    public void parseProduct_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex(INVALID_PRODUCT));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex("ab@c"));
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierIndex(""));
    }
    @Test
    public void parseProduct_validValueWithoutWhitespace_returnsProduct() throws Exception {
        Product expectedProduct = new Product(VALID_PRODUCT);
        assertEquals(expectedProduct, ParserUtil.parseProduct(VALID_PRODUCT));
    }

    @Test
    public void parseProduct_validValueWithWhitespace_returnsTrimmedProduct() throws Exception {
        String productWithWhiteSpace = WHITESPACE + VALID_PRODUCT + WHITESPACE;
        Product expectedProduct = new Product(VALID_PRODUCT);
        assertEquals(expectedProduct, ParserUtil.parseProduct(productWithWhiteSpace));
    }
    @Test
    public void parseQuantity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseQuantity((String) null));
    }
    @Test
    public void parseQuantity_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(INVALID_QUANTITY));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("-200"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("0 kg"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("kg"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("200kg"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("300"));
        assertThrows(ParseException.class, () -> ParserUtil.parseQuantity("abc"));
    }
    @Test
    public void parseQuantity_validValueWithoutWhitespace_returnsQuantity() throws Exception {
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(VALID_QUANTITY));
    }
    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        String quantityWithWhiteSpace = WHITESPACE + VALID_QUANTITY + WHITESPACE;
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(quantityWithWhiteSpace));
    }
    @Test
    public void parseCost_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCost((String) null));
    }
    @Test
    public void parseCost_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCost(INVALID_COST));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("-200"));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("g"));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("20   0kg"));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("abcd"));
        assertThrows(ParseException.class, () -> ParserUtil.parseCost("    "));
    }
    @Test
    public void parseCost_validValueWithoutWhitespace_returnsCost() throws Exception {
        Cost expectedCost = new Cost(VALID_COST);
        assertEquals(expectedCost, ParserUtil.parseCost(VALID_COST));
    }
    @Test
    public void parseCost_validValueWithWhitespace_returnsTrimmedCost() throws Exception {
        String costWithWhiteSpace = WHITESPACE + VALID_COST + WHITESPACE;
        Cost expectedCost = new Cost(VALID_COST);
        assertEquals(expectedCost, ParserUtil.parseCost(costWithWhiteSpace));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_SUPPLIER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SUPPLIER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseStatus() throws ParseException {
        // null status
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStatus(null));

        // invalid status values
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus("invalid"));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseStatus(" "));

        // valid status values - different cases
        assertEquals(Status.PENDING, ParserUtil.parseStatus("pending"));
        assertEquals(Status.DELIVERED, ParserUtil.parseStatus("DELIVERED"));
        assertEquals(Status.CANCELLED, ParserUtil.parseStatus("CaNcElLeD"));
    }

    @Test
    public void parseSortOrder_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortOrder((String) null));
    }

    @Test
    public void parseSortOrder_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSortOrder("invalidSortOrder"));
    }

    @Test
    public void parseSortOrder_validValueWithoutWhitespace_returnsSortOrder() throws Exception {
        SortOrder expectedSortOrder = new SortOrder("a");
        assertEquals(expectedSortOrder, ParserUtil.parseSortOrder("a"));
    }

    @Test
    public void parseSortOrder_validValueWithWhitespace_returnsTrimmedSortOrder() throws Exception {
        String sortOrderWithWhitespace = WHITESPACE + "d" + WHITESPACE;
        SortOrder expectedSortBy = new SortOrder("d");
        assertEquals(expectedSortBy, ParserUtil.parseSortOrder(sortOrderWithWhitespace));
    }

    @Test
    public void parseDeliverySortBy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeliverySortBy((String) null));
    }

    @Test
    public void parseDeliverySortBy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeliverySortBy("invalidSortBy"));
    }

    @Test
    public void parseDeliverySortBy_validValueWithoutWhitespace_returnsDeliverySortBy() throws Exception {
        DeliverySortBy expectedSortBy = new DeliverySortBy("c");
        assertEquals(expectedSortBy, ParserUtil.parseDeliverySortBy("c"));
    }

    @Test
    public void parseDeliverySortBy_validValueWithWhitespace_returnsTrimmedDeliverySortBy() throws Exception {
        String sortByWithWhitespace = WHITESPACE + "d" + WHITESPACE;
        DeliverySortBy expectedSortBy = new DeliverySortBy("d");
        assertEquals(expectedSortBy, ParserUtil.parseDeliverySortBy(sortByWithWhitespace));
    }

    @Test
    public void parseSupplierSortBy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSupplierSortBy((String) null));
    }

    @Test
    public void parseSupplierSortBy_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSupplierSortBy("invalidSortBy"));
    }

    @Test
    public void parseSupplierSortBy_validValueWithoutWhitespace_returnsSupplierSortBy() throws Exception {
        SupplierSortBy expectedSortBy = new SupplierSortBy("n");
        assertEquals(expectedSortBy, ParserUtil.parseSupplierSortBy("n"));
    }

    @Test
    public void parseSupplierSortBy_validValueWithWhitespace_returnsTrimmedSupplierSortBy() throws Exception {
        String sortByWithWhitespace = WHITESPACE + "n" + WHITESPACE;
        SupplierSortBy expectedSortBy = new SupplierSortBy("n");
        assertEquals(expectedSortBy, ParserUtil.parseSupplierSortBy(sortByWithWhitespace));
    }
}
