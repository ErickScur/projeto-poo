package com.api.main;

import com.api.main.factories.controller.AppControllerFactory;
import com.api.presentation.controllers.AppController;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Escolha a opção de interface: 'swing' ou 'console'");
        String presentationType = scanner.nextLine();

        System.out.println("Escolha o tipo de banco de dados: 'memory' ou 'supabse'");
        String databaseType = scanner.nextLine();

        AppController appController = AppControllerFactory.makeAppController(presentationType, databaseType);
        appController.start();
    }
}
