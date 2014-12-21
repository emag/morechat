package org.emamotor.morechat.client;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Yoshimasa Tanabe
 */
public class Chat {

  public final BooleanProperty connected = new SimpleBooleanProperty(false);
  public final BooleanProperty ready = new SimpleBooleanProperty(false);
  public final ObservableList<String> history = FXCollections.observableArrayList();
  public final StringProperty currentMessage = new SimpleStringProperty();
  public final StringProperty userName = new SimpleStringProperty();

}
