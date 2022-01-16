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
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class TaskforceCommand implements MessageCommand {

    @Override
    public void runCommand(Member member, TextChannel channel, Message message, String[] args) {
        try {
            String response = HttpUtil.sendRequest("https://app.ponjo.club/v1/scp/taskforces");
            JSONObject obj = new JSONObject(response);
            JSONArray array = obj.getJSONArray("data");
            for (Object element : array) {
                try {
                    JSONObject jsElement = (JSONObject) element;
                    String field = jsElement.keys().next();
                    JSONObject data = jsElement.getJSONObject(field);
                    System.out.println(Utilities.getNotNull(data, "name", "Not found") + "\n");
                } catch (Exception ignored) { }
            }
        } catch (JSONException | IOException ignored) {

        }
    }
}
