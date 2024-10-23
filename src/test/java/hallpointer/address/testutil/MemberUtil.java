package hallpointer.address.testutil;

import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TAG;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Set;

import hallpointer.address.logic.commands.AddMemberCommand;
import hallpointer.address.logic.commands.UpdateMemberCommand.UpdateMemberDescriptor;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.tag.Tag;

/**
 * A utility class for Member.
 */
public class MemberUtil {

    /**
     * Returns an add command string for adding the {@code member}.
     */
    public static String getAddCommand(Member member) {
        return AddMemberCommand.COMMAND_WORD + " " + getMemberDetails(member);
    }

    /**
     * Returns the part of command string for the given {@code member}'s details.
     */
    public static String getMemberDetails(Member member) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + member.getName().fullName + " ");
        sb.append(PREFIX_TELEGRAM + member.getTelegram().value + " ");
        sb.append(PREFIX_ROOM + member.getRoom().value + " ");
        member.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UpdateMemberDescriptor}'s details.
     */
    public static String getUpdateMemberDescriptorDetails(UpdateMemberDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getTelegram().ifPresent(
                telegram -> sb.append(PREFIX_TELEGRAM).append(telegram.value).append(" "));
        descriptor.getRoom().ifPresent(room -> sb.append(PREFIX_ROOM).append(room.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
