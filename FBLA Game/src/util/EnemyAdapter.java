package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import entities.units.Enemy;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EnemyAdapter extends TypeAdapter<Enemy> {

    @Override
    public void write(JsonWriter out, Enemy value) throws IOException {

    }

    @Override
    public synchronized Enemy read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String enemy = in.nextString();
        CompletableFuture<Enemy> await = CompletableFuture.supplyAsync(() -> {
            try { return (Enemy) Class.forName(enemy).getConstructor().newInstance(); }
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
