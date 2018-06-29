package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.ProxyModel;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MainController {
    private GUI gui;

    @FXML
    private BorderPane main;

    @FXML
    private AnchorPane fxBoard1;

    @FXML
    private AnchorPane fxBoard2;

    @FXML
    private AnchorPane fxBoard3;

    @FXML
    private AnchorPane fxBoard4;

    @FXML
    private HBox fxDraftPool;


    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void update() {
        ProxyModel model = gui.getModel();

        // Update Draft Pool
        for (Die d : model.getDraftPool()) {
            fxDraftPool.getChildren().add(drawDie(d, GUI.TILE_SIZE));
        }

    }

    private Node getDieMark(double size) {
        Circle mark = new Circle(size);
        mark.setFill(Color.BLACK);
        mark.setOpacity(0.84);
        return mark;
    }

    private Node drawDie(Die d, double size) {
        GridPane cont = new GridPane();
        cont.getStyleClass().add("die");
        cont.setBackground(new Background(new BackgroundFill(d.getColor().getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        int spacer = 5;
        cont.setPrefHeight(size);
        cont.setPrefWidth(size);
        //cont.setRotate(Utils.getRandom(-15, 15));
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
                cont.add(getDieMark(spacer), 0, 2);
                cont.add(getDieMark(spacer), 2, 0);
                cont.add(getDieMark(spacer), 0, 0);
                cont.add(getDieMark(spacer), 2, 2);
                cont.add(getDieMark(spacer), 1, 0);
                cont.add(getDieMark(spacer), 1, 2);
                break;

            default:
                break;
        }
        return cont;
    }
}
