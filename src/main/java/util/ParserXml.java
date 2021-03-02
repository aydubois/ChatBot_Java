package util;

import bot.AssocPatternResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;


class ParseXml {
        private final ArrayList<AssocPatternResponse> patternResponse;

        private ParseXml(ParseBuilder builder) {
            this.patternResponse = builder.patternResponse;
        }

        public ArrayList<AssocPatternResponse> getPatternResponse() {
            return patternResponse;
        }

        public void printList(ParseBuilder.types type){
            switch (type){
                case PR -> patternResponse.forEach(patternResponse -> patternResponse.afficher());

            }
        }
        public static class ParseBuilder {
            private final ArrayList<AssocPatternResponse> patternResponse;

            public enum types {PR}

            ;

            public ParseBuilder(String filenameXmlConfig) {
                this.patternResponse = (ArrayList<AssocPatternResponse>) parse(filenameXmlConfig, types.PR);
            }

            public ParseXml build() {
                ParseXml parsexml = new ParseXml(this);
                return parsexml;
            }

            /*public ParseBuilder addXmlAutre(String filenameXmlAutre) {
                //
            }*/

            private ArrayList<?> parse(String filename, types type) {
                try {
                    File file= new File(filename);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    return switchType(type, doc);
                } catch (Exception e) {
                    System.out.println("Une erreur est survenue lors de la configuration du parser.");
                    e.printStackTrace();
                }
                return null;
            }

            private ArrayList<?> switchType(types typeCla, Document doc) {
                switch (typeCla) {
                    case PR:
                        return getConfig(doc);
                    default:
                        return null;

                }
            }

            private static ArrayList<AssocPatternResponse> getConfig(Document doc) {
                ArrayList<AssocPatternResponse> PRObject = new ArrayList<>();
                NodeList nListCategory = doc.getElementsByTagName("Category");
                for (int temp = 0; temp < nListCategory.getLength(); temp++) {

                    Node nNodeCategory = nListCategory.item(temp);

                    if (nNodeCategory.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElementCategory = (Element) nNodeCategory;
                        AssocPatternResponse oneAssocPR = new AssocPatternResponse();
                        String pattern = eElementCategory.getElementsByTagName("pattern").item(0).getTextContent();
                        try{
                            String option = eElementCategory.getElementsByTagName("option").item(0).getTextContent();
                            oneAssocPR.setOption(option);
                        }catch(NullPointerException e){
                            // bah rien faire, juste il n'y a pas d'option, not worry
                        }
                        oneAssocPR.setPattern(pattern);
                        for (int i = 0; i < eElementCategory.getElementsByTagName("template").getLength(); i++) {
                            String template = eElementCategory.getElementsByTagName("template").item(i).getTextContent();
                            oneAssocPR.addTemplate(template);
                        }
                        PRObject.add(oneAssocPR);

                    }
                }
                return PRObject;
            }
        }



    }
