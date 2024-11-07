package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.supplier.Name;

public class AddSupplierCommandParserTest {
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
