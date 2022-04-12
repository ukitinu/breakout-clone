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
    FPS_TARGET("fps.desired", "60"),
    FPS_MAX("fps.max", "80"),
    MAX_LIVES("max_lives", "3"),
    MAX_LEVEL("max_level", "2");

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

    private static final class Reader {
        private static final Logger LOG = LogManager.getLogger(Reader.class);
        private static final String PROP_FILE = "breakout_clone.properties";
        private static final String CONTENT = """
                log.fps=false
                                
                fps.desired=60
                fps.max=80
                                
                max_lives=3
                max_level=2
                                
                """;

        private static final Properties PROPS = new Properties();

        static {
            try (var is = new FileInputStream(PROP_FILE)) {
                PROPS.load(is);
            } catch (FileNotFoundException e) {
                LOG.warn(PROP_FILE + " not found, generating default one");
                try {
                    Files.writeString(Path.of(PROP_FILE), CONTENT, StandardCharsets.UTF_8);
                    System.exit(0);
                } catch (IOException ex) {
                    LOG.error(ex.getMessage());
                    System.exit(1);
                }
            } catch (Exception e) {
                LOG.error("Unable to read " + PROP_FILE + " file: " + e.getMessage());
                System.exit(1);
            }
        }

        private static String get(String key) {
            return PROPS.getProperty(key);
        }
    }
}
