package dev.benpetrillo.commands.slash;

import dev.benpetrillo.managers.PlayerManager;
import dev.benpetrillo.types.ApplicationCommand;
import dev.benpetrillo.utils.EmbedUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.InteractionFailureException;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.regex.PatternSyntaxException;

public final class StopCommand implements ApplicationCommand {

    private final String name = "stop";
    private final String description = "Disconnect from the radio system.";

    @Override
    public void runCommand(SlashCommandEvent event, Member member, Guild guild) {
        try {
            final Member self = guild.getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();
            final GuildVoiceState memberVoiceState = member.getVoiceState();
            assert selfVoiceState != null && memberVoiceState != null;
            if (!memberVoiceState.inAudioChannel()) {
                event.reply("You must be in a voice channel to run this command.").queue();
                return;
            }
            PlayerManager.getInstance().getMusicManager(member.getGuild()).audioPlayer.setVolume(100);
            PlayerManager.getInstance().getMusicManager(member.getGuild()).audioPlayer.destroy();
            PlayerManager.getInstance().getMusicManager(member.getGuild()).scheduler.getQueue().clear();
            self.getGuild().getAudioManager().closeAudioConnection();
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("Disconnected from the radio.", member.getJDA())).queue();
        } catch (InteractionFailureException e) {
            event.replyEmbeds(EmbedUtil.sendDefaultEmbed("Command execution failed.", member.getJDA())).queue();
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
