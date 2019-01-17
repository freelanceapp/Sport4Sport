package technology.infobite.com.sportsforsports;

import java.util.StringJoiner;

public class CommentModel {

    private String tex1;
    private  String tex2;

    public String getTex1() {
        return tex1;
    }

    public void setTex1(String tex1) {
        this.tex1 = tex1;
    }

    public String getTex2() {
        return tex2;
    }

    public void setTex2(String tex2) {
        this.tex2 = tex2;
    }

    public CommentModel(String tex1, String tex2) {
        this.tex1 = tex1;
        this.tex2 = tex2;

    }
}
