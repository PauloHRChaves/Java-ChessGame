package com.pchess;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Monta o conteudo que vai dentro do Stage(JANELA)
        // Carrega o FXML da tela inicial (menu.fxml) e define o tamanho da janela
        scene = new Scene(loadFXML("menu"), 1600, 1024);

        // Aplica o css
        applyStyles(scene, "menu");
        
        // Tamanho minimo da Janela
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        
        stage.setTitle("Java Chess");
        stage.setScene(scene);
        stage.show();
    }

    // Sistema de Navegação Dinâmica via Gerenciador de Janela Única (Single Window Dynamic Navigation)
    public static void setRoot(String fxml, String css) throws IOException {
        scene.setRoot(loadFXML(fxml));
        applyStyles(scene, css);
    }

    // Verificar CSS
    private static void applyStyles(Scene scene, String css) throws IOException {
        scene.getStylesheets().clear();
        String path = "/styles/" + css + ".css";
        URL cssUrl = App.class.getResource(path);
        
        if (cssUrl == null) {
            throw new IOException("Arquivo CSS não encontrado: " + path);
        }
        scene.getStylesheets().add(cssUrl.toExternalForm());
    }

    // Verificar FXML
    private static Parent loadFXML(String fxml) throws IOException {
        String path = "/pages/" + fxml + ".fxml";
        URL url = App.class.getResource(path);
        if (url == null) throw new IOException("Arquivo FXML não encontrado: " + path);
        return new FXMLLoader(url).load();
    }

    public static void main(String[] args) {
        launch();
    }

}