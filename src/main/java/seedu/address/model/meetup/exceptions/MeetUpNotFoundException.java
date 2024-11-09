package seedu.address.model.meetup.exceptions;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Signals that the operation is unable to find the specified meet-up.
 */
public class MeetUpNotFoundException extends RuntimeException {
    private static final Logger logger = LogsCenter.getLogger(MeetUpNotFoundException.class);
    public MeetUpNotFoundException() {
        logger.info("Meetup not found");
    }
}
