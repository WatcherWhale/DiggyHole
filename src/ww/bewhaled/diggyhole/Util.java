package ww.bewhaled.diggyhole;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Util
{
    public static Object Deepclone(Object in)
    {
        try
        {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(in);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            return new ObjectInputStream(bais).readObject();
        }
        catch (Exception ex)
        {
            return in;
        }
    }
}
