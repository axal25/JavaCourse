package utils.exceptions;

class CollectorOperation extends Throwable {

    CollectorOperation(Throwable cause) {
        super("Collector Operation.", cause);
    }
}
