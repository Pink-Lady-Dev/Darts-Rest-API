package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("FakeDartThrowDao")
public class FakeDartThrowAccessService implements DartThrowDao {

    private static List<DartThrow> fakeDB = new ArrayList<>();

    @Override
    public int addDartThrow(User player, int throwNumber, int pie, boolean isDouble, boolean isTriple) {
        fakeDB.add(new DartThrow(player, throwNumber, pie, isDouble , isTriple));
        return 1;
    }

    @Override
    public List<DartThrow> getUncheckedDarts() {

        if (fakeDB.isEmpty()) {
            List<DartThrow> temp = new ArrayList<DartThrow>();
            temp.add(new DartThrow(new User(UUID.randomUUID(), "Jake"), 1, 20, false, false));
            return temp;
        }
        return fakeDB;
    }

    @Override
    public int removeDartThrow(String id) {

        for (DartThrow dt : fakeDB){
            if (dt.getId() == id) {
                fakeDB.remove(dt);
                break;
            }
        }
        return 1;
    }

    @Override
    public DartThrow popDartThrow() {

        return fakeDB.remove(0);
    }
}
