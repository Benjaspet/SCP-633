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

import dev.benpetrillo.SCP062Bot;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildActivityListener extends ListenerAdapter {

    /**
     * Emitted whenever the bot application is added to a guild.
     * @param event GuildJoinEvent - the event object.
     */

    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        String id = event.getGuild().getId();
        String name = event.getGuild().getName();
        SCP062Bot.logger.info("Added to guild {} with ID {}.", name, id);
    }

    /**
     * Emitted whenever the bot application is removed from a guild.
     * @param event GuildLeaveEvent - the event object.
     */

    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        String id = event.getGuild().getId();
        String name = event.getGuild().getName();
        SCP062Bot.logger.info("Removed from guild {} with ID {}.", name, id);
    }
}