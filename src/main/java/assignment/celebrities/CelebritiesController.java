package assignment.celebrities;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CelebritiesController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    @FXML
    private ImageView image;
    @FXML
    private BorderPane CelebrityPortal;
    @FXML
    private Label title;
    @FXML
    private Label about;
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private ComboBox Originality;
    @FXML
    private TextField name;
    Media media;
    MediaPlayer player;
    OrderedDictionary database = null;
    CelebrityRecord celebrity = null;
    int celebrityName = 1;

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }

    public void find() {
        DataKey key = new DataKey(this.name.getText(), celebrityName);
        try {
            celebrity = database.find(key);
            showCelebrity();
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void delete() {
        if (database == null || celebrity == null) {
            return;
        }
        CelebrityRecord previousCelebrity = null;
        try {
            previousCelebrity = database.predecessor(celebrity.getDataKey());
        } catch (DictionaryException ex) {
        }
        CelebrityRecord nextCelebrity = null;
        try {
            nextCelebrity = database.successor(celebrity.getDataKey());
        } catch (DictionaryException ex) {
        }
        DataKey key = celebrity.getDataKey();
        try {
            database.remove(key);
        } catch (DictionaryException ex) {
            System.out.println("Error in delete " + ex);
        }
        if (database.isEmpty()) {
            this.CelebrityPortal.setVisible(false);
            displayAlert("No more Celebrity left");
        } else {
            if (nextCelebrity != null) {
                celebrity = nextCelebrity;
                showCelebrity();
            } else if (previousCelebrity != null) {
                celebrity = previousCelebrity;
                showCelebrity();
            } else {
                try {
                    celebrity = database.largest();
                    showCelebrity();
                } catch (DictionaryException ex) {
                    displayAlert(ex.getMessage());
                }
            }
        }
    }

    private void showCelebrity() {
        play.setDisable(false);
        pause.setDisable(true);
        if (player != null) {
            player.stop();
        }
        String img = celebrity.getImage();
        Image celebrityImage = new Image("file:src/main/resources/assignment/celebrities/images/" + img);
        image.setImage(celebrityImage);
        title.setText(celebrity.getDataKey().getCelebrityName());
        about.setText(celebrity.getAbout());
    }

    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/assignment/celebrities/images/UNTIcon.png"));
            stage.setTitle("Dictionary Exception");
            stage.setTitle("Dictionary Exception");
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    public void getOriginality() {
        switch (this.Originality.getValue().toString()) {
            case "American":
                this.celebrityName = 1;
                break;
            case "British":
                this.celebrityName = 2;
                break;
            case "Canadian":
                this.celebrityName = 3;
                break;
            default:
                break;
        }
    }

    public void first() {
        try {
            celebrity = database.smallest();
            showCelebrity();
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }


    public void last() {
        try {
            celebrity = database.largest();
            showCelebrity();
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }    }

    public void next() {
        if (celebrity == null) {
            return;
        }

        try {
            CelebrityRecord nextCelebrity = database.successor(celebrity.getDataKey());
            if (nextCelebrity != null) {
                celebrity = nextCelebrity;
                showCelebrity();
            }
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }


    public void previous() {
        if (celebrity == null) {
            return;
        }
        try {
            CelebrityRecord prevCelebrity = database.predecessor(celebrity.getDataKey());
            if (prevCelebrity != null) {
                celebrity = prevCelebrity;
                showCelebrity();
            }
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }


    public void play() {
        String filename = "src/main/resources/assignment/celebrities/sounds/" + celebrity.getSound();
        media = new Media(new File(filename).toURI().toString());
        player = new MediaPlayer(media);
        play.setDisable(true);
        pause.setDisable(false);
        player.play();
    }

    public void pause() {
        play.setDisable(false);
        pause.setDisable(true);
        if (player != null) {
            player.stop();
        }
    }

    public void loadDictionary() {
        Scanner input;
        int line = 0;
        try {
            String celebrityName = "";
            String description;
            int size = 0;
            input = new Scanner(new File("BirdsDatabase.txt"));
            while (input.hasNext())
            {
                String data = input.nextLine();
                switch (line % 3) {
                    case 0:
                        size = Integer.parseInt(data);
                        break;
                    case 1:
                        celebrityName = data;
                        break;
                    default:
                        description = data;
                        database.insert(new CelebrityRecord(new DataKey(celebrityName, size), description, celebrityName + ".mp3", celebrityName + ".jpg"));
                        break;
                }
                line++;
            }
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: BirdsDatabase.txt");
            System.out.println(e.getMessage());
        } catch (DictionaryException ex) {
            Logger.getLogger(CelebritiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.CelebrityPortal.setVisible(true);
        this.first();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = new OrderedDictionary();
        Originality.setItems(FXCollections.observableArrayList(
                "American", "British", "Canadian"
        ));
        Originality.setValue("American");
    }

}
