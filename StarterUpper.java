import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class StarterUpper extends Application {

    /**
     * Single parameter start method, being fed in the stage, acting as the entry point
     * for our GUI. Does not need a main method to begin.
     * @param mainStage Stage containing our GUI: the window
     */
    public void start(Stage mainStage) {
        List<StartUpIdea> initIdeas =  new LinkedList<StartUpIdea>();
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 600, 600, Color.DARKGREEN); //Title color (scene color set)
        mainStage.setTitle("Problem Ideation Form.");
        ObservableList<StartUpIdea> obsIdeas = FXCollections.observableArrayList(initIdeas);
        ListView<StartUpIdea> listView = new ListView<StartUpIdea>(obsIdeas);
        border.setBottom(listView);

        //Name label:
        Label nameLabel = new Label("Ryan Lundqvist");
        border.setTop(nameLabel);

        //Text fields, labels, questions & input
        VBox textV = new VBox();
        border.setCenter(textV);

        Label qLabel = new Label("Fill in our problem space questions:");
        qLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        qLabel.setTextFill(Color.DARKGREEN);
        TextField qProblem = new TextField();
        qProblem.setPromptText("What is the problem?");
        TextField qCustomer = new TextField();
        qCustomer.setPromptText("Who is the target customer?");
        TextField qNeed = new TextField();
        qNeed.setPromptText("How badly does the customer NEED this problem fixed (1-10)?");
        TextField qMany = new TextField();
        qMany.setPromptText("How many people do you know who might experience this problem?");
        TextField qTarget = new TextField();
        qTarget.setPromptText("How big is the target market?");
        TextField qCompete = new TextField();
        qCompete.setPromptText("Who are the competitors/existing solutions?");
        TextField qUnfair = new TextField();
        qUnfair.setPromptText("Do you have an unfair advantage in tackling this problem space?");
        textV.getChildren().addAll(qLabel, qProblem, qCustomer, qNeed, qMany, qTarget, qCompete, qUnfair);
        //Button that adds current idea in fields to a list of startupidea objects

        TilePane btnT = new TilePane(Orientation.VERTICAL);
        border.setLeft(btnT);

        btnT.setTileAlignment(Pos.CENTER);

        Button addB = new Button(" Add ");
        //Using a lambda expression
        addB.setOnAction(event -> {
            if (qProblem.getText().isEmpty() || qCustomer.getText().isEmpty() || qNeed.getText().isEmpty()
                    || qMany.getText().isEmpty() || qTarget.getText().isEmpty()
                    || qCompete.getText().isEmpty() || qUnfair.getText().isEmpty()) {
                Popup emptyIssue = new Popup();
                emptyIssue.show(mainStage);
                Label emptyLabel = new Label("ERROR: Please do not leave any data empty...");
                emptyLabel.setFont(Font.font("Impact",  16));
                emptyLabel.setStyle(" -fx-background-color: white;");
                emptyLabel.setTextFill(Color.CRIMSON);
                emptyIssue.setAutoHide(true);
                emptyIssue.getContent().add(emptyLabel);
                emptyLabel.setMinWidth(80);
                emptyLabel.setMinHeight(50);
            } else {
                try {
                    if (parseInt(qNeed.getText()) > 0 && parseInt(qNeed.getText()) <= 10
                            && parseInt(qMany.getText()) > 0
                            && parseInt(qTarget.getText()) > 0) {
                        initIdeas.add(new StartUpIdea((qProblem.getText()), (qCustomer.getText()),
                                parseInt(qNeed.getText()),
                                parseInt(qMany.getText()), parseInt(qTarget.getText()), (qCompete.getText()),
                                (qUnfair.getText())));
                        qProblem.setText("");
                        qCustomer.setText("");
                        qNeed.setText("");
                        qMany.setText("");
                        qTarget.setText("");
                        qCompete.setText("");
                        qUnfair.setText("");

                        AudioClip sfx = new AudioClip(getClass().getResource("/resources/winSFX.wav").toExternalForm());
                        sfx.play();

                        ObservableList<StartUpIdea> obsIdeas2 = FXCollections.observableArrayList(initIdeas);
                        ListView<StartUpIdea> listView2 = new ListView<StartUpIdea>(obsIdeas2);
                        border.setBottom(listView2);


                    } else {
                        Popup negativeIssue = new Popup();
                        negativeIssue.show(mainStage);
                        Label negativeLabel = new Label("ERROR: Please do not input negative values...");
                        negativeLabel.setFont(Font.font("Impact", 16));
                        negativeLabel.setStyle(" -fx-background-color: white;");
                        negativeLabel.setTextFill(Color.CRIMSON);
                        negativeIssue.setAutoHide(true);
                        negativeIssue.getContent().add(negativeLabel);
                        negativeLabel.setMinWidth(80);
                        negativeLabel.setMinHeight(50);

                    }
                } catch (NumberFormatException nfe) {
                    Popup typeIssue = new Popup();
                    typeIssue.show(mainStage);
                    Label typeLabel = new Label("ERROR: Please input correct types...");
                    typeLabel.setFont(Font.font("Impact", 16));
                    typeLabel.setStyle(" -fx-background-color: white;");
                    typeLabel.setTextFill(Color.CRIMSON);
                    typeIssue.setAutoHide(true);
                    typeIssue.getContent().add(typeLabel);
                    typeLabel.setMinWidth(80);
                    typeLabel.setMinHeight(50);
                }
            }
            //Lambda^
            //System.out.println(initIdeas);
        });

        Button sortB = new Button("Sort");
        //Using a lambda expression
        sortB.setOnAction(event -> {
            Collections.sort(initIdeas);
            ObservableList<StartUpIdea> obsIdeas3 = FXCollections.observableArrayList(initIdeas);
            ListView<StartUpIdea> listView3 = new ListView<StartUpIdea>(obsIdeas3);
            border.setBottom(listView3);
        });

        Button resetB = new Button("Reset");
        //Using an anonymous inner class
        resetB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Popup resetForm = new Popup();
                resetForm.show(mainStage);

                resetForm.setWidth(500);
                resetForm.setHeight(500);
                Label resetLabel = new Label("Reset Form                         ");
                resetLabel.setStyle(" -fx-background-color: white;");
                resetLabel.setFont(Font.font("Verdana",  15));

                Label dangerLabel = new Label("DANGER: ARE YOU SURE YOU WANT TO RESET");
                dangerLabel.setStyle(" -fx-background-color: white;");
                dangerLabel.setFont(Font.font("Impact",  13));
                dangerLabel.setTextFill(Color.CRIMSON);

                Button yesB = new Button("Yes");
                yesB.setOnAction(event3 -> {
                    File file = new File("ideas.txt");
                    if (file.exists()) {
                        try {
                            file.delete();
                            System.out.println("Deleted");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    initIdeas.clear();
                    qProblem.setText("");
                    qCustomer.setText("");
                    qNeed.setText("");
                     qMany.setText("");
                    qTarget.setText("");
                    qCompete.setText("");
                    qUnfair.setText("");

                    ObservableList<StartUpIdea> obsIdeas4 = FXCollections.observableArrayList(initIdeas);
                    ListView<StartUpIdea> listView4 = new ListView<StartUpIdea>(obsIdeas4);
                    border.setBottom(listView4);

                    resetForm.hide();
                });
                Button noB = new Button("No");
                noB.setOnAction(event4 -> {
                        resetForm.hide();
                });

                VBox resetV = new VBox();
                resetV.getChildren().addAll(resetLabel, dangerLabel, yesB, noB);
                resetForm.getContent().add(resetV);

            }
        });

        Button saveB = new Button("Save");
        saveB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                File file = new File("ideas.txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        System.out.println("File made");
                    } catch (Exception e) {
                        e.printStackTrace();
                  }
                }
                FileUtil.saveIdeasToFile(initIdeas, file);
                System.out.println("Done");
            }
        });

        Button removeB = new Button("Remove");
        removeB.setOnAction(event -> {
            Popup deletionForm = new Popup();
            deletionForm.show(mainStage);

            deletionForm.setWidth(500);
            deletionForm.setHeight(500);
            Label delLabel = new Label("Deletion Form                                                    ");
            delLabel.setStyle(" -fx-background-color: white;");
            delLabel.setFont(Font.font("Verdana",  15));

            Label delLabel2 = new Label("Enter the index of a startup saved on the list to delete, click away "
                    + "to leave:");
            delLabel2.setStyle(" -fx-background-color: white;");
            delLabel2.setFont(Font.font("Verdana",  10));

            TextField badIdea = new TextField();
            badIdea.setPromptText("Index of the startup idea you would like to remove...");

            Button deleteB = new Button("Submit");
            deleteB.setOnAction(event2 -> {
                try {
                    initIdeas.remove(parseInt(badIdea.getText()));
                    badIdea.setText("");
                    ObservableList<StartUpIdea> obsIdeas5 = FXCollections.observableArrayList(initIdeas);
                    ListView<StartUpIdea> listView5 = new ListView<StartUpIdea>(obsIdeas5);
                    border.setBottom(listView5);
                } catch (Exception e) {
                    e.printStackTrace();
                    AudioClip delSFX = new AudioClip(getClass().getResource("/resources/delSFX.wav").toExternalForm());
                    delSFX.play();
                }

            });
            VBox delV = new VBox();
            delV.getChildren().addAll(delLabel, delLabel2, badIdea, deleteB);
            deletionForm.getContent().add(delV);

            //Label
            //Button to quit out
            deletionForm.setAutoHide(true);
        });

        //Set gap between buttons
        btnT.setVgap(10);
        btnT.getChildren().addAll(addB, sortB, resetB, saveB, removeB);

        ImageView imageView = new ImageView();
        imageView.setImage(new Image("resources/startupIcon.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        border.setRight(imageView);

        mainStage.setScene(scene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    /**
     * Typical main method (public static void main(String[] args) that typically runs
     * an execution of code. Here, it is being used to launch the arguments and load our window.
     * @param args Standard arguments fed in from the beginning.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

