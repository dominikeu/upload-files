package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import network.Client;
import network.GameEvent;
import network.Server;
import javax.swing.SwingConstants;

public class Statki extends JFrame {

	private static Statki instance = null;

	public static Statki getInstance() {
		return instance;
	}

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField czatWyslij = null;

	private JScrollPane jScrollPane1 = null;

	private JTextArea czatOdbierz = null;

	private JScrollPane jScrollPane2 = null;

	private JTextArea status = null;

	private JButton nowaGra = null;

	private JRadioButton klient = null;

	private JRadioButton serwer = null;

	private JTextField adres = null;

	private JButton polacz = null;

	private JButton start = null;

	private Plansza planszaGracza = null;

	private Plansza planszaPrzeciwnika = null;

	private JButton losuj = null;

	private Server server = null;

	private Client client = null;

	private boolean token = false;

	private final int liczbaStatkow = 12;

	private int trafioneStatkiGracza = 0;

	private int trafioneStatkiPrzeciwnika = 0;

	private JLabel statkiGraczaPodsumowanie = null;

	private JLabel statkiPrzeciwnikaPodsumowanie = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel4 = null;

	private boolean clientStarted = false;
	
	private int port;

	public boolean hasToken() {
		return token;
	}

	public void setToken(boolean b) {
		token = b;
	}

	private String getID() {
		return (serwer.isSelected()) ? "ID_SERVER" : "ID_CLIENT";
	}

	public boolean sendMessage(GameEvent ge) {
		if (client != null && client.isAlive()) {
			ge.setPlayerId(getID());
			client.sendMessage(ge);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(542, 12, 67, 16));
			jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel4.setText("zatopione:");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(191, 10, 73, 16));
			jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel5.setText("zatopione:");
			statkiPrzeciwnikaPodsumowanie = new JLabel();
			statkiPrzeciwnikaPodsumowanie.setFont(new Font("Dialog", Font.BOLD,
					18));
			statkiPrzeciwnikaPodsumowanie.setLocation(new Point(610, 7));
			statkiPrzeciwnikaPodsumowanie.setSize(new Dimension(48, 23));
			statkiPrzeciwnikaPodsumowanie
					.setHorizontalAlignment(SwingConstants.RIGHT);
			statkiPrzeciwnikaPodsumowanie.setText("0/12");
			statkiGraczaPodsumowanie = new JLabel();
			statkiGraczaPodsumowanie.setFont(new Font("Dialog", Font.BOLD, 18));
			statkiGraczaPodsumowanie
					.setHorizontalAlignment(SwingConstants.RIGHT);
			statkiGraczaPodsumowanie.setSize(new Dimension(52, 23));
			statkiGraczaPodsumowanie.setLocation(new Point(263, 7));
			statkiGraczaPodsumowanie.setText("0/12");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(357, 359, 72, 16));
			jLabel3.setText("Czat:");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(355, 12, 121, 16));
			jLabel2.setText("Statki przeciwnika:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(12, 10, 117, 16));
			jLabel1.setText("Twoje statki:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCzatWyslij(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(getJScrollPane2(), null);
			jContentPane.add(getNowaGra(), null);
			jContentPane.add(getKlient(), null);
			jContentPane.add(getSerwer(), null);
			jContentPane.add(getAdres(), null);
			jContentPane.add(getPolacz(), null);
			jContentPane.add(getStart(), null);
			jContentPane.add(getPlanszaGracza(), null);
			jContentPane.add(getPlanszaPrzeciwnika(), null);
			jContentPane.add(getLosuj(), null);
			jContentPane.add(statkiGraczaPodsumowanie, null);
			jContentPane.add(statkiPrzeciwnikaPodsumowanie, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel4, null);
			ButtonGroup grupa = new ButtonGroup();
			grupa.add(klient);
			grupa.add(serwer);
			adres.setVisible(false);
			polacz.setVisible(false);
		}
		return jContentPane;
	}

	/**
	 * This method initializes czatWyslij
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCzatWyslij() {
		if (czatWyslij == null) {
			czatWyslij = new JTextField();
			czatWyslij.setLocation(new Point(356, 383));
			czatWyslij.setEnabled(false);
			czatWyslij.setSize(new Dimension(300, 20));
			czatWyslij.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!czatWyslij.getText().trim().isEmpty()) {
						GameEvent ge = new GameEvent(GameEvent.C_CHAT_MSG);
						ge.setMessage(czatWyslij.getText().trim());
						sendMessage(ge);
						czatWyslij.setText("");
					}
				}
			});
		}
		return czatWyslij;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setLocation(new Point(356, 412));
			jScrollPane1.setViewportView(getCzatOdbierz());
			jScrollPane1.setSize(new Dimension(300, 83));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes czatOdbierz
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getCzatOdbierz() {
		if (czatOdbierz == null) {
			czatOdbierz = new JTextArea();
			czatOdbierz.setEnabled(true);
			czatOdbierz.setLineWrap(true);
			czatOdbierz.setWrapStyleWord(true);
			czatOdbierz.setEditable(false);
		}
		return czatOdbierz;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setLocation(new Point(20, 386));
			jScrollPane2.setEnabled(true);
			jScrollPane2.setViewportView(getStatus());
			jScrollPane2.setSize(new Dimension(300, 70));
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes status
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getStatus() {
		if (status == null) {
			status = new JTextArea();
			status.setEnabled(true);
			status.setEditable(false);
			status.setLineWrap(true);
			status.setWrapStyleWord(true);
			status.setFont(new Font("Dialog", Font.BOLD, 12));
			zmienStatus(
					"Witaj w grze Statki\nAby rozpocz¹æ grê wybierz odpowiedni¹ opcjê: serwer lub klient, uzupe³nij wymagane dane, a nastêpnie naciœnij przycisk Start lub Po³¹cz.",
					RodzajWiadomosci.WIADOMOSC_NEUTRALNA);

		}
		return status;
	}

	/**
	 * This method initializes nowaGra
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getNowaGra() {
		if (nowaGra == null) {
			nowaGra = new JButton();
			nowaGra.setLocation(new Point(20, 351));
			nowaGra.setEnabled(false);
			nowaGra.setText("Rozpocznij gr\u0119");
			nowaGra.setSize(new Dimension(131, 23));
			nowaGra.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (losuj.isEnabled()) {
						nowaGra.setEnabled(false);
						losuj.setEnabled(false);
						GameEvent ge = new GameEvent(GameEvent.C_JOIN_GAME);
						sendMessage(ge);
					} else {
						resetujPlansze();
						losuj.setEnabled(true);
						nowaGra.setText("Rozpocznij grê");
						zmienStatus(
								"Ustaw swoje statki a nastêpnie naciœnij przycisk \"Rozpocznij grê\"",
								RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
					}
				}
			});
		}
		return nowaGra;
	}

	/**
	 * This method initializes klient
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getKlient() {
		if (klient == null) {
			klient = new JRadioButton();
			klient.setLocation(new Point(20, 460));
			klient.setText("klient");
			klient.setSize(new Dimension(71, 21));
			klient.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setVisible(false);
					polacz.setVisible(true);
					adres.setVisible(true);
				}
			});
		}
		return klient;
	}

	/**
	 * This method initializes serwer
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getSerwer() {
		if (serwer == null) {
			serwer = new JRadioButton();
			serwer.setLocation(new Point(20, 481));
			serwer.setSelected(true);
			serwer.setText("serwer");
			serwer.setSize(new Dimension(72, 21));
			serwer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setVisible(true);
					polacz.setVisible(false);
					adres.setVisible(false);
				}
			});
		}
		return serwer;
	}

	/**
	 * This method initializes adres
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getAdres() {
		if (adres == null) {
			adres = new JTextField();
			adres.setLocation(new Point(95, 472));
			adres.setSize(new Dimension(132, 20));
			adres.setText("localhost");
			adres.setVisible(true);
		}
		return adres;
	}

	/**
	 * This method initializes polacz
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPolacz() {
		if (polacz == null) {
			polacz = new JButton();
			polacz.setBounds(new Rectangle(230, 471, 87, 23));
			polacz.setText("Po\u0142\u0105cz");
			polacz.setVisible(true);
			polacz.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					polacz.setEnabled(false);
					adres.setEnabled(false);
					if (client == null || !client.isAlive()) {
						serwer.setEnabled(false);
						klient.setEnabled(false);

						String host = adres.getText().trim();
						//int port = 4545;

						client = new Client(getID(), host, port);

						if (client.start()) {
							GameEvent ge = new GameEvent(GameEvent.C_LOGIN);
							sendMessage(ge);

							zmienStatus(
									"Pomyœlnie po³¹czono siê z serwerem!\nUstaw swoje statki a nastêpnie naciœnij przycisk \"Rozpocznij grê\"",
									RodzajWiadomosci.WIADOMOSC_POZYTYWNA);

							czatWyslij.setEnabled(true);
							polacz.setText("Roz³¹cz");
							clientStarted = true;
						} else {
							clientStarted = false;
							serwer.setEnabled(true);
							klient.setEnabled(true);
							adres.setEnabled(true);

							zmienStatus("Nie uda³o sie po³¹czyæ z serwerem!\n",
									RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
						}
					} else {
						zmienStatus("Po³¹czenie przerwane!\n",
								RodzajWiadomosci.WIADOMOSC_NEGATYWNA);

						if (client != null) {
							client.stop();
							clientStarted = false;
						}
						client = null;
						ustawOdNowa();
					}
					polacz.setEnabled(true);
				}
			});
		}
		return polacz;
	}

	/**
	 * This method initializes start
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStart() {
		if (start == null) {
			start = new JButton();
			start.setBounds(new Rectangle(94, 468, 75, 26));
			start.setText("start");
			start.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					start.setEnabled(false);
					if (server == null || !server.isRunning()) {
						serwer.setEnabled(false);
						klient.setEnabled(false);

						String host = "localhost";
						//int port = 4545;

						server = new Server(port);
						if (server.start()) {
							client = new Client(getID(), host, port);

							if (client.start()) {
								GameEvent ge = new GameEvent(GameEvent.C_LOGIN);
								sendMessage(ge);
							}

							zmienStatus(
									"Serwer pomyœlnie uruchomiony!\nOczekiwanie na drugiego gracza...\n",
									RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
							czatWyslij.setEnabled(true);
							start.setText("Stop");

						} else {
							serwer.setEnabled(true);
							klient.setEnabled(true);

							zmienStatus("Nie uda³o sie uruchoniæ serwera!\n",
									RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
						}
					} else {
						zmienStatus("Serwer zatrzymany!\n",
								RodzajWiadomosci.WIADOMOSC_NEGATYWNA);

						if (server != null)
							server.stop();
						server = null;
						ustawOdNowa();
					}
					start.setEnabled(true);
				}
			});
		}
		return start;
	}

	private void resetujPlansze() {
		planszaGracza.rozkladLosowy();
		planszaPrzeciwnika.wyczyscPlansze();
		trafioneStatkiGracza = 0;
		trafioneStatkiPrzeciwnika = 0;

		statkiGraczaPodsumowanie.setText(trafioneStatkiGracza + "/"
				+ liczbaStatkow);
		statkiPrzeciwnikaPodsumowanie.setText(trafioneStatkiPrzeciwnika + "/"
				+ liczbaStatkow);
	}

	private void ustawOdNowa() {
		if (!losuj.isEnabled()) {
			resetujPlansze();
		}
		nowaGra.setText("Rozpocznij grê");
		start.setText("Start");
		polacz.setText("Po³¹cz");
		czatWyslij.setEnabled(false);
		nowaGra.setEnabled(false);
		losuj.setEnabled(true);
		serwer.setEnabled(true);
		klient.setEnabled(true);
		adres.setEnabled(true);
	}

	private void kolejnaGra() {
		nowaGra.setText("Nowa Gra");
		nowaGra.setEnabled(true);
	}

	private void zerwanePolaczenie() {
		if (klient.isSelected()) {
			clientStarted = false;
			ustawOdNowa();
			polacz.setEnabled(true);
		} else {
			if (!losuj.isEnabled()) {
				resetujPlansze();
			}
			nowaGra.setText("Rozpocznij grê");
			nowaGra.setEnabled(false);
			losuj.setEnabled(true);
		}
		zmienStatus("Po³¹czenie zosta³o przerwane!",
				RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
	}

	/**
	 * This method initializes planszaGracza
	 * 
	 * @return game.Plansza
	 */
	private Plansza getPlanszaGracza() {
		if (planszaGracza == null) {
			planszaGracza = new Plansza(RodzajeGraczy.GRACZ);
			planszaGracza.setLocation(new java.awt.Point(13, 34));
		}
		return planszaGracza;
	}

	/**
	 * This method initializes planszaPrzeciwnika
	 * 
	 * @return game.Plansza
	 */
	private Plansza getPlanszaPrzeciwnika() {
		if (planszaPrzeciwnika == null) {
			planszaPrzeciwnika = new Plansza(RodzajeGraczy.PRZECIWNIK);
			planszaPrzeciwnika.setLocation(new java.awt.Point(355, 34));
		}
		return planszaPrzeciwnika;
	}

	/**
	 * This method initializes losuj
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLosuj() {
		if (losuj == null) {
			losuj = new JButton();
			losuj.setBounds(new Rectangle(156, 351, 160, 23));
			losuj.setText("Zmieñ ustawienie");
			losuj.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					planszaGracza.rozkladLosowy();
				}
			});
		}
		return losuj;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Statki thisClass = new Statki();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public Statki() {
		super();
		instance = this;
		initialize();
		port = Konfiguracja.getInstance().getPort();
		adres.setText(Konfiguracja.getInstance().getHost());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(694, 547);
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setTitle("Statki");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				Konfiguracja.getInstance().setHost(adres.getText());
				Konfiguracja.getInstance().zapisz();
			}
		});
		this.resetujPlansze();
		new Thread() {
			@Override
			public void run() {
				while (true) {
					if (client != null && client.isAlive()) {
						processMessages();
					} else if (clientStarted && client != null) {
						client.stop();
						client = null;
						zerwanePolaczenie();
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException ex) {
					}
				}
			}
		}.start();
	}

	private void processMessages() {
		GameEvent ge;
		while (client != null && client.isAlive()
				&& (ge = client.receiveMessage()) != null) {
			switch (ge.getType()) {
			case GameEvent.SB_CHAT_MSG:
				if (getID().compareTo(ge.getPlayerId()) == 0) {
					czatOdbierz.append("TY > ");
				} else {
					czatOdbierz.append("PRZECIWNIK > ");
				}

				czatOdbierz.append(ge.getMessage() + "\n");
				scrollChatBox();
				break;

			case GameEvent.SB_LOGIN:
				if (getID().compareTo(ge.getMessage()) != 0) {
					zmienStatus(
							"Przy³aczy³ siê drugi gracz!\nUstaw swoje statki a nastêpnie naciœnij przycisk \"Rozpocznij grê\"",
							RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
				}
				break;

			case GameEvent.SB_CAN_JOIN_GAME:
				nowaGra.setEnabled(true);
				break;

			case GameEvent.SB_PLAYER_JOINED:
				if (getID().compareTo(ge.getMessage()) == 0) {
					zmienStatus("Oczekiwanie na gotowoœæ przeciwnika...",
							RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
				} else if (nowaGra.isEnabled()) {
					if (losuj.isEnabled()) {
						zmienStatus(
								"Przeciwnik jest ju¿ gotowy\nUstaw swoje statki a nastêpnie naciœnij przycisk \"Rozpocznij grê\"",
								RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
					} else {
						zmienStatus(
								"Przeciwnik jest ju¿ gotowy\nNaciœnij przycisk \"Nowa gra\" i ustaw swoje statki, a nastêpnie naciœnij przycisk \"Rozpocznij grê\"",
								RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
					}
				}
				break;
			case GameEvent.SB_START_GAME:
				if (serwer.isSelected()) {
					zmienStatus("Gra rozpoczêta\nTwoja kolej, oddaj strza³!",
							RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
					setToken(true);
				} else {
					zmienStatus(
							"Gra rozpoczêta\nPierwszy strza³ odda twój przeciwnik!",
							RodzajWiadomosci.WIADOMOSC_NEUTRALNA);
				}
				break;

			case GameEvent.SB_SHOT:
				if (getID().compareTo(ge.getPlayerId()) != 0) {
					String s = ge.getMessage();
					int idx1 = s.indexOf('|');
					String a = s.substring(0, idx1);
					String b = s.substring(idx1 + 1);

					try {
						int x = Integer.parseInt(a);
						int y = Integer.parseInt(b);
						WynikStrzalu w = planszaGracza.sprawdzStrzal(x, y);
						GameEvent geOut = new GameEvent(GameEvent.C_SHOT_RESULT);
						geOut.setMessage(x + "|" + y + "|" + w.ordinal());
						sendMessage(geOut);
					} catch (NumberFormatException ex) {
					}

				}
				break;

			case GameEvent.SB_SHOT_RESULT: {
				String s = ge.getMessage();
				int idx1 = s.indexOf('|');
				int idx2 = s.indexOf('|', idx1 + 1);
				String a = s.substring(0, idx1);
				String b = s.substring(idx1 + 1, idx2);
				String c = s.substring(idx2 + 1);

				try {
					int x = Integer.parseInt(a);
					int y = Integer.parseInt(b);
					int n = Integer.parseInt(c);
					WynikStrzalu w = WynikStrzalu.values()[n];

					if (getID().compareTo(ge.getPlayerId()) != 0) {
						planszaPrzeciwnika.zaznaczStrzal(x, y, w);
					} else {
						planszaGracza.zaznaczStrzal(x, y, w);
					}

					if (w == WynikStrzalu.PUDLO) {
						if (getID().compareTo(ge.getPlayerId()) != 0) {
							zmienStatus(
									"Nie trafi³eœ\nTeraz strzela przeciwnik",
									RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
						} else {
							zmienStatus(
									"Przeciwnik nie trafi³\nTeraz twoja kolej, strzelaj!",
									RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
							setToken(true);
						}
					} else {
						if (w == WynikStrzalu.TRAFIONY) {
							if (getID().compareTo(ge.getPlayerId()) != 0) {
								zmienStatus(
										"Trafi³eœ statek przeciwnika, ale nie jest on jeszcze zatopiony\nStrzelaj jeszcze raz!",
										RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
								setToken(true);
							} else {
								zmienStatus(
										"Przeciwnik trafi³ w twój statek, ale nie jest on jeszcze zatopiony\nKolejny strza³ nale¿y do przeciwnika",
										RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
							}
						} else { // TRAFIONY_ZATOPIONY
							if (getID().compareTo(ge.getPlayerId()) != 0) {
								zmienStatus(
										"Zatopi³eœ statek przeciwnika!\nStrzelaj jeszcze raz!",
										RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
								statkiPrzeciwnikaPodsumowanie
										.setText(++trafioneStatkiPrzeciwnika
												+ "/" + liczbaStatkow);
								if (trafioneStatkiPrzeciwnika == liczbaStatkow) {
									zmienStatus(
											"WYGRA£EŒ!!!\nZatopi³eœ wszystkie statki przeciwnika!\nJeœli chcesz rozpocz¹æ now¹ grê naciœnij przycisk\n\"Nowa Gra\"",
											RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
									GameEvent geOut = new GameEvent(
											GameEvent.C_QUIT_GAME);
									sendMessage(geOut);
									kolejnaGra();
								} else {
									setToken(true);
								}
							} else {
								zmienStatus(
										"Przeciwnik zatopi³ twój statek!\nKolejny strza³ nale¿y do przeciwnika",
										RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
								statkiGraczaPodsumowanie
										.setText(++trafioneStatkiGracza + "/"
												+ liczbaStatkow);
								if (trafioneStatkiGracza == liczbaStatkow) {
									zmienStatus(
											"PRZEGRA£EŒ!!!\nPrzeciwnik zatopi³ ca³¹ twoj¹ flotê!\nJeœli chesz rozpocz¹æ now¹ grê naciœnij przycisk \"Nowa Gra\"",
											RodzajWiadomosci.WIADOMOSC_POZYTYWNA);
									kolejnaGra();
								}
							}
						}
					}
				} catch (NumberFormatException ex) {
				}
			}
				break;

			case GameEvent.SB_PLAYER_QUIT:
				zerwanePolaczenie();
				break;

			case GameEvent.S_TOO_MANY_CONNECTIONS:
				if (client != null)
					client.stop();
				client = null;
				ustawOdNowa();
				zmienStatus(
						"Próba po³¹czenia zakoñczona niepowodzeniem!\nW grze zanjduje siê ju¿ 2 graczy",
						RodzajWiadomosci.WIADOMOSC_NEGATYWNA);
				break;
/*
			default:
				czatOdbierz.append("Nieznany komunikat: #" + ge.getType()
						+ "\n");
				czatOdbierz.append("# PlayerID: " + ge.getPlayerId() + "\n");
				czatOdbierz.append("# Message: " + ge.getMessage() + "\n");
				scrollChatBox();
				break;
*/				
			}
		}
	}

	public void scrollChatBox() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				czatOdbierz.setCaretPosition(czatOdbierz.getText().length());
			}
		});
	}

	private enum RodzajWiadomosci {
		WIADOMOSC_POZYTYWNA, WIADOMOSC_NEUTRALNA, WIADOMOSC_NEGATYWNA
	}

	private void zmienStatus(String wiadomosc, RodzajWiadomosci rodzaj) {
		Color color;
		if (rodzaj == RodzajWiadomosci.WIADOMOSC_POZYTYWNA)
			color = new Color(196, 255, 196);
		else if (rodzaj == RodzajWiadomosci.WIADOMOSC_NEGATYWNA)
			color = new Color(255, 196, 196);
		else
			color = new Color(255, 255, 196);
		status.setBackground(color);
		status.setText("");
		status.append(wiadomosc);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
