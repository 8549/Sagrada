package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.ui.GUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

    public void setWindowPattern(WindowPattern p) {
        windowPattern = p;
        nameLabel.setText(p.getName());
        for (Node c : patternGrid.getChildren()) {
            int i = GridPane.getRowIndex(c);
            int j = GridPane.getColumnIndex(c);
            Node graphic = p.getConstraints()[i][j].getAsGraphic(GUI.BASE_TILE_SIZE);
            ((StackPane) c).getChildren().add(graphic);
        }
        for (int i = 0; i < p.getDifficulty(); i++) {
            Circle token = new Circle(7);
            token.getStyleClass().add("token");
            tokens.getChildren().add(token);
        }
    }

    public void setDie(Die d, int i, int j) {
        for (Node n : patternGrid.getChildren()) {
            if (i == GridPane.getRowIndex(n) && j == GridPane.getColumnIndex(n)) {
                ((StackPane) n).getChildren().add(MainController.drawDie(d, GUI.BASE_TILE_SIZE));
            }
        }
    }
}
