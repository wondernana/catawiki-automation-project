package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

import static org.aeonbits.owner.Config.LoadType.MERGE;

@LoadPolicy(MERGE)
@Sources({"system:env",
        "system:properties",
        "file:src/test/resources/test-config.properties"
})
public interface Properties extends Config {

    @Key("baseUrl")
    String getBaseUrl();

    @Key("locale")
    String getLocale();

    @Key("browserType")
    String getBrowserType();

    @Key("isHeadlessBrowser")
    boolean isHeadlessBrowser();
}
