package chuck.norris.service.api;

public class JokeResponse {
  public String joke;
  public String locale;
  public String nodeId;
  public String version;
  public String hostAddres;

  public JokeResponse(String joke, String locale, String nodeId, String version, String hostAddres) {
    this.joke = joke;
    this.locale = locale;
    this.nodeId = nodeId;
    this.version = version;
    this.hostAddres = hostAddres;
  }
}
