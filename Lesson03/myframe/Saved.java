package myframe;

import java.io.Serializable;

public class Saved implements Serializable {

    private String textFieldUs;
    private String textFieldFr;
    private String textFieldDe;

    public Saved(String textFieldUs, String textFieldFr, String textFieldDe){
        this.textFieldUs = textFieldUs;
        this.textFieldFr = textFieldFr;
        this.textFieldDe = textFieldDe;
    }

    public String gettextFieldUs() {
        return textFieldUs;
    }

    public void settextFieldUs(String textFieldUs) {
        this.textFieldUs = textFieldUs;
    }

    public String gettextFieldFr() {
        return textFieldFr;
    }

    public void settextFieldFr(String textFieldFr) {
        this.textFieldFr = textFieldFr;
    }

    public String textFieldDe() {
        return textFieldDe;
    }

    public void settextFieldDe(String textFieldDe) {
        this.textFieldDe = textFieldDe;
    }

    @Override
    public String toString() {
        String s = "Us=" + textFieldUs +
                "Fr=" + textFieldFr +
                "De=" + textFieldDe ;
        return s;
    }
}
