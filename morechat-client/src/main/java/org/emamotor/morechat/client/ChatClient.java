package org.emamotor.morechat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Yoshimasa Tanabe
 */
public class ChatClient extends Application {

  @Override
  public void start(final Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/chat.fxml"));
    Scene scene = new Scene(root);
    scene.setFill(Color.GRAY);
    stage.setScene(scene);
    stage.setTitle("MoreChat Client");
    stage.show();
  }

  public static void main(final String... args) {
    launch(args);
  }

}
