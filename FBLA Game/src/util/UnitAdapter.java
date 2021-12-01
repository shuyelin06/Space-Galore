package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import entities.core.Entity;
import entities.units.Unit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UnitAdapter extends TypeAdapter<Unit> {

    @Override
    public void write(JsonWriter out, Unit value) throws IOException {

    }

    @Override
    public synchronized Unit read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String unit = in.nextString();
        CompletableFuture<Unit> await = CompletableFuture.supplyAsync(() -> {
            try { return (Unit) Class.forName(unit)
                    .getConstructor(float.class, float.class, Entity.Team.class)
                    .newInstance(0, 0, Entity.Team.Enemy); }
            catch (ClassNotFoundException
                    | NoSuchMethodException
                    | IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException e) { e.printStackTrace(); return null; }
        });

        while (!await.isDone()) System.out.println("CompletableFuture is not done");
        try { return await.get(); }
        catch (InterruptedException | ExecutionException e) { e.printStackTrace(); return null; }

    }

}
