package sia.greetings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  
  private final GreetingProps props;

  public GreetingController(GreetingProps props) {
    this.props = props;
  }

  @GetMapping("/")
  public String message() {
    return "message : " + props.getMessage()
            + ", password from vault : " + props.getPasswordFromVault()
            + ", password from git repo : " + props.getPasswordFromGit();
  }
}
