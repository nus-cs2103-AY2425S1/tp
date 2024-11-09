package seedu.address.model.property.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation is unable to find the specified property.
 */
public class PropertyNotFoundException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(PropertyNotFoundException.class);
    public PropertyNotFoundException() {
        logger.info("Property not found");
    }
}
