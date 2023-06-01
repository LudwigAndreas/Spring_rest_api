package ru.kpfu.itis.lifeTrack.exception.Workflow;

import ru.kpfu.itis.lifeTrack.exception.NotFoundException;

public class WorkflowNotFoundException extends NotFoundException {
    public WorkflowNotFoundException(String message) {
        super(message);
    }
}
