package chatrabia.domain;

import java.io.Serializable;

public class Kaamelott implements Serializable {


    public class Citation{
        public class Infos{
            private String auteur;
            private String acteur;
            private String personnage;
            private String saison;
            private String episode;

            public void setAuteur(String auteur) {
                this.auteur = auteur;
            }

            public void setActeur(String acteur) {
                this.acteur = acteur;
            }

            public void setPersonnage(String personnage) {
                this.personnage = personnage;
            }

            public void setSaison(String saison) {
                this.saison = saison;
            }

            public void setEpisode(String episode) {
                this.episode = episode;
            }

            public String getAuteur() {
                return auteur;
            }

            public String getActeur() {
                return acteur;
            }

            public String getPersonnage() {
                return personnage;
            }

            public String getSaison() {
                return saison;
            }

            public String getEpisode() {
                return episode;
            }
        }

        private String citation;
        private Infos infos;

        public String getCitation() {
            return citation;
        }

        public void setCitation(String citation) {
            this.citation = citation;
        }

        public Infos getInfos() {
            return infos;
        }

        public void setInfos(Infos infos) {
            this.infos = infos;
        }
    }

    private Integer status;
    private Citation citation;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Citation getCitation() {
        return this.citation;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }
}
