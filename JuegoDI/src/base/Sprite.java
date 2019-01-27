package base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author axierGonzalezDiaz Clase Sprite. Representa un elemento pintable y
 *         colisionable del juego.
 */
public class Sprite {

	private BufferedImage buffer;
	private Color color = Color.BLACK;
	// Variables de dimensión
	private int ancho;
	private int alto;
	// Variables de colocación
	private int posX;
	private int posY;
	// Variables para la velocidad
	private int velocidadX;
	private int velocidadY;
	// Ruta de la imagen
	private String rutaImagen;
	private URL ruta;
	private int numVidas;
	public boolean balaD = true;

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public Sprite(int ancho, int alto, int posX, int posY, String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer();
	}

	public int getNumVidas() {
		return numVidas;
	}

	public void setNumVidas(int numVidas) {
		this.numVidas = numVidas;
	}

	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String rutaImagen) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		actualizarBuffer();
	}

	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, String rutaImagen,
			int numVidas) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.rutaImagen = rutaImagen;
		this.numVidas = numVidas;
		actualizarBuffer();
	}

	public Sprite(int ancho, int alto, int posX, int posY, int velocidadX, int velocidadY, URL ruta) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.ruta = ruta;
		actualizarBuffer();
	}

	public void actualizarBuffer() {
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		try {
			BufferedImage imagenSprite = ImageIO.read(new File(rutaImagen));
			// pinto en el buffer la imagen
			g.drawImage(imagenSprite.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);

		} catch (Exception e) {
			g.setColor(color);
			g.fillRect(0, 0, ancho, alto);
			g.dispose();
		}

	}

	public boolean colisionan(Sprite otroSprite) {
		// Checkeamos si comparten algún espacio a lo ancho:
		boolean colisionAncho = false;
		if (posX < otroSprite.getPosX()) { // El Sprite actual se encuentra más cerca del eje de las X.
			colisionAncho = posX + ancho >= otroSprite.getPosX();
		} else { // El otro Sprite se encuentra más cerca del eje de las X.
			colisionAncho = otroSprite.getPosX() + otroSprite.getAncho() >= posX;
		}

		// Checkeamos si comparten algún espacio a lo alto:
		boolean colisionAlto = false;
		if (posY < otroSprite.getPosY()) {
			colisionAlto = alto > otroSprite.getPosY() - posY;
		} else {
			colisionAlto = otroSprite.getAlto() > posY - otroSprite.getPosY();
		}

		return colisionAncho && colisionAlto;
	}

	public void moverSprite(int anchoMundo, int altoMundo) {
		if (posX >= anchoMundo - ancho) { // por la derecha
			velocidadX = -1 * Math.abs(velocidadX);
		}
		if (posX <= 0) {// por la izquierda
			velocidadX = Math.abs(velocidadX);
		}
		if (posY >= altoMundo - alto) { // por la derecha
			velocidadY = -1 * Math.abs(velocidadY);
		}
		if (posY <= 0) {// por la izquierda
			velocidadY = Math.abs(velocidadY);
		}

		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}

	public void moverBoss(int anchoMundo) {
		if (posX >= anchoMundo - ancho) { // por la derecha
			velocidadX = -1 * Math.abs(velocidadX);
		}
		if (posX <= 0) {// por la izquierda
			velocidadX = Math.abs(velocidadX);
		}

		if (posX == anchoMundo - ancho) {
			velocidadX = -1 * Math.abs(velocidadX);
			this.setRutaImagen("Imagenes/bossIzq.gif");
			balaD = false;
		}
		if (posX == 0) {
			velocidadX = Math.abs(velocidadX);
			this.setRutaImagen("Imagenes/bossDer.gif");
			balaD = true;
		}

		actualizarBuffer();

		posX = posX + velocidadX;
	}

	public void moverBoss2(int anchoMundo) {
		if (posX >= anchoMundo - ancho) { // por la derecha
			velocidadX = -1 * Math.abs(velocidadX);
		}
		if (posX <= 0) {// por la izquierda
			velocidadX = Math.abs(velocidadX);
		}

		if (posX == anchoMundo - ancho) {
			velocidadX = -1 * Math.abs(velocidadX);
			this.setRutaImagen("Imagenes/boss2Izq.gif");
		}
		if (posX == 0) {
			velocidadX = Math.abs(velocidadX);
			this.setRutaImagen("Imagenes/boss2Der.gif");
		}

		actualizarBuffer();

		posX = posX + velocidadX;
	}

	public boolean isBalaD() {
		return balaD;
	}

	public void setBalaD(boolean balaD) {
		this.balaD = balaD;
	}

	public void moverSprite() {
		posX = posX + velocidadX;
		posY = posY + velocidadY;
	}

	/**
	 * Método que pinta el Sprite en el mundo teniendo en cuenta las
	 * características propias del Sprite.
	 * 
	 * @param g
	 *            Es el Graphics del mundo que se utilizará para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	// Métodos para obtener:
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public int getVelocidadX() {
		return velocidadX;
	}

	public int getVelocidadY() {
		return velocidadY;
	}

	// métodos para cambiar:
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}

	public void setVelocidadX(int velocidadX) {
		this.velocidadX = velocidadX;
	}

	public void setVelocidadY(int velocidadY) {
		this.velocidadY = velocidadY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		actualizarBuffer();

	}

}
