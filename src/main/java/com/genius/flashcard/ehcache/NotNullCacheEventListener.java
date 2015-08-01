package com.genius.flashcard.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class NotNullCacheEventListener implements CacheEventListener {

	public static final CacheEventListener INSTANCE = new NotNullCacheEventListener();

	@Override
	public void notifyElementRemoved(final Ehcache cache, final Element element) throws CacheException {
	}

	@Override
	public void notifyElementPut(final Ehcache cache, final Element element) throws CacheException {
		removeIfNull(cache, element);
	}

	@Override
	public void notifyElementUpdated(final Ehcache cache, final Element element) throws CacheException {
		removeIfNull(cache, element);
	}

	private void removeIfNull(final Ehcache cache, final Element element) {
		if (element.getObjectValue() == null) {
			cache.remove(element.getKey());
		}
	}

	@Override
	public void notifyElementExpired(final Ehcache cache, final Element element) {
	}

	@Override
	public void notifyElementEvicted(final Ehcache cache, final Element element) {
	}

	@Override
	public void notifyRemoveAll(final Ehcache cache) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Singleton instance");
	}
}
