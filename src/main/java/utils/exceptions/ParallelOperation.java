package utils.exceptions;

class ParallelOperation extends Throwable {

    ParallelOperation(Throwable cause) {
        super("Parallel Operation.", cause);
    }
}
