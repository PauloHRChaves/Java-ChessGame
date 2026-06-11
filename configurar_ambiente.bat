@echo off
cls
echo ============================================================
echo      CONFIGURACAO AUTOMATICA DO JOGO DE XADREZ (JAVA)
echo ============================================================
echo.

:: Verifica se ja tem o JAVA_HOME
if not "%JAVA_HOME%"="" (
    if exist "%JAVA_HOME%\bin\javac.exe" (
        echo [SUCESSO] Voce ja tem o JAVA_HOME configurado em:
        echo %JAVA_HOME%
        echo.
        goto :rodar
    )
)

:: VERIFICA E MOSTRA O 'WHERE JAVA'
echo [AVISO] JAVA_HOME nao foi encontrada ou esta invalida. Vamos configurar agora!
echo.
echo Buscando PATH do Java...
echo ------------------------------------------------------------
where java
echo ------------------------------------------------------------
echo.

:: O Windows testa o resultado do comando que ACABOU de rodar na tela
if %errorlevel% neq 0 (
    cls
    echo [ERRO] O comando 'java' nao foi encontrado no seu PATH.
    echo Certifique-se de que o Java esta instalado corretamente!
    echo E necessario ter o java no PATH nas variaveis de ambiente!
    echo.
    pause
    exit
)

echo ============================================================
echo                       INSTRUCOES:
echo ============================================================
echo O JAVA_HOME precisa do caminho ate a pasta RAIZ do JDK 
echo (A pasta principal que contem a pasta 'bin' dentro dela).
echo.
echo DICAS PARA COPIAR O CAMINHO CERTO:
echo 1. Se o caminho listado acima terminar em "\bin\java.exe":
echo    Copie ele inteiro, mas APAGUE o final "\bin\java.exe".
echo    Exemplo: C:\Users\Nome\.jdks\jdk-17 (Copie ate aqui)
echo.
echo 2. Se o caminho acima for o atalho "javapath" ou outros:
echo    Esse atalho nao serve. Abra o Explorador de Arquivos e 
echo    copie o caminho real de onde instalou o JDK. Locais comuns, Exemplos:
echo    - C:\Program Files\Java\jdk-17
echo    - C:\Users\SeuUsuario\.jdks\
echo.
echo    * Busque a pasta que tem a subpasta 'bin' dentro dela e copie o caminho dessa pasta.
echo ============================================================
echo.

:: Pede para a pessoa colar o caminho correto
set /p CAMINHO_JDK="Cole o caminho completo aqui e dê ENTER: "

if "%CAMINHO_JDK%"=="" (
    echo.
    echo [ERRO] Nenhum caminho foi inserido. Processo cancelado.
    pause
    exit
)

:: Salva permanentemente no Windows usando o setx
echo.
echo Salvando permanentemente no seu Windows...
setx JAVA_HOME "%CAMINHO_JDK%" /M >nul 2>&1
if %errorlevel% neq 0 (
    setx JAVA_HOME "%CAMINHO_JDK%" >nul
)

echo.
echo ============================================================
echo [PRONTO!] JAVA_HOME configurada com sucesso para:
echo %CAMINHO_JDK%
echo.
echo ATENCAO: Feche todas as janelas do CMD/PowerShell e do seu 
echo Editor de Codigo (VS Code, IntelliJ, etc.) para aplicar.
echo.
echo Depois, abra o terminal na pasta do projeto e rode:
echo No PowerShell: ./mvnw javafx:run
echo No CMD:        mvnw javafx:run
echo ============================================================
pause
exit

:rodar
echo Seu ambiente ja esta pronto para rodar o jogo!
pause