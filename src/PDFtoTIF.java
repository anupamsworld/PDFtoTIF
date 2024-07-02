
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;	


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

//	import javax.media.jai.NullOpImage;
//	import javax.media.jai.OpImage;
//	import javax.media.jai.PlanarImage;

import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.TIFFEncodeParam;
public class PDFtoTIF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hi there");

		try {
			String sourceDir = "apps_anupamsworld_net.pdf"; // Pdf files are read from this folder
			String destinationDir = "./saveTif/"; // converted images from pdf document are saved here

			File sourceFile = new File(sourceDir);
			File destinationFile = new File(destinationDir);
			if (!destinationFile.exists()) {
				destinationFile.mkdir();
				System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
			}
			if (sourceFile.exists()) {
				System.out.println("Images copied to Folder: "+ destinationFile.getName());             
				PDDocument document = PDDocument.load(sourceDir);
				List<PDPage> list = document.getDocumentCatalog().getAllPages();
				System.out.println("Total files to be converted -> "+ list.size());

				String fileName = sourceFile.getName().replace(".pdf", "");             
				int pageNumber = 1;
				//BufferedImage image[]=new BufferedImage[list.size()];
				List <BufferedImage>BufImglist = new ArrayList<BufferedImage>(list.size());
				OutputStream fos = new FileOutputStream("./saveTif/new.tif");
				TIFFEncodeParam param = new TIFFEncodeParam();
				param.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);
			      
				for (PDPage page : list) {
					//image[pageNumber-1] = page.convertToImage();
					if(pageNumber>1)
						BufImglist.add(page.convertToImage());
					//System.out.println("Image Created -> "+ outputfile.getName());
					
					pageNumber++;
				}
				param.setExtraImages(BufImglist.iterator());
				ImageEncoder ie = ImageCodec.createImageEncoder("TIFF",fos,param);
			    ie.encode(list.get(0).convertToImage());
			    fos.close();
				document.close();
				System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
			} else {
				System.err.println(sourceFile.getName() +" File not exists");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}



	}

}
