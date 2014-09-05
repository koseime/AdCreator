package com.kosei.dropwizard.adcreator.core;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lanceriedel on 9/4/14.
 */
public class CreatedImageCache {

    private static final int CACHE_SIZE = 20;

    Map<String, ByteBuffer> cache = Collections.synchronizedMap(new LruCache<String, ByteBuffer>(CACHE_SIZE));


    public ByteBuffer  get(String key) {
        return cache.get(key);
    }

    public void put(String key, ByteBuffer value) {
        cache.put(key, value);
    }

    private class LruCache<A, B> extends LinkedHashMap<A, B> {
        private final int maxEntries;

        public LruCache(final int maxEntries) {
            super(maxEntries + 1, 1.0f, true);
            this.maxEntries = maxEntries;
        }

        /**
         * Returns <tt>true</tt> if this <code>LruCache</code> has more entries than the maximum specified when it was
         * created.
         *
         * <p>
         * This method <em>does not</em> modify the underlying <code>Map</code>; it relies on the implementation of
         * <code>LinkedHashMap</code> to do that, but that behavior is documented in the JavaDoc for
         * <code>LinkedHashMap</code>.
         * </p>
         *
         * @param eldest
         *            the <code>Entry</code> in question; this implementation doesn't care what it is, since the
         *            implementation is only dependent on the size of the cache
         * @return <tt>true</tt> if the oldest
         * @see java.util.LinkedHashMap#removeEldestEntry(Map.Entry)
         */
        @Override
        protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
            return super.size() > maxEntries;
        }
    }

}
