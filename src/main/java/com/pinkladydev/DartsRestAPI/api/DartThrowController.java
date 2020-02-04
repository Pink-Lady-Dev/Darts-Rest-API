package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.model.DartThrow;
import com.pinkladydev.DartsRestAPI.service.DartThrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/darts")
@RestController
@CrossOrigin(origins = "*")
public class DartThrowController {

    private final DartThrowService dartThrowService;

    @Autowired
    public DartThrowController(DartThrowService dartThrowService) {
        this.dartThrowService = dartThrowService;
    }

    @PostMapping
    //@RequestBody User player,
    //, @RequestBody boolean isDouble, @RequestBody boolean isTriple
    public void addDartThrow (@RequestBody DartThrow dartThrow) {
        this.dartThrowService.addDartThrow(dartThrow);
    }

    @GetMapping
    public DartThrow getUncheckedDarts() {

        return this.dartThrowService.popDartThrow();
        /*List<DartThrow> temp = new ArrayList<>();
        for (DartThrow dt: this.dartThrowService.getUncheckedDarts()){
            temp.add(new DartThrow(dt));
            this.dartThrowService.removeDartThrow(dt.getId());
        }
        return temp;//this.dartThrowService.getUncheckedDarts();*/
    }
}
