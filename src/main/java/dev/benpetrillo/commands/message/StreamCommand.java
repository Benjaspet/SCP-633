/*
 * Copyright © 2022 Ben Petrillo. All rights reserved.
 *
 * Project licensed under the MIT License: https://www.mit.edu/~amini/LICENSE.md
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * All portions of this software are available for public use, provided that
 * credit is given to the original author(s).
 */

package dev.benpetrillo.commands.message;

import dev.benpetrillo.managers.PlayerManager;
import dev.benpetrillo.types.MessageCommand;
import dev.benpetrillo.utils.Utilities;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class StreamCommand implements MessageCommand {

    @Override
    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        final Member self = channel.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        assert selfVoiceState != null && memberVoiceState != null;
        if (!memberVoiceState.inAudioChannel()) {
            message.reply("You must be in a voice channel to run this command.").queue();
            return;
        }
        if (!PlayerManager.getInstance().getMusicManager(member.getGuild()).scheduler.getQueue().isEmpty()) {
            message.reply("I'm already streaming. Please end the current stream before starting a new one.").queue();
            return;
        }
        final GuildVoiceState authorVoiceState = member.getVoiceState();
        assert authorVoiceState != null;
        final AudioManager audioManager = channel.getGuild().getAudioManager();
        final AudioChannel authorChannel = authorVoiceState.getChannel();
        audioManager.openAudioConnection(authorChannel);
        audioManager.setSelfDeafened(true);
        for (String track : this.getAllPlayableTracks()) {
            PlayerManager.getInstance().loadAndPlay(channel, track);
        }
        message.reply("I've begun streaming classified SCP anomaly data.").queue();
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
}
