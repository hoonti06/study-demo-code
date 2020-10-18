package sia.greetings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class GreetingProps {

  @Value("${greeting.message}")
  private String message;

  @Value("${vault.password}")
  private String passwordFromVault;

  @Value("${encrypted.password}")
  private String passwordFromGit;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPasswordFromVault() {
    return passwordFromVault;
  }

  public void setPasswordFromVault(String passwordFromVault) {
    this.passwordFromVault = passwordFromVault;
  }

  public String getPasswordFromGit() {
    return passwordFromGit;
  }

  public void setPasswordFromGit(String passwordFromGit) {
    this.passwordFromGit = passwordFromGit;
  }
}