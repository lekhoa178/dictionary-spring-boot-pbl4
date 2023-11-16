package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Notebook;

import java.util.List;

public interface NotebookService {

    Boolean checkIsExsitInNotebook(int accountId, String word);

    void deleteNotebook(int accountId, String word);

    void addNotebook(int accountId, String word);

    List<Notebook> findAll();

    List<Notebook> getAllNotebooksByAccountId(Integer accountId);

}
