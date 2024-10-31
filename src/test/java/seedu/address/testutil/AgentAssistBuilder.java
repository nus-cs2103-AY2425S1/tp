package seedu.address.testutil;

import seedu.address.model.AgentAssist;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building AgentAssist objects.
 * Example usage: <br>
 *     {@code AgentAssist ab = new AgentAssistBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code AgentAssist} that we are building.
     */
    public AgentAssistBuilder withPerson(Person person) {
        agentassist.addPerson(person);
        return this;
    }

    public AgentAssist build() {
        return agentassist;
    }
}
