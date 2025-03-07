package com.ubo.tp.message.action;

public interface IAction {

    /**
     * Ajoute un observateur à la session.
     *
     * @param observer
     */
    void addObserver(IActionObserver observer);

    /**
     * Retire un observateur à la session.
     *
     * @param observer
     */
    void removeObserver(IActionObserver observer);

    void exitAction();

    void loginAction();

    void registerAction();

    void disconnectAction();

    void profileAction();

    void listUsersAction();
}
