package util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import entities.units.Unit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class UnitAdapter extends TypeAdapter<Unit> {

    @Override
    public void write(JsonWriter out, Unit value) throws IOException {

    }

    @Override
    public Unit read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String unit = in.nextString();
        try {return (Unit) Class.forName(unit).getConstructor().newInstance(); }
        catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e) { return null; }

    }

}
