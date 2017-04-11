package ut.com.khludkova.plugins.jira.customfields;

import com.khludkova.plugins.jira.interfaces.ParenthesisCheckerInt;
import com.khludkova.plugins.jira.parentheses.ParenthesisChecker;
import org.junit.Before;
import org.junit.Test;

import static com.khludkova.plugins.jira.parentheses.ParenthesisChecker.ParenthesesEnum.EQUALS_PARENTHESES;
import static com.khludkova.plugins.jira.parentheses.ParenthesisChecker.ParenthesesEnum.MORE_CLOSED_PARENTHESES;
import static com.khludkova.plugins.jira.parentheses.ParenthesisChecker.ParenthesesEnum.MORE_OPENED_PARENTHESES;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Tanya on 11.04.2017.
 */
public class ParenthesisCheckerIntTest {
    ParenthesisCheckerInt underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new ParenthesisChecker();
    }

//Tests for method areParenthesesBalanced
    @Test
    public void testThatParenthesesAreBalancedWhenStringIsEmpty() throws Exception {
        boolean result = underTest.areParenthesesBalanced("");
        assert result: "Test for checking if parentheses are balanced when the string is empty has failed.";
    }

    @Test
    public void testThatParenthesesAreBalancedWhenBalancedParentheses() throws Exception {
        boolean result = underTest.areParenthesesBalanced("(Hello)((This) is the test string)");
        assert result: "Test for checking if parentheses are balanced when the string contains balanced parentheses has failed.";
    }

    @Test
    public void testThatParenthesesAreNotBalancedWhenNotBalancedParentheses() throws Exception {
        assertEquals("Test for checking if parentheses aren't balanced when the string contains not balanced parentheses has failed.",
                false, underTest.areParenthesesBalanced("((()"));
    }

    @Test
    public void testThatParenthesesAreNotBalancedWhenDisorderedParentheses() throws Exception {
        assertEquals("Test for checking if parentheses aren't balanced when the string contains disordered parentheses has failed.",
                false, underTest.areParenthesesBalanced(")("));
    }


//Tests for method checkIfCountOfParenthesesIsBalanced
    @Test
    public void testThatCountOfParenthesesIsBalancedWhenEmptyString() throws Exception {
        assertEquals("Test for checking if count of parentheses is equals when the string is empty has failed.",
                EQUALS_PARENTHESES, underTest.checkIfCountOfParenthesesIsBalanced(""));
    }

    @Test
    public void testThatCountOfParenthesesIsBalancedWhenBalancedParentheses() throws Exception {
        assertEquals("Test for checking if count of parentheses is equals when the string has balanced parentheses has failed.",
                EQUALS_PARENTHESES, underTest.checkIfCountOfParenthesesIsBalanced("()"));
    }

    @Test
    public void tstThatCountOfParenthesesHasMoreOpenedParentheses() throws Exception {
        assertEquals("Test for checking if count of parentheses is right when the string has more opened parentheses has failed.",
                MORE_OPENED_PARENTHESES, underTest.checkIfCountOfParenthesesIsBalanced("((()"));
    }

    @Test
    public void testThatCountOfParenthesesHasMoreClosedParentheses() throws Exception {
        assertEquals("Test for checking if count of parentheses is right when the string has more closed parentheses has failed.",
                MORE_CLOSED_PARENTHESES, underTest.checkIfCountOfParenthesesIsBalanced("()))"));
    }


//Tests for method calculateParentheses
    @Test
    public void testIfBothParametersEmpty_CountIsZero() throws Exception {
        assertEquals("Test for checking if return value is zero if both parameters are empty failed.",
               0, underTest.calculateParentheses("", ""));
    }

    @Test
    public void testIfEmptyString_CountIsZero() throws Exception {
        assertEquals("Test for checking if return value is zero if input string is empty failed.",
                0, underTest.calculateParentheses("", "("));
    }

    @Test
    public void testIfWrongParenthesis_CountIsZero() throws Exception {
        assertEquals("Test for checking if return value is zero if input string contains wrong parenthesis failed.",
                0, underTest.calculateParentheses("hello)", "("));
    }

    @Test
    public void testIfOneParenthesis_CountIsOne() throws Exception {
        assertEquals("Test for checking if return value is one if input string contains one parenthesis failed.",
                1, underTest.calculateParentheses("hello)", ")"));
    }

    @Test
    public void testIfTwoParentheses_CountIsTwo() throws Exception {
        assertEquals("Test for checking if return value is 2 if input string contains 2 parentheses failed.",
                2, underTest.calculateParentheses("hello))", ")"));
    }

}