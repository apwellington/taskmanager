package com.wap.taskmanager.util;

import lombok.Getter;

@Getter
public enum TaskStatus {
    PENDING, IN_PROGRESS, DONE, CANCELLED, OTHER;
}
