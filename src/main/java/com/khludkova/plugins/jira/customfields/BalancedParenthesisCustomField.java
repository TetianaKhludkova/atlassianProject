package com.khludkova.plugins.jira.customfields;

import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.khludkova.plugins.jira.parentheses.ParenthesisChecker;

@Scanned
public class BalancedParenthesisCustomField extends AbstractSingleFieldType<String> {

    @ComponentImport CustomFieldValuePersister customFieldValuePersister;
    @ComponentImport GenericConfigManager genericConfigManager;

    public BalancedParenthesisCustomField(CustomFieldValuePersister customFieldValuePersister, GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
        this.customFieldValuePersister = customFieldValuePersister;
        this.genericConfigManager = genericConfigManager;
    }

    protected PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_UNLIMITED_TEXT;
    }

    protected Object getDbValueFromObject(String customFieldObject) {
        return getStringFromSingularObject(customFieldObject);
    }

    protected String getObjectFromDbValue(Object dbValue) throws FieldValidationException {
        return getSingularObjectFromString((String) dbValue);
    }

    public String getStringFromSingularObject(String singularObject) {
        if (singularObject==null)return null;
        else return singularObject;
    }

    public String getSingularObjectFromString(String string) throws FieldValidationException {
        if (string == null) return null;
        ParenthesisChecker parenthesisChecker = new ParenthesisChecker();
        ParenthesisChecker.ParenthesesEnum checkedParentheses = parenthesisChecker.checkIfCountOfParenthesesIsBalanced(string);
        switch (checkedParentheses) {
            case MORE_OPENED_PARENTHESES: throw new FieldValidationException("Not enough closed parentheses");
            case MORE_CLOSED_PARENTHESES: {
                    if (parenthesisChecker.areParenthesesBalanced(string)) return string;
                    else throw new FieldValidationException("You made mistake in order of parentheses");
                }
            case EQUALS_PARENTHESES:  throw new FieldValidationException("Too much closed parentheses");
            }
        return string;
    }

}