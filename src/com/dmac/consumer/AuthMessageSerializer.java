package com.dmac.consumer;

import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class AuthMessageSerializer implements Serializer<AuthMessage> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, AuthMessage authMessage) {

        byte[] b = null;

        try {


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(authMessage);
            oos.close();

            b = baos.toByteArray();


        } catch (IOException e) {
            return new byte[0];
        }

        return b;
    }

    @Override
    public void close() {

    }
}

class AuthMessage {

    String message = "ENCRYPTED_MESSAGE";

}

