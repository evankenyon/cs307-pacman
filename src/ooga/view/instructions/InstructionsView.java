package ooga.view.instructions;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ResourceBundle;


import java.io.File;
import java.util.ResourceBundle;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;

public class InstructionsView {
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 400;
    private static final int TOP_HEIGHT = 350;
    private static final int BUTTON_WIDTH = 100;
    private static final int SPACING = 10;

    private Stage instructionsStage;
    private ResourceBundle myResources;
    private VBox top1;
    private VBox top2;
    VBox[] tops;

    public InstructionsView(Stage instructionsStage, String selectedLanguage, String selectedGameType) {
        myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, selectedLanguage));
        VBox top1 = top1();
        VBox top2 = top2();
        VBox top3 = top3();
        this.tops = new VBox[]{top1, top2, top3};
        this.instructionsStage = instructionsStage;
        this.instructionsStage.setTitle("PACMAN INSTRUCTIONS");
        Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
        this.instructionsStage.getIcons().add(favicon);
        instructionScene(0);
    }

    private void instructionScene(int currIdx) {
        VBox holder = new VBox();
        HBox nextBackClose = nextBackClose(currIdx - 1, currIdx + 1);
        holder.getChildren().addAll(tops[currIdx], nextBackClose);
        Scene instructionScene = new Scene(holder, SCENE_WIDTH, SCENE_HEIGHT);
        this.instructionsStage.setScene(instructionScene);
        this.instructionsStage.show();
    }

    private VBox top1() {
        VBox placeholder = new VBox();
        Label helloWorld = new Label("How to play this version of Pac Man");
        placeholder.getChildren().addAll(helloWorld);
        placeholder.setMinHeight(TOP_HEIGHT);
        return placeholder;
    }

    private VBox top2() {
        VBox placeholder = new VBox();
        Label header = new Label("Use the keys WASD to move");
        ImageView wasd = new ImageView(new Image(new File("data/images/WASD.gif").toURI().toString()));
        wasd.setPreserveRatio(true);
        wasd.setFitWidth(SCENE_WIDTH - 20);
        placeholder.setAlignment(Pos.TOP_CENTER);
        placeholder.getChildren().addAll(header, wasd);
        placeholder.setMinHeight(TOP_HEIGHT);
        return placeholder;
    }

    private VBox top3() {
        VBox placeholder = new VBox();
        Label helloWorld = new Label("Hello World 3!");
        placeholder.getChildren().addAll(helloWorld);
        placeholder.setMinHeight(TOP_HEIGHT);
        return placeholder;
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

}
