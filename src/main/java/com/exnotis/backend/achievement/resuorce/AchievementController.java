package com.exnotis.backend.achievement.resuorce;


import com.exnotis.backend.achievement.dto.request.epAddAchievement;
import com.exnotis.backend.achievement.model.Achievement;
import com.exnotis.backend.achievement.service.impl.AchievementServiceImpl;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievement")
public class AchievementController {

    private final AchievementServiceImpl service;
    public AchievementController(AchievementServiceImpl service){
        this.service=service;
    }

    @PostMapping("/add")
    public epResponseModel<Achievement> addAchievement(@RequestBody epAddAchievement data){
        return service.addAchievement(data);
    }

    @PostMapping("/delete")
    public epResponseModel<String> deleteAchievement(@RequestParam String id){
        return service.deleteAchievement(id);
    }

    @PostMapping("/edit")
    public epResponseModel<Achievement> editAchievement(@RequestParam String id, @RequestBody epAddAchievement data){
        return service.editAchievement(id, data);
    }


}
