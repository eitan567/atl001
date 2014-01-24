package com.spy.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64

import org.joda.time.Period
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

import com.google.code.geocoder.Geocoder
import com.google.code.geocoder.GeocoderRequestBuilder
import com.google.code.geocoder.model.GeocodeResponse
import com.google.code.geocoder.model.GeocoderRequest
import com.google.code.geocoder.model.GeocoderResult
import com.google.code.geocoder.model.GeocoderStatus
import com.google.code.geocoder.model.LatLng
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

import org.apache.commons.codec.binary.Base64

public class SpyUtils {

	def static messageSource = {}

	public static String formatPhoneNumber(String phoneNumber) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberObj = null;
		try {
			phoneNumberObj = phoneUtil.parse(phoneNumber, "IL");
		} catch (NumberParseException e) {
			//e.printStackTrace();
			return phoneNumber;
		}

		boolean isValid = phoneUtil.isValidNumber(phoneNumberObj);
		if (isValid) {
			return phoneUtil.format(phoneNumberObj, PhoneNumberFormat.NATIONAL);
		} else {
			return phoneNumber;
		}
	} 

	public static String formatPeriod(int periodInSec) {
		//String days = messageSource.getMessage("sms.label",null,"sms.label",Locale.getDefault());
		PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
				.appendDays()
				.appendSuffix(" יום", " ימים")
				.appendSeparator(" ")
				.appendHours()
				.appendSuffix(" שעה", " ש'")
				.appendSeparator(" ")
				.appendMinutes()
				.appendSuffix(" דקה", " ד'")
				.appendSeparator(" ו- ")
				.appendSeconds()
				.appendSuffix(" שנייה", " ש'")
				.toFormatter();

		Period period = new Period(0, 0, periodInSec,0);

		return daysHoursMinutes.print(period.normalizedStandard());
	}

	public static String formatPhoneNumberForSQL(String phoneNumber) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Phonenumber phoneNumberObj = null;
		try {
			phoneNumberObj = phoneUtil.parse(phoneNumber, "IL");
		} catch (NumberParseException e) {
			//e.printStackTrace();
			return phoneNumber;
		}

		boolean isValid = phoneUtil.isValidNumber(phoneNumberObj);
		if (isValid) {
			return phoneNumberObj.nationalNumber;
		} else {
			return phoneNumber;
		}
	}

	public static String getCountryCode(String phoneNumber) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberObj = null;
		try {
			phoneNumberObj = phoneUtil.parse(phoneNumber, "IL");
		} catch (NumberParseException e) {
			//e.printStackTrace();
			return null;
		}

		boolean isValid = phoneUtil.isValidNumber(phoneNumberObj);
		if (isValid) {
			return phoneNumberObj.countryCode;
		} else {
			return null;
		}
	}

	public static List<GeocoderResult> getGeocode(Double lat, Double lng) throws Exception {
		final Geocoder geocoder = new Geocoder();

		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(new LatLng(lat, lng)).setLanguage("he").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

		if(geocoderResponse !=null && geocoderResponse.getStatus()==GeocoderStatus.OK && !geocoderResponse.getResults().isEmpty()){
			return geocoderResponse.getResults();
			//assertTrue(geocoderResult.getTypes().contains(GeocoderResultType.STREET_ADDRESS.value()));
		}else{
			println ("no address found for lat=" + lat + " lng=" + lng);
			return null;
		}
	}
	
	public String maskImage(String image) {
		
		BufferedImage master;
		BufferedImage mask;
		BufferedImage tinted;

		try { 
			master = ImageIO.read(new ByteArrayInputStream(Base64.decodeBase64(image.getBytes())));
			mask = generateMask(master, Color.black, 0.5f);
			tinted = tint(master, mask);
			return Base64.encodeBase64(((DataBufferByte) tinted.getData().getDataBuffer()).getData());
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}


	public String getDecodedString(String string){
		byte[] decodedImage = Base64.decodeBase64(string);
		return new String(decodedImage);
	}

	public String getEncodedString(String string){
		String decodedImage = Base64.encodeBase64String(string.getBytes());
		return decodedImage;
	}
	
	public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
		int imgWidth = imgSource.getWidth();
		int imgHeight = imgSource.getHeight();

		BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
		Graphics2D g2 = imgMask.createGraphics();
		applyQualityRenderingHints(g2);

		g2.drawImage(imgSource, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		g2.setColor(color);

		g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
		g2.dispose();

		return imgMask;
	}
	
	public BufferedImage tint(BufferedImage master, BufferedImage tint) {
		int imgWidth = master.getWidth();
		int imgHeight = master.getHeight();

		BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
		Graphics2D g2 = tinted.createGraphics();
		applyQualityRenderingHints(g2);
		g2.drawImage(master, 0, 0, null);
		g2.drawImage(tint, 0, 0, null);
		g2.dispose();

		return tinted;
	}
	
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}
	
	public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
		BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
		image.coerceData(true);
		return image;
	}

	public static void applyQualityRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}

}
