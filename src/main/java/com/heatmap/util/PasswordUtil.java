package com.heatmap.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heatmap.constants.AppConstant;

@Component
public class PasswordUtil {

	private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
	
	@Autowired
	private ExceptionUtil exceptionUtil;
	
	private static final byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public String encryptPassword(String password) {
		logger.info("Executing method encryptPassword of class PasswordUtil");
		try{
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(AppConstant.ALGORITHM);
			KeySpec spec = new PBEKeySpec(AppConstant.SECRETKEY.toCharArray(), AppConstant.SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), AppConstant.SECRETKEYALGO);
			Cipher cipher = Cipher.getInstance(AppConstant.TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(AppConstant.TRANSFORMATIONFORMAT)));
		}
		catch(InvalidKeyException| InvalidAlgorithmParameterException| NoSuchAlgorithmException| NoSuchPaddingException| InvalidKeySpecException| IllegalBlockSizeException| BadPaddingException| UnsupportedEncodingException e ){
			logger.error(exceptionUtil.getExceptionMessage(e));
		}
		
		return null;
	}

	public String decryptPassword(String encryptedPassword) {
		logger.info("Executing method decryptPassword of class PasswordUtil");
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(AppConstant.ALGORITHM);
			KeySpec spec = new PBEKeySpec(AppConstant.SECRETKEY.toCharArray(), AppConstant.SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), AppConstant.SECRETKEYALGO);

			Cipher cipher = Cipher.getInstance(AppConstant.TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedPassword)));
		} catch(InvalidKeyException| InvalidAlgorithmParameterException| NoSuchAlgorithmException| NoSuchPaddingException| InvalidKeySpecException| IllegalBlockSizeException| BadPaddingException e ){
			logger.error(exceptionUtil.getExceptionMessage(e));
		}
		return null;
	}
	
	public static void main(String[] args) {
		PasswordUtil p=new PasswordUtil();
		System.out.println(p.decryptPassword("etI2X3l0y/dyXvkeVBU0DDefN8p2b0IbPTlpaz+LV/w="));
		System.out.println(p.decryptPassword(p.encryptPassword("kelvintsp2020@gmail.com")));
		System.out.println(p.decryptPassword("VNxPa2dW8RVg5y4xksJwV63X7OfLcJPf+3C6qLg2YTY="));
	}
}
