package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import seedu.address.model.company.Company;
import seedu.address.testutil.CompanyBuilder;
public class AddCompanyCommandParserTest {
    private AddCompanyCommandParser parser = new AddCompanyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Company expectedCompany = new CompanyBuilder().build();
    }
}
