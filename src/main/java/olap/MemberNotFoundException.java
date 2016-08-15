package olap;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String node, Throwable cause) {
        super("Member " + node  +" not found.", cause);
    }

}
