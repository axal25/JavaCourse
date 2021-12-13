package input.read.menu;

class ExitOption extends Option {

    ExitOption() {
        super("Exit", new FIExecutableOption() {
            @Override
            public void execWrapper(Menu menu) {
                // do nothing
            }

            @Override
            public void exec() {
                // do nothing
            }
        });
    }
}