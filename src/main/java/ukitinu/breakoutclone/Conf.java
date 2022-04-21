package ukitinu.breakoutclone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public enum Conf {
    LOG_FPS("log.fps", "false"),
    LOG_PHYSICS("log.physics", "false"),
    LOG_BALL("log.ball", "false"),
    LOG_GAME("log.game", "false"),
    LOG_KEYS("log.keys", "false"),
    LOG_THREAD("log.thread", "false"),
    FPS_TARGET("fps.desired", "60"),
    FPS_MAX("fps.max", "80"),
    MAX_LIVES("max_lives", "3"),
    MAX_LEVEL("max_level", "4"),
    INVINCIBILITY("invincibility", "false"),
    BALL_START_SPEED("ball.start_speed", "3.3"),
    BALL_MOD_SPEED("ball.mod_speed", "0.2");

    private final String value;

    Conf(String key, String defaultValue) {
        String read = Reader.get(key);
        this.value = read != null ? read : defaultValue;
    }

    public String str() {
        return value;
    }

    public int num() {
        return Integer.parseInt(value);
    }

    public boolean bool() {
        return Boolean.parseBoolean(value);
    }

    public double dbl() {
        return Double.parseDouble(value);
    }

    private static final class Reader {
        private static final Logger LOG = LogManager.getLogger(Reader.class);
        private static final String PROP_FILE = "breakout_clone.properties";
        private static final String CONTENT = """
                # logging configuration
                log.fps=false
                log.physics=false
                log.ball=false
                log.game=false
                log.keys=false
                log.thread=false
                
                fps.desired=60
                fps.max=80
                
                # min of 1, max of 10
                max_lives=3
                # min of 1, max of 6
                max_level=4
                # cheat option to disable loss of lives
                invincibility=false
                
                # the ball's vertical speed is always at least 3 and at most 6
                # ball initial speed
                ball.start_speed=3.3
                # ball additional speed per level after the first, the value is squeezed between 0 and 0.5
                ball.mod_speed=0.2
                
                """;

        private static final Properties PROPS = new Properties();

        static {
            try (var is = new FileInputStream(PROP_FILE)) {
                PROPS.load(is);
            } catch (FileNotFoundException e) {
                LOG.warn("{} not found, generating default one", PROP_FILE);
                try {
                    Files.writeString(Path.of(PROP_FILE), CONTENT, StandardCharsets.UTF_8);
                    System.exit(0);
                } catch (IOException ex) {
                    LOG.error(ex.getMessage());
                    System.exit(1);
                }
            } catch (Exception e) {
                LOG.error("Unable to read {}: {}", PROP_FILE, e.getMessage());
                System.exit(1);
            }
        }

        private static String get(String key) {
            return PROPS.getProperty(key);
        }
    }
}
