package com.smallgroup.animationapp.utils;

import static org.junit.Assert.*;

import com.smallgroup.animationapp.domain.model.Frame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConverterTest {

    public List<Frame> frames;

    @Before
    public void init() {
        frames = new ArrayList<>();
        Frame frame = new Frame();
        frame.setN(1);
        frames.add(frame);
    }


    @Test
    public void listToByte() {
        List<Frame> a = new ArrayList<>(frames);
        byte[] res = Converter.listToByte(a);
        a = Converter.byteToList(res);
        Assert.assertEquals(a.get(0).getN(), frames.get(0).getN());
    }

    @Test
    public void byteToList() {
    }
}