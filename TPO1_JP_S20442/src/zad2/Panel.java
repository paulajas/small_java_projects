package zad2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Panel extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle ( "TPO" );
        GridPane gridpane = new GridPane ();

        Button send = new Button ( "_Send" );
        send.setMaxWidth ( 300 );
        GridPane.setHalignment ( send, HPos.CENTER );
        GridPane.setValignment ( send, VPos.CENTER );
        send.setMnemonicParsing ( true );

        Button clear = new Button ( "_Clear" );
        clear.setMaxWidth ( 300 );
        GridPane.setHalignment ( clear, HPos.CENTER );
        GridPane.setValignment ( clear, VPos.CENTER );
        clear.setMnemonicParsing ( true );

        Label label1 = new Label ( "" );
//        label1.setMaxWidth ( 300 );
        label1.setAlignment ( Pos.CENTER );

        Label label2 = new Label ( "" );
        label2.setMaxWidth ( 300 );
        label2.setAlignment ( Pos.CENTER );

        Label label3 = new Label ( "" );
        label3.setMaxWidth ( 300 );
        label3.setAlignment ( Pos.CENTER );


        TextField countryField = new TextField ();
        countryField.setAlignment(Pos.CENTER);
        Tooltip aTool = new Tooltip ();
        aTool.setText ( "Enter country" );
        countryField.setTooltip ( aTool );

        TextField cityField = new TextField ();
        Tooltip bTool = new Tooltip ();
        bTool.setText ( "Enter city" );
        cityField.setTooltip ( bTool );
        cityField.setAlignment(Pos.CENTER);

        TextField c = new TextField ();
        Tooltip cTool = new Tooltip ();
        cTool.setText ( "Enter currency" );
        c.setTooltip ( cTool );
        c.setAlignment(Pos.CENTER);

        gridpane.add ( label1, 0, 0, 3, 1 );
        gridpane.add ( label2, 0, 1, 3, 1 );
        gridpane.add ( label3, 0, 2, 3, 1 );
        gridpane.add ( countryField, 0, 3, 1, 1 );
        gridpane.add ( cityField, 1, 3, 1, 1 );
        gridpane.add ( c, 2, 3, 1, 1 );
        gridpane.add ( send, 0, 4, 3, 1 );
        gridpane.add ( clear, 0, 5, 3, 3 );

        clear.setOnAction ( (e) -> {
            countryField.clear ();
            cityField.clear ();
            c.clear();
        });

        send.setOnAction ( (e) -> {
            String country = countryField.getText();
            String city = cityField.getText();
            String curr = c.getText();
            Service ser = new Service(country);
            label1.setStyle ( "-fx-background-color: yellow" );
            label1.setStyle("-fx-text-fill: black");
            label1.setText ( ser.getWeather(city));
            label2.setStyle ( "-fx-background-color: yellow" );
            label2.setStyle("-fx-text-fill: black");
            label2.setText (String.valueOf(ser.getRateFor(curr)));
            label3.setStyle ( "-fx-background-color: yellow" );
            label3.setStyle("-fx-text-fill: black");
            label3.setText (String.valueOf(ser.getNBPRate()));
            GUI gui = new GUI(city);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gui.show();
                }
            });
        } );

        Scene scene = new Scene ( gridpane, 300, 150 );
        stage.setScene ( scene );
        stage.show ();
        stage.setResizable ( true );
    }
    public void run() {
        launch();
    }

}
