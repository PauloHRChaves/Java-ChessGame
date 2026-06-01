package com.pchess;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Gerencia o ciclo de vida da interface gráfica, o carregamento de recursos (FXML/CSS) e a navegação dinâmica entre as diferentes telas MENU e GAME_LAYOUT.
 */
public class App extends Application {
    // Janela gráfica única que mantém o conteúdo visual atual do app
    private static Scene scene;

    /**
     * Ponto de inicialização da interface gráfica do JavaFX.
     * Configura a tela inicial (Menu), carrega fontes, estilos e exibe a janela principal.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carrega fonte personalizada
        Font.loadFont(App.class.getResourceAsStream("/fonts/Jersey25-Regular.ttf"), 10);

        // Inicializa a cena com o FXML do MENU inicial e define as dimensões padrão
        scene = new Scene(loadFXML("menu"), 1600, 1024);

        // Aplica o CSS correspondente ao menu
        applyStyles(scene, "menu");
        
        // Define as restrições de tamanho mínimo da janela
        stage.setMinWidth(1150);
        stage.setMinHeight(910);
        
        // Configura e exibe a janela (Stage) para o usuário
        stage.setTitle("Java Chess");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Utilitário para carregar arquivos FXML de forma dinâmica.
     * Busca os arquivos mapeados dentro do diretório de recursos '/pages/'.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        String path = "/pages/" + fxml + ".fxml";
        URL url = App.class.getResource(path);
        return new FXMLLoader(url).load();
    }
    
    /**
     * Limpa os estilos anteriores e aplica uma nova folha de estilo CSS à cena.
     * Busca os arquivos mapeados dentro do diretório de recursos '/styles/'.
     */
    private static void applyStyles(Scene scene, String css) throws IOException {
        scene.getStylesheets().clear();
        String path = "/styles/" + css + ".css";
        URL cssUrl = App.class.getResource(path);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
    }

    /**
     * Altera o conteúdo e o estilo da janela de forma dinâmica.
     */
    public static void setRoot(String fxml, String css) throws IOException {
        scene.setRoot(loadFXML(fxml));
        applyStyles(scene, css);
    }

    public static void main(String[] args) {
        launch();
    }
}