package com.bmy.wx.drools;

import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    @Autowired
    private KieBase kieBase;

    public void rule(Object object, String ruleName){
        KieSession kieSession = kieBase.newKieSession();
        kieSession.insert(object);
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter(ruleName));
        kieSession.dispose();
    }
}

