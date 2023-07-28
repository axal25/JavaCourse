package concurrency.multithreading.synchronization.lock.intrinsic.wait.notify.reentrant.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StartOrFinish {
    START("start"), FINISH("finish");
    private String value;
}
