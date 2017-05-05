package me.woder.json;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChatMessageDezerializer implements JsonDeserializer<ChatMessage> {
    
    @Override
    public ChatMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ChatMessage message = new ChatMessage();
        JsonObject obj = json.getAsJsonObject();
        message.setTranslate(obj.get("translate").getAsString());
        JsonArray array = obj.getAsJsonArray("with");
        /*message.getWith().add(context.deserialize(array.get(0), With.class));*/
        message.getWith().add(array.get(1).getAsString());
        return message;
    }
}
