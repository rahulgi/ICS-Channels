package mobisocial.musubi.encoding;

public class ObjEncodingException extends Exception {
    public ObjEncodingException(Exception e) {
        super(e);
    }

    public ObjEncodingException(String msg) {
        super(msg);
    }

    public ObjEncodingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ObjEncodingException() {}
}
