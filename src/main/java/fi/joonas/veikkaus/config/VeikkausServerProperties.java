package fi.joonas.veikkaus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
@Data
public class VeikkausServerProperties {

    private String protocol;
    private String hostPort;
    private String applicationName;
}
