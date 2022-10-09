package collections.theory;

import org.junit.jupiter.api.*;
import utils.StringUtils;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Information Test")
public class InformationTest {

    @Test
    @Order(1)
    public void constructor() {
        Information information1 = new Information(getClass());
        Information information2 = new Information(getClass().getSimpleName());

        assertEquals(getClass().getSimpleName(), information1.getClassName());
        assertEquals(getClass().getSimpleName(), information2.getClassName());

        assertEquals(StringUtils.EMPTY, information1.getContents().toString());
        assertEquals(StringUtils.EMPTY, information2.getContents().toString());

        assertEquals(new LinkedList<>(), information1.getChildren());
        assertEquals(new LinkedList<>(), information2.getChildren());
    }

    @Test
    @Order(2)
    public void append() {
        Information information = new Information(getClass());

        String text = "test";
        information.append(text);

        assertEquals(getClass().getSimpleName(), information.getClassName());
        assertEquals(String.format("%s%s", StringUtils.TAB, text), information.getContents().toString());
        assertEquals(new LinkedList<>(), information.getChildren());
    }

    @Test
    @Order(3)
    public void appendln() {
        Information information = new Information(getClass());

        List<String> texts = List.of("testLine3", "testLine1", "testLine2");
        information.appendln(texts);

        assertEquals(getClass().getSimpleName(), information.getClassName());
        String expectedContents = String.format(
                "%s%s%s",
                String.format("%s%s%s", StringUtils.TAB, texts.get(0), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, texts.get(1), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, texts.get(2), StringUtils.NL)
        );
        assertEquals(expectedContents, information.getContents().toString());
        assertEquals(new LinkedList<>(), information.getChildren());
        assertEquals(
                String.format("%s%s", getClass().getSimpleName(), StringUtils.NL),
                information.getStructure("")
        );
        assertEquals(
                String.format(
                        "[%s]%s%s%s%s%s%s%s%s%s%s%s",
                        getClass().getSimpleName(),
                        StringUtils.NL,
                        StringUtils.TAB,
                        "Structure:",
                        StringUtils.NL,
                        StringUtils.TAB,
                        StringUtils.TAB,
                        getClass().getSimpleName(),
                        StringUtils.NL,
                        StringUtils.NL,
                        expectedContents,
                        StringUtils.NL
                ),
                information.getPrintString()
        );
    }


    @Test
    @Order(3)
    public void addChild() {
        Information parentInformation = new Information("PARENT");
        Information childInformation = new Information("CHILD");

        List<String> parentTexts = List.of("parentTestLine3", "parentTestLine1", "parentTestLine2");
        parentInformation.appendln(parentTexts);

        List<String> childTexts = List.of("childTestLine3", "childTestLine1", "childTestLine2");
        childInformation.appendln(childTexts);

        parentInformation.addChild(childInformation);

        assertEquals("PARENT", parentInformation.getClassName());
        assertEquals("CHILD", childInformation.getClassName());

        String expectedParentContents = String.format(
                "%s%s%s",
                String.format("%s%s%s", StringUtils.TAB, parentTexts.get(0), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, parentTexts.get(1), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, parentTexts.get(2), StringUtils.NL)
        );
        assertEquals(expectedParentContents, parentInformation.getContents().toString());

        String expectedChildContents = String.format(
                "%s%s%s",
                String.format("%s%s%s", StringUtils.TAB, childTexts.get(0), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, childTexts.get(1), StringUtils.NL),
                String.format("%s%s%s", StringUtils.TAB, childTexts.get(2), StringUtils.NL)
        );
        assertEquals(expectedChildContents, childInformation.getContents().toString());

        assertEquals(List.of(childInformation), parentInformation.getChildren());
        assertEquals(new LinkedList<>(), childInformation.getChildren());

        assertEquals(
                String.format(
                        "%s%s%s",
                        "PARENT",
                        StringUtils.NL,
                        String.format(
                                "%s%s%s",
                                StringUtils.TAB,
                                "CHILD",
                                StringUtils.NL
                        )
                ),
                parentInformation.getStructure("")
        );
        assertEquals(
                String.format("%s%s", "CHILD", StringUtils.NL),
                childInformation.getStructure("")
        );

        String expectedChildPrintString = new StringBuilder()
                .append("[")
                .append("CHILD")
                .append("]")
                .append(StringUtils.NL)
                .append(StringUtils.TAB)
                .append("Structure:")
                .append(StringUtils.NL)
                .append(StringUtils.TAB)
                .append(StringUtils.TAB)
                .append("CHILD")
                .append(StringUtils.NL)
                .append(StringUtils.NL)
                .append(expectedChildContents)
                .append(StringUtils.NL)
                .toString();
        assertEquals(expectedChildPrintString, childInformation.getPrintString());

        assertEquals(
                new StringBuilder()
                        .append("[")
                        .append("PARENT")
                        .append("]")
                        .append(StringUtils.NL)
                        .append(StringUtils.TAB)
                        .append("Structure:")
                        .append(StringUtils.NL)
                        .append(StringUtils.TAB)
                        .append(StringUtils.TAB)
                        .append("PARENT")
                        .append(StringUtils.NL)
                        .append(StringUtils.TAB)
                        .append(StringUtils.TAB)
                        .append(StringUtils.TAB)
                        .append("CHILD")
                        .append(StringUtils.NL)
                        .append(StringUtils.NL)
                        .append(expectedParentContents)
                        .append(StringUtils.NL)
                        .append(StringUtils.NL)
                        .append(StringUtils.NL)
                        .append(expectedChildPrintString)
                        .toString(),
                parentInformation.getPrintString()
        );
    }
}
