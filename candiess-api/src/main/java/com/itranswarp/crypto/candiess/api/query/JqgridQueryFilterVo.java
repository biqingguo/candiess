package com.itranswarp.crypto.candiess.api.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author heguojun
 */
public class JqgridQueryFilterVo {
    private String groupOp;
    private List<QueryFilter> rules = new ArrayList<>();

    public String getGroupOp() {
        return this.groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<QueryFilter> getRules() {
        return this.rules;
    }

    public void setRules(List<QueryFilter> rules) {
        for (int i = 0; i < rules.size(); i++) {
            if (StringUtils.isEmpty(rules.get(i).getData().toString())) {
                rules.remove(i);
            }
        }
        this.rules = rules;
    }

}
