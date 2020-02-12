package com.pinkladydev.DartsRestAPI.dao;

import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.model.User;

import java.util.List;

public interface DartThrowDao {

    int addDartThrow(User player, int throwNumber, int pie, boolean isDouble, boolean isTriple);

    List<DartThrow> getUncheckedDarts();

    int removeDartThrow(String id);

    DartThrow popDartThrow();

}
