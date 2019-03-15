/**
 * FileName: XmlTools
 * Author:   zghuang
 * Date:     2019/3/14 19:40
 * Description: xml数据工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

import com.zhigang.myspringboot.mapandxml.Node;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈xml数据工具类〉
 *
 * @author zghuang
 * @version 3.2.2
 * @create 2019/3/14 19:40
 */
public class XmlTools {
    private static final String LT = "<";

    private static final String GT = ">";

    private static final String LT_SPRIT = "</";

    private static final String NEW_LINE = "\n";

    public static String nodeToStr(Node node) throws NullPointerException {
        if (node == null || node.getKey() == null) {
            throw new NullPointerException("Node or Node's key is null");
        }
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append(LT).append(node.getKey()).append(GT).append(NEW_LINE);
        if (node.getValue() == null) {
            addSubNodes(sb, node);
        } else {
            sb.append(node.getValue());
        }
        sb.append(LT_SPRIT).append(node.getKey()).append(GT).append(NEW_LINE);
        return sb.toString();
    }

    private static void addSubNodes(StringBuffer sb, Node node) throws NullPointerException {
        List<Node> nodes = node.getSubNodes();
        if (nodes == null || nodes.isEmpty()) {
            return;
        }

        for (Node subNode : nodes) {
            if (subNode.getKey() == null) {
                throw new NullPointerException("subNode's key is null");
            }
            sb.append(LT).append(subNode.getKey()).append(GT);

            if (subNode.getValue() == null && subNode.getSubNodes() != null) {
                sb.append(NEW_LINE);
                addSubNodes(sb, subNode);
            } else {
                sb.append(subNode.getValue());
            }
            sb.append(LT_SPRIT).append(subNode.getKey()).append(GT).append(NEW_LINE);
        }
    }

    public static void main(String[] args) {
        Node node = new Node("root");
        List<Node> subNodes = new ArrayList<Node>() {{
            Node node1 = new Node("gdfga", "dsfgsdfg");
            add(node1);
            Node node2 = new Node("subroot");
            List<Node> subNode2 = new ArrayList<Node>() {{
                Node node2sub1 = new Node("subnode1", "tttttt");
                Node node2sub2 = new Node("subnode2", "33333");
                add(node2sub1);
                add(node2sub2);
            }};
            node2.setSubNodes(subNode2);
            add(node2);
            Node node3 = new Node("NODE3", "DFGADFG");

            add(node3);
        }};
        node.setSubNodes(subNodes);

        System.out.println(nodeToStr(node));

        try {
            System.out.println(formatXML(nodeToStr(node)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String formatXML(String inputXML) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(inputXML));
        String requestXML = null;
        XMLWriter writer = null;
        if (document != null) {
            StringWriter stringWriter = new StringWriter();
            OutputFormat format = new OutputFormat("\t", true, "UTF-8");
            format.setNewLineAfterDeclaration(false);
            format.setNewlines(true);
            format.setTrimText(true);
            writer = new XMLWriter(stringWriter, format);
            writer.write(document);
            writer.flush();
            requestXML = stringWriter.getBuffer().toString();
            writer.close();
        }
        return requestXML;
    }

}
