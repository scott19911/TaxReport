package com.example.taxreports;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

public class MyTag extends TagSupport implements DynamicAttributes{
    private static final long serialVersionUID = 1L;

    private Map<String, Object> map = new HashMap<String, Object>();
    private int myValue;

    public void setMyValue(int value) {
        this.myValue = value;}

    @Override
    public int doStartTag() throws JspException {
        try {

            Integer value = Integer.parseInt(map.get("value").toString());

            Locale locale = new Locale((String) map.get("local"));
            ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);
            switch (value) {
                case 1:
                    pageContext.getOut().print(exampleBundle.getString("filed"));
                    break;
                case 2:
                    pageContext.getOut().print(exampleBundle.getString("accepted"));
                    break;
                case 3:
                    pageContext.getOut().print(exampleBundle.getString("reject"));
                    break;
                case 4:
                    pageContext.getOut().print(exampleBundle.getString("edit"));
                    break;
                case 5:
                    pageContext.getOut().print(exampleBundle.getString("update"));
                    break;
                case 6:
                    pageContext.getOut().print(exampleBundle.getString("processing"));
                    break;
            }

        } catch(IOException ioException) {
            throw new JspException("Error: " + ioException.getMessage());
        }
        return SKIP_BODY;
    }

    public void setDynamicAttribute(String uri, String name, Object value)
            throws JspException {
        map.put(name, value);
    }
}