<h1> PlayCHESS </h1>

Jogo de xadrez desenvolvido em Java aplicando Padrões de Projeto do GoF (Gang of Four).

Tecnologias usadas:
- Java OpenJDK 17
- JavaFX 17.0.2
- Maven 3.9.12 (via Maven Wrapper – mvnw)

<br>
<h2>Execução do Código:</h2>
Para execução é necessário ter o Java 17 e apenas rodar o comando:

<br>
- No Windows (PowerShell/CMD): <br>
<pre>
./mvnw javafx:run
</pre>
- No Linux ou Mac:<br>
<pre>
chmod +x mvnw
./mvnw javafx:run
</pre>

<br>

Recomendado usar a versão com clean para limpar compilações anteriores caso haja alterações significantes no código.

<pre>
./mvnw clean javafx:run
</pre>

<br>

O projeto foi desenvolvido seguindo padrões de arquitetura e ferramentas que visam a organização e a facilidade de colaboração:

- Padrão MVC (Model-View-Controller): A estrutura do projeto é baseada no modelo MVC convencional, separando claramente a lógica de negócio (Model), a interface gráfica (View) e o controle de eventos (Controller).

- Portabilidade com Maven Wrapper: Graças ao mvnw, o Maven é baixada automaticamente na primeira execução, garantindo que o ambiente de desenvolvimento seja idêntico para todos os colaboradores, independente de configurações locais.

- Interface: Utilização de FXML para a estruturação da interface e CSS para a estilização visual, permitindo um design desacoplado do código Java.


<br>
<h2>Padrões de Projetos almejados:</h2>

Criacional:
- Singleton: classe central que controla o fluxo, o cérebro (GameEngine).
- Factory: criar peças sem expor a lógica de instanciação (PiecesFactory).

<br>

Comportamental:
- <u>Memento: historico de partida ou jogadas.</u>
- Observer:  atualizar a interface quando o estado do jogo muda.
- Strategy: definir a lógica de movimento de cada peça de forma independente.
- State: alterar o comportamento do jogo conforme o estado (ex: turno, xeque, xeque-mate).

<br>

Estrutural:
- Composite: organizar a interface em hierarquia (ChessBoardView).
- Flyweight: compartilhar as imagens de peças reutilizadas (ImageCache).

<br>
<h2>Fluxograma:</h2>

<h3>App.java:</h3>
•	Configura a Stage (janela) e a Scene inicial.<br>
•	Carrega o menu.fxml.<br>

<hr>
<h3>menu.fxml:</h3>
•	Exibe o botão "Jogar Partida".<br>
•	Evento: O usuário clica no botão.<br>

<hr>
<h3>MenuController.java:</h3>
•	O método <code>onStartGameClicked()</code> é disparado.<br>
•	Ação: Chama <code>GameEngine.getInstance().startGame()</code>.<br>

<hr>
<h3>GameEngine.java:</h3>
•	<code>getInstance()</code>: Verifica se o motor já existe (se não, cria agora).<br>
•	Executa o construtor do GameEngine e parte para toda criação lógica do tabuleiro em <code>this.board = new Board();</code><br>
•	Realiza toda lógica criação do tabuleiro, criação e posicionamento das peças, mas sem nada visual.<br>
•	Logo após toda a lógica chama o método <code>startGame()</code>: Comanda o App.java a trocar a tela para game_layout.fxml.<br>

<hr>
<h3>game_layout.fxml:</h3>
•	A nova tela é carregada.<br>
•	O JavaFX identifica o fx:controller="GameViewController".

<hr>
<h3>GameViewController.java:</h3>
•	O método <code>initialize()</code> roda automaticamente.<br>
•	Cria o tabuleiro visual (ChessBoardView).<br>
•	Chama <code>GameEngine.getInstance().setupGame()</code> para ligar a lógica das peças ao visual.<br>
<hr>