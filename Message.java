import java.util.Arrays;

public class Message {

    public int typeID;
    public int pieceIndex;
    public byte[] payload;

    public static final int CHOKE= 0;
    public static final int UNCHOKE= 1;
    public static final int INTERESTED= 2;
    public static final int NOT_INTERESTED= 3;
    public static final int HAVE= 4;
    public static final int BITFIELD = 5;
    public static final int REQUEST= 6;
    public static final int PIECE= 7;



    public static byte[] make(int type, byte[] payload, int pieceIndex){
        byte[] mess;

        switch(type){
            case CHOKE:
            case UNCHOKE:
            case INTERESTED:
            case NOT_INTERESTED:
                mess = new byte[5];
                Utils.addIntToByteArray(mess, 1, 0);
                mess[4] = (byte) type;
                return mess; 
            case HAVE:
            case REQUEST:
                mess = new byte[9];
                Utils.addIntToByteArray(mess, 5, 0); 
                mess[4] = (byte) type; 
                Utils.addIntToByteArray(mess, pieceIndex, 5); 
    
                return mess;
            case BITFIELD:
                //bitfield where bitfield is... highest bit to lowest
                //this kind of depends on what data struct we use
                mess = new byte[(5 + payload.length)];
                Utils.addIntToByteArray(mess, 1 + payload.length, 0);
                mess[4] = (byte) type;
                for(int i = 0; i< payload.length; i++){
                    mess[5+i] = payload[i];
                }
                return mess;
           
            case PIECE:
                //will be the same as case 6 EXCEPT
                //will have an addition to however large of a piece
                //we allow for them to pass back and forth
                mess = new byte[(9 + payload.length)];
                // add message length
                Utils.addIntToByteArray(mess, 1 + payload.length, 0);
                // add message type
                mess[4] = (byte) type;
                // add piece index
                Utils.addIntToByteArray(mess, pieceIndex, 5); 
                // add payload
                for(int i = 0; i< payload.length; i++){
                    mess[9+i] = payload[i];
                }
                return mess;
            default: 
                return (new byte[0]);
        }
    }

    public void read(byte[] message){ //change off void later
        int len =  message.length;
        if(message.length  == 0) {
            this.typeID = -1;
            return;
        }
        int type = message[0];
        if (type>=0&&type<=7){
            this.typeID = type; 
            //have to check len to type. what if it doesn't match? going to attempt error maybe?
            if (len == 5&&(type==HAVE||type==REQUEST)){  
                pieceIndex = Utils.byteArrayToInt(Arrays.copyOfRange(message, 1, 5));
            } else if (type==PIECE){
                pieceIndex = Utils.byteArrayToInt(Arrays.copyOfRange(message, 1, 5));
                this.payload = new byte[len - 5];
                for(int i = 5; i< len ;i++){
                    this.payload[i - 5] =  message[i];
                }
            } else if (type==BITFIELD){
                this.payload = new byte[len - 1];
                for(int i=1; i<len; i++){
                    this.payload[i - 1] = message[i]; 
                }
            }
        } 
    }

}