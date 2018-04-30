package kvpaxos;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is a subset of entire test cases
 * For your reference only.
 */
public class KVPaxosTest {


    public void check(Client ck, String key, Integer value){
        Integer v = ck.Get(key);
        assertTrue("Get(" + key + ")->" + v + ", expected " + value, v.equals(value));
    }

    @Test
    public void TestBasic(){
        final int npaxos = 3;
        String host = "127.0.0.1";
        String[] peers = new String[npaxos];
        int[] ports = new int[npaxos];

        Server[] kva = new Server[npaxos];
        for(int i = 0 ; i < npaxos; i++){
            ports[i] = 1100+i;
            peers[i] = host;
        }
        for(int i = 0; i < npaxos; i++){
            kva[i] = new Server(peers, ports, i);
        }

        Client ck = new Client(peers, ports);
        
        System.out.println("Test: Basic put/get ...");
        ck.Put("app", 6);
        check(ck, "app", 6);
        ck.Put("a", 70);
        check(ck, "a", 70);

        System.out.println("... Passed");
        
        Client c[] = new Client[10];
        
        for (int i = 0; i < 10; i++) {
        	c[i] = new Client(peers, ports);
        }
        String keys[] = {"a", "b", "c", "d", "e"};
        System.out.println("Test: dups ...");
        for (int i = 0; i<5; i++) {
        	for(int j = 0; j < 10; j++) {
        		c[j].Put(keys[i], j);
        	}
        }
        check(c[0], "e", 9);
        System.out.println("... Passed?");

    }
    

}
