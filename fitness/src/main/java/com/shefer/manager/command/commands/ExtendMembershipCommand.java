package com.shefer.manager.command.commands;

import java.sql.Date;

import com.shefer.common.command.Command;
import com.shefer.common.command.CommandDao;
import com.shefer.manager.command.CommandDaoImpl;

public class ExtendMembershipCommand implements Command {
    private final Long uid;
    private final Date expiryDate;

    public ExtendMembershipCommand(Long uid, Date expiryDate) {
        this.uid = uid;
        this.expiryDate = expiryDate;
    }

    @Override
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl)commandDao).extendSubscription(uid, expiryDate);
    }
}
