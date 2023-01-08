//cron(0 10-23 ? * * *) every hour from 10am to 11pm, 7 days a week
@Grab(group='net.sourceforge.htmlunit', module='htmlunit', version='2.69.0')
@Grab(group='com.sun.mail', module='javax.mail', version='1.6.2')

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClientOptions
import groovy.xml.XmlParser
import javax.mail.*
import javax.mail.internet.*

def numberOfPlayers
try (final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED)) {
    WebClientOptions options = webClient.getOptions();
    options.setCssEnabled(true);
    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    options.setTimeout(50000);
    webClient.addRequestHeader("Access-Control-Allow-Origin", "*");

    HtmlPage page = webClient.getPage("https://www.playt2.com/server.html");

    // important!  Give the headless browser enough time to execute JavaScript
    // The exact time to wait may depend on your application.
    webClient.setJavaScriptTimeout(10000);
    webClient.waitForBackgroundJavaScript(10000);
    //just wait
    Thread.sleep(10000);
    String xml = page.asXml();
//    System.out.println(xml);
    def xmlParser = new XmlParser()
    def root = xmlParser.parseText(xml)
    numberOfPlayers = root.'**'.find {
        try {
            it.name() == 'strong' && it.@style == 'color:#0a9ba8;'
        } catch(Exception e) {}
    }.text().trim()
    println "Number of players: $numberOfPlayers"
}

if("$numberOfPlayers".toInteger()>7){
    def to = "jesse1819@gmail.com"
    def subject = "PlayT2 has $numberOfPlayers players"
    def body = "Groovy script running from AWS"
    sendEmail(to, subject, body)
}

//Methods
def sendEmail(to, subject, body) {
    String sentFromUser = "jgnotifier@gmail.com"
    String sentFromPass = "dysrgrkleuhkknoe"
    def properties = new Properties()
    properties.put("mail.smtp.host", "smtp.gmail.com")
    properties.put("mail.smtp.port", "587")
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.starttls.enable", "true")
    properties.put("mail.smtp.ssl.trust", "smtp.gmail.com")

    def session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sentFromUser, sentFromPass)
        }
    })
    try {
        def message = new MimeMessage(session)
        message.setFrom(new InternetAddress(sentFromUser))
        message.setRecipients(Message.RecipientType.TO, to)
        message.setSubject(subject)
        message.setText(body)
        Transport.send(message)
        println "Sent message successfully...."
    } catch (MessagingException mex) {
        mex.printStackTrace()
    }
}