package com.shefer.manager.query.queries;

import com.shefer.common.query.Query;
import com.shefer.common.query.QueryDao;
import com.shefer.manager.query.QueryDaoImpl;

public class GetMembershipInfoQuery implements Query {
    private final Long uid;

    public GetMembershipInfoQuery(Long uid) {
        this.uid = uid;
    }

    @Override
    public String process(QueryDao queryDao) throws Exception {
        return ((QueryDaoImpl)queryDao).getSubscriptionInfo(uid);
    }
}
