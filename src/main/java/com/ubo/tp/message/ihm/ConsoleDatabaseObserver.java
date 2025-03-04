package com.ubo.tp.message.ihm;

import com.ubo.tp.message.core.database.IDatabaseObserver;
import com.ubo.tp.message.datamodel.Message;
import com.ubo.tp.message.datamodel.User;

public class ConsoleDatabaseObserver implements IDatabaseObserver {

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        System.out.println("[Notification] Message ajouté : " + addedMessage);
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {
        System.out.println("[Notification] Message supprimé : " + deletedMessage);
    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {
        System.out.println("[Notification] Message modifié : " + modifiedMessage);
    }

    @Override
    public void notifyUserAdded(User addedUser) {
        System.out.println("[Notification] Utilisateur ajouté : " + addedUser);
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        System.out.println("[Notification] Utilisateur supprimé : " + deletedUser);
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        System.out.println("[Notification] Utilisateur modifié : " + modifiedUser);
    }
}
