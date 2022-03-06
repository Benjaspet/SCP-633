package dev.benpetrillo.commands.slash;

import dev.benpetrillo.types.ApplicationCommand;
import dev.benpetrillo.utils.EmbedUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.regex.PatternSyntaxException;

public final class MissionCommand implements ApplicationCommand {

    private final String name = "mission";
    private final String description = "View the mission of the SCP Foundation.";

    @Override
    public void runCommand(SlashCommandEvent event, Member member, Guild guild) {
        try {
            event.replyEmbeds(EmbedUtil.getFoundationMissionEmbed(member.getJDA())).queue();
        } catch (PermissionException ignored) {
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("I do not have permission to do this.", member.getJDA())).queue();
        } catch (Error ignored) {
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("An unknown error occurred.", member.getJDA())).queue();
        } catch (PatternSyntaxException ignored) {
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("Invalid syntax was provided.", member.getJDA())).queue();
        } catch (IllegalArgumentException ignored) {
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("An illegal amount of arguments were provided.", member.getJDA())).queue();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public CommandData getCommandData() {
        return new CommandData(this.name, this.description);
    }
}
