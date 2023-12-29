package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.dao.StageRepository;
import com.pbl4.monolingo.service.LearnService;
import com.pbl4.monolingo.utility.dto.ResponseMessage;
import com.pbl4.monolingo.utility.dto.StageLevelState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameRestController {

    private final LearnService learnService;

    @Autowired
    public GameRestController(LearnService learnService) {
        this.learnService = learnService;
    }

    @GetMapping("/stage/all/{accountId}")
    public List<StageLevelState> getAccountStages(@PathVariable int accountId) {
        return learnService.getAccountStages(accountId);
    }

    @GetMapping("/stage/fulfilled/{stageId}/{levelId}/{accountId}")
    public ResponseEntity<ResponseMessage> levelFulfilled(@PathVariable int stageId,
                                                          @PathVariable int levelId,
                                                          @PathVariable int accountId) {
        learnService.finishLevel(accountId, stageId, levelId);

        return ResponseEntity.ok(new ResponseMessage("Updated Successfully"));
    }

}
