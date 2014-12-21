package org.emamotor.morechat.client;

import am.ik.voicetext4j.EmotionalSpeaker;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;

/**
 * @author Yoshimasa Tanabe
 */
public class ChatClientUtil {

  public static final String SERVER_ENDPOINT = "ws://localhost:8181/ws";

  public static String string2Json(final String userName, final String message, final EmotionalSpeaker speaker) {
    return Json.createObjectBuilder()
      .add("name", userName)
      .add("msg", message)
      .add("voice", speaker.name())
      .build().toString();
  }

  public static String response2String(final String response) {
    JsonObject root = Json.createReader(new StringReader(response)).readObject();
    String name = root.getString("name");
    String msg = root.getString("msg");
    return String.format("%s: %s", name, msg);
  }

  public static String responseUserName(final String response) {
    JsonObject root = Json.createReader(new StringReader(response)).readObject();
    return root.getString("name");
  }

  public static String responseMessage(final String response) {
    JsonObject root = Json.createReader(new StringReader(response)).readObject();
    return root.getString("msg");
  }

  public static EmotionalSpeaker responseVoice(final String response) {
    JsonObject root = Json.createReader(new StringReader(response)).readObject();
    return EmotionalSpeaker.valueOf(root.getString("voice"));
  }

}
