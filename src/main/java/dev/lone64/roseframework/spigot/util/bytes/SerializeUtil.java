package dev.lone64.roseframework.spigot.util.bytes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {

    public static <T> byte[] serialize(T item) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objStream = new ObjectOutputStream(byteStream)) {
            objStream.writeObject(item);
            return byteStream.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream objStream = new ObjectInputStream(byteStream)) {
            return (T) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static <T> List<T> deserializeList(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream objStream = new ObjectInputStream(byteStream)) {
            return (List<T>) objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

}