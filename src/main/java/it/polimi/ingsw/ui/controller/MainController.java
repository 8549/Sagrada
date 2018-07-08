package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.ProxyModel;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainController {
    private GUI gui;
    private HashMap<Player, AnchorPane> anchorPanes;
    private HashMap<Player, WindowPatternController> controllers;
    private HashMap<ToolCard, ImageView> cardsMap;
    int newValue;
    Die selectedFromRT;
    private boolean firstUpdate;
    private int theRound;
    int firstDieRow;
    int firstDieColumn;
    int secondDieRow;
    int secondDieColumn;
    int chosenDice;
    int firstCellRow;
    int firstCellColumn;
    int secondCellRow;
    int secondCellColumn;
    int chosenCoords;

    @FXML
    private BorderPane main;

    @FXML
    private VBox fxButtonContainer;
    @FXML
    private HBox fxPublicObjectiveCardsContainer;

    @FXML
    private HBox fxToolCardsContainer;

    @FXML
    private HBox fxBoardsContainer;

    @FXML
    private Label fxTimer;

    @FXML
    private ImageView fxPrivateObjectiveCard;

    @FXML
    private Label fxMessage;

    @FXML
    private HBox fxDraftPool;

    @FXML
    private VBox fxPlayerNames;

    @FXML
    private ImageView fxRoundTrack;

    /**
     * Helper method that creates a graphical representation of a {@link Die} and will return a {@link Node}
     *
     * @param d    the die to get a representation of
     * @param size the size for the representation
     * @return a node representing the supplied die
     */
    public static Node drawDie(Die d, double size) {
        GridPane cont = new GridPane();
        cont.getStyleClass().add("die");
        cont.setBackground(new Background(new BackgroundFill(d.getColor().getColor(), new CornerRadii(GUI.ROUND_CORNER_RADIUS), Insets.EMPTY)));
        double spacer = GUI.DIE_RELATIVE_SPACER * GUI.BASE_TILE_SIZE;
        //cont.setOpacity(0.7);
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

    /**
     * Helper method to get a graphic representation of a mark of a die. Returns a {@link Node}
     *
     * @param size the size for the mark
     * @return a node representing a mark of a die
     */
    public static Node getDieMark(double size) {
        Circle mark = new Circle(size);
        mark.setFill(Color.BLACK);
        mark.setOpacity(0.84);
        return mark;
    }

    /**
     * Shows the {@link RoundTrack} in a separate window in an "informative" way
     *
     * @param event
     * @see #showRoundTrack(boolean)
     */
    public void showRoundTrackWrapper(MouseEvent event) {
        showRoundTrack(false);
    }

    /**
     * Shows the {@link RoundTrack} in two different modes: an "informative" mode which means the user can only look at
     * the dice without interacting with them, and an "interactive" mode where the user can choose a {@link Die} from it
     * (used by some {@link ToolCard}s)
     *
     * @param canChooseFromRoundTrack a boolean set to true if the interactive mode is required, false if the informative
     *                                mode is required
     */
    private void showRoundTrack(boolean canChooseFromRoundTrack) {
        RoundTrack roundTrack = gui.getModel().getRoundTrack();
        Stage dieChooser = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();
        valueMain.getStylesheets().add("die.css");
        valueMain.getStylesheets().add("board.css");

        Label l = new Label();
        Button confirm = new Button();
        if (canChooseFromRoundTrack) {
            l.setText("Choose a die from the round track");
            confirm.setText("Use the selected die");
            confirm.setDisable(true);
            confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    dieChooser.hide();
                    gui.getClientHandler().sendDieFromRT(selectedFromRT, theRound);
                    selectedFromRT = null;
                    theRound = -1;
                }
            });
        } else {
            l.setText("This is the current round track:");
            confirm.setText("Ok");
            confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    dieChooser.hide();
                }
            });
        }
        Insets spacing = new Insets(20);

        VBox rounds = new VBox();
        rounds.setSpacing(spacing.getBottom() / 2.0);
        for (int i = 0; i < roundTrack.getRoundCounter(); i++) {
            HBox round = new HBox();
            round.setAlignment(Pos.CENTER);
            Label label = new Label(String.valueOf(i + 1));
            label.getStyleClass().add("roundTrackLabel");
            round.getChildren().add(label);
            for (int j = 0; j < roundTrack.getDiceNumberAtRound(i); j++) {
                Die d = roundTrack.getDieAt(i, j);
                Node e = drawDie(d, GUI.BASE_TILE_SIZE);
                if (canChooseFromRoundTrack) {
                    e.setCursor(Cursor.HAND);
                    e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            confirm.setDisable(false);
                            Node source = (Node) event.getSource();
                            int whichRound = rounds.getChildren().indexOf(source.getParent());
                            int whichDie = round.getChildren().indexOf(source) - 1;
                            selectedFromRT = roundTrack.getDieAt(whichRound, whichDie);
                            theRound = whichRound;
                        }
                    });
                }
                round.getChildren().add(e);
            }
            round.setFillHeight(false);
            round.setSpacing(spacing.getBottom());
            rounds.getChildren().add(round);
        }
        rounds.setFillWidth(false);
        BorderPane.setMargin(rounds, spacing);
        BorderPane.setMargin(l, spacing);
        BorderPane.setMargin(confirm, spacing);
        valueMain.setPadding(spacing);
        valueMain.setTop(l);
        valueMain.setCenter(rounds);
        valueMain.setBottom(confirm);

        dieChooser.initModality(Modality.APPLICATION_MODAL);
        dieChooser.setScene(new Scene(valueMain));
        dieChooser.sizeToScene();
        dieChooser.showAndWait();
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     * This method starts the process of using a {@link ToolCard}
     *
     * @param event
     */
    @FXML
    void useToolCard(ActionEvent event) {

        for (Node n : fxToolCardsContainer.getChildren()) {
            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int i = fxToolCardsContainer.getChildren().indexOf(event.getSource());
                    for (Node n : fxToolCardsContainer.getChildren()) {
                        n.setCursor(Cursor.DEFAULT);
                        n.setOnMouseClicked(null);
                    }
                    gui.getClientHandler().useTool(gui.getModel().getToolCards().get(i));
                }
            };
            n.setOnMouseClicked(handler);
            n.setCursor(Cursor.HAND);
        }
    }

    /**
     * Handles the user clicking the "End turn" button
     *
     * @param event
     */
    @FXML
    void endTurn(ActionEvent event) {
        gui.endTurn();
    }

    /**
     * Handles the user clicking the "Place die" button
     *
     * @param event
     */
    @FXML
    void placeDie(ActionEvent event) {
        for (Node n : fxDraftPool.getChildren()) {
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : fxDraftPool.getChildren()) {
                        n.setScaleX(1.0);
                        n.setScaleY(1.0);
                    }
                    int i = fxDraftPool.getChildren().indexOf(event.getSource());
                    gui.selectDie(i);
                    ScaleTransition transition = new ScaleTransition(Duration.seconds(0.5), n);
                    transition.setByX(0.7);
                    transition.setByY(0.7);
                    transition.play();
                }
            });
        }
        WindowPatternController c = controllers.get(gui.getModel().getMyself());
        c.setClickableCells(gui.getModel().isMyTurn(), new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int i = GridPane.getRowIndex((Node) event.getSource());
                int j = GridPane.getColumnIndex((Node) event.getSource());
                for (Node n : fxDraftPool.getChildren()) {
                    n.setScaleX(1.0);
                    n.setScaleY(1.0);
                }
                gui.tryDiePlacement(i, j);
                for (Node n : fxDraftPool.getChildren()) {
                    n.setOnMouseClicked(null);
                }
            }
        });
    }

    /**
     * Initializes some fields in this controller and the main view elemets such as the game boards, the tool cards, the
     * objective cards, the draft pool, the user names and tokens and so on
     */
    public void initBoards() {
        firstUpdate = false;
        fxTimer.textProperty().bind(gui.secondsRemainingProperty().asString("%s s"));

        anchorPanes = new HashMap<>();
        controllers = new HashMap<>();
        cardsMap = new HashMap<>();
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
                controllers.get(players.get(i)).removeNameAndTokens();

                Image img = new Image(getClass().getClassLoader().getResource("images/board/" + (i + 1) + ".png").toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setPreserveRatio(true);
                imgView.setSmooth(true);
                imgView.setCache(true);
                imgView.setFitHeight(GUI.BOARD_RELATIVE_HEIGHT * gui.getHeight());
                imgView.setFitWidth(GUI.BOARD_RELATIVE_WIDTH * gui.getWidth());

                AnchorPane p = new AnchorPane();
                p.getStylesheets().add(getClass().getClassLoader().getResource("windowpattern.css").toExternalForm());
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
        // Load Tool Cards and Objective Cards
        String privPath = "images/objectivecards/private/" + model.getMyself().getPrivateObjectiveCard().getName() + ".jpg";
        String privUrl = getClass().getClassLoader().getResource(privPath).toExternalForm();
        fxPrivateObjectiveCard.setImage(new Image(privUrl));

        int n = fxPublicObjectiveCardsContainer.getChildren().size();
        for (int i = 0; i < model.getPublicObjectiveCards().size(); i++) {
            String pubPath = "images/objectivecards/public/" + model.getPublicObjectiveCards().get(i).getName() + ".jpg";
            String pubUrl = getClass().getClassLoader().getResource(pubPath).toExternalForm();
            if (i < n) {
                ((ImageView) fxPublicObjectiveCardsContainer.getChildren().get(i)).setImage(new Image(pubUrl));
            } else {
                ImageView copy = (ImageView) fxPublicObjectiveCardsContainer.getChildren().get(0);
                copy.setImage(new Image(pubUrl));
                fxPublicObjectiveCardsContainer.getChildren().add(copy);
            }
        }

        n = fxToolCardsContainer.getChildren().size();
        for (int i = 0; i < model.getToolCards().size(); i++) {
            String pubPath = "images/toolcards/" + model.getToolCards().get(i).getName() + ".jpg";
            String pubUrl = getClass().getClassLoader().getResource(pubPath).toExternalForm();
            if (i < n) {
                ((ImageView) fxToolCardsContainer.getChildren().get(i)).setImage(new Image(pubUrl));
                cardsMap.put(model.getToolCards().get(i), (ImageView) fxToolCardsContainer.getChildren().get(i));
                Tooltip.install(fxToolCardsContainer.getChildren().get(i), getTooltip(model.getToolCards().get(i)));
            } else {
                ImageView copy = (ImageView) fxToolCardsContainer.getChildren().get(0);
                copy.setImage(new Image(pubUrl));
                fxToolCardsContainer.getChildren().add(copy);
                cardsMap.put(model.getToolCards().get(i), copy);
                Tooltip.install(copy, getTooltip(model.getToolCards().get(i)));
            }
        }
        // Init players name and tokens
        for (Player p : model.getPlayers()) {

            Label label = new Label(p.getName());
            label.getStyleClass().add("playerLabel");
            label.setContentDisplay(ContentDisplay.RIGHT);
            HBox.setMargin(label, new Insets(0, 45, 0, 0));

            HBox tokens = getTokensBox(p.getTokens(), GUI.CHOOSER_TILE_SIZE);

            label.setGraphic(tokens);
            label.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            fxPlayerNames.getChildren().add(label);
        }
        fxPlayerNames.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    /**
     * Returns an {@link HBox} which is a graphic representation of an arbitrary number of favor tokens
     *
     * @param n    the number of favor tokens to draw
     * @param size the size of the favor tokens
     * @return a box containing the desired amount of tokens
     */
    private HBox getTokensBox(int n, double size) {
        HBox tokens = new HBox();
        tokens.setSpacing(size / 10.0);
        tokens.getStylesheets().add(getClass().getClassLoader().getResource("windowpattern.css").toExternalForm());
        for (int i = 0; i < n; i++) {
            tokens.getChildren().add(WindowPatternController.getToken(size));
        }
        return tokens;
    }

    /**
     * Updates the current game window fetching the latest data from the {@link ProxyModel}, such as dice placed on the
     * players' windows, the tokens placed on a tool card, the dice left in the draft pool and those in the round track
     * and so on
     */
    public void update() {
        if (!firstUpdate) {
            firstUpdate = true;
            gui.setInitialSize();
        }
        ProxyModel model = gui.getModel();

        enableActions(model.isMyTurn());

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
                    } else {
                        controllers.get(p).removeDie(i, j);
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
        for (ToolCard t : cardsMap.keySet()) {
            ImageView view = cardsMap.get(t);
            Tooltip.uninstall(view, null);
            Tooltip.install(view, getTooltip(t));
        }

    }

    /**
     * Creates and return a tooltip displaying the cost of a {@link ToolCard} and the number of favor tokens placed onto
     * it
     *
     * @param toolCard the tool card where to fetch the cost and number of favor tokens from
     * @return a tooltip with the cost and number of favor tokens on a tool card
     */
    private Tooltip getTooltip(ToolCard toolCard) {
        Tooltip tooltip = new Tooltip("Cost: " + toolCard.getCost());
        tooltip.setContentDisplay(ContentDisplay.BOTTOM);
        tooltip.setGraphicTextGap(45.0);
        HBox tokens = getTokensBox(toolCard.getTokens(), GUI.BASE_TILE_SIZE);
        tooltip.setGraphic(tokens);
        return tooltip;
    }

    /**
     * Shows a message into a notification area
     *
     * @param s the string to display
     */
    public void showMessage(String s) {
        fxMessage.setText(s);
        fxMessage.setText(s);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(8), fxMessage);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    /**
     * Updates the game boards hightlighting the current {@link Player}'s window and refreshing the quantity of tokens left for
     * that player
     *
     * @param p the player
     */
    private void updateCurrentPlayer(Player p) {
        if (p != null) {
            for (AnchorPane a : anchorPanes.values()) {
                a.getChildren().get(1).getStyleClass().remove("chosen");
            }
            anchorPanes.get(p).getChildren().get(1).getStyleClass().add("chosen");
            for (Node n : fxPlayerNames.getChildren()) {
                Label l = (Label) n;
                if (l.getText().equals(p.getName())) {
                    HBox updatedTokens = getTokensBox(p.getTokens(), GUI.CHOOSER_TILE_SIZE);
                    l.setGraphic(null);
                    l.setGraphic(updatedTokens);
                }
            }
        }
    }

    /**
     * Tries to place a scheme card along with its dice in the middle of a board (glass window with the top arch)
     */
    public void repositionBoards() {
        for (AnchorPane p : anchorPanes.values()) {
            ImageView v = ((ImageView) p.getChildren().get(0));
            VBox c = ((VBox) p.getChildren().get(1));
            double offset = (v.getFitWidth() - c.getWidth()) / 2.0;
            AnchorPane.setLeftAnchor(c, offset);
            AnchorPane.setLeftAnchor(c, offset); //TODO FIX THIS
        }
    }

    /**
     * Enables or disables the buttons in the right area of the GUI
     * @param myTurn a boolean set to true if the buttons are to be enabled, false if they have to be disabled
     */
    public void enableActions(boolean myTurn) {
        for (Node b : fxButtonContainer.getChildren()) {
            if (myTurn) {
                b.setDisable(false);
            } else {
                b.setDisable(true);
            }
        }
    }

    /**
     * Resize all the elements in the GUI
     */
    public void resizeAll() {
        // Resize DraftPool

        // Resize PatternCard
    }

    /**
     * Reset the GUI elements to a clean state
     */
    public void cleanUI() {
        //TODO IMPLEMENT THIS
        for (WindowPatternController c : controllers.values()) {
            c.setClickableCells(true, null);
        }
    }

    /**
     * Asks the user for a new value to set on a die. Used by {@link ToolCard}s
     * @param color the {@link SagradaColor} of the die that will receive the new value
     */
    public void toolSetValue(SagradaColor color) {
        newValue = 0;
        Stage valueChooser = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();

        Button confirm = new Button("Confirm new value");
        confirm.setDisable(true);
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                valueChooser.hide();
                gui.getClientHandler().sendValue(newValue);
            }
        });

        HBox values = new HBox();
        for (int i = Die.MIN; i <= Die.MAX; i++) {
            URL url = getClass().getClassLoader().getResource("images/shades/" + i + "@4x.png");
            ImageView view = new ImageView(new Image(url.toExternalForm()));
            view.setCache(true);
            view.setSmooth(true);
            view.setFitWidth(GUI.CHOOSER_TILE_SIZE);
            view.setFitHeight(GUI.CHOOSER_TILE_SIZE);
            view.setPreserveRatio(true);
            view.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    confirm.setDisable(false);
                    newValue = values.getChildren().indexOf(event.getSource())+1;
                }
            });
            values.getChildren().add(view);
        }

        Label l = new Label("Choose a new value for the chosen " + color.toString().toLowerCase() + " die:");

        Insets spacing = new Insets(20);
        BorderPane.setMargin(values, spacing);
        BorderPane.setMargin(l, spacing);
        BorderPane.setMargin(confirm, spacing);
        valueMain.setPadding(spacing);
        valueMain.setTop(l);
        valueMain.setCenter(values);
        valueMain.setBottom(confirm);

        valueChooser.initModality(Modality.APPLICATION_MODAL);
        valueChooser.setScene(new Scene(valueMain));
        valueChooser.sizeToScene();
        valueChooser.showAndWait();
    }

    /**
     * Asks the user to choose a {@link Die} from the draft pool. Used by {@link ToolCard}s
     */
    public void toolChooseDieFromDraftPool() {
        showMessage("Choose a die from the Draft Pool!");
        for (Node n : fxDraftPool.getChildren()) {
            ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3), n);
            transition.setByX(0.7);
            transition.setByY(0.7);
            transition.setAutoReverse(true);
            transition.setCycleCount(2);
            transition.play();
            EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    transition.stop();
                    int i = fxDraftPool.getChildren().indexOf(event.getSource());
                    for (Node n : fxDraftPool.getChildren()) {
                        n.setOnMouseClicked(null);
                    }
                    gui.getClientHandler().sendDieFromDP(gui.getModel().getDraftPool().get(i));
                }
            };
            n.setOnMouseClicked(handler);
        }
    }

    /**
     * Asks the user to choose a die from their window. Used by {@link ToolCard}s
     */
    public void toolChooseDieFromWindowPattern() {
        showMessage("Choose a die from your window!");
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            if (((StackPane) n).getChildren().size() < 2) {
                ColorAdjust darken = new ColorAdjust();
                darken.setBrightness(-0.5);
                n.setEffect(darken);
                continue;
            }
            /*Node dieNode = ((StackPane) n).getChildren().get(1);
            ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3), dieNode);
            transition.setByX(0.7);
            transition.setByY(0.7);
            transition.setAutoReverse(true);
            transition.setCycleCount(2);
            transition.play();*/
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //transition.stop();
                    Node source = (Node) event.getSource();
                    int row = GridPane.getRowIndex(source);
                    int col = GridPane.getColumnIndex(source);
                    Die d = gui.getModel().getMyself().getPlayerWindow().getCellAt(row, col).getDie();
                    for (Node n : root.getChildren()) {
                        n.setEffect(null);
                        n.setOnMouseClicked(null);
                    }
                    gui.getClientHandler().sendDieFromWP(d, row, col);
                }
            });
        }
    }

    /**
     * Asks the user if they want to increase or decrease the value of the previously chosen die. Used by {@link ToolCard}s
     */
    public void toolChooseIfDecrease() {
        Stage dialog = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();

        Button increaseBtn = new Button("Increase");
        Button decreaseBtn = new Button("Decrease");
        increaseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendDecreaseChoice(false);
            }
        });
        decreaseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendDecreaseChoice(true);
            }
        });

        HBox values = new HBox();
        values.getChildren().add(increaseBtn);
        values.getChildren().add(decreaseBtn);

        Label l = new Label("Choose if you want to increase or decrease the number on the die:");

        Insets spacing = new Insets(20);
        BorderPane.setMargin(values, spacing);
        BorderPane.setMargin(l, spacing);
        valueMain.setPadding(spacing);
        valueMain.setTop(l);
        valueMain.setCenter(values);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(valueMain));
        dialog.sizeToScene();
        dialog.showAndWait();
    }

    /**
     * Asks the user if they want to place a die on their window or back into the draft pool. Used by {@link ToolCard}s
     */
    public void toolChooseIfPlaceDie(int number) {
        Stage dialog = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();

        Button placeOnWindowBtn = new Button("Place it on my window");
        Button placeOnRoundTrackBtn = new Button("Place it in the draft pool");
        placeOnWindowBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendPlacementChoice(true);
            }
        });
        placeOnRoundTrackBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendPlacementChoice(false);
            }
        });

        HBox values = new HBox();
        values.getChildren().add(placeOnWindowBtn);
        values.getChildren().add(placeOnRoundTrackBtn);

        Label l = new Label("Where do you want to place the die with number " + number + "?");

        Insets spacing = new Insets(20);
        BorderPane.setMargin(values, spacing);
        BorderPane.setMargin(l, spacing);
        valueMain.setPadding(spacing);
        valueMain.setTop(l);
        valueMain.setCenter(values);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(valueMain));
        dialog.sizeToScene();
        dialog.showAndWait();
    }

    /**
     * Asks the user if they want to move one or two dice. Used by {@link ToolCard}s
     */
    public void toolChooseToMoveOneDie() {
        Stage dialog = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();

        Button moveOneBtn = new Button("Move two dice");
        Button moveTwoBtn = new Button("Move only one die");
        moveOneBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendPlacementChoice(true);
            }
        });
        moveTwoBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.hide();
                gui.getClientHandler().sendPlacementChoice(false);
            }
        });

        HBox values = new HBox();
        values.getChildren().add(moveOneBtn);
        values.getChildren().add(moveTwoBtn);

        Label l = new Label("How many dice do you want to move?");

        Insets spacing = new Insets(20);
        BorderPane.setMargin(values, spacing);
        BorderPane.setMargin(l, spacing);
        valueMain.setPadding(spacing);
        valueMain.setTop(l);
        valueMain.setCenter(values);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(new Scene(valueMain));
        dialog.sizeToScene();
        dialog.showAndWait();
    }

    /**
     * Asks the user for the destination coordinates for a die. Used by {@link ToolCard}s
     */
    public void toolSetNewCoordinates() {
        showMessage("Choose an empty cell where to place the die!");
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            if (((StackPane) n).getChildren().size() > 1) {
                ColorAdjust darken = new ColorAdjust();
                darken.setBrightness(-0.5);
                n.setEffect(darken);
                continue;
            }
            //n.getStyleClass().add("chosen");
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node source = (Node) event.getSource();
                    int row = GridPane.getRowIndex(source);
                    int col = GridPane.getColumnIndex(source);
                    for (Node n : root.getChildren()) {
                        n.setOnMouseClicked(null);
                        //n.getStyleClass().remove("chosen");
                        n.setEffect(null);
                    }
                    gui.getClientHandler().sendNewCoordinates(row, col);
                }
            });
        }
    }

    /**
     * Shows the {@link RoundTrack} in an "interactive" mode. Used by {@link ToolCard}s
     */
    public void toolChooseDieFromRoundTrack() {
        showRoundTrack(true);
    }

    /**
     * Asks the user to choose two dice from their window. This method actually let them choose the first die and will call
     * {@link #chooseSecondDice(Node)} in order to make him choose sequentially. Used by {@link ToolCard}s
     */
    public void toolChooseTwoDice() {
        showMessage("Choose the first die from your window!");
        chosenDice = 0;
        // DARKEN ALL NODES
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            darken.setBrightness(-0.5);
            n.setEffect(darken);
            // SET CLICK LISTENERS ON NON-EMPTY CELLS
            if (((StackPane) n).getChildren().size() > 1) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosenDice++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        firstDieRow = GridPane.getRowIndex(source);
                        firstDieColumn = GridPane.getColumnIndex(source);
                        for (Node m : root.getChildren()) {
                            m.setEffect(null);
                            m.setOnMouseClicked(null);
                        }
                        // REPEAT
                        chooseSecondDice(source);
                    }
                });
            }
        }
    }

    /**
     * Continues what started by {@link #toolChooseTwoDice()}. This method actually asks the user for the second of the
     * two needed dice. Used by {@link ToolCard}s
     * @param source the previously selected {@link Node} (a dice or a cell containing a die) so that it isn't highlighted
     *               by the visual effect
     */
    private void chooseSecondDice(Node source) {
        if (chosenDice != 1) {
            showMessage("variable chosenDice should be equal to 1, but it isn't.");
            return;
        }
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        showMessage("Choose the second die from your window!");
        // DARKEN ALL NODES
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            n.setEffect(darken);
            if (n != source) {
                darken.setBrightness(-0.5);
            }
            // SET CLICK LISTENERS ON NON-EMPTY CELLS
            if (((StackPane) n).getChildren().size() > 1) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosenDice++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        secondDieRow = GridPane.getRowIndex(source);
                        secondDieColumn = GridPane.getColumnIndex(source);
                        for (Node n : root.getChildren()) {
                            n.setEffect(null);
                            n.setOnMouseClicked(null);
                        }
                        if (chosenDice == 2) {
                            gui.getClientHandler().sendTwoDice(firstDieRow, firstDieColumn, secondDieRow, secondDieColumn);
                        } else {
                            showMessage("Something went terribly wrong.");
                        }
                        chosenDice = 0;
                        firstDieRow = -1;
                        firstDieColumn = -1;
                        secondDieRow = -1;
                        firstDieColumn = -1;
                    }
                });
            }
        }
    }

    /**
     * Asks the user to choose two pairs of coordinates from their window. This method actually let them choose the
     * first pair and will call {@link #chooseSecondCoord(Node)} in order to make him choose sequentially. Used by
     * {@link ToolCard}s
     */
    public void toolChooseTwoCoordinates() {
        showMessage("Choose the first empty cell from your window!");
        chosenCoords = 0;
        // DARKEN ALL NODES
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            darken.setBrightness(-0.5);
            n.setEffect(darken);
            // SET CLICK LISTENERS ON EMPTY CELLS
            if (((StackPane) n).getChildren().size() < 2) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosenCoords++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        firstCellRow = GridPane.getRowIndex(source);
                        firstCellColumn = GridPane.getColumnIndex(source);
                        for (Node m : root.getChildren()) {
                            m.setEffect(null);
                            m.setOnMouseClicked(null);
                        }
                        // REPEAT
                        chooseSecondCoord(source);
                    }
                });
            }
        }
    }

    /**
     * Continues what started by {@link #toolChooseTwoCoordinates()}. This method actually asks the user for the second
     * pair of the two needed. Used by {@link ToolCard}s
     *
     * @param source the previously selected {@link Node} (a dice or a cell containing a die) so that it isn't highlighted
     *               by the visual effect
     */
    private void chooseSecondCoord(Node source) {
        if (chosenCoords != 1) {
            showMessage("variable chosenCoords should be equal to 1, but it isn't.");
            return;
        }
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        showMessage("Choose the second empty cell from your window!");
        // DARKEN ALL NODES
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            n.setEffect(darken);
            if (n != source) {
                darken.setBrightness(-0.5);
            }
            // SET CLICK LISTENERS ON EMPTY CELLS
            if (((StackPane) n).getChildren().size() < 2) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosenCoords++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        secondCellRow = GridPane.getRowIndex(source);
                        secondCellColumn = GridPane.getColumnIndex(source);
                        for (Node n : root.getChildren()) {
                            n.setEffect(null);
                            n.setOnMouseClicked(null);
                        }
                        if (chosenCoords == 2) {
                            gui.getClientHandler().sendTwoNewCoordinates(firstCellRow, firstCellColumn, secondCellRow, secondCellColumn);
                        } else {
                            showMessage("Something went terribly wrong.");
                        }
                        chosenCoords = 0;
                        firstCellRow = -1;
                        firstCellColumn = -1;
                        secondCellRow = -1;
                        firstCellColumn = -1;
                    }
                });
            }
        }
    }
}
