package com.montecarlo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;


import com.montecarlo.utils.Utils;

/**
 * JavaFX App
 */
public class App extends Application {

    private Canvas canvas;
    private TextField textField;
    private Button drawButton;
    private Button drawFunc;
    private Button clear;
    private TextArea piEstimatio;
    private TextArea numberOfPoints;

    private double in = 0;
    private double tot = 0;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Zone de dessin JavaFX");
 
        VBox root = new VBox();
        canvas = new Canvas(800, 600);
        textField = new TextField();
        drawButton = new Button("Draw Points");
        drawFunc = new Button("Draw func");
        clear = new Button("clear");
        piEstimatio = new TextArea("pi estim");
        piEstimatio.setEditable(false);
        piEstimatio.setPrefSize(200, 30);
        numberOfPoints = new TextArea("number of Points");
        numberOfPoints.setPrefSize(200, 30);
        numberOfPoints.setEditable(false);
        HBox controls = new HBox();


        root.getStyleClass().add("root");
        controls.getStyleClass().add("hbox");
        textField.getStyleClass().add("text-field");
        drawButton.getStyleClass().add("button");
        clear.getStyleClass().add("button");
        canvas.getStyleClass().add("canvas");
        piEstimatio.getStyleClass().add("text-area");
        numberOfPoints.getStyleClass().add("text-area");

        controls.getChildren().addAll(textField, drawButton, drawFunc, clear, piEstimatio, numberOfPoints);
        root.getChildren().addAll(controls, canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        initDraw(gc);

        // Ajouter des écouteurs d'événements pour dessiner à la main
        canvas.setOnMousePressed(e -> {
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(e -> {
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });

        drawFunc.setOnAction(e -> {
            List<List<Double>> coord = Utils.getFCoordinates();
            try {
                drawPoints(gc, coord.get(0), coord.get(1), Color.BLACK);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Action du bouton pour dessiner des points
        drawButton.setOnAction(e -> {
            String input = textField.getText();
            Integer samples = 0;
            try
            {
                samples = Integer.parseInt(input);
            }
            catch (final NumberFormatException nbe)
            {
                Alert alert = new Alert(AlertType.ERROR, "Enter a valid number of Points", ButtonType.OK);
                alert.showAndWait();
            }

            final List<Double> x = Utils.getRand(samples);
            final List<Double> y = Utils.getRand(samples);
            try {
                drawPoints(gc, x, y, Color.RED);
                piEstimatio.setText("PI ~ " + String.valueOf(getEstim(x, y)));
                numberOfPoints.setText(tot + " points");

            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                
                e1.printStackTrace();
            }
        });

        clear.setOnAction(e -> {
            clearCanvas(gc);
        });

        Scene scene = new Scene(root, 900, 800); // Taille ajustée pour inclure la zone de texte et le bouton
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm()); // Ajouter la feuille de style CSS
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initDraw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
    }

    private void drawPoints(GraphicsContext gc, final List<Double> x, final List<Double> y, final Color color) throws InterruptedException{
        gc.setFill(color); // Vous pouvez changer la couleur des points si nécessaire

        for (int i = 0; i < x.size(); i++) {
            double xi = x.get(i) *800;
            double yi = y.get(i) *600;
            gc.fillOval(xi, yi, 5, 5);
        }

    }

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
    }


    private double getEstim(final List<Double> x, final List<Double> y)
    {
        tot += x.size();
        for (int i = 0; i < x.size(); i++)
        {
            if (Math.pow(x.get(i), 2) + Math.pow(y.get(i), 2) <= 1)
            {
                in +=1;
            }
        }
        return 4*in/tot;
    }

    public static void main(String[] args) {
        launch(args);
        
       // drawPoints(x, y);

    }
}