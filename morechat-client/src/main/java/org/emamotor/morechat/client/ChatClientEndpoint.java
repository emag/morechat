package org.emamotor.morechat.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * @author Yoshimasa Tanabe
 */
@ClientEndpoint
public class ChatClientEndpoint {

  private Session session;
  private MessageHandler messageHandler;

  public ChatClientEndpoint(final URI endopointURI) {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    try {
      container.connectToServer(this, endopointURI);
    } catch (DeploymentException | IOException e) {
      e.printStackTrace();
    }
  }

  @OnOpen
  public void onOpen(final Session session) {
    this.session = session;
  }

  @OnClose
  public void onClose(final Session session, final CloseReason reason) {
    this.session = null;
  }

  @OnMessage
  public void onMessage(final String message) {
    if (messageHandler != null) {
      messageHandler.handleMessage(message);
    }
  }

  public void addMessageHandler(final MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  public void sendMessage(final String message) {
    session.getAsyncRemote().sendText(message);
  }

  public static interface MessageHandler {
    void handleMessage(String message);
  }
}
