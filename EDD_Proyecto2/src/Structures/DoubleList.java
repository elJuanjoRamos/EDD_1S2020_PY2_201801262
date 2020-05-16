package Structures;

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
    
}
