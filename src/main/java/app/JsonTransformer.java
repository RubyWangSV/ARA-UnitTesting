package app;

import com.google.gson.Gson;

public class JsonTransformer {
    private Gson gson = new Gson();

    //@Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
