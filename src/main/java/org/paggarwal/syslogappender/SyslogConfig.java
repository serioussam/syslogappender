package org.paggarwal.syslogappender;

/**
 * Created by peeyushaggarwal on 3/20/17.
 */
public class SyslogConfig {
  private String programName;
  private String host;
  private int port;
  private Protocol protocol;

  /**
   * Getter for property 'programName'.
   *
   * @return Value for property 'programName'.
   */
  public String getProgramName() {
    return programName;
  }

  /**
   * Setter for property 'programName'.
   *
   * @param programName Value to set for property 'programName'.
   */
  public void setProgramName(String programName) {
    this.programName = programName;
  }

  /**
   * Getter for property 'host'.
   *
   * @return Value for property 'host'.
   */
  public String getHost() {
    return host;
  }

  /**
   * Setter for property 'host'.
   *
   * @param host Value to set for property 'host'.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Getter for property 'port'.
   *
   * @return Value for property 'port'.
   */
  public int getPort() {
    return port;
  }

  /**
   * Setter for property 'port'.
   *
   * @param port Value to set for property 'port'.
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Getter for property 'protocol'.
   *
   * @return Value for property 'protocol'.
   */
  public Protocol getProtocol() {
    return protocol;
  }

  /**
   * Setter for property 'protocol'.
   *
   * @param protocol Value to set for property 'protocol'.
   */
  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }
}
