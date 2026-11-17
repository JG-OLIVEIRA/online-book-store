package com.dev.jorge.onlinebookstore.application.command.inventory;

public record CreateInventoryCommand (
        int quantity,
        boolean isAvailable
){ }
