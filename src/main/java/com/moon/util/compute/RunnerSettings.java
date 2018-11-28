package com.moon.util.compute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public class RunnerSettings {

    protected Supplier<List> arrCreator;
    protected Supplier<Map> objCreator;
    private final Map<String, Class> callers = new HashMap<>();

    private RunnerSettings(Map<? extends String, ? extends Class> callers, Supplier<List> arrCreator, Supplier<Map> objCreator) {
        this.callers.putAll(callers);
        this.arrCreator = arrCreator;
        this.objCreator = objCreator;
    }

    public Supplier<List> getArrCreator() {
        return arrCreator;
    }

    public Supplier<Map> getObjCreator() {
        return objCreator;
    }

    public RunnerSettings setArrCreator(Supplier<List> arrCreator) {
        this.arrCreator = arrCreator;
        return this;
    }

    public RunnerSettings setObjCreator(Supplier<Map> objCreator) {
        this.objCreator = objCreator;
        return this;
    }

    public RunnerSettings addCaller(Class clazz) {
        return addCaller(clazz.getSimpleName(), clazz);
    }

    public RunnerSettings addCallers(Class... classes) {
        for (Class type : classes) {
            addCaller(type);
        }
        return this;
    }

    public RunnerSettings addCaller(String name, Class staticCallerClass) {
        this.callers.put(name, staticCallerClass);
        return this;
    }

    public RunnerSettings addCallers(Map<String, Class> callers) {
        this.callers.putAll(callers);
        return this;
    }

    public RunnerSettings removeCaller(String name) {
        this.callers.remove(name);
        return this;
    }

    public RunnerSettings removeCallers(String... names) {
        for (String name : names) {
            this.callers.remove(name);
        }
        return this;
    }

    public Class getCaller(String name) {
        return this.callers.get(name);
    }

    public final static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Supplier arrCreator = ArrayList::new;
        private Supplier<Map> objCreator = HashMap::new;
        private Map<String, Class> callers = new HashMap<>();

        public Supplier getArrCreator() {
            return arrCreator;
        }

        public Builder setArrCreator(Supplier arrCreator) {
            this.arrCreator = arrCreator;
            return this;
        }

        public Supplier<Map> getObjCreator() {
            return objCreator;
        }

        public Builder setObjCreator(Supplier<Map> objCreator) {
            this.objCreator = objCreator;
            return this;
        }

        public Map<String, Class> getCallers() {
            return callers;
        }

        public Builder addCaller(String name, Class staticCallerClass) {
            this.callers.put(name, staticCallerClass);
            return this;
        }

        public Builder addCaller(Class clazz) {
            return addCaller(clazz.getSimpleName(), clazz);
        }

        public Builder addCallers(Class... classes) {
            for (Class type : classes) {
                addCaller(type);
            }
            return this;
        }

        public Builder addCallers(Map<String, Class> callers) {
            this.callers.putAll(callers);
            return this;
        }

        public Builder removeCaller(String name) {
            this.callers.remove(name);
            return this;
        }

        public Builder removeCallers(String... names) {
            for (String name : names) {
                this.callers.remove(name);
            }
            return this;
        }

        public RunnerSettings build() {
            return new RunnerSettings(callers, arrCreator, objCreator);
        }
    }
}
