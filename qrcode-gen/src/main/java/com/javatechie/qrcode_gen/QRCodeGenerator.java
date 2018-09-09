package com.javatechie.qrcode_gen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Hello world!
 *
 */
public class QRCodeGenerator {

	private static String QRCODE_PATH = "C:\\Users\\basan\\Desktop\\QRCODE-SERVER\\";

	public String writeQRCode(PaytmRequestBody paytmRequestBody) throws Exception {
		String qrcode = QRCODE_PATH + paytmRequestBody.getUserName() + "-QRCODE.png";
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = writer.encode(
				paytmRequestBody.getUserName() + "\n" + paytmRequestBody.getAccountNo() + "\n"
						+ paytmRequestBody.getAccountType() + "\n" + paytmRequestBody.getMobileNo(),
				BarcodeFormat.QR_CODE, 350, 350);
		Path path = FileSystems.getDefault().getPath(qrcode);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		return "QRCODE is generated successfully....";
	}

	public String readQRCode(String qrcodeImage) throws Exception {
		BufferedImage bufferedImage = ImageIO.read(new File(qrcodeImage));
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitmap);
		return result.getText();

	}

	public static void main(String[] args) throws Exception {
		QRCodeGenerator codeGenerator = new QRCodeGenerator();
		/*
		 * System.out.println( codeGenerator.writeQRCode(new
		 * PaytmRequestBody("Javatechie", "1234567890", "Personal", "acc23457612")));
		 */
		System.out.println(codeGenerator.readQRCode(QRCODE_PATH + "Javatechie-QRCODE.png"));
	}
}
