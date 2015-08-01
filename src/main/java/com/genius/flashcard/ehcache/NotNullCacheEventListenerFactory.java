package com.genius.flashcard.ehcache;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class NotNullCacheEventListenerFactory extends CacheEventListenerFactory {

	@Override
	public CacheEventListener createCacheEventListener(final Properties properties) {
		return NotNullCacheEventListener.INSTANCE;
	}
}