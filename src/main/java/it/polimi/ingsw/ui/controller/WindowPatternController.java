package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.ui.GUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class WindowPatternController {
    private WindowPattern windowPattern;

    @FXML
    private GridPane patternGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox tokens;

    public WindowPattern getWindowPattern() {
        return windowPattern;
    }

    public void setWindowPattern(WindowPattern p, double size) {
        windowPattern = p;
        nameLabel.setText(p.getName());
        for (Node c : patternGrid.getChildren()) {
            int i = GridPane.getRowIndex(c);
            int j = GridPane.getColumnIndex(c);
            Node graphic = p.getConstraints()[i][j].getAsGraphic(size);
            ((StackPane) c).getChildren().add(graphic);
        }
        for (int i = 0; i < p.getDifficulty(); i++) {
            Circle token = new Circle(GUI.TOKEN_RELATIVE_SIZE * size);
            token.getStyleClass().add("token");
            tokens.getChildren().add(token);
        }
    }

    public void setDie(Die d, int i, int j) {
        for (Node n : patternGrid.getChildren()) {
            if (i == GridPane.getRowIndex(n) && j == GridPane.getColumnIndex(n)) {
                Node e = MainController.drawDie(d, GUI.BASE_TILE_SIZE);
                ((StackPane) n).getChildren().add(e);
                e.toFront();
            }
        }
    }

    public void setClickableCells(boolean b, EventHandler<MouseEvent> handler) {
        for (Node stackPane : patternGrid.getChildren()) {
            stackPane.setOnMouseClicked(handler);
        }
    }
}
