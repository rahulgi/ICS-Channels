package mobisocial.musubi.model;

public class MSequenceNumber {
    public static final String TABLE = "sequence_numbers";
    /**
     * Primary ID for an encoded message
     */
    public static final String COL_ID = "_id";
    
    /**
     * The id of the encoded in encoded_messages
     */
    public static final String COL_ENCODED_ID = "encoded_id";

    /**
     * The identity id of the sender
     */
    public static final String COL_RECIPIENT = "recipient_id";

    /**
     * The sequence number this message was encoded as to the specific recipient
     */
    public static final String COL_SEQUENCE_NUMBER = "seq_number";

    public long id_;
    public long encodedId_;
    public long recipientId_;
    public long sequenceNumber_;
}
