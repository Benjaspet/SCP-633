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
import dev.benpetrillo.utils.EmbedUtil;
import net.dv8tion.jda.api.entities.*;

public final class StopCommand implements MessageCommand {

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
        PlayerManager.getInstance().getMusicManager(member.getGuild()).audioPlayer.setVolume(100);
        PlayerManager.getInstance().getMusicManager(member.getGuild()).audioPlayer.destroy();
        PlayerManager.getInstance().getMusicManager(member.getGuild()).scheduler.getQueue().clear();
        self.getGuild().getAudioManager().closeAudioConnection();
        message.replyEmbeds(EmbedUtil.sendDefaultEmbed("Disconnected from the radio.", member.getJDA())).queue();
    }
}
