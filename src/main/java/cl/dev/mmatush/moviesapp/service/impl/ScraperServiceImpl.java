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

    private static final String GENRES = "genres";
    private static final String CAST = "cast";
    private static final String THUMBNAIL = "thumbnail";
    private static final String FULLCOVER = "fullCover";
    private static final String IMAGES = "images";
    private static final String ID = "id";

    private final XPathProperties xPathProperties;

    @Override
    public Map<String, Object> extractData(String movieId) throws IOException {
        String url = xPathProperties.getUrl() + movieId.toLowerCase();
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

                if (GENRES.equalsIgnoreCase(key) || CAST.equalsIgnoreCase(key)) {
                    data.put(key, searchTextList(res));
                } else if (THUMBNAIL.equalsIgnoreCase(key) || FULLCOVER.equalsIgnoreCase(key)) {
                    data.put(IMAGES, searchMap(res, data, key, movieId));
                } else {
                    data.put(key, searchText(res));
                }
            }
            return data;
        }
    }

    private static String searchText(List<DomNode> nodo) {
        StringBuilder text = new StringBuilder();
        for (DomNode node : nodo) {
            text.append(node.getNodeValue());
        }
        return cleanText(text.toString());
    }

    private static List<String> searchTextList(List<DomNode> nodo) {
        List<String> texts = new ArrayList<>();
        for (DomNode node : nodo) {
            texts.add(cleanText(node.getNodeValue()));
        }
        return texts;
    }

    private static Map<String, Object> searchMap(List<DomNode> nodo, Map<String, Object> data, String key, String movieId) {
        Map<String, Object> images = (Map<String, Object>) data.getOrDefault(IMAGES, new HashMap<>(Map.of(ID, movieId)));
        images.put(key, searchText(nodo));
        return images;
    }

    private static String cleanText(String text) {
        return text.replace("\r", "")
                .replace("\n", "")
                .replace("\t", "")
                .replace(". ", "")
                .trim();
    }

}
