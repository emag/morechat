package org.emamotor.morechat.server;

import ninja.siden.App;

import java.nio.file.Paths;

/**
 * @author Yoshimasa Tanabe
 */
public class ChatServer {

  public static void main(String[] args) {
    App app = new App();
    app.get("/", (req, res) -> Paths.get("assets/chat.html"));
    app.websocket("/ws").onText(
      (con, txt) -> con.peers().forEach(c -> c.send(txt)));
    app.listen("0.0.0.0", 8181);
  }

}
