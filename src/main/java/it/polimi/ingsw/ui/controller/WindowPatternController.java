package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.WindowPattern;
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
            Node graphic = p.getConstraints()[i][j].getAsGraphic(70);
            ((StackPane) c).getChildren().add(graphic);
        }
        for (int i = 0; i < p.getDifficulty(); i++) {
            Circle token = new Circle(7);
            token.getStyleClass().add("token");
            tokens.getChildren().add(token);
        }
    }
}