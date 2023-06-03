package org.example.blockchain;

public class Main {

    /*
     * Note: Those "transactions" are just supposed to simulate the behavior of what happens in cryptocurrencies such as bitcoin
     * when you send someone some money ( The '50', '100', '40' etc. you see ).
     * It is important to realize the fact that in real world this process is a bit more complex than that and it's just supposed
     * to give you an example of how it could be done. 
     */
    
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        String transaction1 = "John -> James, 50\n";
        String transaction2 = "Robert -> Emma, 100\n";
        String transaction3 = "Michael -> Joseph, 40\n";
        String transaction4 = "Emma -> John, 60\n";
        String transaction5 = "James -> Robert, 200\n";

        blockchain.addBlock(transaction1 + transaction2);
        blockchain.addBlock(transaction3 + transaction4 + transaction5);
        blockchain.addBlock(transaction2 + transaction5 + transaction1 + transaction3);
        System.out.println(blockchain.toString());
        System.out.println("Is the blockchain valid? " + blockchain.isValid());
    }
}