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
 *
 */

package com.flipkart.flux.client.runtime;

import java.lang.reflect.Method;

/**
 * Used to denote an illegal method signature
 *
 * @author yogesh.nachnani
 */
public class IllegalSignatureException extends RuntimeException {
    public IllegalSignatureException(String methodIdentifier, String reason) {
        super("Method " + methodIdentifier + " is invalid. " + reason);
    }
}