package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.ProxyModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
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
import java.util.HashMap;
import java.util.List;

public class MainController {
    private GUI gui;
    private HashMap<Player, AnchorPane> anchorPanes;
    private HashMap<Player, WindowPatternController> controllers;

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

    public static Node drawDie(Die d, double size) {
        GridPane cont = new GridPane();
        cont.getStyleClass().add("die");
        cont.setBackground(new Background(new BackgroundFill(d.getColor().getColor(), new CornerRadii(GUI.ROUND_CORNER_RADIUS), Insets.EMPTY)));
        double spacer = GUI.DIE_RELATIVE_SPACER;
        cont.setHgap(spacer);
        cont.setVgap(spacer);
        cont.setPrefHeight(size);
        cont.setPrefWidth(size);
        //cont.setRotate(Utils.getRandom((int) -GUI.ROUND_CORNER_RADIUS, (int) GUI.ROUND_CORNER_RADIUS));
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

    public static Node getDieMark(double size) {
        Circle mark = new Circle(size);
        mark.setFill(Color.BLACK);
        mark.setOpacity(0.84);
        return mark;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @FXML
    void endTurn(ActionEvent event) {
        gui.endTurn();
    }

    public void initBoards() {
        anchorPanes = new HashMap<>();
        controllers = new HashMap<>();
        ProxyModel model = gui.getModel();
        List<Player> players = model.getPlayers();
        players.add(model.getMyself());
        for (int i = 0; i < model.getPlayers().size(); i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/windowpattern.fxml"));
            try {
                Parent root = loader.load();
                root.getStylesheets().add(getClass().getClassLoader().getResource("windowpattern.css").toExternalForm());
                controllers.put(players.get(i), loader.getController());
                controllers.get(players.get(i)).setWindowPattern(players.get(i).getPlayerWindow().getWindowPattern(), GUI.BASE_TILE_SIZE);

                Image img = new Image(getClass().getClassLoader().getResource("images/board/" + (i + 1) + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setCache(true);
                imgView.setFitHeight(GUI.BOARD_RELATIVE_HEIGHT * gui.getHeight());
                imgView.setFitWidth(GUI.BOARD_RELATIVE_WIDTH * gui.getWidth());

                AnchorPane p = new AnchorPane();
                p.getChildren().add(0, imgView);
                AnchorPane.setTopAnchor(root, GUI.BOARD_RELATIVE_HEIGHT * imgView.getFitHeight());
                root.layout();
                p.getChildren().add(1, root);
                anchorPanes.put(players.get(i), p);
                fxBoardsContainer.getChildren().add(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        ProxyModel model = gui.getModel();

        // Update Draft Pool
        /*int max = fxDraftPool.getChildren().size();
        if (max > 0) {
            fxDraftPool.getChildren().remove(0, max - 1);
        }*/
        fxDraftPool.getChildren().clear();
        for (Die d : model.getDraftPool()) {
            fxDraftPool.getChildren().add(drawDie(d, GUI.BASE_TILE_SIZE));
        }

        // Update players' PlayerWindow + tokens
        for (Player p : model.getPlayers()) {
            for (int i = 0; i < WindowPattern.ROWS; i++) {
                for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                    if (!p.getPlayerWindow().getCellAt(i, j).isEmpty()) {
                        controllers.get(p).setDie(p.getPlayerWindow().getCellAt(i, j).getDie(), i, j);
                    }
                }
            }
        }
        updateCurrentPlayer(model.getCurrentPlayer());

        Player p = model.getMyself();
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                if (!p.getPlayerWindow().getCellAt(i, j).isEmpty()) {
                    controllers.get(p).setDie(p.getPlayerWindow().getCellAt(i, j).getDie(), i, j);
                }
            }
        }

        // Update Tool Cards token used


    }

    public void showMessage(String s) {
        fxMessage.setText(s);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(8), fxMessage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    private void updateCurrentPlayer(Player p) {
        if (p != null) {
            for (AnchorPane a : anchorPanes.values()) {
                a.getChildren().get(1).getStyleClass().remove("chosen");
            }
            anchorPanes.get(p).getChildren().get(1).getStyleClass().add("chosen");
        }
    }

    public void repositionBoards() {
        for (AnchorPane p : anchorPanes.values()) {
            ImageView v = ((ImageView) p.getChildren().get(0));
            VBox c = ((VBox) p.getChildren().get(1));
            double offset = (v.getFitWidth() - c.getWidth()) / 2.0;
            AnchorPane.setLeftAnchor(c, offset);
            AnchorPane.setLeftAnchor(c, offset); //TODO IS THIS NEEDED?
        }
    }

    public void enableActions(boolean myTurn) {
        for (Node b : fxButtonContainer.getChildren()) {
            if (myTurn) {
                b.setDisable(false);
            } else {
                b.setDisable(true);
            }
        }
    }

    public void resizeAll() {
        // Resize DraftPool

        // Resize PatternCard
    }
}
