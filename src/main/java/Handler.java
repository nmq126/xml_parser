import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class Handler extends DefaultHandler {
    private boolean isTitle;
    private boolean isDescription;
    private boolean isPubDate;
    private boolean isGuid;
    private boolean isLink;
    private Article currentArticle;
    private ArrayList<Article> articleArrayList = new ArrayList<>();

    public ArrayList<Article> getArticleArrayList() {
        return articleArrayList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case "item":
                currentArticle = new Article();
                break;
            case Article.TITLE_TAG:
                isTitle = true;
                break;
            case Article.DESCRIPTION_TAG:
                isDescription = true;
                break;
            case Article.PUBDATE_TAG:
                isPubDate = true;
                break;
            case Article.LINK_TAG:
                isLink = true;
                break;
            case Article.GUID_TAG:
                isGuid = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "item":
                articleArrayList.add(currentArticle);
                break;
            case Article.TITLE_TAG:
                isTitle = false;
                break;
            case Article.DESCRIPTION_TAG:
                isDescription = false;
                break;
            case Article.PUBDATE_TAG:
                isPubDate = false;
                break;
            case Article.LINK_TAG:
                isLink = false;
                break;
            case Article.GUID_TAG:
                isGuid = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        String value = new String(ch, start, length);
        if (currentArticle != null) {
            if (isTitle) {
                currentArticle.setTitle(value);
            } else if (isDescription) {
                currentArticle.setDescription(value);
            } else if (isPubDate) {
                currentArticle.setPubDate(value);
            } else if (isLink) {
                currentArticle.setLink(value);
            } else if (isGuid) {
                currentArticle.setGuid(value);
            }
        }
    }
}
