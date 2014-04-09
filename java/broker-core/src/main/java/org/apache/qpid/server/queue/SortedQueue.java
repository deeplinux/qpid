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
package org.apache.qpid.server.queue;

import org.apache.qpid.server.message.MessageInstance;
import org.apache.qpid.server.message.ServerMessage;
import org.apache.qpid.server.model.Queue;
import org.apache.qpid.server.util.Action;
import org.apache.qpid.server.util.MapValueConverter;
import org.apache.qpid.server.virtualhost.VirtualHostImpl;

import java.util.Map;

public class SortedQueue extends OutOfOrderQueue
{
    //Lock object to synchronize enqueue. Used instead of the object
    //monitor to prevent lock order issues with consumer sendLocks
    //and consumer updates in the super classes
    private final Object _sortedQueueLock = new Object();
    private final String _sortedPropertyName;

    protected SortedQueue(VirtualHostImpl virtualHost,
                          Map<String, Object> attributes,
                          QueueEntryListFactory factory)
    {
        super(virtualHost, attributes, factory);
        _sortedPropertyName = MapValueConverter.getStringAttribute(Queue.SORT_KEY,attributes);
    }


    protected SortedQueue(VirtualHostImpl virtualHost,
                          Map<String, Object> attributes)
    {
        this(virtualHost,
             attributes,
             new SortedQueueEntryListFactory(MapValueConverter.getStringAttribute(Queue.SORT_KEY, attributes)));
    }



    public String getSortedPropertyName()
    {
        return _sortedPropertyName;
    }

    @Override
    public void enqueue(final ServerMessage message,
                        final Action<? super MessageInstance> action)
    {
        synchronized (_sortedQueueLock)
        {
            super.enqueue(message, action);
        }
    }
}
