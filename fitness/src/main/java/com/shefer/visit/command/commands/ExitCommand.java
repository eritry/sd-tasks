package com.shefer.visit.command.commands;

import com.shefer.common.command.Command;
import com.shefer.common.command.CommandDao;
import com.shefer.visit.command.CommandDaoImpl;

public class ExitCommand implements Command {
    Long uid;
    Long timestamp;

    public ExitCommand(Long uid, Long timestamp) {
        this.uid = uid;
        this.timestamp = timestamp;
    }


    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl)commandDao).exit(uid, timestamp);
    }
}
