package com.nju.edu.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: Leonezhurui
 * @Date: 2022/3/20 - 21:09
 * @Package: SEECII-ERP
 */

@Configuration
public class CORSConfig {

    private static String[] originsVal =
            new String[] {
                    "localhost:8000",
                    "127.0.0.1:8000",
                    "127.0.0.1",
            };

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        addAllowedOrigins(corsConfiguration);
        // 允许token放置于请求头
        corsConfiguration.addExposedHeader("token");
        // 2
        corsConfiguration.addAllowedHeader("*");
        // 3
        corsConfiguration.addAllowedMethod("*");
        // 跨域session共享
        corsConfiguration.setAllowCredentials(true);
        // 4
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    private void addAllowedOrigins(CorsConfiguration corsConfiguration) {
        for (String origin : originsVal) {
            corsConfiguration.addAllowedOrigin("http://" + origin);
            corsConfiguration.addAllowedOrigin("https://" + origin);
        }
    }
}
