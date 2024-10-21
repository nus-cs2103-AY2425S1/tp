package seedu.address.testutil.meetup;

import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;

/**
 * A utility class to help with building EditMeetUpDescriptor objects.
 */
public class EditMeetUpDescriptorBuilder {

    private EditCommand.EditMeetUpDescriptor descriptor;

    public EditMeetUpDescriptorBuilder() {
        descriptor = new EditCommand.EditMeetUpDescriptor();
    }

    public EditMeetUpDescriptorBuilder(EditCommand.EditMeetUpDescriptor descriptor) {
        this.descriptor = new EditCommand.EditMeetUpDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMeetUpDescriptor} with fields containing {@code meetIp}'s details
     */
    public EditMeetUpDescriptorBuilder(MeetUp meetUp) {
        descriptor = new EditCommand.EditMeetUpDescriptor();
        descriptor.setMeetUpName(meetUp.getName());
        descriptor.setMeetUpInfo(meetUp.getInfo());
        descriptor.setMeetUpFrom(meetUp.getFrom());
        descriptor.setMeetUpTo(meetUp.getTo());
    }

    /**
     * Sets the {@code Name} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withName(String name) {
        descriptor.setMeetUpName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Info} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withInfo(String info) {
        descriptor.setMeetUpInfo(new Info(info));
        return this;
    }

    /**
     * Sets the {@code To} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withTo(String to) {
        descriptor.setMeetUpTo(new To(to));
        return this;
    }

    /**
     * Sets the {@code From} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withFrom(String from) {
        descriptor.setMeetUpFrom(new From(from));
        return this;
    }

    public EditCommand.EditMeetUpDescriptor build() {
        return descriptor;
    }
}
