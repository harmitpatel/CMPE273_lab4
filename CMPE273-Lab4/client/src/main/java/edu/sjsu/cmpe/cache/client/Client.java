package edu.sjsu.cmpe.cache.client;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");

        CRDTImplement crdt = new CRDTImplement();
        crdt.add("http://localhost:3000");
        crdt.add("http://localhost:3001");
        crdt.add("http://localhost:3002");


        //Test for Read Repair
        crdt.writeToAll(1, "a");
        Thread.sleep(30 * 1000);
        crdt.writeToAll(1, "b");
        Thread.sleep(30 * 1000);
        System.out.println("all servers: " + crdt.readFromAll(1));


        //Test for Write Rollback
        Thread.sleep(30 * 1000);
        crdt.writeToAll(2, "c");


        System.out.println("Exiting Cache Client.");
    }

}
