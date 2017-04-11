package com.khludkova.plugins.jira.interfaces;

import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.khludkova.plugins.jira.parentheses.ParenthesisChecker.ParenthesesEnum;

/**
 * Created by Tanya on 11.04.2017.
 */
public interface ParenthesisCheckerInt {

    boolean areParenthesesBalanced(String string) throws FieldValidationException;

    ParenthesesEnum checkIfCountOfParenthesesIsBalanced(String string);

    int calculateParentheses(String string, String parenthesis);
}