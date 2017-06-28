package game;

import java.io.*;
import java.util.LinkedList;

public class Konfiguracja {
	
	private static Konfiguracja instance = null;

	public static Konfiguracja getInstance() {
		if (instance == null) instance = new Konfiguracja();
		return instance;
	}	

	private String host = "localhost";

	private int port = 4545;
	
	private String fileName = "statki.ini";

	public Konfiguracja() {		
		odczyt();
	}

	private void odczyt() {
		LinkedList<String> lines = new LinkedList<String>();

		try {
			// odczytanie wszystkich wierszy z pliku do listy
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = reader.readLine();
				// koniec wierszy do odczytywania
				if (line == null) {
					reader.close();
					break;
				}

				// dodawanie wszystkich wierszy poza komentarzami
				if (!line.startsWith("#")) {
					lines.add(line);
				}
			}
		} catch (IOException e) {
		}

		while (!lines.isEmpty()) {
			String s = (String) lines.getFirst();
			lines.removeFirst();
			int idx = s.indexOf('=');
			if (idx != -1) {
				String key = s.substring(0, idx).trim();
				String value = s.substring(idx + 1).trim();

				if (key.compareToIgnoreCase("host") == 0) {
					setHost(value);
				} else if (key.compareToIgnoreCase("port") == 0) {
					try {
						int x = Integer.parseInt(value);
						setPort(x);
					} catch (NumberFormatException ex) {
					}
				}
			}
		}
	}

	public void zapisz() {
		
		try {
			PrintWriter writer;
			writer = new PrintWriter(new BufferedWriter(
					new FileWriter(fileName)));
			writer.print("host=");
			writer.println(getHost());
			writer.print("port=");
			writer.println(getPort());

			writer.close();
		} catch (IOException e) {
		}
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}
}
