package io;

import java.io.Serializable;

/**
 * Message that contains the checksum of the encrypted
 * data along with the data itself.
 */
public class ChecksumMessage implements Serializable{

    /**Checksum of this message's payload.*/
    private final String checkSum;

    /**Encoded and encrypted bytes for this checksum's payload.*/
    private final byte[] encodedBytes;

    /**
     * Create a new message with the user's message.
     *
     * @param bytes represent the encoded data.
     */
    public ChecksumMessage(byte[] bytes) {
        this.encodedBytes = bytes;
        this.checkSum = Parser.computeCheckSum(bytes);
    }

    /**
     * Retrieve checksum of this message.
     * @return this message's checksum
     */
    public String getCheckSum() {
        return checkSum;
    }

    /**
     * Retrieve this message's encoded data.
     * @return encoded bytes for this message.
     */
    public byte[] getEncodedBytes(){
        return encodedBytes;
    }
}
