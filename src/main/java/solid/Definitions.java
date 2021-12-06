package solid;

import utils.StringUtils;
import utils.exceptions.UnsupportedCollectorParallelLeftFoldOperation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Definitions {

    private static final String DEFINITION_PREFIX = new StringBuilder()
            .append(StringUtils.NL)
            .append(StringUtils.TAB)
            .toString();

    private final String groupName;
    private final Map<String, Descriptions> definitionsToDescriptions;

    Definitions(String groupName, Map<String, Descriptions> definitionsToDescriptions) {
        this.groupName = groupName;
        this.definitionsToDescriptions = Collections.unmodifiableMap(definitionsToDescriptions != null ? definitionsToDescriptions : new HashMap<>());
    }

    String toPrintableString() {
        return definitionsToDescriptions.entrySet().stream()
                .collect(Collectors.collectingAndThen(
                        Collector.of(
                                StringBuilder::new,
                                (defToDescsSb, defToDescsEntry) -> defToDescsSb
                                        .append(StringUtils.isBlank(defToDescsEntry.getKey()) ? StringUtils.EMPTY : DEFINITION_PREFIX)
                                        .append(StringUtils.isBlank(defToDescsEntry.getKey()) ? StringUtils.EMPTY : defToDescsEntry.getKey())
                                        .append(defToDescsEntry.getValue().toPrintableString()),
                                UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                                StringBuilder::toString
                        ),
                        definitionString -> new StringBuilder()
                                .append(StringUtils.isBlank(groupName) ? StringUtils.EMPTY : groupName)
                                .append(StringUtils.isBlank(definitionString) ? StringUtils.EMPTY : definitionString)
                                .toString()
                ));
    }

    static class Descriptions {

        private static final String SHORT_DESC_PREFIX = new StringBuilder()
                .append(DEFINITION_PREFIX)
                .append(StringUtils.TAB)
                .toString();
        private static final String LONG_DESC_PREFIX = new StringBuilder()
                .append(SHORT_DESC_PREFIX)
                .append(StringUtils.TAB)
                .toString();

        private final String shortDesc;
        private final List<String> longDescs;

        Descriptions(String shortDesc, List<String> longDescs) {
            this.shortDesc = shortDesc;
            this.longDescs = Collections.unmodifiableList(longDescs);
        }

        String toPrintableString() {
            return longDescs.stream().collect(Collectors.collectingAndThen(
                    Collector.of(
                            StringBuilder::new,
                            (longDescsSb, longDesc) -> longDescsSb
                                    .append(StringUtils.isBlank(longDesc) ? StringUtils.EMPTY : LONG_DESC_PREFIX)
                                    .append(StringUtils.isBlank(longDesc) ? StringUtils.EMPTY : longDesc),
                            UnsupportedCollectorParallelLeftFoldOperation.getLambdaThrowUnsupportedCollectorParallelLeftFoldOperation(),
                            StringBuilder::toString
                    ),
                    longDescsPrintString -> new StringBuilder()
                            .append(StringUtils.isBlank(shortDesc) ? StringUtils.EMPTY : SHORT_DESC_PREFIX)
                            .append(StringUtils.isBlank(shortDesc) ? StringUtils.EMPTY : shortDesc)
                            .append(longDescsPrintString).toString()
            ));
        }
    }
}
