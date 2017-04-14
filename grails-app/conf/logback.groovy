import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
//import ch.qos.logback.core.status.OnConsoleStatusListener
import grails.util.BuildSettings
import grails.util.Environment
import java.nio.charset.Charset

// See http://logback.qos.ch/manual/groovy.html for details on configuration
//statusListener(OnConsoleStatusListener)
scan("10 minutes")

def defaultCharset = Charset.forName("UTF-8")
def defaultPattern = "%date [%highlight(%-5level)] [%thread] [%cyan(%logger{32})] - %message%n"
//"%date [%highlight(%-5level)] [%thread] [%cyan(%logger{32})] - %message%n"
//"%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
//"%d{ISO8601} %p %t %c{0}.%M - %m%n"
def defaultHistory = 365
def defaultFilename = "/var/log/estima/estima.log"

appender("CONSOLE", ConsoleAppender) {

    encoder(PatternLayoutEncoder) {
        pattern = defaultPattern
        charset = defaultCharset
    }
}

if(Environment.current == Environment.DEVELOPMENT) {

    // DEVELOPMENT settings
    def targetDir = BuildSettings.TARGET_DIR
    if(targetDir) {

        appender("FILE", FileAppender) {

            file = "${targetDir}/stacktrace.log"
            append = true
            encoder(PatternLayoutEncoder) {
                pattern = defaultPattern
            }
        }
        logger("StackTrace", ERROR, ['FILE'], false )
    }

    logger('org.springframework.boot.autoconfigure.security', INFO)
    logger('grails.app', INFO)

    logger('com.estima', DEBUG)

//    logger 'org.hibernate.SQL', DEBUG
//    logger 'org.hibernate.type.descriptor.sql.BasicBinder', TRACE

} else {

    // PRODUCTION settings
    appender("FILE", RollingFileAppender) {

        fileName = defaultFilename

        encoder(PatternLayoutEncoder) {
            charset = defaultCharset
            pattern = defaultPattern
        }

        rollingPolicy(TimeBasedRollingPolicy) {
            maxHistory = defaultHistory
            fileNamePattern = "${defaultFilename}.%d{yyyy-MM-dd}.gz"
        }
    }
}

root INFO, ["CONSOLE", "FILE"]