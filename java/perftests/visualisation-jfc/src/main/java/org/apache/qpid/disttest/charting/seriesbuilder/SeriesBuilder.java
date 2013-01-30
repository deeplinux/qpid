/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.qpid.disttest.charting.seriesbuilder;

import java.util.List;

import org.apache.qpid.disttest.charting.definition.SeriesDefinition;

public interface SeriesBuilder
{
    /**
     * Uses the supplied {@link SeriesDefinition}s to read the series data
     * and pass it to the callback set up in {@link #setSeriesBuilderCallback(SeriesBuilderCallback)}.
     */
    void build(List<SeriesDefinition> seriesDefinitions);

    /**
     * Stores the supplied callback so it can be used in {@link #build(List)}.
     */
    void setSeriesBuilderCallback(SeriesBuilderCallback seriesBuilderCallback);

}