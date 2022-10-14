package p2pOverlay;

import p2pOverlay.util.Connection;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.BitSet;

public class Peer {
    // Peer contains two arraylist for its clockwise and anticlockwise neighbours at each level
    // The index of the arraylist corresponds to the level
    private final ArrayList<Connection> clockwise;
    private final ArrayList<Connection> antiClockwise;
    public BitSet id;
    private static final int NBITS = 32;
    public static ArrayList<Connection>[] routeTable;

    // temp arraylist for testing
    private final ArrayList<Connection> knownConnections;


    public Peer(BitSet id, ArrayList<Connection> clockwise, ArrayList<Connection> antiClockwise) {
        this.id = id;
        this.clockwise = clockwise;
        this.antiClockwise = antiClockwise;
        routeTable = new ArrayList[]{clockwise, antiClockwise};
        this.knownConnections = new ArrayList<>();
    }
    public Peer(BitSet id) {
        this(id, new ArrayList<>(), new ArrayList<>());
    }

    public Peer(){ this(new BitSet(NBITS)); }

    public long getLongId() {
        long[] longArray = id.toLongArray();

        // if id is set to 0, longArray will have no elements
        if(longArray.length > 0) return longArray[0];
        return 0;
    }

    public void setId(int id){this.id = BitSet.valueOf(new long[] {id});}

    // TODO: check if ring level exists
    public void updateTable(int h, Connection clockwiseNeighbour, Connection antiClockwiseNeighbour) {
        clockwise.set(h, clockwiseNeighbour);
        antiClockwise.set(h, antiClockwiseNeighbour);
    }

    public void addConnection(String ip, int port, BitSet peerID){
       knownConnections.add(new Connection(peerID, new InetSocketAddress(ip, port)));
    }

    public Connection getConnection(BitSet peerID){
        for(Connection c : knownConnections){
            if(c.peerID().equals(peerID)) return c;
        }
        return null;
    }
}
