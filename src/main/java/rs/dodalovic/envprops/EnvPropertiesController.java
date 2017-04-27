package rs.dodalovic.envprops;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class EnvPropertiesController {
    private final Environment environment;

    @GetMapping("/sys/props")
    public Map<String, Object> getSysProperties() {
        val builder = ImmutableMap.builder();
        System.getProperties().stringPropertyNames().forEach(name -> {
            builder.put(name, environment.getProperty(name));
        });
        val systemProperties = builder.build();
        return ImmutableMap.of(
                "size", systemProperties.size(),
                "values", systemProperties
        );
    }

    @GetMapping("/sys/props/custom")
    public Map<String, Object> getCustomEnvProperties() {
        val builder = ImmutableMap.builder();
        builder.put("properties.key1", environment.getProperty("properties.key1"));
        builder.put("properties.key2", environment.getProperty("properties.key2"));

        builder.put("foo", environment.getProperty("foo"));
        builder.put("bar", environment.getProperty("bar"));
        builder.put("baz", environment.getProperty("baz"));

        val customProperties = builder.build();

        return ImmutableMap.of(
                "size", customProperties.size(),
                "values", customProperties
        );
    }

    @GetMapping("/sys/env")
    public Map<String, Object> get() {
        val env = System.getenv();
        return ImmutableMap.of(
                "size", env.size(),
                "values", env
        );
    }
}
