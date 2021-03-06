package com.khludkova.plugins.jira.customfields;

import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.khludkova.plugins.jira.parentheses.ParenthesisChecker;

import javax.inject.Inject;

@Scanned
public class BalancedParenthesisCustomField extends AbstractSingleFieldType<String>  {
    private final ParenthesisChecker parenthesisChecker = new ParenthesisChecker();

    @ComponentImport CustomFieldValuePersister customFieldValuePersister;
    @ComponentImport GenericConfigManager genericConfigManager;

    @Inject
    public BalancedParenthesisCustomField(CustomFieldValuePersister customFieldValuePersister,
                                          GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
        this.customFieldValuePersister = customFieldValuePersister;
        this.genericConfigManager = genericConfigManager;
    }

    @Override
    public PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_UNLIMITED_TEXT;
    }

    @Override
    public Object getDbValueFromObject(String customFieldObject) {
        return getStringFromSingularObject(customFieldObject);
    }

    @Override
    public String getObjectFromDbValue(Object dbValue) throws FieldValidationException {
        return getSingularObjectFromString((String) dbValue);
    }

    @Override
    public String getStringFromSingularObject(String singularObject) {
        if (singularObject==null)return null;
        else return singularObject;
    }

    @Override
    public String getSingularObjectFromString(String string) throws FieldValidationException {
        if (string == null) return null;
        ParenthesisChecker.ParenthesesValidationEnum checkedParentheses =
                parenthesisChecker.validateParenthesesNumber(string);
        switch (checkedParentheses) {
            case EXTRA_OPENED_PARENTHESES: throw new FieldValidationException("Extra opened parentheses");
            case EQUALS_PARENTHESES: {
                    if (parenthesisChecker.areParenthesesBalanced(string)) return string;
                    else throw new FieldValidationException("Parentheses order not valid");
                }
            case EXTRA_CLOSED_PARENTHESES:  throw new FieldValidationException("Extra closed parentheses");
            }
        return string;
    }

}