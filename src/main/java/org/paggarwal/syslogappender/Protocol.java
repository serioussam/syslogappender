package org.paggarwal.syslogappender;

import com.cloudbees.syslog.Facility;
import com.cloudbees.syslog.MessageFormat;
import com.cloudbees.syslog.Severity;
import com.cloudbees.syslog.SyslogMessage;
import com.cloudbees.syslog.sender.AbstractSyslogMessageSender;
import com.cloudbees.syslog.sender.SyslogMessageSender;
import com.cloudbees.syslog.sender.TcpSyslogMessageSender;
import com.cloudbees.syslog.sender.UdpSyslogMessageSender;

/**
 * Created by peeyushaggarwal on 3/20/17.
 */
public enum Protocol {
  TCP {
    public AbstractSyslogMessageSender getMessageSenderInternal(String host, int port) {
      TcpSyslogMessageSender messageSender = new TcpSyslogMessageSender();
      messageSender.setSyslogServerHostname(host);
      messageSender.setSyslogServerPort(port);
      return messageSender;
    }

    @Override
    public SyslogMessage getSyslogMessage(SyslogConfig syslogConfig) {
      return new TcpSyslogMessage();
    }
  },
  UDP {
    public AbstractSyslogMessageSender getMessageSenderInternal(String host, int port) {
      UdpSyslogMessageSender messageSender = new UdpSyslogMessageSender();
      messageSender.setSyslogServerHostname(host);
      messageSender.setSyslogServerPort(port);
      return messageSender;
    }

    @Override
    public SyslogMessage getSyslogMessage(SyslogConfig syslogConfig) {
      return new SyslogMessage();
    }
  },
  TCP_TLS {
    public AbstractSyslogMessageSender getMessageSenderInternal(String host, int port) {
      TcpSyslogMessageSender messageSender = new TcpSyslogMessageSender();
      messageSender.setSyslogServerHostname(host);
      messageSender.setSyslogServerPort(port);
      messageSender.setSsl(true);
      return messageSender;
    }

    @Override
    public SyslogMessage getSyslogMessage(SyslogConfig syslogConfig) {
      return new TcpSyslogMessage();
    }
  };


  protected abstract AbstractSyslogMessageSender getMessageSenderInternal(String host, int port);

  public abstract SyslogMessage getSyslogMessage(SyslogConfig syslogConfig);
  public SyslogMessageSender getMessageSender(SyslogConfig config) {
    AbstractSyslogMessageSender messageSender = getMessageSenderInternal(config.getHost(), config.getPort());
    messageSender.setDefaultAppName(config.getProgramName());
    messageSender.setDefaultFacility(Facility.USER);
    messageSender.setDefaultSeverity(Severity.INFORMATIONAL);
    messageSender.setMessageFormat(MessageFormat.RFC_5424); // optional, default is RFC 3164
    return messageSender;
  }
}
