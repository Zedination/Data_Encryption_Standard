package com.example.rescontroller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.example.encrypt.Des;
import com.example.encrypt.GenerateKeys;
import com.example.encrypt.RSAthucong;
@CrossOrigin(origins = "*")
@RestController
public class RestForDes {
	public static PrivateKey getPrivateKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(GenerateKeys.PRIVATE_KEY_FILE).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static PublicKey getPublicKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(GenerateKeys.PUBLIC_KEY_FILE).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	@PostMapping("/encryption-des")
	public ResponseEntity<String> encryption_DES(@RequestParam(name = "hexagon") String hexagon, @RequestParam(name = "key") String key){
		System.out.println(hexagon + "\n"+key);
		Des cipher = new Des();
		System.out.println("Encryption:\n"); 
		hexagon = cipher.encrypt(hexagon, key); 
		System.out.println( 
			"\nCipher Text: " + hexagon.toUpperCase() + "\n"); 
		return new ResponseEntity<String>(hexagon.toUpperCase(),HttpStatus.OK);
	}
	@PostMapping("/decryption-des")
	public ResponseEntity<String> decryption_DES(@RequestParam(name = "encrypted") String encrypted, @RequestParam(name = "key") String key){
		System.out.println(encrypted + "\n"+key);
		Des cipher = new Des();
		System.out.println("Encryption:\n"); 
		encrypted = cipher.decrypt(encrypted, key); 
		System.out.println( 
			"\nCipher Text: " + encrypted.toUpperCase() + "\n"); 
		return new ResponseEntity<String>(encrypted.toUpperCase(),HttpStatus.OK);
	}
	@PostMapping("/encryption-aes")
	public ResponseEntity<String> encryption_aes(@RequestParam(name = "plaintext") String plaintext, @RequestParam(name = "key") String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] byteEncrypted = cipher.doFinal(plaintext.getBytes());
		String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
		return new ResponseEntity<String>(encrypted,HttpStatus.OK);
	}
	@PostMapping("/decryption-aes")
	public ResponseEntity<String> decryption_aes(@RequestParam(name = "encrypted") String encrypted, @RequestParam(name = "key") String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] byteEncrypted = Base64.getDecoder().decode(encrypted);
		byteEncrypted = cipher.doFinal(byteEncrypted);
		String decrypted =  new String(byteEncrypted);
		return new ResponseEntity<String>(decrypted,HttpStatus.OK);
	}
	@PostMapping("/encryption-rsa")
	public ResponseEntity<String> encryption_RSA(@RequestParam(name = "plaintext") String plaintext) throws Exception{
//		PrivateKey privateKey = getPrivateKey();
		PublicKey publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		String original = plaintext;
		byte[] byteEncrypted = cipher.doFinal(original.getBytes());
		String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
		return new ResponseEntity<String>(encrypted,HttpStatus.OK);
	}
	@PostMapping("/decryption-rsa")
	public ResponseEntity<String> decryption_RSA(@RequestParam(name = "encrypted") String encrypted) throws Exception{
		PrivateKey privateKey = getPrivateKey();
//		PublicKey publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] byteEncrypted = Base64.getDecoder().decode(encrypted);
		byteEncrypted = cipher.doFinal(byteEncrypted);
		String decrypted = new String(byteEncrypted);
		return new ResponseEntity<String>(decrypted,HttpStatus.OK);
	}
	@PutMapping("/create-key-rsa")
	public ResponseEntity<String> create_key_RSA() throws Exception{
		try {
			new GenerateKeys(1024).generateKeysToFile();
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/rsa-thucong-mahoa")
	public ResponseEntity<String> encryption_rsa_thucong(@RequestParam("plaintext") String plaintext, @RequestParam("p") String p,
			@RequestParam("q") String q){
		String responseText = RSAthucong.RsaEncryption(plaintext, p, q);
		return new ResponseEntity<String>(responseText,HttpStatus.OK);
	}
	@PostMapping("/rsa-thucong-giaima")
	public ResponseEntity<String> decryption_rsa_thucong(@RequestParam("encrypted") String Encrypted, @RequestParam("p") String p,
			@RequestParam("q") String q){
		String responseText = RSAthucong.RsaDecryption(Encrypted, p, q);
		return new ResponseEntity<String>(responseText,HttpStatus.OK);
	}
}
