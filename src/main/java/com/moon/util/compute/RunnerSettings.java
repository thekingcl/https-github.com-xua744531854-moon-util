package com.moon.util.compute;

import com.moon.lang.ThrowUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author benshaoye
 */
public class RunnerSettings {

    public final static RunnerSettings DEFAULT = new PrivateRunnerSettings();

    protected Supplier<List> arrCreator;
    protected Supplier<Map> objCreator;
    private final Map<String, Class> callers = new HashMap<>();

    public RunnerSettings() {
    }

    public RunnerSettings(Map<? extends String, ? extends Class> callers, Supplier<List> arrCreator, Supplier<Map> objCreator) {
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

    private static class PrivateRunnerSettings extends RunnerSettings {
        private final Supplier<List> arrCreator;
        private final Supplier<Map> objCreator;

        private PrivateRunnerSettings() {
            if (RunnerSettings.DEFAULT == null) {
                this.arrCreator = ArrayList::new;
                this.objCreator = HashMap::new;
            } else {
                this.arrCreator = ThrowUtil.rejectAccessError();
                this.objCreator = ThrowUtil.rejectAccessError();
            }
        }

        @Override
        public Supplier<List> getArrCreator() {
            return this.arrCreator;
        }

        @Override
        public Supplier<Map> getObjCreator() {
            return this.objCreator;
        }

        @Override
        public PrivateRunnerSettings addCaller(String name, Class staticCallerClass) {
            return this;
        }

        @Override
        public RunnerSettings addCallers(Map<String, Class> callers) {
            return this;
        }

        @Override
        public RunnerSettings removeCaller(String name) {
            return this;
        }

        @Override
        public RunnerSettings removeCallers(String... names) {
            return this;
        }
    }
}
