package com.khludkova.plugins.jira.parentheses;

import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.khludkova.plugins.jira.interfaces.ParenthesisCheckerInt;

/**
 * Created by Tanya on 10.04.2017.
 */
public class ParenthesisChecker implements ParenthesisCheckerInt {
    static final private String openedParenthesis = "(";
    static final private String closedParenthesis = ")";
    public enum ParenthesesEnum{
        MORE_OPENED_PARENTHESES, MORE_CLOSED_PARENTHESES, EQUALS_PARENTHESES
    }

    @Override
    public boolean areParenthesesBalanced(String inputString) throws FieldValidationException {
        int indexOfOpenedParenthesis = 0, indexOfClosedParenthesis = 0;

        while (indexOfOpenedParenthesis!=-1 && indexOfClosedParenthesis!=-1){
            indexOfOpenedParenthesis = inputString.indexOf(openedParenthesis, indexOfOpenedParenthesis);
            indexOfClosedParenthesis = inputString.indexOf(closedParenthesis, indexOfClosedParenthesis);
            if (indexOfOpenedParenthesis>indexOfClosedParenthesis) return false;
            if (indexOfOpenedParenthesis==-1 && indexOfClosedParenthesis==-1) return true;
            indexOfOpenedParenthesis++;
            indexOfClosedParenthesis++;
        }
        return true;
    }

    @Override
    public ParenthesesEnum checkIfCountOfParenthesesIsBalanced(String inputString){
        int countOfOpenedParentheses = calculateParentheses(inputString, openedParenthesis);
        int countOfClosedParentheses = calculateParentheses(inputString, closedParenthesis);

        if (countOfOpenedParentheses > countOfClosedParentheses) return ParenthesesEnum.MORE_OPENED_PARENTHESES;
        else if(countOfOpenedParentheses == countOfClosedParentheses) return ParenthesesEnum.EQUALS_PARENTHESES;
        else return ParenthesesEnum.MORE_CLOSED_PARENTHESES;
    }

    @Override
    public int calculateParentheses(String inputString, String parenthesis){
        if (inputString == null | parenthesis == null)
            throw new IllegalArgumentException("Illegal arg");
        return Math.toIntExact(inputString.chars()
                .mapToObj(i -> (char) i)
                .filter(i -> String.valueOf(i).equals(parenthesis))
                .count());
    }

}
