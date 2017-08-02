/*
(c) rodney@daftdroid.com 2017

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/

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
