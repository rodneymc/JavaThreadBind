package com.daftdroid.droidlib.threadbind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test
{
	Test(int port)
	{
		this.PORT = port;
	}
	final int PORT;
	
	abstract class TestThread extends Thread
	{
		//private Throwable threadEx;
		final String ourip;

		protected TestThread (String ip)
		{
			this.ourip = ip;
			
			// Run as low priority, so all the test threads start together
			// when mainthread exits. Maximizes the probability of problems
			setPriority(Thread.MIN_PRIORITY);
		}
		protected void reportException(Throwable t)
		{
			t.printStackTrace();
			//threadEx = t;
			// Any exception is a test failure of some sort
			// whether we explictly threw it or not. Return error code
			// to the system
			System.exit(-1);
		}
		@Override
		public final void run()
		{
			ThreadBind.setThreadBindVal(ourip);
			try
			{
				runTest();
			}
			catch (Throwable t)
			{
				// We do this because run can't throw
				// due to the fact Thread.run doeesn't throw.
				reportException(t);
			}
		}
		abstract void runTest() throws Throwable;

		void exchangeDetails(Socket s) throws Exception
		{
			try (PrintWriter writer = new PrintWriter(s.getOutputStream());
				 InputStreamReader ins = new InputStreamReader(s.getInputStream());
				 BufferedReader reader = new BufferedReader(ins))
			{
System.out.println("Send "+ourip);
				writer.println(ourip);
				writer.flush();
				String theirReportedIp = reader.readLine();
System.out.println("Recvd "+theirReportedIp);
				String theirActualIp = s.getInetAddress().toString().substring(1);
				
				if (!theirReportedIp.equals(theirActualIp))
					throw new Exception ("Test failed! " +theirReportedIp+" " + theirActualIp);
			}
		}
	}
	
	class ServerThread extends TestThread
	{
		public ServerThread(String ip)
		{
			super(ip);
		}
		private ServerSocket bindSocket;
		
		public void runTest() throws Exception
		{
			bindSocket = new ServerSocket(PORT);
			synchronized(this)
			{
				isListening = true;
				notify();
			}
			
			Socket s = bindSocket.accept();
			exchangeDetails(s);
			s.close();
			bindSocket.close();
		}
		volatile boolean isListening;
		public synchronized void waitUntilListening() throws InterruptedException
		{
			while (!isListening)
				wait();
		}
	}
	class ClientThread extends TestThread
	{
		private final String connectTo;
		public ClientThread(String ip, String connectTo)
		{
			super(ip);
			this.connectTo = connectTo;
		}
		
		public void runTest() throws Exception
		{
			Socket s = new Socket(connectTo, PORT);
			exchangeDetails(s);
			s.close();
		}
	}
	public static void main(String args[]) throws Exception
	{
		int port = Integer.parseInt(args[0]);
		new Test(port).run();
	}
	public void run() throws Exception
	{
		List<ClientThread> clients = new ArrayList<ClientThread>(256);
		List<ServerThread> servers = new ArrayList<ServerThread>(256);
		// TODO: you can improve this if you like....
		for (int i = 1; i < 254; i++)
		{
			String srvAddr = "127.0.1."+i;
			String clientAddr = "127.0.2."+i;
			
			clients.add(new ClientThread(clientAddr, srvAddr));
			servers.add(new ServerThread(srvAddr));
		}
		Collections.shuffle(clients);
		Collections.shuffle(servers);

		for (ServerThread t: servers)
		{
			t.start();
			t.waitUntilListening();
		}
		
		for (Thread t: clients)
		{
			t.start();
		}
	}
}
