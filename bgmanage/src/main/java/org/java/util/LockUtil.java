package org.java.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class LockUtil {
	public volatile static ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
	public volatile static ReadLock readLock = rw.readLock();
	public volatile static WriteLock writeLock = rw.writeLock();
}
