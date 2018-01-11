package org.lf.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 内存缓存工具类
 * 
 * @author sunwill
 *
 * @param <K>
 * @param <V>
 */
public class CacheMap<K, V> extends AbstractMap<K, V> {
	/**
	 * 失效时间，5分钟,
	 */
	public static final long DEFAULT_TIMEOUT = 1000 * 60 * 5;// 单位毫秒
	private static CacheMap<Object, Object> defaultInstance;

	/**
	 * 获得缓存实例
	 * 
	 * @return
	 */
	public static synchronized final CacheMap<Object, Object> getCacheInstance() {
		if (defaultInstance == null) {
			defaultInstance = new CacheMap<Object, Object>(DEFAULT_TIMEOUT);
		}
		return defaultInstance;
	}

	/**
	 * 缓存Entry
	 * 
	 * @author sunwill
	 *
	 */
	private class CacheEntry implements Entry<K, V> {
		long time;
		V value;
		K key;

		CacheEntry(K key, V value) {
			super();
			this.value = value;
			this.key = key;
			this.time = System.currentTimeMillis();
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}
	}

	/**
	 * 缓存实现线程
	 * 
	 * @author sunwill
	 *
	 */
	private class ClearThread extends Thread {
		ClearThread() {
			setName("clear cache thread");
		}

		@Override
		public void run() {
			while (true) {
				try {
					long now = System.currentTimeMillis();
					Object[] keys = map.keySet().toArray();
					for (Object key : keys) {
						CacheEntry entry = map.get(key);
						if (now - entry.time >= cacheTimeout) {
							synchronized (map) {
								map.remove(key);
							}
						}
					}
					Thread.sleep(cacheTimeout);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private long cacheTimeout;
	private Map<K, CacheEntry> map = new HashMap<K, CacheEntry>();

	public CacheMap(long timeout) {
		this.cacheTimeout = timeout;
		new ClearThread().start();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return null;
	}

	/**
	 * 获得缓存数据
	 */
	@Override
	public V get(Object key) {
		CacheEntry entry = map.get(key);
		return entry == null ? null : entry.value;
	}

	/**
	 * 添加缓存数据
	 */
	@Override
	public V put(K key, V value) {
		CacheEntry entry = new CacheEntry(key, value);
		synchronized (map) {
			map.put(key, entry);
		}
		return value;
	}

}
