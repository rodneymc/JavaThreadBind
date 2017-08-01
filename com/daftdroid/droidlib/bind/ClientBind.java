package com.daftdroid.droidlib.bind;

import java.util.WeakHashMap;

public class ClientBind
{
	private static WeakHashMap<Thread, String> bindStrings = new WeakHashMap<Thread, String>();

	/*
	 * When this class loads, load also the native library.
	 */
	static
	{
		System.loadLibrary("bindsetter");
	}

	/*
	 * Initialises the native code.
	 */
	public static native void initialiseBindSetter();

	/*
	 * Get the IP address string for the current thread.
	 * @return The IP address as a String. Can be null if none is set
	 * 
	 * Note as well as being part of the API, this method is also called
	 * by the native code.
	 */
	public static synchronized String getThreadBindVal()
	{
		return bindStrings.get(Thread.currentThread());
	}
	
	/*
	 * Set the IP address string for the current thread.
	 * @param String s - the IP address as a string
	 * Note: No sanity check is made on the String content.
	 */
	public static synchronized void setThreadBindVal(String s)
	{
		bindStrings.put(Thread.currentThread(), s);
	}
}
