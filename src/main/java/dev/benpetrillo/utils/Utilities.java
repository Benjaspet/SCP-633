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

import dev.benpetrillo.Config;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.Random;

public class Utilities {

    public static Object getNotNull(JSONObject object, String valueToGrab, Object fallBack) {
        if (object.isNull(valueToGrab)) {
            return fallBack;
        } else {
            return object.get(valueToGrab);
        }
    }

    public static String[] shuffleArray(String[] array) {
        int index;
        String temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static int getIndexOfElement(JSONArray array, String element) {
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("name").equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get the bot's message command prefix.
     * @return String
     */

    public static String getPrefix() {
        return Config.get("PREFIX");
    }

    /**
     * Get the default embed color.
     * @return Color
     */

    public static Color getDefaultEmbedColor() {
        return Color.decode("#4287f5");
    }
}
