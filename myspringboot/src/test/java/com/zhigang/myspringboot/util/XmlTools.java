/**
 * FileName: XmlTools
 * Author:   admin
 * Date:     2019/3/14 19:40
 * Description: xml数据工具类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.zhigang.myspringboot.util;

import com.zhigang.myspringboot.mapandxml.XmlNode;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 〈xml数据工具类〉
 *
 * @author admin
 * @version 3.2.2
 * @create 2019/3/14 19:40
 */
@Slf4j
public class XmlTools {
    private static final String LT = "<";

    private static final String GT = ">";

    private static final String LT_SPRIT = "</";

    private static final String NEW_LINE = "\n";

    public static String nodeToStr(XmlNode xmlNode) throws NullPointerException {
        if (xmlNode == null || xmlNode.getKey() == null) {
            throw new NullPointerException("XmlNode or XmlNode's key is null");
        }
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append(LT).append(xmlNode.getKey()).append(GT).append(NEW_LINE);
        if (xmlNode.getValue() == null) {
            addSubNodes(sb, xmlNode);
        } else {
            sb.append(xmlNode.getValue());
        }
        sb.append(LT_SPRIT).append(xmlNode.getKey()).append(GT).append(NEW_LINE);
        return sb.toString();
    }

    private static void addSubNodes(StringBuffer sb, XmlNode xmlNode) throws NullPointerException {
        List<XmlNode> xmlNodes = xmlNode.getSubXmlNodes();
        if (xmlNodes == null || xmlNodes.isEmpty()) {
            return;
        }

        for (XmlNode subXmlNode : xmlNodes) {
            if (subXmlNode.getKey() == null) {
                throw new NullPointerException("subXmlNode's key is null");
            }
            sb.append(LT).append(subXmlNode.getKey()).append(GT);

            if (subXmlNode.getValue() == null) {
                sb.append(NEW_LINE);
                addSubNodes(sb, subXmlNode);
            } else {
                sb.append(subXmlNode.getValue());
            }
            sb.append(LT_SPRIT).append(subXmlNode.getKey()).append(GT).append(NEW_LINE);
        }
    }


    private static String nodeToStr_dsf(XmlNode xmlNode) {
        if (xmlNode == null || xmlNode.getKey() == null) {
            throw new NullPointerException("XmlNode or XmlNode's key is null");
        }
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

        System.out.println(sb.toString());


        Stack<XmlNode> nodeStack = new Stack<>();
        sb.append(LT).append(xmlNode.getKey()).append(GT).append(NEW_LINE);
        nodeStack.push(xmlNode);
        while (!nodeStack.empty()) {
            XmlNode node = nodeStack.pop();

            if (node.getValue() == null) {
                List<XmlNode> xmlNodes = node.getSubXmlNodes();
                if (xmlNodes != null && !xmlNodes.isEmpty()) {
                    Iterator<XmlNode> nodeIterable = xmlNodes.iterator();
                    while (nodeIterable.hasNext()) {
                        XmlNode subNode = nodeIterable.next();
                        sb.append(LT).append(subNode.getKey()).append(GT).append(NEW_LINE);
                        nodeStack.push(subNode);
                    }
                }

            } else {
                sb.append(node.getValue());
            }
            sb.append(LT_SPRIT).append(node.getKey()).append(GT).append(NEW_LINE);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        XmlNode xmlNode = new XmlNode("root");
        List<XmlNode> subXmlNodes = new ArrayList<XmlNode>() {{
            XmlNode xmlNode1 = new XmlNode("gdfga", "dsfgsdfg");
            add(xmlNode1);
            XmlNode xmlNode2 = new XmlNode("subroot");
            List<XmlNode> subXmlNode2 = new ArrayList<XmlNode>() {{
                XmlNode xmlNode2Sub1 = new XmlNode("subnode1", "tttttt");
                XmlNode xmlNode2Sub2 = new XmlNode("subnode2", "33333");
                add(xmlNode2Sub1);
                add(xmlNode2Sub2);
            }};
            xmlNode2.setSubXmlNodes(subXmlNode2);
            add(xmlNode2);
            XmlNode xmlNode3 = new XmlNode("NODE3", "DFGADFG");

            add(xmlNode3);
            add(new XmlNode("key"));
        }};
        xmlNode.setSubXmlNodes(subXmlNodes);

        System.out.println(nodeToStr(xmlNode));
        System.out.println(nodeToStr_dsf(xmlNode));
        try {
            System.out.println(formatXML(nodeToStr(xmlNode)));
        } catch (Exception e) {
            log.info("exception:", e);
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
