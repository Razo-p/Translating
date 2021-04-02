import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    public String[] originals;
    public String[] translates;
    private String artist;
    private String song;
    private Document targetHTML;
    private int iterationCount;

    public String site;

    public Parser(String artist, String song){
        iterationCount = 0;

        this.artist=artist;
        this.song=song;

        for (int i = 0; i < 4; i++) {
            try {
                targetHTML = Jsoup.connect(getRequest()).get();
            } catch (Exception e) { iterationCount++;}
            if (targetHTML != null) break;
        }
        if (targetHTML != null){
            switch (iterationCount){
                case 0:
                    toStringAmalgama();
                    break;
                case 1:
                    toStringLyricHUB();
                    break;
                case 2:
                    toStringPerevodPesen();
                    break;
                case 3:
                    toStringLyrsense();
                    break;

            }
        }
        else{
            System.out.println("не получилось найти");
        }

    }



    private String getRequest(){
        switch (iterationCount){
            case 0:
                return "https://www.amalgama-lab.com/songs/"+
                        artist.toLowerCase().replace(' ', '_').charAt(0)+ "/" +
                            artist.toLowerCase().replace(' ', '_')+"/"+
                                song.toLowerCase().replace(' ', '_')+".html";
            case 1:
                return "https://lyricshub.ru/track/"+artist.replace(' ', '-')+
                        "/"+song.replace(' ', '-')+"/translation";

            case 2:
                return "http://www.perevod-pesen.ru/"+
                        artist.replace(' ', '-').toLowerCase()+"-"+
                        song.replace(' ', '-').toLowerCase()+"/";
            case 3:
                return "https://en.lyrsense.com/"+
                        artist.replace(' ', '_').toLowerCase()+"/"+
                        song.replace(' ', '_').toLowerCase();
            default: return null;

        }
    }





    private void toStringAmalgama(){
        site = "AMALGAMA";
        Elements originalsE = targetHTML.getElementsByClass("original");
        Elements translatesE = targetHTML.getElementsByClass("translate");

        Element[] original = originalsE.toArray(new Element[0]);
        Element[] translate = translatesE.toArray(new Element[0]);

        originals = new String[original.length];
        translates = new String[translate.length];

        for (int i=0; i<original.length; i++){
            originals[i]=original[i].text();
            translates[i]=translate[i].text();
        }
    }





    private void toStringLyricHUB(){
        site = "LyricHUB";
        Elements originalsE = targetHTML.getElementsByClass("orlangstr");
        Elements translatesE = targetHTML.getElementsByClass("trsllangstr");

        Element titleOriginal = targetHTML.getElementsByClass("lyrtitle").first();
        Element titleTranslate = targetHTML.getElementsByClass("lyrtitle").last();

        Element[] original = originalsE.toArray(new Element[0]);
        Element[] translate = translatesE.toArray(new Element[0]);


        originals = new String[original.length+1];
        translates = new String[translate.length+1];


        originals[0]=titleOriginal.text().substring(titleOriginal.text().indexOf("-")+2);
        translates[0]=titleTranslate.text().substring(titleTranslate.text().indexOf("-")+2);

        for (int i=1; i<original.length+1; i++){
            originals[i]=original[i-1].text();
            translates[i]=translate[i-1].text();
        }
    }







    private void toStringPerevodPesen(){
        site = "Perevod-Pesen";
        Elements originalsE = targetHTML.getElementsByClass("text");
        Elements translatesE = targetHTML.getElementsByClass("prvd");

        Element[] original = originalsE.toArray(new Element[0]);
        Element[] translate = translatesE.toArray(new Element[0]);

        originals = new String[original.length];
        translates = new String[translate.length];

        for (int i=0; i<original.length; i++){
            originals[i]=original[i].text();
            translates[i]=translate[i].text();
        }
    }





    private void toStringLyrsense(){
        site = "Lyrsense";
        Elements originalsE = targetHTML.getElementById("fr_text").getElementsByClass("highlightLine");
        Elements translatesE = targetHTML.getElementById("ru_text").getElementsByClass("highlightLine");

        Element titleOriginal = targetHTML.getElementById("fr_title");
        Element titleTranslate = targetHTML.getElementById("ru_title");

        Element[] original = originalsE.toArray(new Element[0]);
        Element[] translate = translatesE.toArray(new Element[0]);

        originals = new String[original.length+1];
        translates = new String[translate.length+1];

        originals[0] = titleOriginal.text();
        translates[0] = titleTranslate.text();

        for (int i=1; i<original.length+1; i++){
            originals[i]=original[i-1].text();
            translates[i]=translate[i-1].text();
        }
    }


}
