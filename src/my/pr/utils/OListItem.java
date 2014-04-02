/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.pr.utils;

/**
 *
 * @author prando
 */
public class OListItem {
    
    private int id = -1;
    private Object value = null;
    
    public OListItem() {
        
    }
    
    public OListItem(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
    public String toString() {
        return String.valueOf(value);
    }
    
}
