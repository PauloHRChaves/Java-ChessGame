package com.pchess.model.observer;

import java.util.ArrayList;
import java.util.List;

import com.pchess.controller.GameSession;

/**
 * Gerenciador de notificações do padrão de projeto OBSERVER.
 * Atua como o 'Subject' (Sujeito), mantendo o registro de todos os observadores e transmitindo as atualizações de estado do jogo de forma centralizada.
 */
public class ObserverNotifier {
    // Lista de componentes cadastrados que aguardam atualizações do jogo (ex: ChessBoardView, GameViewController)
    private final List<GameObserver> observers = new ArrayList<>();

    /**
     * Inscreve um novo observador na lista de transmissão de eventos.
     */
    public void add(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Varre a lista de observadores e dispara o evento de atualização para cada um deles.
     * Desembrulha os dados essenciais da GameSession e os envia via parâmetro.
     */
    public void notify(GameSession session) {
        for (GameObserver observer : observers) {
            // Entrega o tabuleiro, o gerenciador de seleções e o árbitro atualizados para as Views
            observer.onBoardChanged(session.getBoard(), session.getSelection(), session.getReferee());
        }
    }
}