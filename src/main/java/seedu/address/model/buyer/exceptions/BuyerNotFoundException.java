package seedu.address.model.buyer.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation is unable to find the specified buyer.
 */
public class BuyerNotFoundException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(BuyerNotFoundException.class);
    public BuyerNotFoundException() {
        logger.info("Buyer not found");
    }
}
