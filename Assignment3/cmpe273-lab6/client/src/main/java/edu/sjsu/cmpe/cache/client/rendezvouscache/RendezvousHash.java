package edu.sjsu.cmpe.cache.client.rendezvouscache;

/**
 * Created by Madhura on 5/05/15.
 */

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RendezvousHash<T> {

    private final HashFunction hash;
    private final HashMap<Integer, T> nodeList =
            new HashMap<Integer, T>();

    public RendezvousHash(Collection<T> nodes) {

        this.hash = Hashing.md5();

        for (T node : nodes) {
            add(node);
        }

        System.out.println("\n******New List ---> " + nodeList);
    }

    public void add(T node) {

        int hash = this.hash.newHasher().putString(node.toString(), Charset.defaultCharset()).hash().asInt();
        System.out.println("\n********hash while adding ----> " + hash);
        nodeList.put(hash, node);

    }

    public void remove(T node) {

        nodeList.remove(hash.newHasher().putString(node.toString(), Charset.defaultCharset()).hash().asInt());

    }

    public T getCache(Object key) {

        if (nodeList.isEmpty()) {
            return null;
        }

        Integer maxHash = Integer.MIN_VALUE;
        T maxNode = null;

        for (Map.Entry<Integer, T> node : nodeList.entrySet()) {
            int temp = hash.newHasher()
                    .putString(key.toString(), Charset.defaultCharset())
                    .putString(node.getValue().toString(), Charset.defaultCharset()).hash().asInt();

            if (temp > maxHash) {
                maxHash = temp;
                maxNode = node.getValue();

            }
        }

        return maxNode;

    }

}
