package MainPackage;

import MainPackage.IO.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Display { // Всё связанное с отображением окна и текстур в нём.
    private static boolean created = false;
    private static JFrame frame;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferedGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;
    private static int amountBuffers;

    public static void create(int width, int height, String title, int _clearColor, int amountBuffers) { // Настройка окна.
        if(created)
            return;

        frame = new JFrame(title);
        content = new Canvas();
        Dimension size = new Dimension(width, height); // Подгоняет размер нашего канваса (На котором рисуем) под размер само окно.
        content.setPreferredSize(size); // Тут и подгоняем.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Чтобы окно полностью закрывалось при нажатии на крестик.
        frame.getContentPane().add(content); // Сначала нашему окну даём его размер без учёта панели вверху, а потом добавляем туда наш лист для рисования.
        frame.setResizable(false); // Чтобы нельзя было менять размер окна.
        frame.pack(); // Не помню точно, но нужно для полной работоспособности окна.
        frame.setVisible(true);

        content.createBufferStrategy(amountBuffers); // Создаём нашей бумаге для рисования стратегию множественной буферизации.
        bufferStrategy = content.getBufferStrategy(); 

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferedGraphics = buffer.getGraphics();
        ((Graphics2D) bufferedGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        created = true;
    }

    public static void clear() { // Очищаем старое изображение (Старый кадр игры) на пустое поле для нового.
        Arrays.fill(bufferData, clearColor);
    }


    public static void swapBuffer() { // Тут мы меняем готовое отрисованное полностью изображение в игре.
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics() { // Отсюда будем присваивать объектам графику для рисования.
        return (Graphics2D) bufferedGraphics;
    }

    public static void destroy() { // Уничтожение окна.
        if(!created)
            return;

        frame.dispose();
    }
    public static void setTitle(String title) { // Для показателей возле названия (см в гейм лупе).
        frame.setTitle(title);
    }

    public static void addInputListener(Input inputListener) { // Добавляет окну слушателя клавиатуры.
        frame.add(inputListener);
    }
}
