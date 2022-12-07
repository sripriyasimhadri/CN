import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MessageManager{


    public static byte[] makeMessage(int type, byte[] payload, int pieceIndex){
        //could return int, probably the actual message thats created...
        //assuming "actual message" as defined by the project
        return Message.make(type, payload, pieceIndex);
    }
    
    // return type Message
    public static Message receiveMessage(byte[] messageArray){
        //assumes "actual message", not handshake
        //return type isnt finalized
        Message message = new Message();
        message.read(messageArray);
        //int returned is TYPE, then byte array as ints.
        return message;
    }
    public static byte[] createHandShake(int peerID)
    {
        byte[] handShakePacket = new byte[32];

        byte[] handShakeHeader = "P2PFILESHARINGPROJ".getBytes();
        byte[] zeroPadding = "0000000000".getBytes();// for filling th length in btw the header and peerID
        byte[] peerIDInBytes = ByteBuffer.allocate(4).put(String.valueOf(peerID).getBytes()).array();

        int idx=0;

        for(int i =0; i<handShakeHeader.length;i+=1)
        {
            handShakePacket[idx] = handShakeHeader[i];
            idx+=1;
        }

        for (int i =0; i<zeroPadding.length;i+=1)
        {
            handShakePacket[idx] = zeroPadding[i];
            idx+=1;
        }

        for(int i =0; i< peerIDInBytes.length ; i+=1)
        {
            handShakePacket[idx] = peerIDInBytes[i];
            idx+=1;
        }

        System.out.println("Hand Shake Packet --- "+new String(handShakePacket, StandardCharsets.UTF_8));
        return handShakePacket;

    }

}