package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;
import java.util.*;


public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");

        List<String> serversList = new ArrayList();
        serversList.add("http://localhost:3000");
        serversList.add("http://localhost:3001");
        serversList.add("http://localhost:3002");

        int bucket = Hashing.consistentHash(Hashing.md5().hashInt(1), serversList.size());
        String[] values = {" ","a","b","c","d","e","f","g","h","i","j"};

        CacheServiceInterface cache;
        // Add cache values to bucktes by consistant hashing
        for(int i = 1;i <= 10; i++)
        {
            bucket = Hashing.consistentHash(Hashing.md5().hashInt(i), serversList.size());
            cache = new DistributedCacheService(serversList.get(bucket));
            cache.put(i,values[i]);
	    System.out.println("\nPut( " + i + " =>" + values[i] + ")");
	    System.out.println("Bucket: " + i);
        }
        // Get all the cache values from the servers

        for(int i = 1; i <= 10; i++) {
            bucket = Hashing.consistentHash(Hashing.md5().hashInt(i), serversList.size());
            cache = new DistributedCacheService(serversList.get(bucket));
            System.out.println("\nGet( " + i + " =>" + cache.get(i) + ")");            
            System.out.println("Server: " + serversList.get(bucket));

        }
        System.out.println("Exiting Cache Client...");
    }
}



	
