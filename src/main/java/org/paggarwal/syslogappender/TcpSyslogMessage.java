package org.paggarwal.syslogappender;

import com.cloudbees.syslog.SyslogMessage;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by peeyushaggarwal on 3/20/17.
 */
public class TcpSyslogMessage extends SyslogMessage {

  @Override
  public void toRfc5424SyslogMessage(Writer out) throws IOException {
    StringWriter stringWriter = new StringWriter();
    super.toRfc5424SyslogMessage(stringWriter);
    out.write(Integer.toString(stringWriter.toString().length()));
    out.write(' ');
    super.toRfc5424SyslogMessage(out);
  }

}
