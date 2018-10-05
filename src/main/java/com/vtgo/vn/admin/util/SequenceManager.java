package com.vtgo.vn.admin.util;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Value;
import com.vtgo.vn.admin.aerospike.AerospikeFactory;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SequenceManager {
    private static final Logger logger = Logger.getLogger(SequenceManager.class);

    private static SequenceManager instance;

    public static synchronized SequenceManager getInstance() {
        if (instance == null) {
            instance = new SequenceManager();
        }
        return instance;
    }

    private Map<String, SequenceObject> mapSequenceMonthly = new ConcurrentHashMap<>();
    private Map<String, SequenceObject> mapSequence = new ConcurrentHashMap<>();


    public synchronized long getSequenceMonthly(String sequenceName) {
        sequenceName = sequenceName + "_Monthly";
        SequenceObject object = mapSequenceMonthly.get(sequenceName);
        if (object == null) {
            object = new SequenceObject();
            object.setSequenceKey(sequenceName);
            mapSequenceMonthly.put(sequenceName, object);
        }
        String monthYearString = DateTimeUtils.getInstance().getStringMonthYear(System.currentTimeMillis());
        if (DateTimeUtils.getInstance().isSameMonth(object.getTimeGetSequence(), System.currentTimeMillis())) {
            if (object.getCurrentSequence() >= object.getMaxSequence()) {
                long maxSequence = getAndResetSequenceBatch(sequenceName, 100, monthYearString);
                if (maxSequence <= 0) {
                    //return error if get sequence error
                    return -1;
                }
                long currentSequence = maxSequence - 100;
                object.setMaxSequence(maxSequence);
                object.setCurrentSequence(currentSequence);
                object.setTimeGetSequence(System.currentTimeMillis());
            }
            object.setCurrentSequence(object.getCurrentSequence() + 1);
            return object.getCurrentSequence();
        } else {
            long maxSequence = getAndResetSequenceBatch(sequenceName, 100, monthYearString);
            if (maxSequence <= 0) {
                //return error if get sequence error
                return -1;
            }
            long currentSequence = maxSequence - 100;
            object.setMaxSequence(maxSequence);
            object.setCurrentSequence(currentSequence);
            object.setTimeGetSequence(System.currentTimeMillis());
            object.setCurrentSequence(object.getCurrentSequence() + 1);
            return object.getCurrentSequence();
        }
    }

    public synchronized long getSequence(String sequenceName) {
        SequenceObject object = mapSequence.get(sequenceName);
        if (object == null) {
            object = new SequenceObject();
            object.setSequenceKey(sequenceName);
            mapSequence.put(sequenceName, object);
        }
        if (object.getCurrentSequence() >= object.getMaxSequence()) {
            long maxSequence = getSequenceBatch(sequenceName, 100);
            if (maxSequence <= 0) {
                //return error if get sequence error
                return -1;
            }
            long currentSequence = maxSequence - 100;
            object.setMaxSequence(maxSequence);
            object.setCurrentSequence(currentSequence);
            object.setTimeGetSequence(System.currentTimeMillis());
        }
        object.setCurrentSequence(object.getCurrentSequence() + 1);
        return object.getCurrentSequence();

    }


    public long getSequenceBatch(String sequenceName, long batchSize) {
        try {
            return (long) (AerospikeFactory.getInstance().execute(AerospikeFactory.getInstance().writePolicy, "vgo", "sequence"
                    , sequenceName, "sequence-utils", "getSequence", Value.get(batchSize)));
        } catch (AerospikeException e) {
            return -1;
        }
    }

    public long getAndResetSequenceBatch(String sequenceName, long batchSize, String currentStrTimeMonth) {
        try {
            return (long) (AerospikeFactory.getInstance().execute(AerospikeFactory.getInstance().writePolicy, "vgo", "sequence"
                    , sequenceName, "sequence-utils", "getAndResetSequence", Value.get(batchSize), Value.get(currentStrTimeMonth)));
        } catch (AerospikeException e) {
            return -1;
        }
    }


    public enum SEQUENCE_MODE {
        RESET_BY_DAY, NORMAL
    }

    private static class SequenceObject {
        String sequenceKey;
        long maxSequence = -1;
        long currentSequence = -1;
        SEQUENCE_MODE mode = SEQUENCE_MODE.RESET_BY_DAY;
        long timeGetSequence;

        public long getTimeGetSequence() {
            return timeGetSequence;
        }

        public void setTimeGetSequence(long timeGetSequence) {
            this.timeGetSequence = timeGetSequence;
        }

        public String getSequenceKey() {
            return sequenceKey;
        }

        public void setSequenceKey(String sequenceKey) {
            this.sequenceKey = sequenceKey;
        }

        public long getMaxSequence() {
            return maxSequence;
        }

        public void setMaxSequence(long maxSequence) {
            this.maxSequence = maxSequence;
        }

        public long getCurrentSequence() {
            return currentSequence;
        }

        public void setCurrentSequence(long currentSequence) {
            this.currentSequence = currentSequence;
        }
    }


}
