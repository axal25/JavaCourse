package input.read.formatter;

import org.junit.jupiter.api.*;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for input.read.formatter.BlockTest")
public class BlockTest {

    @BeforeAll
    public void beforeAll() {
    }

    @AfterAll
    public void afterAll() {
    }

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    @Test
    @Order(1)
    public void constructor() {
        Block block = new Block();
        assertEquals(block.getLines(), 0);
        assertEquals(block.getContents().toString(), StringUtils.EMPTY);
    }

    private static final List<String> newLinePermutations =
            List.of(StringUtils.EMPTY, StringUtils.LF, StringUtils.CR, StringUtils.NL);

    private List<String> getInputContentsWithNewLinePermutations(final List<String> contents, final String toAdd) {
        return newLinePermutations.stream()
                .flatMap(newLine -> contents.stream()
                        .map(content -> String.format("%s%s%s", content, toAdd, newLine)))
                .collect(Collectors.toList());
    }

    @Test
    @Order(2)
    public void append() {
        constructor();

        List<String> inputContents = new ArrayList<>();

        int prevSize = 0;
        List<String> inputContentsWithNewLinePermutations = List.of(String.valueOf(1));
        for (int i = 0; i < 5; i++) {
            inputContents.addAll(inputContentsWithNewLinePermutations);
            prevSize = inputContents.size() - 1;
            inputContentsWithNewLinePermutations =
                    getInputContentsWithNewLinePermutations(
                            inputContents.subList(prevSize, inputContents.size()),
                            i == 0 ? StringUtils.EMPTY : String.valueOf(i + 1)
                    );
        }
        List<Block> blocks = inputContents.stream()
                .map(inputContent -> new Block().append(inputContent))
                .collect(Collectors.toList());
        assertEquals(4 / newLinePermutations.size() + 1, blocks.get(4).getLines());
        IntStream.range(0, blocks.size()).forEach(i -> assertEquals(i / newLinePermutations.size() + 1, blocks.get(i).getLines()));
        int[] expectedLines = new int[]{1, 1, 2, 2, 3, 3};
        String[] expectedContents = new String[]{

        };
//        assertEquals(block.getLines(), 0);
//        assertEquals(block.getContents().toString(), StringUtils.EMPTY);
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Tests for V1")
    public class V1Test {

        @BeforeAll
        public void beforeAll() {
        }

        @AfterAll
        public void afterAll() {
        }

        @BeforeEach
        void beforeEach() {
        }

        @AfterEach
        void afterEach() {
        }

        @Test
        @Order(1)
        public void getMember() {

        }
    }
}
