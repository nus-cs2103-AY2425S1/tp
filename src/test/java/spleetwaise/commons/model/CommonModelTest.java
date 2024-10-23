package spleetwaise.commons.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;

public class CommonModelTest {
    @Test
    void shouldBeSingleton() {
        TransactionBookModel tbModel = new TransactionBookModelManager();
        AddressBookModel abModel = new AddressBookModelManager();

        CommonModel.initialise(abModel, tbModel);

        CommonModel x = CommonModel.getInstance();
        CommonModel y = CommonModel.getInstance();
        CommonModel z = CommonModel.getInstance();

        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
    }

    @Test
    void getInstanceDeinitialised() {
        CommonModel.deinitialise();
        assertThrows(NullPointerException.class, CommonModel::getInstance);
    }

    @Test
    void nullInitialisation() {
        CommonModel.initialise(null, null);
        CommonModel model = CommonModel.getInstance();
        assertThrows(NullPointerException.class, model::getUserPrefs);
        assertThrows(NullPointerException.class, model::getTransactionBook);
    }
}
