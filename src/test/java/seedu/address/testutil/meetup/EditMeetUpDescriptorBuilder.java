package seedu.address.testutil.meetup;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.model.meetup.AddedBuyer;
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
        descriptor.setName(meetUp.getName());
        descriptor.setInfo(meetUp.getInfo());
        descriptor.setFrom(meetUp.getFrom());
        descriptor.setTo(meetUp.getTo());
        descriptor.setAddedBuyers(meetUp.getAddedBuyers());
    }

    /**
     * Sets the {@code Name} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Info} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withInfo(String info) {
        descriptor.setInfo(new Info(info));
        return this;
    }

    /**
     * Sets the {@code To} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withTo(String to) {
        descriptor.setTo(new To(to));
        return this;
    }

    /**
     * Sets the {@code From} of the {@code EditMeetUpDescriptor} that we are building.
     */
    public EditMeetUpDescriptorBuilder withFrom(String from) {
        descriptor.setFrom(new From(from));
        return this;
    }

    /**
     * Parses the {@code addedBuyers} into a {@code Set<AddedBuyer>} and set it to the {@code EditBuyerDescriptor}
     * that we are building.
     */
    public EditMeetUpDescriptorBuilder withAddedBuyer(String... addedBuyers) {
        Set<AddedBuyer> addedBuyerSet = Stream.of(addedBuyers).map(AddedBuyer::new).collect(Collectors.toSet());
        descriptor.setAddedBuyers(addedBuyerSet);
        return this;
    }

    public EditCommand.EditMeetUpDescriptor build() {
        return descriptor;
    }
}
