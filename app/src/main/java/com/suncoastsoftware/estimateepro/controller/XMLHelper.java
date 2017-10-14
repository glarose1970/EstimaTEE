package com.suncoastsoftware.estimateepro.controller;

import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLHelper {

    public void SaveXML(Context context, String id, String company, String contact, String phone) {


             File dir = context.getFilesDir();
             File file = new File(dir, "customers.xml");
             //file.delete();

        //File does not exist
            if (!file.exists()) {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                Document doc;

                try {

                   // FileOutputStream fos = context.openFileOutput("customers.xml", context.MODE_APPEND);
                    DocumentBuilder dBuilder = docBuilderFactory.newDocumentBuilder();
                    doc = dBuilder.newDocument();

                    //Root Element
                    Element rootElement = doc.createElement("customers");
                    doc.appendChild((rootElement));

                    //customer element
                    Element custElement = doc.createElement("customer");
                    rootElement.appendChild(custElement);
                    //set attribute for customer id
                    Attr custElementAtt = doc.createAttribute("id");
                    custElementAtt.setValue(id);
                    custElement.setAttributeNode(custElementAtt);

                    //company element
                    Element companyElement = doc.createElement("company");
                    companyElement.appendChild(doc.createTextNode(company));
                    custElement.appendChild(companyElement);

                    //contact element
                    Element contactElement = doc.createElement("contactname");
                    contactElement.appendChild(doc.createTextNode(contact));
                    rootElement.appendChild(contactElement);

                    //phone element
                    Element phoneElement = doc.createElement("phone");
                    phoneElement.appendChild(doc.createTextNode(phone));
                    rootElement.appendChild(phoneElement);

                    //save xml to file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(file);

                    transformer.transform(source, result);
                    //fos.close();

                }catch (Exception e) {
                    e.printStackTrace();
                }
                //File does exist..needs updating
            }else {

                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                Document doc;

                try {

                   // FileOutputStream fos = context.openFileOutput("customers.xml", context.MODE_APPEND);
                    DocumentBuilder dBuilder = docBuilderFactory.newDocumentBuilder();
                    doc = dBuilder.parse(file);
                    Element root = doc.getDocumentElement();

                   // NodeList customerNodes = doc.getElementsByTagName("customer");

                    if (isMatch(context, company)) {
                        Toast.makeText(context, "Customer Already Exists, Please choos a new Customer", Toast.LENGTH_LONG).show();
                        //return;
                    }
                    else {

                        //customer element
                        Element custElement = doc.createElement("customer");
                       // root.appendChild(custElement);
                        //set attribute for customer id
                        Attr custElementAtt = doc.createAttribute("id");
                        custElementAtt.setValue(id);
                        custElement.setAttributeNode(custElementAtt);

                        //company element
                        Element companyElement = doc.createElement("company");
                        companyElement.appendChild(doc.createTextNode(company));
                        custElement.appendChild(companyElement);

                        //contact element
                        Element contactElement = doc.createElement("contactname");
                        contactElement.appendChild(doc.createTextNode(contact));
                        root.appendChild(contactElement);

                        //phone element
                        Element phoneElement = doc.createElement("phone");
                        phoneElement.appendChild(doc.createTextNode(phone));
                        root.appendChild(phoneElement);

                        //append the customer node
                        root.appendChild(custElement);

                        //save xml to file
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(file);

                        transformer.transform(source, result);
                       // fos.close();
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    public boolean isMatch(Context context, String customer) {

        boolean matched = false;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        Document doc;

        try {
           // fos = context.openFileOutput("customers.xml", context.MODE_PRIVATE);
            File xmldir = context.getFilesDir();
            File xmlfile = new File(xmldir, "customers.xml");
            DocumentBuilder dBuilder = docBuilderFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlfile);

            NodeList custormerNodes = doc.getElementsByTagName("company");

            for (int i = 0; i < custormerNodes.getLength(); i++) {
                String company = custormerNodes.item(i).getTextContent();
                if (company.equalsIgnoreCase(customer)) {
                    matched = true;
                }
                else {
                    matched = false;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return matched;
    }

}
