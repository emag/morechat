package org.emamotor.morechat.client;

import am.ik.voicetext4j.EmotionalSpeaker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Yoshimasa Tanabe
 */
public class ChatClientController implements Initializable {

  static {
    System.setProperty("voicetext.apikey", System.getProperty("voicetext.apikey", "INVALID_KEY"));
  }

  @FXML
  private MenuItem exit;

  @FXML
  private ChoiceBox<EmotionalSpeaker> voicePatterns;

  @FXML
  private Button connect;

  @FXML
  private TextField userName;

  @FXML
  private TextField message;

  @FXML
  private CheckBox muteMyVoice;

  @FXML
  private Button say;

  @FXML
  private ListView<String> history;

  private final Chat chat = new Chat();

  private ChatClientEndpoint chatClientEndpoint;

  @Override
  public void initialize(URL url, ResourceBundle bundle) {
    exit.setOnAction(event -> Platform.exit());

    voicePatterns.setItems(FXCollections.observableArrayList(EmotionalSpeaker.values()));
    voicePatterns.getSelectionModel().select(0);

    chat.userName.bindBidirectional(this.userName.textProperty());
    chat.ready.bind(chat.userName.isNotEmpty().and(voicePatterns.selectionModelProperty().isNotNull()));

    say.disableProperty().bind(chat.connected.not().or(chat.currentMessage.isEmpty()));

    message.disableProperty().bind(chat.connected.not());
    message.textProperty().bindBidirectional(chat.currentMessage);

    connect.disableProperty().bind(chat.ready.not().or(chat.connected));

    history.setItems(chat.history);

    message.setOnAction(event -> {
      handleSendMessage();
    });

    say.setOnAction(event -> {
      handleSendMessage();
    });

    connect.setOnAction(event -> {
      chat.history.add("Connected Success!");
      try {
        chatClientEndpoint = new ChatClientEndpoint(new URI(ChatClientUtil.SERVER_ENDPOINT));
        chatClientEndpoint.addMessageHandler(response -> {
          Platform.runLater(() -> {
            chat.history.add(ChatClientUtil.response2String(response));
            if (muteMyVoice.isSelected() && ChatClientUtil.responseUserName(response).equals(userName.getText())) {
              // through
            } else {
              ChatClientUtil.responseVoice(response).ready().speak(ChatClientUtil.responseMessage(response));
            }
          });
        });
        chat.connected.set(true);
      } catch (URISyntaxException e) {
        showDialog("Error: " + e.getMessage());
      }
    });

  }

  private void handleSendMessage() {
    chatClientEndpoint.sendMessage(
      ChatClientUtil.string2Json(chat.userName.get(), chat.currentMessage.get(), voicePatterns.getValue()));
    chat.currentMessage.set("");
    message.requestFocus();
  }

  private void showDialog(final String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    VBox box = new VBox();
    box.getChildren().addAll(new Label(message));
    box.setAlignment(Pos.CENTER);
    box.setPadding(new Insets(5));
    dialog.setScene(new Scene(box));
    dialog.show();
  }

}
