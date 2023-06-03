package org.example.blockchain;

import java.util.ArrayList;
import java.util.List;

// This is our Blockchain class. It represents a chain of Blocks we've made before.
public class Blockchain {
    private List<Block> chain;

    // The constructor for the Blockchain class. When a new Blockchain object is created,
    // it is initialized with a single block, known as the Genesis Block.
    public Blockchain() {
        this.chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    // The createGenesisBlock() function is a helper function to create the first block of the chain,
    // called the Genesis Block. This block is added when the blockchain is initialized.
    private Block createGenesisBlock() {
        Block genesisBlock = new Block(chain.size(), System.currentTimeMillis(), "Genesis Block", "0".repeat(64));
        genesisBlock.mine();
        System.out.println("Genesis Block Data: " + genesisBlock.getData());
        return genesisBlock;
    }

    // The addBlock() function is used to add new blocks to the blockchain. It takes the data for the block as a parameter.
    // This function first retrieves the latest block on the blockchain, and then creates a new block with the given data
    // and the hash of the latest block. It then adds this new block to the chain.
    // Note: Each block in the chain contains a hash of the previous block. This is what makes the blockchain "immutable", 
    // as changing any block in the chain would require changing all subsequent blocks as well.
    public void addBlock(String data) {
        System.out.println("---------------------------");
        Block latestBlock = chain.get(chain.size() - 1);
        System.out.println("Adding block no. " + (latestBlock.getIndex() + 1) +"...");
        System.out.println("Block Data:\n" + data);
        Block newBlock = new Block(chain.size(), System.currentTimeMillis(), data, latestBlock.getHash());
        newBlock.mine();
        chain.add(newBlock);
        System.out.println("Block added successfully!");
    }

    // The isValid() function is used to validate the integrity of the blockchain. It checks each block to ensure that the data hasn't been tampered with.
    // This includes validating the hash of each block, validating the signature of each block (to ensure the block was mined correctly), 
    // and ensuring that each block in the chain correctly references the hash of the previous block.
    // If any of these checks fail for any block, the function returns false, indicating that the blockchain is not valid.
    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Validate the hash of the block. If the current block's hash does not equal to the calculated hash of current block, 
            // it means data in this block might have been tampered, and hence blockchain is not valid.
            if(!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Validate the proof of work of the block. If the block's proof of work is not valid, then the blockchain is not valid.
            if(!currentBlock.isValid()) {
                return false;
            }

            // Validate the link to the previous block. Each block in the blockchain refers to the hash of the previous block.
            // If this hash doesn't match the actual hash of the previous block, then the blockchain is not valid.
            if(!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public List<Block> getChain() {
        return chain;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("---------------------------\n");
        builder.append("Blockchain:\n");
        for (Block block : chain) {
            builder.append(block.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}