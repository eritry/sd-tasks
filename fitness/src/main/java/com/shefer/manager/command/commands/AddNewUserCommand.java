package com.shefer.manager.command.commands;

import com.shefer.common.command.Command;
import com.shefer.common.command.CommandDao;
import com.shefer.manager.command.CommandDaoImpl;

public class AddNewUserCommand implements Command {
    public String process(CommandDao commandDao) throws Exception {
        return ((CommandDaoImpl) commandDao).addNewUser();
    }
}
