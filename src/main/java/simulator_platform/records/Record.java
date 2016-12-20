// Knows how to store a set of key value pairs

package simulator_platform.records;

import java.util.HashMap;

public class Record {

    int id;
    private HashMap vals;

    public Record(int id) {
        this.id = id;
        this.vals = new HashMap();
    }

    public int getID(){
        return id;
    }

    public void setStr(String key, String new_value) {
        vals.put(key, new_value);
    }

    public void setBool(String key, Boolean new_value) {
        vals.put(key, new_value);
    }

    public void setInt(String key, Integer new_value) {
        vals.put(key, new_value);
    }

    public void setDouble(String key, Double new_value) {
        vals.put(key, new_value);
    }

    public void setBoolFromStr(String key, String new_value) {
        if (new_value.equals("1")) new_value = "true";
        vals.put(key, Boolean.parseBoolean(new_value));
    }

    public void setIntFromStr(String key, String new_value) {
        vals.put(key, Integer.valueOf(new_value));
    }

    public void setDoubleFromStr(String key, String new_value) {
        vals.put(key, Double.parseDouble(new_value));
    }

    public String getStr(String key) {
        return (String) vals.get(key);
    }

    public Boolean getBool(String key) {
        return (Boolean) vals.get(key);
    }

    public int getInt(String key) {
        return (Integer) vals.get(key);
    }

    public double getDouble(String key) {
        return (Double) vals.get(key);
    }

}