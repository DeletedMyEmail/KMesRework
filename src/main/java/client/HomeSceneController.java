package client;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Controller for the GUI home scene
 *
 * @version v2.0.0 | last edit: 24.08.2022
 * @author Joshua H. | KaitoKunTatsu#3656
 * */
public class HomeSceneController {

    private ClientBackend backend;

    @FXML
    public Text titleText;

    @FXML
    public TextField messageTextField;

    @FXML
    public ListView contactsList;

    @FXML
    public ScrollPane messagesScrollpane;

    private void showAllMessages(Text pText) {
        ObservableList<Node> messageList = ((VBox)messagesScrollpane.getContent()).getChildren();
        showAllMessages(pText, messageList);
    }

    private void showAllMessages(Text pText, ObservableList pMessageList)
    {
        if (backend.getMessagesForUser(pText.getText()) != null)
        {
            for (String msg : backend.getMessagesForUser(pText.getText()))
            {
                pMessageList.add(createMessageHBox(msg));
            }
        }
    }

    private void clearCurrentMessages() {
        ObservableList messageList = ((VBox)messagesScrollpane.getContent()).getChildren();
        messageList.clear();
    }

    @FXML
    public void initialize()
    {
        backend = SceneManager.getBackend();
        contactsList.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                clearCurrentMessages();
                showAllMessages((Text)t1);
            }
        }));
    }

    @FXML
    protected void onAccountButtonClick()
    {
        if (!backend.getUsername().equals(""))
        {
            SceneManager.switchToSettingsScene();
        }
        else
        {
            SceneManager.switchToLoginScene();
        }
    }

    private HBox createMessageHBox(String pContent)
    {
        String cssLayout = "-fx-border-color: #6bc490";
        HBox hbox = new HBox();
        hbox.setMaxWidth(350.0);
        if (pContent.startsWith("Sent: [image]")) {
            byte[] lImageBytes = Base64.getDecoder().decode(pContent.substring(13));
            Image lImg = new Image(new ByteArrayInputStream(lImageBytes));

            hbox.getChildren().add(new ImageView(lImg));
        }
        else if (pContent.startsWith("Received: [image]")) {
            byte[] lImageBytes = Base64.getDecoder().decode(pContent.substring(17));
            Image lImg = new Image(new ByteArrayInputStream(lImageBytes));

            hbox.getChildren().add(new ImageView(lImg));
        }
        else {
            hbox.getChildren().add(new Text(pContent));
        }
        hbox.setStyle(cssLayout);
        return hbox;
    }

    protected void showNewMessage(String pAuthor, String pMessage)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                Text item = ((Text)contactsList.getSelectionModel().getSelectedItem());
                if (item != null && item.getText().equals(pAuthor))
                {
                    ((VBox)messagesScrollpane.getContent()).getChildren().add(createMessageHBox(pMessage));
                }
            }
        });
    }

    protected void showNewContact(String pUsername)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                contactsList.getItems().add(new Text(pUsername));
            }
        });
    }

    @FXML
    protected void onSendButtonClick(ActionEvent actionEvent) throws IOException
    {
        Text selectedContact = ((Text)contactsList.getSelectionModel().getSelectedItem());
        if (selectedContact == null) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Please select a contact", "Error occurred while sending the message", ButtonType.OK);
            return;
        }

        String lReceiver = selectedContact.getText();
        String lMsg = messageTextField.getText();
        backend.sendMessageToOtherUser(lReceiver, lMsg);
    }

    @FXML
    protected void onAddContactButtonClick(ActionEvent actionEvent)
    {
        SceneManager.showAddContactWindow();
    }

    private void clearContacts()
    {
        contactsList.getSelectionModel().clearSelection();
        contactsList.getItems().clear();
    }

    protected void clearShowMessagesAndContacts()
    {
        clearCurrentMessages();
        clearContacts();
    }

    @FXML
    public void onFileButtonClick(ActionEvent actionEvent) {
        Text selectedContact = ((Text)contactsList.getSelectionModel().getSelectedItem());
        if (selectedContact == null) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Please select a contact", "Error occurred while sending the message", ButtonType.OK);
            return;
        }

        String lReceiver = selectedContact.getText();
        backend.fileButtonClick(lReceiver);
    }
}
