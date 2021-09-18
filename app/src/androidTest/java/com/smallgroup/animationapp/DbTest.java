package com.smallgroup.animationapp;

import android.util.Log;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.smallgroup.animationapp.db.AppDatabase;
import com.smallgroup.animationapp.db.UserProject;
import com.smallgroup.animationapp.db.UserProjectDao;
import com.smallgroup.animationapp.domain.model.Frame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class DbTest {

    private AppDatabase db;
    private UserProjectDao dao;

    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                AppDatabase.class
        ).build();
        dao = db.userProjectDao();
    }


    @Test
    public void simpleTest() {
        UserProject project = new UserProject();
        List<Frame> frames = new ArrayList<>();
        Frame frame = new Frame();
        frame.setN(1);
        frames.add(frame);
        project.title = "First";
        project.frames = frames;

        dao.insert(project);
        List<UserProject> dbProject = dao.loadAll();

        Assert.assertEquals(1, dbProject.size());
        Assert.assertEquals("First", dbProject.get(0).title);
        Assert.assertEquals(1, dbProject.get(0).frames.size());
    }

    @After
    public void closeDb() {
        db.close();
    }



}

