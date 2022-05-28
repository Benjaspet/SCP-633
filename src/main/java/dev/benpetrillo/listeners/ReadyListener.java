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

package dev.benpetrillo.listeners;

import dev.benpetrillo.Config;
import dev.benpetrillo.SCP633;
import dev.benpetrillo.commands.slash.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    public static JDA jda;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        jda = event.getJDA();
        SCP633.logger.info("{} has logged in.", event.getJDA().getSelfUser().getAsTag());
        String[] guildIds = Config.get("GUILD").split(",");
        for (String id : guildIds) {
            Guild guild = jda.getGuildById(id);
            if (guild != null) {
                if (Boolean.parseBoolean(Config.get("DEPLOY-GUILD"))) {
                    CommandListUpdateAction commands = guild.updateCommands().addCommands(
                            new BindCommand().getCommandData(),
                            new BranchesCommand().getCommandData(),
                            new HelpCommand().getCommandData(),
                            new MissionCommand().getCommandData(),
                            new SCPCommand().getCommandData(),
                            new StopCommand().getCommandData(),
                            new StreamCommand().getCommandData()
                    );
                    commands.queue();
                    SCP633.logger.info("All guild slash commands have been deployed.");
                } else if (Boolean.parseBoolean(Config.get("DELETE-GUILD"))) {
                    CommandListUpdateAction commands = guild.updateCommands().addCommands();
                    commands.queue();
                    SCP633.logger.info("All guild slash commands have been deleted.");
                }
            } else {
                SCP633.logger.info("An error occurred while deploying guild slash commands.");
            }
        }
    }
}