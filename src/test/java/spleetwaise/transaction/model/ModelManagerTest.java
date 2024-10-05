package spleetwaise.transaction.model;

import org.junit.jupiter.api.Test;

public class ModelManagerTest {

    spleetwaise.transaction.model.Model transactionModel = new spleetwaise.transaction.model.ModelManager();
    spleetwaise.address.model.Model addressBookModel = new spleetwaise.address.model.ModelManager();

    @Test
    void transactionModel_is_not_addressBookModel() {
        assert !(transactionModel.equals(addressBookModel));
        assert !(addressBookModel.equals(transactionModel));
    }

}
