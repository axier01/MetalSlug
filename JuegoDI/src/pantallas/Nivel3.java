package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import base.PanelJuego;
import base.Pantalla;
import base.Sprite;

public class Nivel3 implements Pantalla {
	private static final int ANCHO_SOLDADOS = 90;
	private static final int ANCHO_PERSONAJE = 90;
	private static final int ANCHO_DISPARO = 30;
	private static final int ALTO_DISPARO = 30;
	private static final Color COLOR_PUNTUACION = Color.WHITE;

	PanelJuego panelJuego;

	// LISTAS QUE CADA UNA ALMACENA LOS SPRITES
	ArrayList<Sprite> soldados;
	ArrayList<Sprite> disparos;
	ArrayList<Sprite> disparoSoldado;
	// SPRITES
	Sprite personaje;
	Sprite disparo;
	Sprite disparoS;
	Sprite tuboSprite;

	// IMAGEN
	BufferedImage imagenOriginal;
	Image imagenReescalada;

	// SONIDO FONDO
	Clip disparoD;
	Clip disparoI;
	Clip disparoSol;
	// BOLEANO PARA COMPROBAR EN QUE POSICION ESTA (DERECHA O IZQUIERDA)
	boolean derecha = true;
	boolean balaD = true;

	// VARIABLES PARA EL CRONOMETRO
	double tiempoInicial;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal;
	Font fuenteTiempo;

	// VARIABLES
	int muertos = 0;
	int contador = 0;
	int posicionXActual;
	int posY = 270;
	int puntuacion = 0;
	int numeroFrames = 0;

	// CONSTRUCTOR DE LA CLASE NIVEL 2
	public Nivel3(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		tiempoInicial = System.nanoTime();
	}

	// METODO QUE INICIALIZA LA PANTALLA
	@Override
	public void inicializarPantalla() {
		Cursor cursor;
		ImageIcon icon = new ImageIcon("");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		cursor = toolkit.createCustomCursor(icon.getImage(), new Point(), "cursor");
		panelJuego.setCursor(cursor);

		soldados = new ArrayList<Sprite>();
		disparos = new ArrayList<Sprite>();
		disparoSoldado = new ArrayList<Sprite>();
		// CREAR SOLDADOS ENEMIGOS
		for (int i = 0; i < 1; i++) {
			Sprite soldado;
			soldado = new Sprite(ANCHO_SOLDADOS + 180, ANCHO_SOLDADOS + 220, -200, 100, 2, 0, "Imagenes/boos2Der.gif",
					40);
			soldados.add(soldado);
		}
		try {
			imagenOriginal = ImageIO.read(new File("Imagenes/fondo3.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		personaje = new Sprite(ANCHO_PERSONAJE, ANCHO_PERSONAJE, 300, 270, "Imagenes/personajeDer.png");

		fuenteTiempo = new Font("Arial", Font.BOLD, 20);
		tiempoInicial = System.nanoTime();
		tiempoDeJuego = 0;
		formatoDecimal = new DecimalFormat("#.##");
		reescalarImagen();
	}

	// METODO QUE PINTA EN LA PANTALLA LOS SPRITES
	@Override
	public void pintarPantalla(Graphics g) {
		rellenarFondo(g);
		for (Sprite cuadrado : soldados) {
			cuadrado.pintarSpriteEnMundo(g);
		}
		for (Sprite sprite : disparos) {
			sprite.pintarSpriteEnMundo(g);
		}
		for (Sprite sprite : disparoSoldado) {
			sprite.pintarSpriteEnMundo(g);
		}
		if (tuboSprite != null) {
			tuboSprite.pintarSpriteEnMundo(g);
		}
		if (personaje != null) {
			personaje.pintarSpriteEnMundo(g);
		}
		pintarTiempo(g);
//		pintarPuntuacion(g);
	}

	// METODO QUE PINTA EL TIEMPO EN LA PANTALLA
	private void pintarTiempo(Graphics g) {
		Font f = g.getFont();
		Color c = g.getColor();

		g.setColor(COLOR_PUNTUACION);
		g.setFont(fuenteTiempo);
		actualizarTiempo();
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000d), 25, 25);

		g.setColor(c);
		g.setFont(f);
	}

//	// METODO PARA PINTAR LA PUNTUACION
//	public void pintarPuntuacion(Graphics g) {
//		Font f = g.getFont();
//		Color c = g.getColor();
//		g.setColor(COLOR_PUNTUACION);
//		g.setFont(fuenteTiempo);
//		g.drawString("" + puntuacion, 650, 25);
//		g.setColor(c);
//		g.setFont(f);
//
//	}

	// METODO QUE ACTUALIZA EL CRONOMETRO DEL JUEGO
	private void actualizarTiempo() {
		tiempoDeJuego = System.nanoTime() - tiempoInicial;
	}

	// PINTAR IMAGEN DE FONDO REESCALADA
	private void rellenarFondo(Graphics g) {
		// Pintar la imagen de fondo reescalada:
		g.drawImage(imagenReescalada, 0, 0, null);
	}

	// METODO PARA MOVER LOS SPRITES
	private void moverSprites() {

		for (int i = 0; i < soldados.size(); i++) {
			Sprite aux = soldados.get(i);
			aux.moverSprite(-100, panelJuego.getHeight());
		}

		for (int i = 0; i < disparos.size(); i++) {
			Sprite aux = disparos.get(i);
			aux.moverSprite();
			if (aux.getPosX() + aux.getAncho() <= 0) {
				disparos.remove(aux);
			}
			if (aux.getPosX() + aux.getAncho() >= 700) {
				disparos.remove(aux);
			}
		}
		for (int i = 0; i < disparoSoldado.size(); i++) {
			Sprite aux = disparoSoldado.get(i);
			aux.moverSprite();
			if (aux.getPosX() + aux.getAncho() <= 0) {
				disparoSoldado.remove(aux);
			}
			if (aux.getPosX() + aux.getAncho() >= 700) {
				disparoSoldado.remove(aux);
			}
		}

		if (personaje != null) {
			personaje.moverSprite(panelJuego.getWidth(), panelJuego.getHeight());
		}
	}

	// COMPROBAR SI EL DISPARO DEL PERSONAJE HA TOCADO A LOS SOLDADOS
	private void comprobarColisiones() {
		if (personaje != null) {
			for (int i = 0; i < soldados.size() && disparos.size() != 0; i++) {
				for (int j = 0; j < disparos.size(); j++) {

					if (disparos.get(j).colisionan(soldados.get(i))) {

						if (disparos.get(j).getRutaImagen().equalsIgnoreCase("Imagenes/disparoEspecial.png")) {
							disparos.remove(j);
							soldados.get(i).setNumVidas(soldados.get(i).getNumVidas() - 5);
						} else {
							disparos.remove(j);
							soldados.get(i).setNumVidas(soldados.get(i).getNumVidas() - 1);
						}
					}
					if (soldados.get(i).getNumVidas() <= 0) {
						tuboSprite = new Sprite(ANCHO_PERSONAJE, ANCHO_PERSONAJE, 600, 260, "Imagenes/tubo.png");
					}
				}
				if (soldados.get(i).getNumVidas() <= 0) {
					soldados.remove(i);
					muertos++;
					contador++;
				}
			}
		}
	}

	// COMPROBAR SI ALGUN SOLDADO HA COLISIONADO CON EL PERSONAJE
	private void comprobarColisionesPersonaje() {
		for (int i = 0; i < soldados.size(); i++) {
			if (personaje != null) {
				if (soldados.get(i).colisionan(personaje)) {
					personaje = null;
				}
			}
		}

		for (int i = 0; i < disparoSoldado.size(); i++) {
			if (personaje != null) {
				if (disparoSoldado.get(i).colisionan(personaje)) {
					disparoSoldado.remove(i);
					personaje = null;
				}
			}
		}

		if (personaje == null) {
			PantallaGameOver pantallaFinal = new PantallaGameOver(panelJuego);
			pantallaFinal.inicializarPantalla();
			pantallaFinal.puntuacion = muertos;
			panelJuego.setPantallaActual(pantallaFinal);
		}
	}

	// COMPROBACION DE DISPARO DE UN SOLDADO CONTRA DISPARO DE PERSONAJE
	public void comprobarDisparos() {
		for (int i = 0; i < disparos.size(); i++) {
			for (int j = 0; j < disparoSoldado.size(); j++) {
				if (disparos.size() != 0) {
					if (disparos.get(i).colisionan(disparoSoldado.get(j))) {
						disparos.remove(i);
						disparoSoldado.remove(j);
						puntuacion += 5;
					}
				}
			}
		}
	}

	// METODO QUE COMPRUEBO LA POSICION Y DEL PERSONAJE PARA QUE VUELVA A LA
	// ORIGINAL
	public void caerSalto() {
		if (personaje != null) {
			if (personaje.getPosY() + personaje.getAlto() < posY + personaje.getAlto()) {
				personaje.setVelocidadY(personaje.getVelocidadY() + 1);
			} else {
				personaje.setPosY(280);
				personaje.setVelocidadY(0);
			}
		}
	}

	// METODO QUE PASA AL SIGUIENTE NIVEL
	public void salirPantalla() {
		if (personaje != null && tuboSprite != null && personaje.getPosX() == tuboSprite.getPosX()) {
			PantallaWin pantallaGanar = new PantallaWin(panelJuego);
			pantallaGanar.soldadosMuertos = muertos;
			pantallaGanar.textoString = "Enhorabuena, te has pasado el juego!";
			pantallaGanar.contador = 3;
			pantallaGanar.inicializarPantalla();
			panelJuego.setPantallaActual(pantallaGanar);
		}
	}

	// METODO QUE HACE QUE EL SOLDADO DISPARE
	public void disparoSoldado() {
		int posicionYAleatoria = (int) (Math.random() * 210 + 210);
		if (numeroFrames % 60 == 0) {
			for (int j = 0; j < soldados.size(); j++) {
				disparoS = new Sprite(ANCHO_DISPARO + 20, ALTO_DISPARO + 25, soldados.get(j).getPosX() + 100,
						posicionYAleatoria, 10, 0, "Imagenes/disparoSoldado.png");
				disparoSoldado.add(disparoS);
				try {
					disparoSol = AudioSystem.getClip();
					disparoSol.open(AudioSystem.getAudioInputStream(new File("Musica/disparoSoldado.wav")));
					FloatControl volumen = (FloatControl) disparoSol.getControl(FloatControl.Type.MASTER_GAIN);
					volumen.setValue(-10.0f);
					disparoSol.start();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	// EJECUTAR FRAME
	@Override
	public void ejecutarFrame() {
		comprobarColisiones();
		comprobarColisionesPersonaje();
		comprobarDisparos();
		disparoSoldado();
		moverSprites();
		caerSalto();
		salirPantalla();
		numeroFrames++;
	}

	// MOVER EL RATON
	@Override
	public void moverRaton(MouseEvent e) {
		if (personaje != null) {
			moverPersonaje(e);
		}
	}

	// PULSAR EL RATON (DISPARAR)
	public void pulsarRaton(MouseEvent e) {
		// SI PRESIONA EL BOTON IZQUIERDO DISPARO NORMAL
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (!derecha) {
				disparo = new Sprite(ANCHO_DISPARO + 30, ALTO_DISPARO - 20, personaje.getPosX() - 25,
						personaje.getPosY() + 35, -25, 0, "Imagenes/disparo1Izq.png");
				disparos.add(disparo);
				try {
					disparoI = AudioSystem.getClip();
					disparoI.open(AudioSystem.getAudioInputStream(new File("Musica/disparo.wav")));
					FloatControl volumen = (FloatControl) disparoI.getControl(FloatControl.Type.MASTER_GAIN);
					volumen.setValue(-10.0f);
					disparoI.start();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		// SI PRESIONA EL BOTON DEL MEDIO DOS VECES SALTA
		if (SwingUtilities.isMiddleMouseButton(e)) {
			if (e.getClickCount() == 2) {
				personaje.setVelocidadY(-20);
			}
		}

		// SI PRESIONA EL BOTON DERECHO DISPARA MISIL
		if (SwingUtilities.isRightMouseButton(e)) {
			if (!derecha) {
				disparo = new Sprite(ANCHO_DISPARO - 10, ALTO_DISPARO - 10, personaje.getPosX() - 36,
						personaje.getPosY() + 48, -1, 0, "Imagenes/disparoEspecial.png");
				disparos.add(disparo);

			}

		}
	}

	// REDIMENSIONAR PANTALLA
	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		reescalarImagen();
	}

	// REESCALAR IMAGEN
	private void reescalarImagen() {
		// Pensar en cada caso particular
		imagenReescalada = imagenOriginal.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

	// MOVER PERSONAJE Y CAMBIAR SU IMAGEN SEGUN LA DIRECCION DEL RATON
	public void moverPersonaje(MouseEvent e) {
		int posX = e.getX() - personaje.getAncho() / 2;
		if (posX == posicionXActual) {
			personaje.setRutaImagen("Imagenes/personajeIzq.png");
			personaje.actualizarBuffer();
		} else {
			if (posX < posicionXActual) {
				personaje.setRutaImagen("Imagenes/personajeIzq.png");
				personaje.actualizarBuffer();
				derecha = false;
			} else {
				personaje.setRutaImagen("Imagenes/personajeIzq.png");
				personaje.actualizarBuffer();
				derecha = false;
			}
		}
		personaje.setPosX(posX);
		posicionXActual = posX;
	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
