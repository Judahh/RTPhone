package util;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Java NIO template Server.
 * 
 * @author Ada
 * 
 */
public abstract class Server implements Runnable {
	private String name;
	private int port;

	private final ServerSocketChannel serverSocketChannel;
	private final ServerSocket serverSocket;
	private final Selector selector;
	private boolean running;
	private boolean paused;

	private final Thread thread;

	public Server(int port, String name) throws IOException {
		this.running = false;
		this.paused = false;
		this.port = port;
		this.name = name;

		System.out.println(name + " initializing.");

		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocket = serverSocketChannel.socket();
		this.selector = Selector.open();

		this.serverSocket.bind(new InetSocketAddress(this.port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println(name + " listening on PORT " + port);

		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}

	public Server(int port) throws IOException {
		this.running = false;
		this.paused = false;
		this.port = port;
		this.name = "Server";

		System.out.println(name + " initializing.");

		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocket = serverSocketChannel.socket();
		this.selector = Selector.open();

		this.serverSocket.bind(new InetSocketAddress(this.port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println(name + " listening on PORT " + port);

		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public Server(String name) throws IOException {
		this.running = false;
		this.paused = false;
		this.port = 6789;
		this.name = name;

		System.out.println(name + " initializing.");

		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocket = serverSocketChannel.socket();
		this.selector = Selector.open();

		this.serverSocket.bind(new InetSocketAddress(this.port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println(name + " listening on PORT " + port);

		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public Server() throws IOException {
		this.running = false;
		this.paused = false;
		this.port = 6789;
		this.name = "Server";

		System.out.println(name + " initializing.");

		this.serverSocketChannel = ServerSocketChannel.open();
		this.serverSocket = serverSocketChannel.socket();
		this.selector = Selector.open();

		this.serverSocket.bind(new InetSocketAddress(this.port));
		this.serverSocketChannel.configureBlocking(false);
		this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println(name + " listening on PORT " + port);

		this.running = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	public void run() {
		while (running) {
			int updatedKeysCount;

			try {
				updatedKeysCount = selector.select(); // Blocking

				if (updatedKeysCount <= 0) {
					// Nothing to update
					continue;
				}

				// Iterator over the selected (updated) keys
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();

					if (key.isAcceptable()) {
						accept(key.channel());
					}
					if (key.isReadable()) {
						read(key.channel());
					}
					// Remove key from Selected list
					iterator.remove();
				}
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		System.out.println(name + " closing.");
	}

	/**
	 * A new Connection has been Accepted. Write your own implementation.
	 * 
	 * @param selectableChannel
	 */
	protected void accept(SelectableChannel selectableChannel) {
		
	}

	/**
	 * New data is available for reading. Write your own implementation.
	 * 
	 * @param selectableChannel
	 */
	protected void read(SelectableChannel selectableChannel) {
		
	}

	/**
	 * Closes the Channel.
	 * 
	 * @param selectableChannel
	 * @throws IOException
	 */
	protected void close(SelectableChannel selectableChannel)
			throws IOException {
		selectableChannel.close();
	}

	protected void start() {
		if (!running) {
			running = true;
			thread.start();
			return;
		}
		thread.notify();
		paused = false;
		System.out.println(name + " resumed.");
	}

	protected void pause() {
		paused = true;
		try {
			thread.interrupt();
			System.out.println(name + " paused.");
			thread.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void stop() {
		running = false;
		thread.interrupt();
	}
}