package com.webtp.gimme.config;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Code exécuté lors de la création d'une session
        System.out.println("Session created: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Code exécuté lors de la destruction d'une session
        System.out.println("Session destroyed: " + se.getSession().getId());
    }
}
