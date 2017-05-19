package chuck.norris.service;

import chuck.norris.service.api.HealthResponse;
import chuck.norris.service.api.JokeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController("/")
public class ChuckApplication extends WebMvcConfigurerAdapter {

    private final static List<String> acceptedLanguages = Arrays.asList("en", "de");

    // stores the current nodeId of this service - current implemented as uuid
    private final String nodeId;

    // stores the host address on which this service runs
    private final String hostAddress;

    // stores the information if this service should be return healthy or unhealthy
    private boolean healthy = true;

    // stores the application version of this service
    @Value("${SERVICE_VERSION:1}")
    private String version;

    // stores the mysql url to connect to
    @Value("${MYSQL_URL:jdbc:mysql://localhost:3306/chuck?user=root&password=&useTimezone=true&serverTimezone=Europe/Berlin}")
    private String mysqlUrl;

    public ChuckApplication() {
        nodeId = UUID.randomUUID().toString();
        hostAddress = getHostAddress();
    }

    @Autowired
    JdbcTemplate template;

    @RequestMapping("/")
    public JokeResponse randomJoke() {
        String locale = LocaleContextHolder.getLocale().getLanguage();
        if (!acceptedLanguages.contains(locale)) {
            locale = acceptedLanguages.get(0);
        }
        Map<String, Object> query = template.queryForMap("SELECT * FROM `jokes` WHERE lang=? ORDER BY RAND() LIMIT 0,1;", locale);

        return new JokeResponse(
            hostAddress,
            locale,
            nodeId,
            version,
            query.get("joke").toString()
        );
    }

    @RequestMapping("/health")
    public HealthResponse healthy() {
        if (healthy) {
            return new HealthResponse(true);
        } else {
            throw new RuntimeException("meh!");
        }
    }

    @RequestMapping(value = "/health`", method = RequestMethod.DELETE)
    public HealthResponse toggleHealth() {
        healthy = false;
        return new HealthResponse(false);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ChuckApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(mysqlUrl);
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
