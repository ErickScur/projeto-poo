package com.api.main.factories.controller;

import com.api.presentation.controllers.AppController;
import com.api.main.factories.presentation.PresentationFactory;

public class AppControllerFactory {
    public static AppController makeAppController(String presentationType, String databaseType) {
        return new AppController(
                PresentationFactory.makePresentation(presentationType, databaseType)
        );
    }
}
