package spleetwaise.transaction.model;

import java.util.logging.Logger;

import spleetwaise.address.commons.core.LogsCenter;

/**
 * Represents the in-memory model of the transaction data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(spleetwaise.address.model.ModelManager.class);

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager() {
        logger.fine("Initializing Transaction Model...");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Model)) {
            return false;
        }

        // TODO: model properties equality checks
        //  ModelManager otherModelManager = (ModelManager) other;
        //  ...
        // Return true for now as Model has no values
        return true;
    }

}
