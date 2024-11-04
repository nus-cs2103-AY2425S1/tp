package seedu.address.testutil;

import seedu.address.model.AgentAssist;
import seedu.address.model.client.Client;

/**
 * A utility class to help with building AgentAssist objects.
 * Example usage: <br>
 *     {@code AgentAssist ab = new AgentAssistBuilder().withClient("John", "Doe").build();}
 */
public class AgentAssistBuilder {

    private AgentAssist agentassist;

    public AgentAssistBuilder() {
        agentassist = new AgentAssist();
    }

    public AgentAssistBuilder(AgentAssist agentassist) {
        this.agentassist = agentassist;
    }

    /**
     * Adds a new {@code Client} to the {@code AgentAssist} that we are building.
     */
    public AgentAssistBuilder withClient(Client client) {
        agentassist.addClient(client);
        return this;
    }

    public AgentAssist build() {
        return agentassist;
    }
}
