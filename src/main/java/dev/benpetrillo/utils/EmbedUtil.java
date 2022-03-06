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

package dev.benpetrillo.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class EmbedUtil {

    public static MessageEmbed sendDefaultEmbed(String content, JDA jda) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Utilities.getDefaultEmbedColor())
                .setDescription(content)
                .setFooter("SCP-062 Operating System", jda.getSelfUser().getAvatarUrl());
        return embed.build();
    }

    public static MessageEmbed getHelpEmbed(JDA jda) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("SCP-062 OS | Help")
                .setColor(Utilities.getDefaultEmbedColor())
                .setDescription("SCP-062 appears to be an unbranded personal desktop computer housed in an aluminum " +
                        "case of indeterminate manufacture. SCP-062 is unusually heavy at approximately 24kg, and lacks " +
                        "manufacturing or branding labels of any kind. Despite this, SCP-062 operates as expected for a " +
                        "normal desktop computer with the exception that its performance, operating system, contained data, " +
                        "and language appears to be different upon every activation. This anomaly is used to access " +
                        "classified Foundation data. `!` is the system's command prefix.")
                .addField("All System Commands", """
                        `bind` - have the anomaly join your voice channel.
                        `branches` - view all SCP Foundation worldwide branches.
                        `mission` - view the mission of the SCP Foundation.
                        `help` - access SCP-062's help menu.
                        `scp <anomaly>` - view classified data on any SCP anomaly.
                        `stream` - access the Foundation's anomaly radio system.
                        `stop` - disconnect from the radio system.
                        `userinfo` - view information on a user.""",false)
                .setFooter("SCP-062 Operating System", jda.getSelfUser().getAvatarUrl())
                .setTimestamp(new Date().toInstant());
        return embed.build();
    }

    public static MessageEmbed getFoundationMissionEmbed(JDA jda) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("SCP Foundation | Mission")
                .setColor(Utilities.getDefaultEmbedColor())
                .setThumbnail("https://raw.githubusercontent.com/Eerie6560/Archives/main/images/icons/SCP-Foundation.png")
                .setDescription("Operating clandestine and worldwide, the Foundation operates beyond jurisdiction, " +
                        "empowered and entrusted by every major national government with the task of containing " +
                        "anomalous objects, entities, and phenomena. These anomalies pose a significant threat to " +
                        "global security by threatening either physical or psychological harm." + "\n\n" +
                        "The Foundation operates to maintain normalcy, so that the worldwide civilian population " +
                        "can live and go on with their daily lives without fear, mistrust, or doubt in their personal " +
                        "beliefs, and to maintain human independence from extraterrestrial, extradimensional, and " +
                        "other extranormal influence. Our mission is three-fold:")
                .addField("Secure", "The Foundation secures anomalies with the goal of preventing them from " +
                        "falling into the hands of civilian or rival agencies, through extensive observation and " +
                        "surveillance and by acting to intercept such anomalies at the earliest opportunity.", false)
                .addField("Contain", "The Foundation contains anomalies with the goal of preventing " +
                        "their influence or effects from spreading, by either relocating, concealing, or dismantling " +
                        "such anomalies or by suppressing or preventing public dissemination of knowledge thereof.", false)
                .addField("Protect", "The Foundation protects humanity from the effects of such anomalies " +
                        "as well as the anomalies themselves until such time that they are either fully understood " +
                        "or new theories of science can be devised based on their properties and behavior. The " +
                        "Foundation may also neutralize or destroy anomalies as an option of last resort, if they " +
                        "are determined to be too dangerous to be contained.", false)
                .setFooter("SCP-062 Operating System", jda.getSelfUser().getAvatarUrl())
                .setTimestamp(new Date().toInstant());
        return embed.build();
    }

    public static MessageEmbed getBranchesEmbed(JDA jda) throws IOException {
        try {
            String response = HttpUtil.sendRequest("https://app.ponjo.club/v1/scp/branches");
            JSONObject body = new JSONObject(response);
            JSONObject full = body.getJSONObject("branches");
            String international = full.getJSONObject("international").getString("about");
            String russian = full.getJSONObject("russian").getString("about");
            String german = full.getJSONObject("german").getString("about");
            String chinese = full.getJSONObject("chinese").getString("about");
            String french = full.getJSONObject("french").getString("about");
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("SCP Foundation | Branches")
                    .setColor(Utilities.getDefaultEmbedColor())
                    .setThumbnail(full.getJSONObject("international").getString("logo"))
                    .addField("International", international, false)
                    .addField("Russian", russian, false)
                    .addField("German", german, false)
                    .addField("Chinese", chinese, false)
                    .addField("French", french, false)
                    .setFooter("SCP-062 Operating System", jda.getSelfUser().getAvatarUrl())
                    .setTimestamp(new Date().toInstant());
            return embed.build();
        } catch (IOException ignored) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("Unable to fetch data. Please try again later.")
                    .setColor(Utilities.getDefaultEmbedColor());
            return embed.build();
        }
    }
}
