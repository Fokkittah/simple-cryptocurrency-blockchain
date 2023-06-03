package org.example.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {

    // The mining process involves finding a hash that has a certain number of leading zeros. 
    // The number of leading zeros is defined by the difficulty. 
    // The higher the difficulty, the lower the probability of finding a  hash that satisfies the condition, 
    // which in turn increases the time it takes to mine a block. 
    // You may play around and increase it and see how much longer it takes to execute the code.
    // If '4' is too fast for you, try setting the difficulty to '12'. :) 
    private static final int DIFFICULTY = 4;

    // Index of the block in the blockchain. The index of the first block, also known as the Genesis block, is 0.
    private final int index;
    
    // The nonce is a value that is used in the mining process. It is an arbitrary number that can be used just once.
    // It starts at 0 and is incremented in the mining process until we find a hash that satisfies the difficulty condition.    
    private long nonce = 0;

    // Timestamp at which the block was created.
    private long timestamp;

    // The data that is stored in the block. This can represent transactions in a cryptocurrency or any kind of data that you want 
    // to store in the blockchain.
    private final String data;

    // The hash of the previous block in the blockchain. This is crucial for maintaining the integrity of the blockchain.
    // Any change in the data of a block will result in a change of the hash of the block. This will in turn result in a change in 
    // the previousHash value of the next block, and all subsequent blocks. So, any change in the data of a block will break the chain.
    private final String previousHash;

    // The hash of this block. It is calculated by taking the SHA-256 hash of the concatenation of the index, timestamp, nonce, data, 
    // and previousHash.
    private String hash;

    // Well, just a constructor
    public Block(int index, long timestamp, String data, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    // The mine() function is used to calculate a valid hash for the block. 
    // This is done by continuously calculating the hash of the block with different nonce values until a hash is found that 
    // satisfies the difficulty condition. The difficulty condition is that the hash must start with a certain number of zeros.
    // The number of zeros is defined by the difficulty variable. The nonce is started at 0 and is incremented in every loop until 
    // a satisfying hash is found.
    public void mine() {
        System.out.println("---------------");
        System.out.println("Starting mining block number " + index + "...");
        String target = new String(new char[DIFFICULTY]).replace('\0', '0');
        while (!hash.substring(0, DIFFICULTY).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined! Nonce: " + nonce);
        System.out.println("Hash: " + hash);
    }

    // calculateHash() is used to calculate the hash of the block.
    // It uses the SHA-256 algorithm to compute the hash of the concatenation of the block's index, timestamp, nonce, data, and the 
    // hash of the previous block.
    public String calculateHash() {
        String rawData = index + Long.toString(timestamp) + Long.toString(nonce) + data + previousHash;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // bytesToHex() is a utility method that converts an array of bytes to a hexadecimal string. 
    // It is used in calculateHash() to convert the byte array returned by the SHA-256 hash computation to a string so it can be 
    // stored and displayed.
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexSb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexSb.append('0');
            hexSb.append(hex);
        }
        return hexSb.toString();
    }

    // isValid() checks whether the block's hash satisfies the difficulty condition, i.e., whether it starts with a certain number of 
    // zeros, as specified by the difficulty variable. 
    public boolean isValid() {
        String target = new String(new char[DIFFICULTY]).replace('\0', '0');
        return hash.substring(0, DIFFICULTY).equals(target);
    }

    public String getHash() {
        return hash;
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getNonce() {
        return nonce;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String toString() {
        return "Block #" + index + " [previousHash=" + previousHash + ", hash=" + hash + "]";
    }
}