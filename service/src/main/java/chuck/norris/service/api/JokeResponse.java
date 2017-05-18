package chuck.norris.service.api;

public class JokeResponse {
  public String hostAddress;
  public String locale;
  public String nodeId;
  public String version;
  public String joke;

  public JokeResponse(String hostAddress, String locale, String nodeId, String version, String joke) {
    this.hostAddress = hostAddress;
    this.locale = locale;
    this.nodeId = nodeId;
    this.version = version;
    this.joke = joke;
  }
}
