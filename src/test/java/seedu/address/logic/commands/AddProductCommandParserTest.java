package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;

public class AddProductCommandParserTest {

    @Test
    public void parseProductName_whitespaceNormalization_success() throws Exception {
        String inputName1 = "big banana";
        String inputName2 = "big    banana";
        ProductName productName1 = ParserUtil.parseProductName(inputName1);
        ProductName productName2 = ParserUtil.parseProductName(inputName2);

        assertEquals(productName1, productName2);
        assertEquals("big banana", productName1.getOriginalName());
    }

    @Test
    public void parseProductName_caseNormalization_success() throws Exception {
        String inputName1 = "Apple";
        String inputName2 = "apple";
        ProductName productName1 = ParserUtil.parseProductName(inputName1);
        ProductName productName2 = ParserUtil.parseProductName(inputName2);

        assertEquals(productName1, productName2);
        assertEquals("apple", productName1.getNormalizedName());
        assertEquals("apple", productName2.getNormalizedName());
    }

    public static class AddSupplierCommandParserTest {
        @Test
        public void parseName_whitespaceNormalization_success() throws Exception {
            String inputName1 = "John Smith";
            String inputName2 = "John    Smith";
            Name name1 = ParserUtil.parseName(inputName1);
            Name name2 = ParserUtil.parseName(inputName2);

            assertEquals(name1, name2);
            assertEquals("John Smith", name1.fullName);
        }

        @Test
        public void parseSupplierName_caseNormalization_success() throws Exception {
            String inputName1 = "John Doe";
            String inputName2 = "johN   dOe";
            Name supplierName1 = ParserUtil.parseName(inputName1);
            Name supplierName2 = ParserUtil.parseName(inputName2);

            assertEquals(supplierName1, supplierName2);
            assertEquals("john doe", supplierName1.getNormalizedName());
            assertEquals("john doe", supplierName2.getNormalizedName());
        }

    }
}
