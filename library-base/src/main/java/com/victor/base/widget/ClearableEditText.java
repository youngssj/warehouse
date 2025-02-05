package com.victor.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.victor.base.R;

public class ClearableEditText extends LinearLayout {

    private ImageView ivStartIcon;
    private View viewLine;
    private EditText editText;
    private ImageView ivClear;

    private TextChangeListener textChangeListener;

    public ClearableEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ClearableEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.clearable_edittext, this);
        ivStartIcon = view.findViewById(R.id.ivStartIcon);
        viewLine = view.findViewById(R.id.viewLine);
        editText = view.findViewById(R.id.editText);
        ivClear = view.findViewById(R.id.ivClear);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText);

        String text = ta.getString(R.styleable.ClearableEditText_text);
        if (!TextUtils.isEmpty(text)) {
            editText.setText(text);
            ivClear.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.INVISIBLE);
        }

        String hint = ta.getString(R.styleable.ClearableEditText_hint);
        if (!TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }

        int startIcon = ta.getResourceId(R.styleable.ClearableEditText_startIcon, -1);
        if (startIcon != -1) {
            ivStartIcon.setVisibility(VISIBLE);
            viewLine.setVisibility(VISIBLE);
            ivStartIcon.setImageResource(startIcon);
        } else {
            ivStartIcon.setVisibility(GONE);
            viewLine.setVisibility(GONE);
            editText.setPaddingRelative(0, 0, 0, 0);
        }

        int inputType = ta.getInt(R.styleable.ClearableEditText_inputType, EditorInfo.TYPE_NULL);
        if (inputType != EditorInfo.TYPE_NULL) {
            editText.setInputType(inputType);
        }

        if (inputType == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)
                || inputType == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                || inputType == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD)
                || inputType == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD)) {
            initPasswordVisiable();
            setPasswordListener();
        } else {
            setListener();
        }

        int maxLength = ta.getInt(R.styleable.ClearableEditText_maxLength, -1);
        if (maxLength != -1) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        int maxLines = ta.getInt(R.styleable.ClearableEditText_maxLines, -1);
        if (maxLines != -1) {
            editText.setMaxLines(maxLines);
        }

        ta.recycle();
    }

    private void setListener() {
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    ivClear.setVisibility(View.INVISIBLE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
                if (textChangeListener != null) {
                    textChangeListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setPasswordListener() {
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordVisiable();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textChangeListener != null) {
                    textChangeListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initPasswordVisiable() {
        if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            ivClear.setImageResource(R.mipmap.login_close);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            ivClear.setImageResource(R.mipmap.login_open);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    private void setPasswordVisiable() {
        if (editText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            ivClear.setImageResource(R.mipmap.login_open);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivClear.setImageResource(R.mipmap.login_close);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public interface TextChangeListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    public void setTextChangeListener(TextChangeListener listener) {
        this.textChangeListener = listener;
    }
}
