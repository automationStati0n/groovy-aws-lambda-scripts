@Grapes(
        @Grab(group='net.sourceforge.htmlunit', module='htmlunit', version='2.69.0')
)

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClientOptions
import groovy.xml.XmlParser

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
    System.out.println(xml);
    def xmlParser = new XmlParser()
    def root = xmlParser.parseText(xml)

    def numberOfPlayers = root.'**'.find { it.name() == 'strong' && it.@style == 'color:#0a9ba8;' }.text()

    println "Number of players: $numberOfPlayers"
}