package com.smallgroup.animationapp.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import com.smallgroup.animationapp.domain.model.Frame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public static byte[] listToByte(List<Frame> list) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(list);
            byte[] bytes = bos.toByteArray();
            System.out.println("complete to byte");
            return bytes;
        }
        catch (IOException e) {
            System.out.println("IO 1");
            return new byte[]{};
        }


    }

    @TypeConverter
    public static List<Frame> byteToList(byte[] obj) {

        try {
            ByteArrayInputStream bos = new ByteArrayInputStream(obj);
            ObjectInputStream oos = new ObjectInputStream(bos);
            List<Frame> list = (List<Frame>) oos.readObject();
//            Log.d("Status", "Success");
            System.out.println("complete to list");
            return list;
        }
        catch (IOException e) {
//            Log.d("Status", "IO Ex");
            System.out.println("IO");
            return new ArrayList<Frame>();
        }
        catch (ClassNotFoundException e) {
            System.out.println("ex");
//            Log.d("Status", "CNFE Ex");
            return new ArrayList<Frame>();
        }


    }

}
