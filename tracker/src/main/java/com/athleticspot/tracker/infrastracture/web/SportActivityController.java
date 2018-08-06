package com.athleticspot.tracker.infrastracture.web;

import com.athleticspot.tracker.domain.model.SportActivity;
import com.athleticspot.tracker.infrastracture.web.dto.in.SportActivityInDto;
import com.athleticspot.tracker.infrastracture.web.dto.out.SportActivityOutDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tomasz Kasprzycki
 */
@RestController
@RequestMapping(value = SportTrackersApiUrl.SPORT_ACTIVITY_URL)
public class SportActivityController {

    @GetMapping
    public List<SportActivityOutDto> getUserSportActivities(){
        return null;
    }

    @PostMapping
    public void createSportActivity(@RequestBody SportActivityInDto sportActivityInDto){

    }

    @PutMapping
    public void updateSportActivity(){

    }

    @DeleteMapping
    public void deleteSportActivity(){

    }
}