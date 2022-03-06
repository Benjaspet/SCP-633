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

package dev.benpetrillo.commands.slash;

import dev.benpetrillo.types.ApplicationCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.managers.AudioManager;

public class BindCommand implements ApplicationCommand {

    private final String name = "bind";
    private final String description = "Create an audio connection to your voice channel.";

    @Override
    public void runCommand(SlashCommandEvent event, Member member, Guild guild) {
        final GuildVoiceState selfVoiceState = member.getGuild().getSelfMember().getVoiceState();
        assert selfVoiceState != null;
        if (selfVoiceState.inAudioChannel()) {
            event.reply("I'm already in a voice channel.").queue();
            return;
        }
        final GuildVoiceState authorVoiceState = member.getVoiceState();
        assert authorVoiceState != null;
        if (!authorVoiceState.inAudioChannel()) {
            event.reply("You must be in a voice channel to run this command.").queue();
            return;
        }
        final AudioManager audioManager = member.getGuild().getAudioManager();
        final AudioChannel authorChannel = authorVoiceState.getChannel();
        audioManager.openAudioConnection(authorChannel);
        audioManager.setSelfDeafened(true);
        assert authorChannel != null;
        event.replyFormat("I've connected to %s successfully.", authorChannel.getName()).queue();
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
