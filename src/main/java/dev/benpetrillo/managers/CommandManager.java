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

package dev.benpetrillo.managers;

import dev.benpetrillo.commands.message.*;
import dev.benpetrillo.types.MessageCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public final class CommandManager {

    private final Map<String, MessageCommand> commands;

    public CommandManager() {
        commands = new HashMap<>();
        commands.put("ping", new PingCommand());
        commands.put("foundation", new FoundationCommand());
        commands.put("branches", new BranchesCommand());
        commands.put("scp", new SCPCommand());
        commands.put("bind", new BindCommand());
        commands.put("stream", new StreamCommand());
        commands.put("help", new HelpCommand());
        commands.put("shutdown", new ShutdownCommand());
        commands.put("userinfo", new UserInfoCommand());
        commands.put("taskforces", new TaskforceCommand());
    }

    /**
     * @param member The guild member that instantiated this.
     * @param channel The TextChannel in which this command was run.
     * @param message The Message type for this command.
     * @param args The array of arguments for this command.
     */

    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        String cmd = message.getContentDisplay().split(" ")[0].substring(1);
        MessageCommand command;
        if ((command = commands.get(cmd)) != null) {
            command.runCommand(member, channel, message, args);
        }
    }
}
