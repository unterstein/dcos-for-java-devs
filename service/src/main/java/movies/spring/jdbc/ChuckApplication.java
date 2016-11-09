package movies.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@EnableAutoConfiguration
@ComponentScan
@RestController("/")
public class ChuckApplication extends WebMvcConfigurerAdapter {

    private final static List<String> acceptedLanguages = Arrays.asList("de", "en");

    // stores the current nodeId of this service - current implemented as uuid
    private final String nodeId;

    // stores the application version of this service
    private final String version;

    // stores the host address on which this service runs
    private final String hostAddress;

    // stores the information if this service should be return healthy or unhealthy
    private boolean healthy = true;

    public ChuckApplication() {
        nodeId = UUID.randomUUID().toString();
        String tmpVersion = System.getenv("SERVICE_VERSION");
        if (tmpVersion == null)
            tmpVersion = System.getProperty("SERVICE_VERSION", "1");
        version = tmpVersion;
        hostAddress = getHostAddress();
    }

    @Autowired
    JdbcTemplate template;

    @RequestMapping("/")
    public Map<String, String> randomJoke() {
        Map<String, String> result = new HashMap<>();
        String locale = LocaleContextHolder.getLocale().getLanguage();
        if (!acceptedLanguages.contains(locale)) {
            locale = acceptedLanguages.get(0);
        }
        Map<String, Object> query = template.queryForMap("SELECT * FROM `jokes` WHERE lang=? ORDER BY RAND() LIMIT 0,1;", locale);

        result.put("joke", query.get("joke").toString());
        result.put("locale", locale);
        result.put("nodeId", nodeId);
        result.put("version", version);
        result.put("hostAddress", hostAddress);
        return result;
    }

    @RequestMapping("/health")
    public String healthy() {
        if (healthy) {
            return "healthy";
        } else {
            throw new RuntimeException("meh!");
        }
    }

    @RequestMapping(value = "/health", method = RequestMethod.PUT)
    public String toggleHealth() {
        healthy = !healthy; // toggled value
        return "toggled, now: " + healthy;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChuckApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        String MYSQL_URL = System.getenv("MYSQL_URL");
        if (MYSQL_URL == null)
            MYSQL_URL = System.getProperty("MYSQL_URL", "jdbc:mysql://localhost:3306/chuck?user=root&password=");
        return new DriverManagerDataSource(MYSQL_URL);
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
