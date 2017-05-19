package chuck.norris.service;

import chuck.norris.service.api.HealthResponse;
import chuck.norris.service.api.JokeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@SpringBootApplication
@RestController("/")
public class ChuckApplication extends WebMvcConfigurerAdapter {

    @Component
    @ConfigurationProperties // (prefix = "chuck") you can also prefix that stuff
    static class ChuckConfiguration {
        /** stores the application version of this service */
        private String serviceVersion;

        public String getServiceVersion() {
            return serviceVersion;
        }

        public void setServiceVersion(String serviceVersion) {
            this.serviceVersion = serviceVersion;
        }
    }
    
    private final static List<String> acceptedLanguages = Arrays.asList("en", "de");

    // stores the current nodeId of this service - current implemented as uuid
    private final String nodeId;

    // stores the host address on which this service runs
    private final String hostAddress;

    // stores the information if this service should be return healthy or unhealthy
    private boolean healthy = true;

    private final ChuckConfiguration chuckConfiguration;
   
    public ChuckApplication(final ChuckConfiguration chuckConfiguration) {
        this.chuckConfiguration = chuckConfiguration;
        nodeId = UUID.randomUUID().toString();
        hostAddress = getHostAddress();
    }

    @Autowired
    JdbcTemplate template;

    @RequestMapping("/")
    public JokeResponse randomJoke(final Locale locale) {
        String lang = locale.getLanguage();
        if (!acceptedLanguages.contains(lang)) {
            lang = acceptedLanguages.get(0);
        }
        Map<String, Object> query = template.queryForMap("SELECT * FROM `jokes` WHERE lang=? ORDER BY RAND() LIMIT 0,1;", lang);

        return new JokeResponse(
            hostAddress,
            lang,
            nodeId,
            chuckConfiguration.getServiceVersion(),
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
   
    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
