package Structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.Blockchain;
import models.Configuration;
import nodes.DoubleListNode;
import nodes.SimpleListNode;
import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DoubleList {
    private DoubleListNode first = null;
    private DoubleListNode last = null;
    private static String hash;
    private static BigInteger nonce;
    
    public void addLastNode(String ip, Object obj) {
        ArrayList data = new ArrayList();
        Blockchain b = new Blockchain();
        Date date = new Date();
        String dt1 = date.getYear() + "-" + date.getMonth() + "-"+ date.getDay() + "::"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();            
        b.setDateTime(dt1);
        DoubleListNode nuevo = new DoubleListNode(b);
        if (first==null) {
            first = nuevo;
            nuevo.getBlockchain().setPrecedingsHash("0000");
            nuevo.getBlockchain().setId(0);
        } else {
            nuevo.setPrevious(last);
            nuevo.getBlockchain().setId(nuevo.getPrevious().getBlockchain().getId() + 1);
            nuevo.getBlockchain().setPrecedingsHash(last.getBlockchain().getHash());
            last.setNext(nuevo);
        }
        last = nuevo;
        
        getHash((nuevo.getBlockchain().getId() + /*nuevo.getBlockchain().getData().hashCode() */ nuevo.getBlockchain().getPrecedingsHash() + nuevo.getBlockchain().getData())
                .replace(" ", ""));
        nuevo.getBlockchain().setHash(hash);
        nuevo.getBlockchain().setNonce(String.valueOf(nonce));
        
        
        ArrayList e = new ArrayList();
        e.add(obj);
        nuevo.getBlockchain().setData(e);
        
        
        nuevo.getBlockchain().setIp(ip);
        //JSONBlock.getInstance().generateJSON(nuevo);
        //nuevo.setData(JSONBlock.getInstance().getData());
    }
    
    private static String getSHA(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encodeHex(hash));
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null;
    }

    public static void getHash(String data) {
        nonce = new BigInteger("0");
        String aux = getSHA(data);

        while (true) {
            if (aux.startsWith("0000")){
                break;
            }
            nonce = nonce.add(BigInteger.ONE);
            aux = getSHA(data+nonce);
        }
        hash = aux;
    }
    
    
    public boolean Exist(String ip){
        boolean findIt = false;
        DoubleListNode temp = first;
        while(temp != null){
            
            if(temp.getBlockchain().getIp().equals(ip)){
                findIt = true;
                break;
            } else {
                temp = temp.getNext();
            }
        }
        return findIt;
    }
    public DoubleListNode getRoot(){
        return first;
    }
    
    public void findBlock(String ip, Object o){
        DoubleListNode temp = first;
        
        while (temp != null) {            
            if (temp.getBlockchain().getIp().equals(ip)) {
                
                ArrayList ar = temp.getBlockchain().getData();
                ar.add(o);
                temp.getBlockchain().setData(ar);
                break;
            } else {
                temp = temp.getNext();
            }
        }
    }
    public void show() {
        
        //Creating an arary of objects
        JSONArray objectList = new JSONArray();
        DoubleListNode aux = first;
        //ITERATE IN LIST
        while(aux!=null) {
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            //Inserting key-value pairs into the json object
            jsonObject.put("INDEX", aux.getBlockchain().getId());
            jsonObject.put("TIMESTAMP", aux.getBlockchain().getDateTime());
            jsonObject.put("DATA", aux.getBlockchain().getData());
            jsonObject.put("NONCE", aux.getBlockchain().getNonce());
            jsonObject.put("HASH", aux.getBlockchain().getHash());
            jsonObject.put("IP", aux.getBlockchain().getIp());
            objectList.add(jsonObject);
            aux = aux.getNext();
        }
      //Write JSON file
        try (FileWriter file = new FileWriter("BlockChain.json")) {
            file.write(objectList.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
    
    public void Print() throws IOException {
        String texto = "";
        
        //
        //texto = "digraph grafica{\n" + "rankdir=TB;" + "graph[label=\"" + name + "\", labelloc=t, fontsize=20, compound=true]\n" + "node [shape=plaintext, fontcolor=red, fontsize=18];\n\"Pointers:\" -> \"Values:\" -> \"Indices:\" [color=white];" + this.GetBody() + "\n\n}";
        texto = "digraph grafica{\n" + "graph[label=\"Lista de nodos en red\", labelloc=t, fontsize=20, compound=true];" + "\nrankdir = LR;\nnode [shape=record];\nsplines=false; " + GetBody() + "}";

        try {
            File f = new File("DoubleList.dot");
            if(f.exists() && !f.isDirectory()) { 
                f.delete();
            }
            FileWriter myObj = new FileWriter("DoubleList.dot");
            myObj.write(texto);
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Runtime.getRuntime().exec("dot -Tjpg -o DoubleList.png DoubleList.dot");

    }
     
     public String GetBody() {
        String body = "";
        int counter = 0;
        DoubleListNode aux = first;
        while(aux != null){
            body = body + "NodeLogChange" + counter + " [label =\"" + "ID: " + aux.getBlockchain().getId()+ 
                    "\\nFecha: "+aux.getBlockchain().getDateTime()+
                    "\\nNonce: "+aux.getBlockchain().getNonce()+
                    "\\nHash anterior: "+aux.getBlockchain().getPrecedingsHash()+
                    "\\nHash: "+aux.getBlockchain().getHash()+
                    " \"]\n";
            aux = aux.getNext();
            counter++;
        }
        for (int i = 0; i < counter - 1; i++)
	{
		body = body + "NodeLogChange" + i + "->NodeLogChange" + (i+1) + "[dir=both]";
	}         
        return body;
     }
    
}
