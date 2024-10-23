package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.company.Company;



/**
 * Deletes a company identified using it's displayed index from the address book.
 */
public class DeleteCompanyCommand extends DeleteCommand<Company> {

    public static final String ENTITY_WORD = "company";
    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Company %1$s - %2$s - %3$s - %4$s "
            + "has been successfully deleted.";

    public DeleteCompanyCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    protected List<Company> getEntityList(Model model) {
        return model.getFilteredCompanyList();
    }

    @Override
    protected void deleteEntity(Model model, Company companyToDelete) {
        model.deleteCompany(companyToDelete);
    }

    @Override
    protected String getSuccessMessage(Company companyToDelete) {
        String name = companyToDelete.getName().toString();
        String address = companyToDelete.getAddress().toString();
        String billingDate = companyToDelete.getBillingDate().toString();
        String phone = companyToDelete.getPhone().toString();

        return String.format(MESSAGE_DELETE_COMPANY_SUCCESS, name,
                address, billingDate, phone);
    }
}
