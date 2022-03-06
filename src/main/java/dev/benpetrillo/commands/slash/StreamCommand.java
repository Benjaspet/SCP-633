package dev.benpetrillo.commands.slash;

import dev.benpetrillo.managers.PlayerManager;
import dev.benpetrillo.types.ApplicationCommand;
import dev.benpetrillo.utils.EmbedUtil;
import dev.benpetrillo.utils.Utilities;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.regex.PatternSyntaxException;

public final class StreamCommand implements ApplicationCommand {

    private final String name = "stream";
    private final String description = "Access the Foundation's anomaly radio system.";

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
            if (!PlayerManager.getInstance().getMusicManager(member.getGuild()).scheduler.getQueue().isEmpty()) {
                event.reply("I'm already streaming. Please end the current stream before starting a new one.").queue();
                return;
            }
            final GuildVoiceState authorVoiceState = member.getVoiceState();
            assert authorVoiceState != null;
            final AudioManager audioManager = guild.getAudioManager();
            final AudioChannel authorChannel = authorVoiceState.getChannel();
            audioManager.openAudioConnection(authorChannel);
            audioManager.setSelfDeafened(true);
            for (String track : this.getAllPlayableTracks()) {
                PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), track);
            }
            event.reply("I've begun streaming classified SCP anomaly data.").queue();
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

    private String[] getAllPlayableTracks() {
        return Utilities.shuffleArray(new String[]{
                "https://www.youtube.com/watch?v=E2rtQFAibEA",
                "https://www.youtube.com/watch?v=I84DuJF4W5s",
                "https://www.youtube.com/watch?v=f46xG0fHIME",
                "https://www.youtube.com/watch?v=ORI7EpwjR4Q",
                "https://www.youtube.com/watch?v=PhQksV8fbKw",
                "https://www.youtube.com/watch?v=vVloMqJEsME",
                "https://www.youtube.com/watch?v=GkXnc1JxrQM",
                "https://www.youtube.com/watch?v=4B-KnwrUUgE",
                "https://www.youtube.com/watch?v=bG0wXQ59MuU",
                "https://www.youtube.com/watch?v=RE3XR3WZjF8",
                "https://www.youtube.com/watch?v=79MvZdbInOc",
                "https://www.youtube.com/watch?v=E_Y0swRJiAQ",
                "https://www.youtube.com/watch?v=yjWYdDaTYAE",
                "https://www.youtube.com/watch?v=dTpKABK_l4A",
                "https://www.youtube.com/watch?v=hMJwQ47W8ZU",
                "https://www.youtube.com/watch?v=o6JXQm5pcDI",
                "https://www.youtube.com/watch?v=8v93A-lR5l4",
                "https://www.youtube.com/watch?v=u8UV86NBifI",
                "https://www.youtube.com/watch?v=ptWWkLfLNV0"
        });
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
