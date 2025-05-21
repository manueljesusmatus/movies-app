package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.configuration.property.XPathProperties;
import cl.dev.mmatush.moviesapp.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScraperServiceImpl implements ScraperService {

    private final XPathProperties xPathProperties;

    @Override
    public Map<String, Object> extractData(String url) throws IOException {
        log.info("Extrayendo data desde <url: {}>", url);
        try (final WebClient client = new WebClient(BrowserVersion.CHROME)) {
            client.getOptions().setJavaScriptEnabled(false);
            client.getOptions().setCssEnabled(false);

            HtmlPage page = client.getPage(url);

            Map<String, Object> data = new HashMap<>();

            for (Map.Entry<String, String> entry : xPathProperties.getXpaths().entrySet()) {
                String key = entry.getKey();
                String expr = entry.getValue();

                List<DomNode> res = page.getByXPath(expr);

                if (key.equalsIgnoreCase("genres") || key.equalsIgnoreCase("cast")) {
                    List<String> texts = new ArrayList<>();
                    for (DomNode node : res) {
                        texts.add(cleanText(node.getNodeValue()));
                    }
                    data.put(key, texts);
                } else {
                    StringBuilder text = new StringBuilder();
                    for (DomNode node : res) {
                        text.append(node.getNodeValue());
                    }
                    data.put(key, cleanText(text.toString()));
                }
            }

            return data;
        }
    }

    private static String cleanText(String text) {
        return text.replace("\r", "")
                .replace("\n", "")
                .replace("\t", "")
                .replace(". ", "")
                .trim();
    }

}
