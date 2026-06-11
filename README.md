<h1> PlayCHESS </h1>

Jogo de xadrez desenvolvido em Java aplicando Padrões de Projeto do GoF (Gang of Four).

Tecnologias usadas:

- Java JDK 17
- JavaFX 17.0.2
- Maven 3.9.12 (via Maven Wrapper – mvnw)

<br>

<h2>Execução do Jogo:</h2>

Para rodar o JOGO basta apenas executar o arquivo PChess.exe que esta dentro do PChess.zip, necessário descomprimir o arquivo.

<br>

<h2>Execução do Código:</h2>

Para executar o CÓDIGO é necessário ter o JDK 17 instalado e a variável de ambiente ```JAVA_HOME``` devidamente configurada, por exigencia do maven wrapper.

<br>

Se você não tem certeza se a sua JAVA_HOME está configurada execute o script ```configurar_ambiente.bat``` incluído na pasta do projeto. Ele vai ajudar a configurar seu ambiente.


<h3>Com o ambiente OK execute o comando:</h3>

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

<h2>SOBRE O PROJETO:</h2>

O projeto foi desenvolvido seguindo padrões de arquitetura e ferramentas que visam a organização e a facilidade de colaboração:

- Padrão MVC (Model-View-Controller): A estrutura do projeto é baseada no modelo MVC convencional, separando claramente a lógica de negócio (Model), a interface gráfica (View) e o controle de eventos (Controller).

- Portabilidade com Maven Wrapper: Graças ao mvnw, o Maven é baixada automaticamente na primeira execução, garantindo que o ambiente de desenvolvimento seja idêntico para todos os colaboradores, independente de configurações locais.

- Interface: Utilização de FXML para a estruturação da interface e CSS para a estilização visual, permitindo um design desacoplado do código Java.

- Jogo: A primeira tela a ser gerada é o Menu Principal em que o player tem 2 opções, até o momento, Partida PvP - Funciona como uma partida local divida em turnos das peças, Sair - Encerra o app. Durante a partida é possivel voltar ao Menu Principal e Reiniciar a Partida apertando a tecla ESC - Acessando o Menu de Pausa.

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