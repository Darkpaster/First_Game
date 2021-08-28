package MainPackage.Game;

import MainPackage.Display;
import MainPackage.Graphics.Sprite;
import MainPackage.Graphics.SpriteSheet;
import MainPackage.Graphics.TextureAtlas;
import MainPackage.IO.Input;
import MainPackage.Levels.Level;
import MainPackage.Utils.Time;
import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class Game implements Runnable { //Самый главный игровой класс.
    public static final int WIDTH = Camera.WORLD_SIZE_X; //Размер окна 1008
    public static final int HEIGHT = Camera.WORLD_SIZE_Y;//Размер окна 624
    public static final String title = "Title"; // Название окна
    public static final int CLEAR_COLOR = 0xff000000; //Цвет очистки буферного изображения (Всей карты) перед новой отрисовкой.
    public static final int NUM_BUFFERS = 3; // Количество буферов одновременно отрисовывающих изображение (Тройная буферизация).

    public static final float UPDATE_RATE = 60.0f; // Фпс
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE; //Секунду делим на 60 (Результат в наносекундах).
    public static final long IDLE_TIME = 1; // Задержка потока в 1 миллисекунду

    public static final String RAT_KING_NAME = "ratking.png";
    public static final String FOREST = "tiles_forest.png";
    public static final String CHARACTER = "characters.png";
    public static final String MY_SPRITE = "mySprite.png";
    public static final String GRASS = "grass.png";

    private boolean	running; // Для проверки.
    private Thread	gameThread; // Основной игровой поток в котором всё будет отрисовываться (Цикловой).
    private Graphics2D graphics; //Инструмент для рисования 2д.

    private Input input; //Объект для подключения клавы.
    private TextureAtlas atlas;// Объект класса который принимает в себя текстуру в виде параметра.
    private TextureAtlas atlas1;// Объект класса который принимает в себя текстуру в виде параметра.
    private Player				player; // Объект класса для координат текстуры игрока, его масштаба и т.п
    private Level lvl; //Класс для управления тайлмапы

    private SpriteSheet sheet;
    private Sprite sprite;

    private static boolean test = true; //херня.


    public static int x = Camera.CAMERA_SIZE_X - 32; // Начальные координаты игрока.
    public static int y = Camera.CAMERA_SIZE_Y - 32;

    public static float speed = 2; // Скорость движения игрока.


    public Game() { // Комната управления всей игрой (Почти).
        System.out.println("Game constructor");
        running = false;
        Display.create(Camera.CAMERA_SIZE_X, Camera.CAMERA_SIZE_Y, title, CLEAR_COLOR, NUM_BUFFERS); // Настройка окна.
        graphics = Display.getGraphics(); // Присвоение объекту класса графики графику из дисплея (для рисования всего).
        input = new Input(); // Инициализация объекта для подключения клавы.
        Display.addInputListener(input);  // Подключение клавиатуры к окну (В параметрах).
        atlas = new TextureAtlas(MY_SPRITE); // Присваиваем объекту файл (в параметрах текстура игрока).
        atlas1 = new TextureAtlas(GRASS); // То же самое, но файл тайлмапы, а не перса.
        sheet = new SpriteSheet(atlas.cut(0, 0, 16 * 1, 16), 1, 16);
        sprite = new Sprite(sheet, 1);
        player = new Player(x, y, 3, speed, atlas);
        lvl = new Level(atlas1);
    }


    public synchronized void start() { //Запускает игровой цикл (run).
        System.out.println("start method");
        if (running)
            return;

        gameThread = new Thread(this);
        gameThread.start();
        running = true;
    }

    public void run() {
        System.out.println("game loop");

        int fps = 0; // Счётчик фпс, будет увеличиваться до 60.
        int upd = 0; // Счётчик обновления координат, должно быть примерно равно фпс.
        int updl = 0; // Счётчик задержки (Необязательно).

        long count = 0; // Просто для подсчёта времени в наносекундах.

        float delta = 0; // Для контроля цикла.

        long lastTime = Time.get(); // Отмеряем стартовое время.
        running = true;
        while (running) { 
            long now = Time.get(); // Инициализируем теперешнее время.
            long elapsedTime = now - lastTime; // Получаем разницу во времени (Между началом цикла и концом).
            lastTime = now; // Приравниваем стартовое время для последующих вычислений.

            count += elapsedTime; // Счётчик времени (Для определения что прошла секунда в цикле).

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL); // Время, прошедшее за полную итерацию цикла делится на одну шестидесятую часть секунды.
            while (delta > 1) { // Когда проходит одна шестидесятая секунды дельта становится 1.
                update(); // Обновление координат текстур.
                upd++; // Счётчик обновлений.
                delta--; // Заново будем считать 1/60 секунды.
                if (render) { // Проверяем первый ли раз запустился этот цикл. 
                    updl++;
                } else {
                    render = true;
                }
            } //Если джвм долго читает верхний код, то предыдущий цикл будет повторяться несколько раз, что означает медленную работу, потерю фпс.
			// Другими словами чем больше становится дельта на момент начал цикла, тем труднее справляется машина и сильнее падает фпс.

            if (render) { // Запустится когда отлагает предыдущий цикл (Если больше чем одна итерация в прошлом цикле то это лаги).
                render(); // Отрисовка всех текстур.
                fps++; // Подсчёт фреймов за каждую отрисовку.
            } else {
                try {
                    Thread.sleep(IDLE_TIME); // Если срабатывает else, то это значит, что цикл сбился и ждём 1 милисек чтобы привести его в порядок.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (count >= Time.SECOND) { // Срабатывает каждую одну секунду.
                Display.setTitle(title + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl + " | camX = " + Camera.camX +
                        " | camY = " + Camera.camY + " | playerX = " + player.getPlayerX() + " | playerY = " + player.getPlayerY()); // Соответственно каждую секунду выводит на панель состояние игры.
                upd = 0;
                fps = 0; // Тут обновляются все показатели для след итерации.
                updl = 0;
                count = 0;
            }

        }
    }

    public synchronized void stop() { // Правильно всё выключает в случае чего.
        if (!running)
            return;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cleanUp();
    }

    private void update() { // Обновления координат.
        player.update(input);
        lvl.update();
   }

    private void render() { // Вся отрисовка.
        if(test) {
            System.out.println("Method render");
        }
        test = false;
        Display.clear();
        lvl.render(graphics);
        player.render(graphics);
        Display.swapBuffer();
    }

    private void cleanUp() { // Уничтожение окна.
        Display.destroy();
    }
}
