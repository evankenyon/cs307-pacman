package ooga.view.instructions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.ResourceBundle;

import java.io.File;
import java.util.ResourceBundle;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;

public class InstructionsView {
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 400;
    private static final int TOP_HEIGHT = 330;
    private static final int TOP_IMG_MAX_HEIGHT = 220;
    private static final int TOP_IMG_MAX_WIDTH = 360;
    private static final int BUTTON_WIDTH = 100;
    private static final int PADDING = 20;
    private static final int SPACING = 10;

    private String STYLESHEET;
    private Stage instructionsStage;
    private ResourceBundle myResources;
    private VBox[] tops;

    public InstructionsView(Stage instructionsStage, String selectedLanguage, String selectedGameType, String selectedViewMode) {
        this.STYLESHEET = "/ooga/view/resources/" + selectedViewMode + ".css";
        myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, selectedLanguage));
        VBox top1 = top1(selectedGameType);
        VBox top2 = top2();
        VBox top3 = top3();
        VBox top4 = top4();
        VBox top5 = top5();
        VBox top6 = top6();
        VBox top7 = top7();
        VBox top8 = top8();
        this.tops = new VBox[]{top1, top2, top3, top4, top5, top6, top7, top8};
        this.instructionsStage = instructionsStage;
        this.instructionsStage.setTitle("INSTRUCTIONS");
        Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
        this.instructionsStage.getIcons().add(favicon);
        instructionScene(0);
    }

    private void instructionScene(int currIdx) {
        VBox holder = new VBox();
        holder.getStyleClass().add("boxes");
        holder.setPadding(new Insets(PADDING));
        HBox nextBackClose = nextBackClose(currIdx - 1, currIdx + 1);
        holder.getChildren().addAll(tops[currIdx], nextBackClose);
        Scene instructionScene = new Scene(holder, SCENE_WIDTH, SCENE_HEIGHT);
        instructionScene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
        this.instructionsStage.setScene(instructionScene);
        this.instructionsStage.show();
    }

    private VBox top1(String selectedGameType) {
        String headerText = myResources.getString("header1");
        String imageFile = myResources.getString("imageFile1");
        String instructionText = myResources.getString("instructionText1");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top2() {
        String headerText = myResources.getString("header2");
        String imageFile = myResources.getString("imageFile2");
        String instructionText = myResources.getString("instructionText2");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top3() {
        String headerText = myResources.getString("header3");
        String imageFile = myResources.getString("imageFile3");
        String instructionText = myResources.getString("instructionText3");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top4() {
        String headerText = myResources.getString("header4");
        String imageFile = myResources.getString("imageFile4");
        String instructionText = myResources.getString("instructionText4");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top5() {
        String headerText = myResources.getString("header5");
        String imageFile = myResources.getString("imageFile5");
        String instructionText = myResources.getString("instructionText5");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top6() {
        String headerText = myResources.getString("header6");
        String imageFile = "statsButton-" + myResources.getString("stats") + ".png";
        String instructionText = myResources.getString("instructionText6");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top7() {
        String headerText = myResources.getString("header7");
        String imageFile = "saveButton-" + myResources.getString("save") + ".png";
        String instructionText = myResources.getString("instructionText7");
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox top8() {
        String headerText = "HOW TO RESTART THE GAME";
        String imageFile = "restartButton-" + myResources.getString("restart") + ".png";
        String instructionText = "Click the RESTART button to restart the game from fresh.";
        return makeTopVBox(headerText, imageFile, instructionText);
    }

    private VBox makeTopVBox(String headerText, String imageFile, String instructionText) {
        VBox topHolder = new VBox();
        topHolder.getStyleClass().add("boxes");
        topHolder.setAlignment(Pos.TOP_CENTER);
        topHolder.setMinHeight(TOP_HEIGHT);
        topHolder.setSpacing(SPACING);
        Label header = new Label(headerText);
        header.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        header.getStyleClass().add("header");
        ImageView image = makeTopImg(imageFile);
        Text text = new Text(instructionText);
        text.getStyleClass().add("text");
        text.setFont(Font.font ("Verdana", 14));
        text.setWrappingWidth(SCENE_WIDTH - (2 * PADDING));
        text.setTextAlignment(TextAlignment.CENTER);
        topHolder.getChildren().addAll(header, image, text);
        return topHolder;
    }

    private HBox nextBackClose(int backIdx, int nextIdx) {
        Boolean backVisible = true;
        if (backIdx < 0) {
            backVisible = false;
        }
        Boolean nextVisible = true;
        if (nextIdx >= tops.length) {
            nextVisible = false;
        }

        ImageView back = makeImgButton("back");
        back.setOnMouseReleased(e -> {
            instructionScene(backIdx);
        });
        back.setVisible(backVisible);

        ImageView next = makeImgButton("next");
        next.setOnMouseReleased(e -> {
            instructionScene(nextIdx);
        });
        next.setVisible(nextVisible);

        ImageView close = makeImgButton("close");
        close.setOnMouseReleased(e -> {
            this.instructionsStage.close();
        });

        HBox nextBackClose = new HBox();
        nextBackClose.setAlignment(Pos.CENTER);
        nextBackClose.setSpacing(SPACING);
        nextBackClose.getChildren().addAll(back, next, close);
        return nextBackClose;
    }

    private ImageView makeImgButton(String label) {
        String imgPath = "data/images/" + label + "Button-" + myResources.getString(label) + ".png";
        ImageView img = new ImageView(new Image(new File(imgPath).toURI().toString()));
        img.setPreserveRatio(true);
        img.setFitWidth(BUTTON_WIDTH);
        return img;
    }

    private ImageView makeTopImg(String filename) {
        ImageView img = new ImageView(new Image(new File("data/images/" + filename).toURI().toString()));
        img.setPreserveRatio(true);
        double imgWidth = img.getImage().getWidth();
        double imgHeight = img.getImage().getHeight();
        if (imgWidth > imgHeight) {
            img.setFitWidth(TOP_IMG_MAX_WIDTH);
        } else {
            img.setFitHeight(TOP_IMG_MAX_HEIGHT);
        }
        return img;
    }

}
