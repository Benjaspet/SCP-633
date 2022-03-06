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

package dev.benpetrillo;

import dev.benpetrillo.listeners.*;
import dev.benpetrillo.managers.CommandManager;
import dev.benpetrillo.managers.SlashCommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.AllowedMentions;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public final class SCP062Bot {

    public static CommandManager commandManager;
    public static SlashCommandManager slashCommandManager;
    public static Logger logger = LoggerFactory.getLogger(SCP062Bot.class);

    public static void main(String[] args) {
        try {
            new SCP062Bot(Config.get("TOKEN"));
        } catch (LoginException | IllegalArgumentException | InterruptedException exception) {
            logger.info("Unable to log into the bot. Is the token valid?");
        }
    }

    private SCP062Bot(String token) throws LoginException, IllegalArgumentException, InterruptedException {
        BasicConfigurator.configure();
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.watching("over the foundation."))
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(
                        new MessageListener(),
                        new ReadyListener(),
                        new ShutdownListener(),
                        new GuildActivityListener(),
                        new SlashCommandListener()
                ).build();
        AllowedMentions.setDefaultMentionRepliedUser(false);
        commandManager = new CommandManager();
        slashCommandManager = new SlashCommandManager(jda, true);
    }

    public static CommandManager  getMessageCommandManager() {
        return commandManager;
    }

    public static SlashCommandManager getSlashCommandManager() {
        return slashCommandManager;
    }

    public SCP062Bot getInstance() {
        return this;
    }
}
