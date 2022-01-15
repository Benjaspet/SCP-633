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

import dev.benpetrillo.types.MessageCommand;
import dev.benpetrillo.utils.HttpUtil;
import dev.benpetrillo.utils.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class SCPCommand implements MessageCommand {

    @Override
    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Utilities.getDefaultEmbedColor());
        embed.setFooter("SCP-062 Operating System", member.getJDA().getSelfUser().getAvatarUrl());
        embed.setTimestamp(new Date().toInstant());
        try {
            switch (args.length) {
                default -> {}
                case 1 -> {
                    embed.setDescription(String.format("Usage: `%sscp <item>`", Utilities.getPrefix()));
                    message.replyEmbeds(embed.build()).queue();
                }
                case 2 -> {
                    String scp = args[1];
                    String response = HttpUtil.sendRequest("https://app.ponjo.club/v1/scp?item=" + scp);
                    JSONObject body = new JSONObject(response);
                    JSONObject fullBody = body.getJSONObject("data");
                    String name = Utilities.getNotNull(fullBody, "item", "private").toString();
                    String className = Utilities.getNotNull(fullBody, "class", "Unclassified").toString();
                    String description = Utilities.getNotNull(fullBody, "description", "Not found.").toString();
                    String procedures = Utilities.getNotNull(fullBody, "procedures", "Not found.").toString();
                    String image = Utilities.getNotNull(fullBody, "imageSrc", "").toString();
                    if (description.length() > 1024) description = description.substring(0, 1021) + "...";
                    if (procedures.length() > 1024) procedures = procedures.substring(0, 1021) + "...";
                    embed.setDescription("Class: " + className);
                    embed.setAuthor(name);
                    embed.addField("Item Description", description,false);
                    embed.addField("Containment Procedures", procedures, false);
                    if (!image.isEmpty()) embed.setImage(image);
                    message.replyEmbeds(embed.build()).queue();
                }
            }
        } catch (JSONException ignored) {
            embed.setDescription("Invalid response from the API.");
            message.replyEmbeds(embed.build()).queue();
        } catch (IOException ignored) {
            embed.setDescription("An error occurred while running this command.");
            message.replyEmbeds(embed.build()).queue();
        } catch (PermissionException ignored) {
            embed.setDescription("I do not have permission to do this.");
            message.replyEmbeds(embed.build()).queue();
        }
    }
}
