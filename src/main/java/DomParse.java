import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.ConnectionHelper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class DomParse {
    public static void main(String[] args) {
        readXml();
    }

    public static boolean register(Article article) {
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null) {
                System.out.println("Connection error!!");
                return false;
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("insert into articles");
            sqlBuilder.append(" ");
            sqlBuilder.append("(title, description, pubDate, link, guid)");
            sqlBuilder.append(" ");
            sqlBuilder.append("values");
            sqlBuilder.append(" ");
            sqlBuilder.append("(?, ?, ?, ?, ?)");
            PreparedStatement preparedStatement = cnn.prepareStatement(sqlBuilder.toString());
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, article.getPubDate());
            preparedStatement.setString(4, article.getLink());
            preparedStatement.setString(5, article.getGuid());
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    public static ArrayList<Article> readXml() {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse("https://vnexpress.net/rss/tam-su.rss");
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Article article = new Article();
                Node cdNode = nodeList.item(i);
                NodeList cdChildNodeList = cdNode.getChildNodes();
                for (int j = 0; j < cdChildNodeList.getLength(); j++) {
                    Node childNode = cdChildNodeList.item(j);
                    if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    switch (childNode.getNodeName()) {
                        case "title":
                            article.setTitle(childNode.getTextContent());
                            break;
                        case "description":
                            article.setDescription(childNode.getTextContent());
                            break;
                        case "pubDate":
                            article.setPubDate(childNode.getTextContent());
                            break;
                        case "link":
                            article.setLink(childNode.getTextContent());
                            break;
                        case "guid":
                            article.setGuid(childNode.getTextContent());
                            break;
                    }
                }
                articleArrayList.add(article);
            }
            for (int i = 0; i < articleArrayList.size(); i++) {
                register(articleArrayList.get(i));
//                System.out.println(articleArrayList.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleArrayList;
    }
}
