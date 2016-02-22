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

package com.flipkart.flux.domain;

/**
 * @understands An Event is the result of a com.flipkart.flux.api.Task's execution.
 * It is to be posted back to the Flux execution engine once a worker has executed the task
 */
public class Event {
    private String name;
    private String type;
    private EventStatus status;
    private String dataJson;
    private String toState;

    public enum EventStatus {
        pending,triggered;
    }
    public void addEventData(String dataJson) {
        this.dataJson = dataJson;
    }
}
