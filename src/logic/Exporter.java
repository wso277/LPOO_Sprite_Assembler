package logic;

import gui.Gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Exporter {

	// to be implemented later
	public boolean readXML() {
		return false;
	}

	/**
	 * Exports the project to a png containing all images and a xpm containing
	 * all relevant project info.
	 * @throws URISyntaxException 
	 */
	public static void export() throws URISyntaxException {
		// Exporting image
		BufferedImage bi = new BufferedImage(Gui.getPanel().getSize().width,
				Gui.getPanel().getSize().height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		Gui.getPanel().paint(g); // this == JComponent
		g.dispose();
		try {
			CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			File png = new File(jarDir + File.separator + Main.getProject().getName() + ".png");
			ImageIO.write(bi, "png", png);
		} catch (Exception ex) {
		}

		// Exporting XML

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// spritemap
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("spritemap");
			doc.appendChild(rootElement);

			// image info
			Element pngInfo = doc.createElement("pnginfo");
			Attr fileName = doc.createAttribute("fileName");
			fileName.setValue(Main.getProject().getName() + ".png");
			pngInfo.setAttributeNode(fileName);
			Attr pngWidth = doc.createAttribute("width");
			pngWidth.setValue(Integer.toString(Gui.panelWidth));
			pngInfo.setAttributeNode(pngWidth);
			Attr pngHeight = doc.createAttribute("height");
			pngHeight.setValue(Integer.toString(Gui.panelHeight));
			pngInfo.setAttributeNode(pngHeight);
			rootElement.appendChild(pngInfo);

			// sprites
			for (int i = 0; i < Main.getProject().getSprites().size(); i++) {
				Element sprite = doc.createElement("sprite");
				rootElement.appendChild(sprite);
				// sprite attributes
				{
					// name
					Attr name = doc.createAttribute("name");
					name.setValue(Main.getProject().getSprites().get(i)
							.getName());
					sprite.setAttributeNode(name);

					// canLoop
					Attr canLoop = doc.createAttribute("canLoop");
					if (Main.getProject().getSprites().get(i).getCanLoop()) {

						canLoop.setValue("true");
					} else {
						canLoop.setValue("false");
					}
					sprite.setAttributeNode(canLoop);

					// fps
					Attr fps = doc.createAttribute("fps");
					fps.setValue(Integer.toString(Main.getProject().getSprites()
							.get(i).getFps()));
					sprite.setAttributeNode(fps);
				}
				// sprite elements
				for (int j = 0; j < Main.getProject().getSprites().get(i)
						.getImages().size(); j++) {
					// image
					Element image = doc.createElement("image");
					sprite.appendChild(image);
					// image properties
					{
						// x
						Attr x = doc.createAttribute("x");
						x.setValue(Integer.toString(Main.getProject()
								.getSprites().get(i).getImages().get(j).getX()));
						image.setAttributeNode(x);
						// y
						Attr y = doc.createAttribute("y");
						y.setValue(Integer.toString(Main.getProject()
								.getSprites().get(i).getImages().get(j).getY()));
						image.setAttributeNode(y);
						// width
						Attr width = doc.createAttribute("width");
						width.setValue(Integer.toString(Main.getProject()
								.getSprites().get(i).getImages().get(j)
								.getWidth()));
						image.setAttributeNode(width);
						// height
						Attr height = doc.createAttribute("height");
						height.setValue(Integer.toString(Main.getProject()
								.getSprites().get(i).getImages().get(j)
								.getHeight()));
						image.setAttributeNode(height);
					}
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
			File jarFile = new File(codeSource.getLocation().toURI().getPath());
			String jarDir = jarFile.getParentFile().getPath();
			StreamResult result = new StreamResult(new File(jarDir + File.separator + Main.getProject()
					.getName() + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
