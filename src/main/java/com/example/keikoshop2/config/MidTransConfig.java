package com.example.keikoshop2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.midtrans.Config;
import com.midtrans.ConfigBuilder;

@Configuration
public class MidTransConfig {
  @Value("${midtrans.server-key}")
  private String serverKey;

  @Value("${midtrans.client-key}")
  private String clientKey;

  @Value("${midtrans.is-production}")
  private boolean isProduction;

  @Bean
  public Config midtransConfig() {
  return new ConfigBuilder()
  .setServerKey(serverKey)
  .setClientKey(clientKey)
  .setIsProduction(isProduction)
  .build();
  }

  public String getServerKey() {
  return serverKey;
  }
}