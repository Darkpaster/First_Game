package MainPackage;

import MainPackage.Game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class Launch { // Собственно самый первый класс.

    public static void main(String[] args) { // Отсюда начинается весь код, точка запуска так сказать.
        System.out.println("main method"); // Писал ауты для псведо дебага.
        Game game = new Game();
        game.start();

    }


}
