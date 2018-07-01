package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.Utils;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.ProxyModel;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private GUI gui;
    private List<AnchorPane> anchorPanes;
    private List<WindowPatternController> controllers;

    @FXML
    private BorderPane main;

    @FXML
    private VBox fxButtonContainer;

    @FXML
    private HBox fxBoardsContainer;

    @FXML
    private ImageView fxPrivObjCard;

    @FXML
    private Label fxMessage;

    @FXML
    private HBox fxDraftPool;


    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public static Node getDieMark(double size) {
        Circle mark = new Circle(size);
        mark.setFill(Color.BLACK);
        mark.setOpacity(0.84);
        return mark;
    }

    public static Node drawDie(Die d, double size) {
        GridPane cont = new GridPane();
        cont.getStyleClass().add("die");
        cont.setBackground(new Background(new BackgroundFill(d.getColor().getColor(), new CornerRadii(10), Insets.EMPTY)));
        int spacer = 5;
        cont.setPrefHeight(size);
        cont.setPrefWidth(size);
        cont.setRotate(Utils.getRandom(-10, 10));
        switch (d.getNumber()) {
            case 1:
                cont.add(getDieMark(spacer), 1, 1);
                break;
            case 2:
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 0);
                break;
            case 3:
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 0);
                cont.add(getDieMark(spacer), 1, 1);
                break;
            case 4:
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 0);
                cont.add(getDieMark(spacer), 0, 0);
                cont.add(getDieMark(spacer), 2, 2);
                break;
            case 5:
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 0);
                cont.add(getDieMark(spacer), 0, 0);
                cont.add(getDieMark(spacer), 2, 2);
                cont.add(getDieMark(spacer), 1, 1);
                break;
            case 6:
                cont.add(getDieMark(spacer), 0, 0);
                cont.add(getDieMark(spacer), 2, 0);
                cont.add(getDieMark(spacer), 0, 1);
                cont.add(getDieMark(spacer), 2, 1);
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 2);
                break;

            default:
                break;
        }
        return cont;
    }

    public void initBoards() {
        anchorPanes = new ArrayList<>();
        controllers = new ArrayList<>();
        ProxyModel model = gui.getModel();
        List<Player> players = model.getPlayers();
        players.add(model.getMyself());
        for (int i = 0; i < model.getPlayers().size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/windowpattern.fxml"));
            try {
                Parent root = loader.load();
                root.getStylesheets().add(getClass().getClassLoader().getResource("windowpattern.css").toExternalForm());
                controllers.add(i, loader.getController());
                controllers.get(i).setWindowPattern(players.get(i).getPlayerWindow().getWindowPattern());

                Image img = new Image(getClass().getClassLoader().getResource("images/board/" + (i + 1) + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setCache(true);
                imgView.setFitHeight(GUI.BOARDS_RELATIVE_SIZE * gui.getHeight());
                AnchorPane p = new AnchorPane();
                p.getChildren().add(0, imgView);
                AnchorPane.setBottomAnchor(root, GUI.BOARDS_RELATIVE_SIZE * imgView.getFitHeight());
                p.getChildren().add(1, root);
                anchorPanes.add(p);
                fxBoardsContainer.getChildren().add(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        ProxyModel model = gui.getModel();

        // Update Draft Pool
        int n = fxDraftPool.getChildren().size();
        if (n > 0) {
            fxDraftPool.getChildren().remove(0, n - 1);
        }
        for (Die d : model.getDraftPool()) {
            fxDraftPool.getChildren().add(drawDie(d, GUI.BASE_TILE_SIZE));
        }

        // Update players' PlayerWindow + tokens

        // Update Tool Cards token used


    }

    public void showMessage(String s) {
        fxMessage.setText(s);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), fxMessage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
}
