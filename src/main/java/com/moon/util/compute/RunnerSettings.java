package com.moon.util.compute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public class RunnerSettings extends HashMap<String, Class> {

    private Supplier<List> arrCreator;
    private Supplier<Map> objCreator;

    public RunnerSettings() {
    }

    public Supplier<List> getArrCreator() {
        return arrCreator;
    }

    public void setArrCreator(Supplier<List> arrCreator) {
        this.arrCreator = arrCreator;
    }

    public Supplier<Map> getObjCreator() {
        return objCreator;
    }

    public void setObjCreator(Supplier<Map> objCreator) {
        this.objCreator = objCreator;
    }
}
