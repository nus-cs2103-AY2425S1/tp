package spleetwaise.transaction.model;

import org.junit.jupiter.api.Test;

public class ModelManagerTest {

    private spleetwaise.transaction.model.Model transactionModel = new spleetwaise.transaction.model.ModelManager();
    private spleetwaise.address.model.Model addressBookModel = new spleetwaise.address.model.ModelManager();

    @Test
    void transactionModelIsNotAddressBookModel() {
        assert !(transactionModel.equals(addressBookModel));
        assert !(addressBookModel.equals(transactionModel));
    }

}
