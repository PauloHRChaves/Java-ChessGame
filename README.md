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
- Singleton: classe central que controla o fluxo da partida (Orchestrator).
- Factory: criar peças sem expor a lógica de instanciação (PiecesFactory).

<br>

Comportamental:
- <u>Memento: historico de partida ou jogadas.</u>
- Observer: atualizar a interface quando o estado do jogo muda (GameObserver).
- Strategy: definir a lógica de movimento de cada peça de forma independente (MovementStrategy).
- State: alterar o comportamento do jogo conforme o estado, ex: turno, xeque, xeque-mate.

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
•	Ação: Chama <code>Orchestrator.getInstance().startGame()</code>.<br>

<hr>
<h3>Orchestrator.java:</h3>
•	<code>getInstance()</code>: Verifica se o motor já existe (se não, cria agora).<br>
•	Executa o construtor do Orchestrator e parte para toda criação lógica do tabuleiro em <code>this.board = new Board();</code><br>
•	Realiza toda lógica criação do tabuleiro, criação e posicionamento das peças, mas sem nada visual.<br>
•	Instancia o "juiz" da partida em <code>this.referee = new MatchReferee();</code><br>
•	Instancia a classe que vai cuidar dos squares selecionados<code>this.selectionManager = new SelectionManager();</code><br>
•	Instancia a classe que vai lidar dos cliques no tabuleiro<code>this.clickHandler = new ClickHandler(board, referee, selectionManager, this);</code><br>
•	Logo após chama o método <code>startGame()</code>: Comanda o App.java a trocar a tela para game_layout.fxml.<br>

<hr>
<h3>game_layout.fxml:</h3>
•	A nova tela é carregada.<br>
•	O JavaFX identifica o fx:controller="GameController".

<hr>
<h3>GameController.java:</h3>
• O método <code>initialize()</code> é executado automaticamente pelo JavaFX ao carregar a tela.<br>
• Obtém a instância única do <code>Orchestrator</code>, responsável por coordenar a lógica central da partida.<br>
• Cria o componente visual do tabuleiro (<code>ChessBoardView</code>) e injeta nele o <code>SelectionManager</code>, responsável por armazenar a peça selecionada e seus movimentos possíveis.<br>
• Adiciona o tabuleiro visual ao centro do layout principal da interface.<br>
• Registra a <code>ChessBoardView</code> como observadora do jogo utilizando o padrão Observer.<br>
• Sempre que o estado do tabuleiro mudar, a <code>ChessBoardView</code> será notificada automaticamente pelo <code>Orchestrator</code>.<br>
• Realiza a atualização inicial da interface gráfica com base no estado atual do <code>Board</code>.<br>
<hr>