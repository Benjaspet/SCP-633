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
import dev.benpetrillo.utils.HttpUtil;
import dev.benpetrillo.utils.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.InteractionFailureException;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class SCPCommand implements ApplicationCommand {

    private final String name = "scp";
    private final String description = "Retrieve information on any SCP anomaly.";
    private final String[] options = {"item"};
    private final String[] optionDescriptions = {"The SCP anomaly to search for."};

    @Override
    public void runCommand(SlashCommandEvent event, Member member, Guild guild) {
        EmbedBuilder messageEmbed = new EmbedBuilder().setColor(Utilities.getDefaultEmbedColor());
        event.deferReply().queue(hook -> {
            try {
                String scp = Objects.requireNonNull(event.getOption("item")).getAsString();
                String response = HttpUtil.sendRequest("https://app.ponjo.club/v1/scp?item=" + scp);
                JSONObject body = new JSONObject(response);
                JSONObject fullBody = body.getJSONObject("data");
                String name = Utilities.getNotNull(fullBody, "item", "private").toString();
                String className = Utilities.getNotNull(fullBody, "class", "Unclassified").toString().toUpperCase();
                String description = Utilities.getNotNull(fullBody, "description", "Not found.").toString();
                String procedures = Utilities.getNotNull(fullBody, "procedures", "Not found.").toString();
                String image = Utilities.getNotNull(fullBody, "imageSrc", "").toString();
                if (description.length() > 1024) description = description.substring(0, 1021) + "...";
                if (procedures.length() > 1024) procedures = procedures.substring(0, 1021) + "...";
                messageEmbed
                        .setAuthor(name)
                        .setColor(Utilities.getDefaultEmbedColor())
                        .setDescription("Class: " + className)
                        .addField("Item Description", description, false)
                        .addField("Containment Procedures", procedures, false);
                if (!image.isEmpty()) messageEmbed.setImage(image);
                hook.editOriginalEmbeds(messageEmbed.build()).queue();
            } catch (JSONException ignored) {
                messageEmbed.setDescription(this.getRandomFailureMessage());
                messageEmbed.setAuthor("Click here to view this file.", "https://scp-wiki.wikidot.com/scp-%s".formatted(Objects.requireNonNull(event.getOption("item")).getAsString()));
                hook.editOriginalEmbeds(messageEmbed.build()).queue();
            } catch (IOException | InteractionFailureException ignored) {
                messageEmbed.setDescription("An error occurred while running this command.");
                hook.editOriginalEmbeds(messageEmbed.build()).queue();
            } catch (PermissionException ignored) {
                messageEmbed.setDescription("I do not have permission to do this.");
                event.getHook().editOriginalEmbeds(messageEmbed.build()).queue();
            }
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

    @Override
    public CommandData getCommandData() {
        return new CommandData(this.name, this.description)
                .addOption(OptionType.STRING, this.options[0], this.optionDescriptions[0], true);
    }

    private String getRandomFailureMessage() {
        String[] messages = Utilities.shuffleArray(new String[]{
                "You aren't cleared to access this data.",
                "That data is classified!",
                "An internal error occurred within SCP-633.",
                "Whoopsie, you can't do that!",
                "You must be of personnel Level 3 or higher to access this."
        });
        return messages[0];
    }
}
