package com.genius.flashcard;

public class Context {
	public static ThreadLocal<Long> requestId = new ThreadLocal<Long>();
}
