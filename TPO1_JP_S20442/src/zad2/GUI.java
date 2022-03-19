package zad2;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

public class GUI {
    private JFXPanel panel;
    private WebEngine webEngine;
    private final static String WIKI_URL = "https://wikipedia.org/wiki/%s";
    private String city;

    public GUI(String city){
        this.city = city;
    }

    public void show()
    {
        JFrame frame = new JFrame("WikipediaFX");
        panel = new JFXPanel();
        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = createScene();
                panel.setScene(scene);
            }
        });
    }

    private  Scene createScene() {
        Group root  =  new  Group();
        Scene  scene  =  new  Scene(root);
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        webView.setPrefHeight(600);
        webView.setPrefWidth(1000);
        webEngine.load(String.format(WIKI_URL, city));
        root.getChildren().add(webView);
        return (scene);
    }
}
