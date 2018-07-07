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
import javafx.scene.control.Label;
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
    int newValue;
    Die selectedFromRT;
    private boolean firstUpdate;
    private int theRound;
    int firstDieRow;
    int firstDieColumn;
    int secondDieRow;
    int secondDieColumn;
    int chosen;

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
    private ImageView fxRoundTrack;

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

    public static Node getDieMark(double size) {
        Circle mark = new Circle(size);
        mark.setFill(Color.BLACK);
        mark.setOpacity(0.84);
        return mark;
    }

    public void showRoundTrackWrapper(MouseEvent event) {
        showRoundTrack(false);
    }

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
            Label label = new Label(String.valueOf(i));
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
                            int whichDie = round.getChildren().indexOf(source);
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

    @FXML
    void endTurn(ActionEvent event) {
        gui.endTurn();
    }

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

    public void initBoards() {
        firstUpdate = false;
        fxTimer.textProperty().bind(gui.secondsRemainingProperty().asString("%s s"));

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
                controllers.get(players.get(i)).removeNameAndTokens();

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
        // Load Tool Cards and Objective Cards
        String privPath = "images/objectivecards/private/" + model.getMyself().getPrivateObjectiveCard().getName() + ".png";
        String privUrl = getClass().getClassLoader().getResource(privPath).toExternalForm();
        fxPrivateObjectiveCard.setImage(new Image(privUrl));

        int n = fxPublicObjectiveCardsContainer.getChildren().size();
        for (int i = 0; i < model.getPublicObjectiveCards().size(); i++) {
            String pubPath = "images/objectivecards/public/" + model.getPublicObjectiveCards().get(i).getName() + ".png";
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
            String pubPath = "images/toolcards/" + model.getToolCards().get(i).getName() + ".png";
            String pubUrl = getClass().getClassLoader().getResource(pubPath).toExternalForm();
            if (i < n) {
                ((ImageView) fxToolCardsContainer.getChildren().get(i)).setImage(new Image(pubUrl));
            } else {
                ImageView copy = (ImageView) fxToolCardsContainer.getChildren().get(0);
                copy.setImage(new Image(pubUrl));
                fxToolCardsContainer.getChildren().add(copy);
            }
        }
    }

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


    }

    public void showMessage(String s) {
        fxMessage.setText(s);
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
            AnchorPane.setLeftAnchor(c, offset); //TODO FIX THIS
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

    public void cleanUI() {
        //TODO IMPLEMENT THIS
        for (WindowPatternController c : controllers.values()) {
            c.setClickableCells(true, null);
        }
    }

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

    public void toolChooseDieFromWindowPattern() {
        showMessage("Choose a die from your window!");
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            if (((StackPane) n).getChildren().size() < 2) {
                continue;
            }
            Node dieNode = ((StackPane) n).getChildren().get(1);
            ScaleTransition transition = new ScaleTransition(Duration.seconds(0.3), dieNode);
            transition.setByX(0.7);
            transition.setByY(0.7);
            transition.setAutoReverse(true);
            transition.setCycleCount(2);
            transition.play();
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    transition.stop();
                    Node source = (Node) event.getSource();
                    int row = GridPane.getRowIndex(source);
                    int col = GridPane.getColumnIndex(source);
                    Die d = gui.getModel().getMyself().getPlayerWindow().getCellAt(row, col).getDie();
                    for (Node n : root.getChildren()) {
                        n.setOnMouseClicked(null);
                    }
                    gui.getClientHandler().sendDieFromWP(d, row, col);
                }
            });
        }
    }

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

    public void toolChooseIfPlaceDie() {
        Stage dialog = new Stage(StageStyle.UNDECORATED);
        BorderPane valueMain = new BorderPane();

        Button placeOnWindowBtn = new Button("Place it on my window");
        Button placeOnRoundTrackBtn = new Button("Place it on the round track");
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

        Label l = new Label("Where do you want to place the die?");

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

    public void toolSetNewCoordinates() {
        showMessage("Choose an empty cell where to place the die!");
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            if (((StackPane) n).getChildren().size() > 1) {
                continue;
            }
            n.getStyleClass().add("chosen");
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node source = (Node) event.getSource();
                    int row = GridPane.getRowIndex(source);
                    int col = GridPane.getColumnIndex(source);
                    for (Node n : root.getChildren()) {
                        n.setOnMouseClicked(null);
                        n.getStyleClass().remove("chosen");
                    }
                    gui.getClientHandler().sendNewCoordinates(row, col);
                }
            });
        }
    }

    public void toolChooseDieFromRoundTrack() {
        showRoundTrack(true);
    }

    public void toolChooseTwoDice() {
        showMessage("Choose the first die from your window!");
        chosen = 0;
        // DARKEN ALL NODES
        Player myself = gui.getModel().getMyself();
        GridPane root = (GridPane) ((Pane) ((VBox) anchorPanes.get(myself).getChildren().get(1)).getChildren().get(0)).getChildren().get(0);
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            darken.setBrightness(0.5);
            n.setEffect(darken);
            // SET CLICK LISTENERS ON NON-EMPTY CELLS
            if (((StackPane) n).getChildren().size() > 1) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosen++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        firstDieRow = GridPane.getRowIndex(source);
                        firstDieColumn = GridPane.getColumnIndex(source);
                        for (Node n : root.getChildren()) {
                            n.setEffect(null);
                            n.setOnMouseClicked(null);
                        }

                    }
                });
            }
        }
        // REPEAT
        showMessage("Choose the second die from your window!");
        // DARKEN ALL NODES
        for (Node n : root.getChildren()) {
            ColorAdjust darken = new ColorAdjust();
            darken.setBrightness(0.5);
            n.setEffect(darken);
            // SET CLICK LISTENERS ON NON-EMPTY CELLS
            if (((StackPane) n).getChildren().size() > 1) {
                n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        chosen++;
                        Node source = (Node) event.getSource();
                        // KEEP THE SELECTED COORDINATES
                        secondDieRow = GridPane.getRowIndex(source);
                        secondDieColumn = GridPane.getColumnIndex(source);
                        for (Node n : root.getChildren()) {
                            n.setEffect(null);
                            n.setOnMouseClicked(null);
                        }

                    }
                });
            }
        }
        if (chosen == 2) {
            gui.getClientHandler().sendTwoDice(firstDieRow, firstDieColumn, secondDieRow, secondDieColumn);
        } else {
            showMessage("Something went terribly wrong.");
        }
    }
}
