<h1> PlayCHESS </h1>

Jogo de xadrez desenvolvido em Java aplicando Padrões de Projeto do GoF (Gang of Four).

Tecnologias usadas:

- Java JDK 17
- JavaFX 17.0.2
- Maven 3.9.12 (via Maven Wrapper – mvnw)

<br>
<h2>Execução do Código:</h2>

Para executar o jogo, é necessário ter o JDK 17 instalado e a variável de ambiente JAVA_HOME devidamente configurada apontando para a pasta raiz do seu Java.

<br>

Se você não tem certeza se a sua JAVA_HOME está configurada ou se o Maven der erro, execute o script ```configurar_ambiente.bat``` incluído na pasta do projeto. Ele vai validar seu ambiente e ajudar a configurar tudo para você.

<br>

- No Windows (PowerShell): <br>

<pre>
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
- Singleton: Garante uma única instância global de uma classe com acesso centralizado. (GameManager)
- Facade: Fornece uma interface simplificada para um sistema complexo, escondendo interações internas do jogo. (GameManager)
- Factory: Encapsula a criação de objetos (peças), ocultando a lógica de instanciação. (PieceFactory)

<br>

Comportamental:
- Observer: Permite que múltiplos objetos sejam notificados automaticamente quando o estado do jogo muda. (GameObserver)
- Strategy: Define diferentes algoritmos de movimento para peças de forma independente e intercambiável. (MovementStrategy)
- State: Altera o comportamento do jogo conforme seu estado interno (normal, xeque, xeque-mate).
<br>

Estrutural:
- Composite: Estrutura objetos em hierarquia parte-todo, permitindo tratar elementos individuais e compostos de forma uniforme. (ChessBoardView)
- Flyweight: Reutiliza objetos compartilhados para otimizar uso de memória. (ImageCache)

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
<h3>Menu.java:</h3>
•	O método <code>onStartGameClicked()</code> é disparado.<br>
•	Ação: Chama <code>GameManager.getInstance().startGame()</code>.<br>

<hr>
<h3>GameManager.java:</h3>
•	<code>getInstance()</code>: Verifica se o motor já existe (se não, cria agora).<br>
•	Executa o construtor do GameManager e parte para toda criação lógica do tabuleiro.<br>
•	Realiza toda a criação da matriz do tabuleiro, criação e posicionamento das peças, sem nada visual,<br>
•	Instancia o "juiz" da partida,<br>
•	Instancia a classe que vai cuidar dos squares selecionados,<br>
•	Instancia a classe que vai lidar dos cliques no tabuleiro,<br>
•	Logo após chama o método <code>startGame()</code>: Comanda o App.java a trocar a tela para game_layout.fxml.<br>

<hr>
<h3>game_layout.fxml:</h3>
•	A nova tela é carregada.<br>
•	O JavaFX identifica o fx:controller="GameViwController".

<hr>
<h3>GameViewController.java:</h3>
• O método <code>initialize()</code> é executado automaticamente pelo JavaFX ao carregar a tela.<br>
• Obtém a instância única do <code>GameManager</code>, responsável por atuar como Facade e coordenar o fluxo geral da partida (entrada de comandos, estado do jogo e notificações).<br>
• A partir do <code>GameManager</code>, acessa a <code>GameSession</code>, que centraliza todo o estado do jogo, incluindo o <code>Board</code>, o <code>Referee</code> e o <code>SelectionManager</code>.<br>
• Cria o componente visual do tabuleiro (<code>ChessBoardView</code>) e o adiciona ao centro do layout principal da interface.<br>
• Registra a <code>ChessBoardView</code> como observadora do jogo utilizando o padrão Observer, permitindo que ela seja automaticamente atualizada sempre que o estado da partida mudar.<br>
• Registra também o próprio <code>GameViewController</code> como observador para atualizar elementos de interface fora do tabuleiro (como turno e status da partida).<br>
• Realiza a atualização inicial da interface gráfica com base no estado atual da <code>GameSession</code>, garantindo que a UI esteja sincronizada com o estado do jogo ao ser carregada.<br>
<hr>