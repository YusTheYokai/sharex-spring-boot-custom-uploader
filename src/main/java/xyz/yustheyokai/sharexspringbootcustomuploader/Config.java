package xyz.yustheyokai.sharexspringbootcustomuploader;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${sharex.folder}")
    private String folder;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Bean
    public void createFolder() {
        new File(folder).mkdirs();
    }
}
