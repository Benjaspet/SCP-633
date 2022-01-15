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
 *
 */

package dev.benpetrillo.commands.message;

import dev.benpetrillo.types.MessageCommand;
import dev.benpetrillo.utils.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Date;

public final class UserInfoCommand implements MessageCommand {

    @Override
    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        if (args.length < 2) {
            message.reply("You need to mention a user.").queue();
        } else {
            try {
                Member name = message.getMentionedMembers().get(0);
                long epoc = name.getTimeJoined().toInstant().getEpochSecond();
                long created = name.getUser().getTimeCreated().toInstant().getEpochSecond();
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Utilities.getDefaultEmbedColor())
                        .setAuthor(name.getUser().getAsTag())
                        .setThumbnail(name.getUser().getAvatarUrl())
                        .setDescription("Created: <t:" + created + ":R>" + "\n" + "Joined: " + "<t:" + epoc + ":R>")
                        .addField("User ID", name.getUser().getId(), false)
                        .setFooter("SCP-062 Operating System", message.getJDA().getSelfUser().getAvatarUrl())
                        .setTimestamp(new Date().toInstant());
                message.replyEmbeds(embed.build()).queue();
            } catch (IndexOutOfBoundsException ignored) {
                message.reply("You must mention a user.").queue();
            }
        }
    }
}