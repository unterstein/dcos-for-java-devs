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

    private final String nodeId;

    public ChuckApplication() {
        nodeId = UUID.randomUUID().toString();
    }

    @Autowired
    JdbcTemplate template;

    @RequestMapping
    public Map<String, String> random() {
        Map<String, String> result = new HashMap<>();
        String locale = LocaleContextHolder.getLocale().getLanguage();
        if (acceptedLanguages.contains(locale)) {
            Map<String, Object> query = template.queryForMap("SELECT * FROM `jokes` WHERE lang=? ORDER BY RAND() LIMIT 0,1;", locale);

            result.put("joke", query.get("joke").toString());
            result.put("locale", locale);
            result.put("nodeId", nodeId);
            try {
                result.put("hostname", InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return result;
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

}
