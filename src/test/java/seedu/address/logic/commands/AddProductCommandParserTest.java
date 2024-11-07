package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.product.ProductName;

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

}
