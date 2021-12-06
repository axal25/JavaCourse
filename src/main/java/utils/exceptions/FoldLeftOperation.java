package utils.exceptions;

class FoldLeftOperation extends Throwable {
    
    FoldLeftOperation(Throwable cause) {
        super("Left fold operation.", cause);
    }
}
