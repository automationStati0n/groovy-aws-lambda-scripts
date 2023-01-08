//JBANG
//JAVA 11
//DEPS org.apache.groovy:groovy:+
//DEPS org.apache.groovy:groovy-ant:+
//DEPS org.apache.groovy:groovy-astbuilder:+
//DEPS org.apache.groovy:groovy-backports-compat23:+
//DEPS org.apache.groovy:groovy-bsf:+
//DEPS org.apache.groovy:groovy-cli-commons:+
//DEPS org.apache.groovy:groovy-cli-picocli:+
//DEPS org.apache.groovy:groovy-console:+
//DEPS org.apache.groovy:groovy-contracts:+
//DEPS org.apache.groovy:groovy-datetime:+
//DEPS org.apache.groovy:groovy-dateutil:+
//DEPS org.apache.groovy:groovy-docgenerator:+
//DEPS org.apache.groovy:groovy-ginq:+
//DEPS org.apache.groovy:groovy-groovydoc:+
//DEPS org.apache.groovy:groovy-groovysh:+
//DEPS org.apache.groovy:groovy-jaxb:+
//DEPS org.apache.groovy:groovy-jmx:+
//DEPS org.apache.groovy:groovy-json:+
//DEPS org.apache.groovy:groovy-jsr223:+
//DEPS org.apache.groovy:groovy-macro:+
//DEPS org.apache.groovy:groovy-macro-library:+
//DEPS org.apache.groovy:groovy-nio:+
//DEPS org.apache.groovy:groovy-servlet:+
//DEPS org.apache.groovy:groovy-sql:+
//DEPS org.apache.groovy:groovy-swing:+
//DEPS org.apache.groovy:groovy-templates:+
//DEPS org.apache.groovy:groovy-test:+
//DEPS org.apache.groovy:groovy-test-junit5:+
//DEPS org.apache.groovy:groovy-testng:+
//DEPS org.apache.groovy:groovy-toml:+
//DEPS org.apache.groovy:groovy-typecheckers:+
//DEPS org.apache.groovy:groovy-xml:+
//DEPS org.apache.groovy:groovy-yaml:+
//DEPS net.sourceforge.htmlunit:htmlunit:2.69.0

import groovy.lang.GroovyShell;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream is = new FileInputStream(System.getProperty("script"));
        new GroovyShell().evaluate(new InputStreamReader(is));
    }
}