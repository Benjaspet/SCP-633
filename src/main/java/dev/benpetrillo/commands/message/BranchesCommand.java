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
import dev.benpetrillo.utils.EmbedUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

public class BranchesCommand implements MessageCommand {

    @Override
    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        try {
            message.replyEmbeds(EmbedUtil.getBranchesEmbed(member.getJDA())).queue();
        } catch (IOException ignored) {
            message.replyEmbeds(EmbedUtil.sendDefaultEmbed("Unable to fetch data. Please try again later.", member.getJDA())).queue();
        } catch (PermissionException ignored) {
            message.replyEmbeds(EmbedUtil.sendDefaultEmbed("I do not have permission to do this.", member.getJDA())).queue();
        } catch (Error ignored) {
            message.replyEmbeds(EmbedUtil.sendDefaultEmbed("An unknown error occurred.", member.getJDA())).queue();
        } catch (PatternSyntaxException ignored) {
            message.replyEmbeds(EmbedUtil.sendDefaultEmbed("Invalid syntax was provided.", member.getJDA())).queue();
        } catch (IllegalArgumentException ignored) {
            message.replyEmbeds(EmbedUtil.sendDefaultEmbed("An illegal amount of arguments were provided.", member.getJDA())).queue();
        }
    }
}
