/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ray
 */
public class JSObject {

    private final String name;
    private final boolean isObject;
    private final HashMap<String, JSObject> children = new HashMap<String, JSObject>();
    private final List<String> parameters;

    public JSObject(String name) {
        this.name = name;
        this.isObject = true;
        this.parameters = null;
    }

    public JSObject(String name, List<String> parameters) {
        this.name = name;
        this.isObject = false;
        this.parameters = parameters;
    }

    public void addObject(String name) {
        if (!children.containsKey(name)) {
            children.put(name, new JSObject(name));
        } else {
            System.out.println("warn o " + name);
        }
    }

    public void addFunction(String name, List<String> parameters) {
        if (!children.containsKey(name)) {
            children.put(name, new JSObject(name, parameters));
        } else {
            System.out.println("warn f " + name);
        }
    }

    public boolean isFunction() {
        return !isObject;
    }

    public boolean isObject() {
        return isObject;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public JSObject getObject(String name) {
        if (!children.containsKey(name)) {
            children.put(name, new JSObject(name));
        }
        return children.get(name);
    }

    public HashMap<String, JSObject> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        StringBuffer sb = new StringBuffer();
        sb.append(indent + name + "\n");

        Set<String> keySet = children.keySet();
        for (String key : keySet) {
            sb.append(children.get(key).toString(indent + name+".") + "\n");
        }

        return sb.toString();
    }
}
