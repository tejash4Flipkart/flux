/*
 * Copyright 2012-2016, the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flipkart.flux.dao;

import com.flipkart.flux.api.EventDefinition;
import com.flipkart.flux.api.StateDefinition;
import com.flipkart.flux.api.StateMachineDefinition;
import com.flipkart.flux.dao.iface.StateMachinesDAO;
import com.flipkart.flux.dao.iface.StatesDAO;
import com.flipkart.flux.domain.State;
import com.flipkart.flux.domain.StateMachine;
import com.flipkart.flux.representation.DomainTypeCreator;
import com.flipkart.flux.rules.DbClearRule;
import com.flipkart.flux.runner.GuiceJunit4Runner;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * <code>StateMachinesDAOTest</code> class tests the functionality of {@link StateMachinesDAO} using JUnit tests.
 * @author shyam.akirala
 * @author kartik.bommepally
 */
@RunWith(GuiceJunit4Runner.class)
public class StateMachinesDAOTest {

    @Inject
    @ClassRule
    public static DbClearRule dbClearRule;

    @Inject
    StateMachinesDAO stateMachinesDAO;

    @Inject
    StatesDAO statesDAO;

    @Inject
    DomainTypeCreator domainTypeCreator;

    @Before
    public void setup() {}

    @Test @Transactional
    public void createSMTest() {
        DummyTask task = new DummyTask();
        DummyOnEntryHook onEntryHook = new DummyOnEntryHook();
        DummyOnExitHook onExitHook = new DummyOnExitHook();
        State<DummyEventData> state1 = statesDAO.create(new State<>(2L, "state1", "desc1", onEntryHook, task, onExitHook, 3L, 60L));
        State<DummyEventData> state2 = statesDAO.create(new State<>(2L, "state2", "desc2", null, null, null, 2L, 50L));
        Set<State<DummyEventData>> states = new HashSet<>();
        states.add(state1);
        states.add(state2);
        StateMachine<DummyEventData> stateMachine = new StateMachine<>(2L, "SM_name", "SM_desc", states);
        String savedSMId = stateMachinesDAO.create(stateMachine).getId();

        StateMachine stateMachine1 = stateMachinesDAO.findById(savedSMId);
        Assert.assertNotNull(stateMachine1);
    }

    /** Test creation of State machine from state machine definition */
    @Test @SuppressWarnings("unchecked")
    public void createSMDTest() {
        EventDefinition eventDefinition1 = new EventDefinition("event1");
        EventDefinition eventDefinition2 = new EventDefinition("event2");
        Set eventSet1 = new HashSet<>();
        eventSet1.add(eventDefinition1);
        Set eventSet2 = new HashSet<>();
        eventSet2.add(eventDefinition2);
        StateDefinition stateDefinition1 = new StateDefinition(1L, "state1", "desc1", new DummyOnEntryHook(), new DummyTask(), new DummyOnExitHook(), 0L, 60L, eventSet1);
        StateDefinition stateDefinition2 = new StateDefinition(1L, "state2", "desc2", new DummyOnEntryHook(), new DummyTask(), new DummyOnExitHook(), 0L, 60L, eventSet2);
        Set stateSet = new HashSet<>();
        stateSet.add(stateDefinition1);
        stateSet.add(stateDefinition2);
        StateMachineDefinition stateMachineDefinition = new StateMachineDefinition(1L, "SMD_name", "SMD_desc", stateSet);

        String stateMachineId = domainTypeCreator.createStateMachine(stateMachineDefinition).getId();

        StateMachine stateMachine = stateMachinesDAO.findById(stateMachineId);
        Assert.assertNotNull(stateMachine);
    }
}