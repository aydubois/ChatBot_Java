package chatrabia.bot;

import java.util.ArrayList;

public class AssocPatternResponse {
    private String pattern = "";
    private String option = "";
    private ArrayList<String> templates = new ArrayList();

    public void setPattern(String pattern){
        this.pattern = pattern;
    }
    public void setOption(String option){
        this.option = option;
    }
    public void addTemplate(String template){
        this.templates.add(template);
    }

    public String getPattern() {
        return pattern;
    }
    public String getOption() {
        return option;
    }
    public ArrayList<String> getTemplates() {
        return templates;
    }

    public void afficher(){
        System.out.println("PATTERN ===> "+this.pattern);
        if(this.option.length() > 0){
            System.out.println("OPTION ===> "+this.option);
        }
        System.out.println("TEMPLATES : ");
        for (int i = 0; i < templates.size(); i++) {
            System.out.println("  - "+templates.get(i));
        }
    }
}
