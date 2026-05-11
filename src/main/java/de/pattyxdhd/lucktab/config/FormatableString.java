package de.pattyxdhd.lucktab.config;

public class FormatableString {

    private String string;

    public FormatableString(String string) {
        this.string = string;
    }

    public String formatColors(){
        if(string != null){
            return string.replace('&', '§');
        }
        return "Der String konnte nicht gefunden werden.";
    }

    public String getString(){
        if(string != null){
            return string;
        }
        return "Der String konnte nicht gefunden werden.";
    }

}
