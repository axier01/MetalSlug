package pantallas;

import java.awt.Color;
import java.awt.Cursor;
import java.io.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import base.PanelJuego;
import base.Pantalla;

public class PantallaInicial implements Pantalla {

	PanelJuego panelJuego;
	
	BufferedImage imagenOriginalInicial;
	Image imagenReescaladaInicial;
	Font fuenteInicial;
	//Inicio pantalla
	Color colorLetras = Color.YELLOW;
	int contadorColorFrames = 0;
	static final int CAMBIO_COLOR_INICIO = 5;
	Clip sonidoClipInicial;
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;	
		
	}
	
	
	@Override
	public void inicializarPantalla() {
		try {
			Cursor cursor;
			ImageIcon icon = new ImageIcon("");
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			cursor = toolkit.createCustomCursor(icon.getImage(), new Point(), "cursor");
			panelJuego.setCursor(cursor);
			sonidoClipInicial = AudioSystem.getClip();
			sonidoClipInicial.open(AudioSystem.getAudioInputStream(new File("Musica/musica1.wav")));
				
			imagenOriginalInicial = ImageIO.read(new File("Imagenes/fondoInicial.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fuenteInicial = new Font("Arial", Font.BOLD, 20); 
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imagenReescaladaInicial, 0,0, null);
	}

	@Override
	public void ejecutarFrame() {
		
	}

	@Override
	public void moverRaton(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		PantallaJuego pantallaJuego = new PantallaJuego(panelJuego);
		pantallaJuego.inicializarPantalla();
		FloatControl volumen = (FloatControl) sonidoClipInicial.getControl(FloatControl.Type.MASTER_GAIN);
		volumen.setValue(-8.0f);	
		sonidoClipInicial.start();		
		panelJuego.setPantallaActual(pantallaJuego);
		
	}

	@Override
	public void redimensionarPantalla(ComponentEvent e) {
		imagenReescaladaInicial = imagenOriginalInicial.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}


	@Override
	public void pulsarTecla(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



}
