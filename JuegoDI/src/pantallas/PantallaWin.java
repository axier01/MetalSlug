package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import base.PanelJuego;
import base.Pantalla;

public class PantallaWin implements Pantalla {

	PanelJuego panelJuego;
	BufferedImage imagenOriginal;
	Image imagenReescalada;
	BufferedImage imagenOriginalInicial;
	Image imagenReescaladaInicial;
	Font fuenteInicial;
	Font fuenteVolver;
	Color colorLetras = Color.YELLOW;
	int contadorColorFrames = 0;
	int soldadosMuertos = 0;
	public int puntuacion = 0;
	PantallaInicial inicial;
	static final int CAMBIO_COLOR_INICIO = 5;
	Clip sonidoClipInicial;
	int contador = 0;
	String textoString = "Pulsa para ir al siguiente nivel";

	public PantallaWin(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}

	@Override
	public void inicializarPantalla() {
		try {

			sonidoClipInicial = AudioSystem.getClip();
			sonidoClipInicial.open(AudioSystem.getAudioInputStream(new File("Musica/victoria.wav")));
			FloatControl volumen = (FloatControl) sonidoClipInicial.getControl(FloatControl.Type.MASTER_GAIN);
			volumen.setValue(-6.0f);
			sonidoClipInicial.start();
			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondoInicial.png"));

			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondoInicial.png"));
			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondo.png"));
			imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(),
					panelJuego.getHeight(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fuenteInicial = new Font("Arial", Font.BOLD, 40);
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaInicial, 0, 0, null);
		g.setColor(colorLetras);
		g.setFont(fuenteInicial);
		g.drawString("YOU WIN", panelJuego.getWidth() / 2 - 120, panelJuego.getHeight() / 2 - 15);

		fuenteVolver = new Font("Arial", Font.BOLD, 20);
		g.setFont(fuenteVolver);
		g.drawString(textoString, panelJuego.getWidth() / 2 - 230,
				panelJuego.getHeight() / 2 + 45);

		g.setFont(fuenteVolver);
		g.drawString("Soldados muertos: " + soldadosMuertos, panelJuego.getWidth() / 2 - 230,
				panelJuego.getHeight() / 2 + 95);
	}

	@Override
	public void ejecutarFrame() {
		contadorColorFrames++;
		if (contadorColorFrames % CAMBIO_COLOR_INICIO == 0) {

			if (colorLetras.equals(Color.YELLOW)) {
				colorLetras = Color.RED;
			} else {
				colorLetras = Color.YELLOW;
			}
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		if (contador == 1) {
			sonidoClipInicial.close();
			Nivel2 nivel2 = new Nivel2(panelJuego);
			nivel2.inicializarPantalla();
			panelJuego.setPantallaActual(nivel2);
		}

		if (contador == 2) {
			sonidoClipInicial.close();
			Nivel3 nivel3 = new Nivel3(panelJuego);
			nivel3.inicializarPantalla();
			panelJuego.setPantallaActual(nivel3);
		}
		if (contador == 3) {
			sonidoClipInicial.close();
			PantallaInicial nivel3Superado = new PantallaInicial(panelJuego);
			nivel3Superado.inicializarPantalla();
			panelJuego.setPantallaActual(nivel3Superado);
		}

	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {

		imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
				Image.SCALE_SMOOTH);
	}

	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

}
