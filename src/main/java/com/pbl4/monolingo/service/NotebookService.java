package com.pbl4.monolingo.service;

import com.pbl4.monolingo.entity.Notebook;

import java.util.List;

public interface NotebookService {

    List<Notebook> getAllNotebooksByAccountId(Integer accountId);

}
