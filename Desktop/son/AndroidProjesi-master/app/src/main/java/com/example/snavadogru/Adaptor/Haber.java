package com.example.snavadogru.Adaptor;

public class Haber {

        private String xmllitle;
        private String xmlimage;
        private String xmllink;

    public Haber(String xmllitle) {
        this.xmllitle = xmllitle;
    }

    public Haber() {
        }

        public Haber(String xmllitle, String xmlimage, String xmllink) {
            this.xmllitle = xmllitle;
            this.xmlimage = xmlimage;
            this.xmllink = xmllink;
        }

        public String getXmllitle() {
            return xmllitle;
        }

        public void setXmllitle(String xmllitle) {
            this.xmllitle = xmllitle;
        }

        public String getXmlimage() {
            return xmlimage;
        }

        public void setXmlimage(String xmlimage) {
            this.xmlimage = xmlimage;
        }

        public String getXmllink() {
            return xmllink;
        }

        public void setXmllink(String xmllink) {
            this.xmllink = xmllink;
        }
    }

