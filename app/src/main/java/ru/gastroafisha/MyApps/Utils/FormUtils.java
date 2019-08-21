package ru.gastroafisha.MyApps.Utils;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import ru.gastroafisha.MyApps.Model.Form.FormItemModel;

/**
 * Created by alex on 17.01.2018.
 */

public class FormUtils {

    public static View generateElement(Context context, FormItemModel formItemModel) {
        if (formItemModel.getType() == null) {
            return null;
        }

        switch (formItemModel.getType()) {

            case String:
                return generateString(context, formItemModel);

            case Phone:
                return generatePhone(context, formItemModel);

            case Email:
                return generateEmail(context, formItemModel);

            case Text:
                return generateText(context, formItemModel);

            default:
                return null;
        }
    }

    public static String getElementValue(View view, FormItemModel formItemModel) {
        if (formItemModel.getType() == null) {
            return null;
        }

        switch (formItemModel.getType()) {

            case String:
            case Phone:
            case Email:
            case Text:
                return getEditValue(view);

            default:
                return null;
        }
    }

    private static View generateString(Context context, FormItemModel formItemModel) {
        EditText text = generateEdit(context, formItemModel);
        text.setInputType(InputType.TYPE_CLASS_TEXT);
        return text;
    }

    private static View generatePhone(Context context, FormItemModel formItemModel) {
        EditText text = generateEdit(context, formItemModel);
        text.setInputType(InputType.TYPE_CLASS_PHONE);
        return text;
    }

    private static View generateEmail(Context context, FormItemModel formItemModel) {
        EditText text = generateEdit(context, formItemModel);
        text.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        return text;
    }

    private static View generateText(Context context, FormItemModel formItemModel) {
        EditText text = generateEdit(context, formItemModel);
        text.setSingleLine(false);
        text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        text.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        text.setMinLines(5);
        return text;
    }

    private static EditText generateEdit(Context context, FormItemModel formItemModel) {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        EditText text = new EditText(context);
        text.setLayoutParams(mParams);
        text.setHint(formItemModel.getTitle());
        text.setText(formItemModel.getDefaultValue());
        text.setTag(formItemModel);

        return text;
    }

    private static String getEditValue(View view) {
        EditText text = (EditText) view;
        return text.getText().toString();
    }
}
