package org.emamotor.morechat.server;

import ninja.siden.App;

/**
 * @author Yoshimasa Tanabe
 */
public class ChatServer {

  public void start() {
    App app = new App();
    app.websocket("/ws").onText(
      (con, txt) -> con.peers().forEach(c -> c.send(txt)));
    app.listen("localhost", 8181);
  }

  public static void main(String[] args) {
    new ChatServer().start();
  }

}
