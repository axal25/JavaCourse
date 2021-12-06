package threads;

import utils.StringUtils;

public interface IMessager {

    default public StringBuilder getMessage(String className, String objectName, String methodName, String startOrFinishOrSleeping) {
        return new StringBuilder()
                .append(className)
                .append(StringUtils.isBlank(getName()) ? StringUtils.EMPTY : " - ")
                .append(StringUtils.isBlank(getName()) ? StringUtils.EMPTY : getName())
                .append(StringUtils.isBlank(methodName) ? StringUtils.EMPTY : " - ")
                .append(StringUtils.isBlank(methodName) ? StringUtils.EMPTY : methodName)
                .append(StringUtils.isBlank(startOrFinishOrSleeping) ? StringUtils.EMPTY : " - ")
                .append(StringUtils.isBlank(startOrFinishOrSleeping) ? StringUtils.EMPTY : startOrFinishOrSleeping);
    }

    default public String getName() {
        return null;
    }
}
