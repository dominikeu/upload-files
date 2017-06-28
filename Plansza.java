package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;

import network.GameEvent;

public class Plansza extends JPanel {

	private static final long serialVersionUID = 1L;

	private ElementyPlanszy[][] tablica = new ElementyPlanszy[15][15];

	private RodzajeGraczy wlascicielPlanszy;

	private void zerowanieTablicy() {
		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[i].length; j++) {
				tablica[i][j] = ElementyPlanszy.POLE_PUSTE;
			}
		}
	}

	private enum Kierunek {
		POZIOMO, PIONOWO
	}

	public void wyczyscPlansze() {
		zerowanieTablicy();
		repaint();
	}

	public void rozkladLosowy() {
		zerowanieTablicy();

		for (int i = 0; i < 12; i++) {
			int dlugosc;
			if (i < 1)
				dlugosc = 5;
			else if (i < 3)
				dlugosc = 4;
			else if (i < 5)
				dlugosc = 3;
			else if (i < 8)
				dlugosc = 2;
			else
				dlugosc = 1;

			boolean ok;
			Point goraLewo;
			Point dolPrawo;
			int x, y;
			Kierunek k;

			do {

				Random r = new Random();
				k = (Math.abs(r.nextInt()) % 2 == 0) ? Kierunek.PIONOWO
						: Kierunek.POZIOMO;

				if (k == Kierunek.POZIOMO) {
					x = Math.abs(r.nextInt()) % (15 - dlugosc);
					y = Math.abs(r.nextInt()) % 15;
					goraLewo = new Point(Math.max(x - 1, 0), Math.max(y - 1, 0));
					dolPrawo = new Point(Math.min(x + dlugosc + 1, 14), Math
							.min(y + 1, 14));
				} else {
					x = Math.abs(r.nextInt()) % 15;
					y = Math.abs(r.nextInt()) % (15 - dlugosc);
					goraLewo = new Point(Math.max(x - 1, 0), Math.max(y - 1, 0));
					dolPrawo = new Point(Math.min(x + 1, 14), Math.min(y
							+ dlugosc + 1, 14));
				}

				ok = true;

				petla: for (int m = goraLewo.x; m <= dolPrawo.x; m++)
					for (int n = goraLewo.y; n <= dolPrawo.y; n++)
						if (tablica[m][n] != ElementyPlanszy.POLE_PUSTE) {
							ok = false;
							break petla;
						}
			} while (!ok);

			for (int d = 0; d < dlugosc; d++)
				if (k == Kierunek.POZIOMO) {
					tablica[x + d][y] = ElementyPlanszy.STATEK;
				} else {
					tablica[x][y + d] = ElementyPlanszy.STATEK;
				}
		}
		repaint();
	}

	public WynikStrzalu sprawdzStrzal(int x, int y) {
		if (tablica[x][y] == ElementyPlanszy.STATEK) {
			if (sprawdzCzyZatopiony(x, y)) {
				return WynikStrzalu.TRAFIONY_ZATOPIONY;
			} else {
				return WynikStrzalu.TRAFIONY;
			}
		} else {
			return WynikStrzalu.PUDLO;
		}
	}

	public void zaznaczStrzal(int x, int y, WynikStrzalu w) {
		if (w == WynikStrzalu.PUDLO) {
			tablica[x][y] = ElementyPlanszy.PUDLO;
		} else {
			tablica[x][y] = ElementyPlanszy.STATEK_TRAFIONY;

			if (w == WynikStrzalu.TRAFIONY_ZATOPIONY) {
				zaznaczZatopiony(x, y);
			}
		}
		repaint();
	}

	private boolean sprawdzCzyZatopiony(int x, int y) {
		int t = x;
		while (--t >= 0
				&& (tablica[t][y] == ElementyPlanszy.STATEK || tablica[t][y] == ElementyPlanszy.STATEK_TRAFIONY))
			if (tablica[t][y] == ElementyPlanszy.STATEK)
				return false;

		t = x;
		while (++t < 15
				&& (tablica[t][y] == ElementyPlanszy.STATEK || tablica[t][y] == ElementyPlanszy.STATEK_TRAFIONY))
			if (tablica[t][y] == ElementyPlanszy.STATEK)
				return false;

		t = y;
		while (--t >= 0
				&& (tablica[x][t] == ElementyPlanszy.STATEK || tablica[x][t] == ElementyPlanszy.STATEK_TRAFIONY))
			if (tablica[x][t] == ElementyPlanszy.STATEK)
				return false;

		t = y;
		while (++t < 15
				&& (tablica[x][t] == ElementyPlanszy.STATEK || tablica[x][t] == ElementyPlanszy.STATEK_TRAFIONY))
			if (tablica[x][t] == ElementyPlanszy.STATEK)
				return false;

		return true;
	}

	private void zaznaczZatopiony(int x, int y) {
		int x1 = x;
		int x2 = x;
		int y1 = y;
		int y2 = y;

		while (x1 > 0 && tablica[x1][y] == ElementyPlanszy.STATEK_TRAFIONY) {
			x1--;
		}

		while (x2 < 14 && (tablica[x2][y] == ElementyPlanszy.STATEK_TRAFIONY)) {
			x2++;
		}

		while (y1 > 0 && tablica[x][y1] == ElementyPlanszy.STATEK_TRAFIONY) {
			y1--;
		}

		while (y2 < 14 && (tablica[x][y2] == ElementyPlanszy.STATEK_TRAFIONY)) {
			y2++;
		}

		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				if (tablica[i][j] == ElementyPlanszy.STATEK_TRAFIONY)
					tablica[i][j] = ElementyPlanszy.STATEK_ZATOPIONY;
				else if (wlascicielPlanszy == RodzajeGraczy.PRZECIWNIK
						&& tablica[i][j] == ElementyPlanszy.POLE_PUSTE) {
					tablica[i][j] = ElementyPlanszy.PUSTE_AUTOMAT;
				}
	}

	public Plansza(RodzajeGraczy wlasciciel) {
		Dimension rozmiar = new Dimension(302, 302);
		setSize(rozmiar);
		setMinimumSize(rozmiar);
		setMaximumSize(rozmiar);
		setPreferredSize(rozmiar);

		wlascicielPlanszy = wlasciciel;

		zerowanieTablicy();
		if (wlascicielPlanszy == RodzajeGraczy.GRACZ) {
			rozkladLosowy();
		}

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& wlascicielPlanszy == RodzajeGraczy.PRZECIWNIK
						&& Statki.getInstance().hasToken()) {

					Point p = e.getPoint();

					if (p.x % 20 != 0 && p.x % 20 != 1 && p.y % 20 != 0
							&& p.y % 20 != 1) {
						int x = (p.x - 1) / 20;
						int y = (p.y - 1) / 20;

						if (tablica[x][y] == ElementyPlanszy.POLE_PUSTE) {
							Statki.getInstance().setToken(false);
							tablica[x][y] = ElementyPlanszy.ODDANY_STRZAL;
							repaint();
							GameEvent ge = new GameEvent(GameEvent.C_SHOT);
							ge.setMessage(x + "|" + y);
							Statki.getInstance().sendMessage(ge);
						}
					}
				}
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		Image img = createImage(getSize().width, getSize().height);

		Graphics2D g2 = (Graphics2D) img.getGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.black);
		g2.fillRect(0, 0, 302, 302);

		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[i].length; j++) {
				/*
				 * if ((i+j)%2 == 0) g2.setColor(new Color(200,228,255)); else
				 */

				if (tablica[i][j] == ElementyPlanszy.POLE_PUSTE) {
					g2.setColor(new Color(215, 230, 255));
				} else if (tablica[i][j] == ElementyPlanszy.STATEK) {
					g2.setColor(new Color(120, 160, 220));
				} else if (tablica[i][j] == ElementyPlanszy.ODDANY_STRZAL) {
					g2.setColor(new Color(255, 255, 196));
				} else if (tablica[i][j] == ElementyPlanszy.STATEK_TRAFIONY) {
					g2.setColor(new Color(120, 120, 200));
				} else if (tablica[i][j] == ElementyPlanszy.PUDLO) {
					g2.setColor(new Color(170, 170, 170));
				} else if (tablica[i][j] == ElementyPlanszy.STATEK_ZATOPIONY) {
					g2.setColor(new Color(220, 120, 120));
				} else if (tablica[i][j] == ElementyPlanszy.PUSTE_AUTOMAT) {
					g2.setColor(new Color(200, 200, 200));
				}

				g2.fillRect(2 + 20 * i, 2 + 20 * j, 18, 18);

				if (tablica[i][j] == ElementyPlanszy.ODDANY_STRZAL) {
					g2.setFont(new Font("Dialog", Font.BOLD, 14));
					g2.setColor(new Color(50, 50, 50));
					g2.drawString("?", 7 + 20 * i, 16 + 20 * j);
				} else if (tablica[i][j] == ElementyPlanszy.STATEK_TRAFIONY
						|| tablica[i][j] == ElementyPlanszy.STATEK_ZATOPIONY) {
					g2.setStroke(new BasicStroke(2.0f));
					g2.setColor(new Color(50, 50, 50));
					g2.drawLine(2 + 20 * i, 2 + 20 * j, 19 + 20 * i,
							19 + 20 * j);
					g2.drawLine(19 + 20 * i, 2 + 20 * j, 2 + 20 * i,
							19 + 20 * j);
				}
			}
		}

		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
