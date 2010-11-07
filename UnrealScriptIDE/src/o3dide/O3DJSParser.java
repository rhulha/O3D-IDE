/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ray
 */
class O3DJSParser {

    JSObject jsObject = new JSObject("ROOT");
    boolean debug = false;

    public JSObject getJsObject() {
        return jsObject;
    }

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

    // i.e.: o3djs . getObjectByName = function ( name , opt_obj )
    // or: o3djs.base.isArray = function(value)
    public void printExpr(ArrayList<String> al) {
        JSObject myJsObj = jsObject;
        // 1st get to correct object i.e.
        for (int i = 1; i < al.size(); i += 2) {
            if (!".".equals(al.get(i))) {
                break;
            }
            if (debug) {
                System.out.println(al.get(i - 1));
            }
            myJsObj = myJsObj.getObject(al.get(i - 1));
        }

        int funcIndex = al.indexOf("function");
        if (funcIndex > 0) {
            List<String> params = al.subList(funcIndex + 2, al.size() - 1);

            myJsObj.addFunction(al.get(funcIndex - 2), params);
            //if( ".".equals( al.get(1)) && "function".equals( al.get(5)) )
        } else if (al.size() > 0) {
            myJsObj.addObject(al.get(0));
        }
        if (debug && false) {
            for (String string : al) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

    public void parse(File file) throws IOException {
        Reader reader = new FileReader(file);
        StreamTokenizer st = new StreamTokenizer(reader);

//     st.slashSlashComments( true ); */
        st.slashStarComments(true);
        st.wordChars('_', '_');
        //st.parseNumbers();
        st.ordinaryChar('.');
        st.whitespaceChars(',', ',');
        //st.quoteChar('\'');
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
        op.debug = true;
        //op.parse("C:\\IDE\\3D\\o3d samples\\o3djs\\util.js");
    }
}
