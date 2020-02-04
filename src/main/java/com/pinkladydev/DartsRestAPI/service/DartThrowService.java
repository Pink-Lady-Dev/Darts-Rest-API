package com.pinkladydev.DartsRestAPI.service;

import com.pinkladydev.DartsRestAPI.dao.DartThrowDao;
import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DartThrowService {

    private final DartThrowDao dartThrowDao;

    @Autowired
    public DartThrowService(@Qualifier("FakeDartThrowDao") DartThrowDao dartThrowDao) {
        this.dartThrowDao = dartThrowDao;
    }

    public int addDartThrow(DartThrow dartThrow){
        this.dartThrowDao.addDartThrow(dartThrow.getPlayer(), dartThrow.getThrowNumber(), dartThrow.getPie(), dartThrow.isDouble(), dartThrow.isTriple());
        return 1;
    }

    public int addDartThrow(User player, int throwNumber, int pie, boolean isDouble, boolean isTriple){

        this.dartThrowDao.addDartThrow(player, throwNumber, pie, isDouble, isTriple);
        return 1;
    }

    public List<DartThrow> getUncheckedDarts(){
        return  this.dartThrowDao.getUncheckedDarts();
    }

    public void removeDartThrow(String id) {
        this.dartThrowDao.removeDartThrow(id);
    }

    public DartThrow popDartThrow(){
        return this.dartThrowDao.popDartThrow();
    }
}
