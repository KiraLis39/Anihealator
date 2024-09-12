package ru;

import lombok.extern.slf4j.Slf4j;
import ru.gui.AniFrame;
import ru.config.Registry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.File;
import java.io.InputStream;

@Slf4j
public class MainClass {
    public static Thread disclaimerThread;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
//			SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            log.error("Couldn't get specified look and feel, for some reason: {}", e.getMessage(), e);
        }

        try (InputStream is = MainClass.class.getResourceAsStream("/pic/bim0.png")) {
            assert is != null;
            Registry.messageIcon = new ImageIcon(ImageIO.read(is));
        } catch (Exception e) {
            log.error("Not found message icon: {}", e.getMessage());
        }

        disclaimerThread = new Thread(() -> {
			log.info("Отображение начального отказа об ответственности...");

			try {
				JOptionPane.showConfirmDialog(null,
						"<html><H3>Все расчеты соответствуют<br>официально заявленным дозам<br>в инструкциях препаратов.", "Внимание!",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, Registry.messageIcon);
			} catch (Exception e) {
                log.info("Что-то пошло не так при начале работы. Ошибка при отображении стартового предупреждения.");
				e.printStackTrace();
			}
		});
        disclaimerThread.start();

        log.info("Старт программы. Проверка наличия директорий и файлов...");
        checkFiles();

        log.info("Чтение конфигурации...");
        buildIOM();

        log.info("Загрузка UI...");
        new AniFrame();
    }

    private static void buildIOM() {
        IOM.add(IOM.HEADERS.CONFIG, new File("./data/config.txt"));

        Registry.isAdminModeAllow = IOM.getBoolean(IOM.HEADERS.CONFIG, "isAdminAllowed");
        Registry.isResizeAllow = IOM.getBoolean(IOM.HEADERS.CONFIG, "isResizeAllow");
        Registry.isRenderOn = IOM.getBoolean(IOM.HEADERS.CONFIG, "isRenderOn");

        try {
            Registry.frameWidth = IOM.getInt(IOM.HEADERS.CONFIG, "frameWidth");
            Registry.frameHeight = IOM.getInt(IOM.HEADERS.CONFIG, "frameHeight");
        } catch (Exception e) {
            log.error("Err: {}", e.getMessage());
        }
    }

    private static void checkFiles() {
        try {
            if (!new File("./data/").exists()) {
                new File("./data/").mkdirs();
                log.info("Не найдена дирректория базы данных!");
            }
            if (!Registry.photoDir.exists()) {
                Registry.photoDir.mkdir();
            }
            if (!new File("./data/db.db").exists()) {
                log.info("Отсутствует файл базы данных! Будет создан новый, если возиожно.");
            }
        } catch (Exception e) {
            log.info("Провал проверки наличия необходимых файлов!");
            e.printStackTrace();
            exit(46);
        }
    }

    public static void exit(int i) {
        IOM.saveAll();

        log.info("Выход из программы с кодом " + i);
        System.exit(i);
    }
}
