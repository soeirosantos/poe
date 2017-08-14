package br.com.soeirosantos.poe.cipher.service;

public interface CipherService {

    String encrypt(String message, byte[] key);

    String decrypt(String encryptedMessage, byte[] key);

}
