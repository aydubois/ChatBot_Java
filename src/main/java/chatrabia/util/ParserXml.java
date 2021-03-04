package chatrabia.util;

import chatrabia.bot.AssocPatternResponse;
import chatrabia.bot.AssocWordCitation;
import chatrabia.domain.Citation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * <p>Builder pattern</p>
 * <p>On evite de devoir creer de nouveaux constructeurs à chaque fois qu'on veut rajouter une propriété</p>
 */
class ParseXml {
    private final ArrayList<AssocPatternResponse> patternResponse;
    private final ArrayList<String> services;
    private final ArrayList<AssocWordCitation> assocCitations;

        private ParseXml(ParseBuilder builder) {
            this.services = builder.services;
            this.patternResponse = builder.patternResponse;
            this.assocCitations = builder.assocCitations;
        }

        // todo: protect ?
        public ArrayList<AssocPatternResponse> getPatternResponse() {
            return patternResponse;
        }
        public ArrayList<String> getServices(){return services;}
        public ArrayList<AssocWordCitation> getAssocCitations(){return assocCitations;}


        public static class ParseBuilder {
            private final ArrayList<AssocPatternResponse> patternResponse;
            private ArrayList<String> services;
            private ArrayList<AssocWordCitation> assocCitations;
            public enum Types {PR, SERV, CITATION}

            ;

            public ParseBuilder(String filenameXmlConfig) {
                this.patternResponse = (ArrayList<AssocPatternResponse>) parse(filenameXmlConfig, Types.PR);
            }

            public ParseXml build() {
                ParseXml parsexml = new ParseXml(this);
                return parsexml;
            }

            public ParseBuilder addXmlService(String filenameXmlService) {
                this.services = (ArrayList<String>) parse(filenameXmlService, Types.SERV);
                return this;
            }
            public ParseBuilder addXmlCitation(String filenameXmlService) {
                this.assocCitations = (ArrayList<AssocWordCitation>) parse(filenameXmlService, Types.CITATION);
                return this;
            }
            private ArrayList<?> parse(String filename, Types type) {
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

            private ArrayList<?> switchType(Types typeCla, Document doc) {
                switch (typeCla) {
                    case PR:
                        return getConfig(doc);
                    case SERV:
                        return getServ(doc);
                    case CITATION:
                        return getCit(doc);
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


            private static ArrayList<String> getServ(Document doc) {
                ArrayList<String> SERVObject = new ArrayList<>();
                NodeList nListCategory = doc.getElementsByTagName("Services");
                for (int temp = 0; temp < nListCategory.getLength(); temp++) {

                    Node nNodeCategory = nListCategory.item(temp);

                    if (nNodeCategory.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElementCategory = (Element) nNodeCategory;
                        String oneAssocPR = eElementCategory.getElementsByTagName("pattern").item(0).getTextContent();
                        SERVObject.add(oneAssocPR);

                    }
                }
                return SERVObject;
            }

            private static ArrayList<AssocWordCitation> getCit(Document doc) {
                ArrayList<AssocWordCitation> CITATIONObject = new ArrayList<>();
                NodeList nListCategory = doc.getElementsByTagName("Couple");
                for (int temp = 0; temp < nListCategory.getLength(); temp++) {

                    Node nNodeCategory = nListCategory.item(temp);

                    if (nNodeCategory.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElementCategory = (Element) nNodeCategory;
                        AssocWordCitation oneAssocCIT = new AssocWordCitation();
                        String englishWordAPI = eElementCategory.getElementsByTagName("english").item(0).getTextContent();
                        String patternFr = eElementCategory.getElementsByTagName("francais").item(0).getTextContent();

                        oneAssocCIT.setWordAPI(englishWordAPI);
                        oneAssocCIT.setPatternFr(patternFr);

                        CITATIONObject.add(oneAssocCIT);

                    }
                }
                return CITATIONObject;
            }
        }
    }



