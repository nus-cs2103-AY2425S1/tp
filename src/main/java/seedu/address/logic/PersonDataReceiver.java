package seedu.address.logic;

import java.util.logging.Logger;

import seedu.address.model.person.Person;
import seedu.address.ui.OverviewPanel;

/**
 * Receives and processes data from PersonListPanel.
 */
public class PersonDataReceiver {
    private final Logger logger = Logger.getLogger(PersonDataReceiver.class.getName());
    private final OverviewPanel overviewPanel;

    public PersonDataReceiver(OverviewPanel overviewPanel) {
        this.overviewPanel = overviewPanel;
    }

    /**
     * Processes the received person data.
     *
     * @param person The person whose data is being received.
     */
    public void receivePersonData(Person person) {
        if (person != null) {
            overviewPanel.updateOverviewDetails(person);
        } else {
            logger.warning("Received null data for person.");
        }
    }
}
