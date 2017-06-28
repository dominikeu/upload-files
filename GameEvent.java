package network;

public class GameEvent {

	// sta�e eventType
	// C_* (Client) - zdarzenia wysy�ane przez klienta
	// S_* (Server) - zdarzenia wysy�ane przez serwer
	// SB_* (Server broadcast) - zdarzenia wysy�ane przez serwer do wszystkich
	// klient�w

	/** Pr�ba zalogowania przez klienta */
	public static final int C_LOGIN = 1001;

	/** Logowanie nie powiod�o si� */
	public static final int S_LOGIN_FAIL = 1002;

	/** Logowanie powiod�o si� - wysy�anie informacji do wszystkich graczy */
	public static final int SB_LOGIN = 1003;

	/** Pr�ba wylogowania si� przez klienta */
	public static final int C_LOGOUT = 1004;

	/** Wylogowanie klienta - wysy�anie informacji do wszytkich graczy */
	public static final int SB_LOGOUT = 1005;

	/** W grze znajduje sie juz maksymalna liczba graczy */
	public static final int S_TOO_MANY_CONNECTIONS = 1006;
	
	/** W grze znajduje sie juz osoba o takim ID */
	public static final int S_USER_EXIST = 1007;

	/** Wys�anie informacji do wszytkich graczy, ze mozna dolaczyc sie do gry */
	public static final int SB_CAN_JOIN_GAME = 1101;

	/** Wys�anie informacji do wszytkich graczy, ze nie mozna dolaczyc sie do gry */
	public static final int SB_CANNOT_JOIN_GAME = 1102;

	/** Pr�ba do��czenia do gry przez klienta */
	public static final int C_JOIN_GAME = 1103;

	/** Klient do��czy� do gry */
	public static final int S_JOIN_GAME_OK = 1104;

	/** Klient nie do��czy� do gry */
	public static final int S_JOIN_GAME_FAIL = 1105;

	/** Klient do��czy� do gry - wysy�anie informacji do wszystkich graczy */
	public static final int SB_PLAYER_JOINED = 1106;

	/** Wysy�anie informacji do wszystkich graczy o rozpoczeciu gry */
	public static final int SB_START_GAME = 1107;

	/** Pr�ba zakonczenia gry przez gracza */
	public static final int C_QUIT_GAME = 1108;

	/** Klient zako�czy� gr� - wysy�anie informacji do wszytkich graczy */
	public static final int SB_PLAYER_QUIT = 1109;

	/** Wszystkie grafiki za�adowane - gracz gotowy do gry */
	public static final int C_READY = 1110;

	/** Wszyscy gracze gotowi do gry */
	public static final int SB_ALL_READY = 1111;

	/** Klient wysy�a wiadomo�� tekstow� */
	public static final int C_CHAT_MSG = 1201;

	/** Swerwer przesy�a wiadomo�� tekstow� do wszystkich graczy */
	public static final int SB_CHAT_MSG = 1202;

	/** Gracz strzela do przeciwnika */
	public static final int C_SHOT = 1301;

	/** Serwer przesyla informacje o strzale gracza */
	public static final int SB_SHOT = 1302;

	/** Wynik Strza�u */
	public static final int C_SHOT_RESULT = 1304;

	/** Serwer rozszy�a wynik strza�u */
	public static final int SB_SHOT_RESULT = 1305;	
	
	/** */
	public static final int C_DEAD = 1308;

	/** */
	public static final int SB_DEAD = 1309;

	/**  */
	public static final int C_PLAYER_DEAD = 1310;

	/**  */
	public static final int SB_GAME_OVER = 1311;

	// -----------------------------------------------------

	/** Typ zdarzenia */
	private int eventType;

	/** ID gracza kt�ry przes�a� wiadomo�� */
	private String playerId = "";

	/** tre�� wiadomo�ci */
	private String message;

	public GameEvent() {

	}

	public GameEvent(int type) {
		setType(type);
	}

	public GameEvent(int type, String message) {
		this(type);
		this.message = message;
	}

	public GameEvent(String receivedMessage) {
		String x = receivedMessage;
		int idx1 = x.indexOf('|');
		int idx2 = x.indexOf('|', idx1 + 1);
		String a = x.substring(0, idx1);
		String b = x.substring(idx1 + 1, idx2);
		String c = x.substring(idx2 + 1);
		try {
			setType(Integer.parseInt(a));
		} catch (NumberFormatException ex) {
			setType(-1);
		}
		setPlayerId(b);
		setMessage(c);
	}

	public String toSend() {
		String toSend = eventType + "|" + playerId + "|" + getMessage();
		return toSend;
	}

	public void setType(int type) {
		eventType = type;
	}

	public int getType() {
		return eventType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String id) {
		playerId = id;
	}
}
