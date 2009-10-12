/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

/**
 *
 * @author Ray
 */
class O3DJSParser {

    String f = "C:\\IDE\\3D\\o3d samples\\o3djs\\base.js";

    public void swallowBlock(StreamTokenizer st) throws IOException {
        for (int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF;) {
            if (tval > 0) {
                char c = (char) st.ttype;
                if (c == '{') {
                    swallowBlock(st);
                } else if (c == '}') {
                    return;
                }
            }
        }
    }

    public void printExpr(ArrayList<String> al) {
        for (String string : al) {
            System.out.print(string + " ");
        }
        System.out.println();
    }

    public void parse() throws IOException {
        Reader reader = new FileReader(f);
        StreamTokenizer st = new StreamTokenizer(reader);

//     st.slashSlashComments( true ); */
        st.slashStarComments(true);
        //st.ordinaryChar('/');
        st.wordChars('_', '_');
        st.parseNumbers();
        st.eolIsSignificant(true);

        ArrayList<String> expr = new ArrayList<String>();

        for (int tval; (tval = st.nextToken()) != StreamTokenizer.TT_EOF;) {
            if (tval == StreamTokenizer.TT_NUMBER) {
                System.out.println("Nummer: " + st.nval);
            } else if (tval == StreamTokenizer.TT_WORD) {
                //System.out.println("Wort: " + st.sval);
                expr.add(st.sval);
            } else if (tval == StreamTokenizer.TT_EOL) {
                //System.out.println("Ende der Zeile");
            } else {
                char c = (char) st.ttype;
                if (c == ';') {
                    printExpr(expr);
                    expr.clear();
                } else if (c == '{') {
                    swallowBlock(st);
                    printExpr(expr);
                    expr.clear();
                } else {
                    //System.out.println("Zeichen: " + c);
                    expr.add("" + c);
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        O3DJSParser op = new O3DJSParser();
        op.parse();
    }
}
