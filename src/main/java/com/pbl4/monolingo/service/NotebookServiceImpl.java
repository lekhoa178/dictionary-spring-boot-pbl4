package com.pbl4.monolingo.service;

import com.pbl4.monolingo.dao.NotebookRepository;
import com.pbl4.monolingo.entity.Notebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotebookServiceImpl implements NotebookService {

    private final NotebookRepository notebookRepository;

    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    @Override
    public List<Notebook> getAllNotebooksByAccountId(Integer accountId) {
        return null;
    }
}
