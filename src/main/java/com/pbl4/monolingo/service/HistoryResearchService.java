package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.HistoryResearch;
import com.pbl4.monolingo.entity.Notebook;

import java.util.List;

public interface HistoryResearchService {

    void deleteResearch(int accountId, String word);

    void deleteFirstResearch(int accountId);

    void addResearch(int accountId, String word);

    List<HistoryResearch> getAllByAccountId(Integer accountId);

}
