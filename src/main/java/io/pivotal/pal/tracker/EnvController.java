package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private final String address;
    private final String index;
    private final String memoryLimit;
    private String port; 

    public EnvController(@Value("${PORT:NOT SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String index,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String address) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.index = index;
        this.address = address;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {

        Map<String, String> env = new HashMap<>();
        env.put("MEMORY_LIMIT", this.memoryLimit);
        env.put("PORT", this.port);
        env.put("CF_INSTANCE_INDEX", this.index);
        env.put("CF_INSTANCE_ADDR", this.address);
        return env;

    }
}
