package dev.vk.jfc.app.storage.appstorage.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SpringUtils {

    private final ApplicationContext context;

    public Properties getSpringProperties() {
        Map<String, String> propertyMap = getPropertyMap();
        Properties properties = new Properties(propertyMap.size());
        properties.putAll(propertyMap);
        return properties;
    }

    public MutablePropertySources getEnvironmentPropertySources() {
        return context.getBean(ConfigurableEnvironment.class).getPropertySources();
    }

    public String getEnvironmentProperty(String propertyName) {
        return context.getBean(ConfigurableEnvironment.class).getProperty(propertyName);
    }

    public Map<String, String> getPropertyMap() {
        return StreamSupport.stream(getEnvironmentPropertySources().spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::<String>stream)
                .map(propName -> Pair.of(propName, getEnvironmentProperty(propName)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (existed, newOne) -> existed));
    }
}
