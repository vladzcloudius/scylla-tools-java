/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.db.partitions;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.config.CFMetaData;

/**
 * Java 11 version for the partition-locks in {@link AtomicBTreePartition}.
 */
public abstract class AtomicBTreePartitionBase extends AbstractBTreePartition
{
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AtomicBTreePartitionBase.class);

    protected AtomicBTreePartitionBase(CFMetaData metadata, DecoratedKey partitionKey)
    {
        super(metadata, partitionKey);
    }

    // Replacement for Unsafe.monitorEnter/monitorExit.
    private final ReentrantLock lock = new ReentrantLock();

    protected final void acquireLock()
    {
        lock.lock();
    }

    protected final void releaseLock()
    {
        lock.unlock();
    }
}
