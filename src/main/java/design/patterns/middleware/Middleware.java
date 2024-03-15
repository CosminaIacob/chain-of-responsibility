package design.patterns.middleware;

// Basic validation interface - BaseHandler
public abstract class Middleware {

    private Middleware next;

    // Build chains if middleware objects
    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    // Subclasses will implement this methid with concrete checks
    public abstract boolean check(String email, String password);

    // Runs check on the next object in chain or ends traversing if we're in last object in chain.
    protected boolean checkNext(String email, String password) {
        return next == null ? true : next.check(email, password);
    }
}
