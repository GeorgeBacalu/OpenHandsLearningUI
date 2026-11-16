package com.project.ohl.presentation;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.project.ohl.R;

/** Loads static page artwork from S3 with the same layout attributes as ImageView. */
public final class S3ImageView extends AppCompatImageView {
    private final String mediaName;

    public S3ImageView(Context context) {
        this(context, null);
    }

    public S3ImageView(Context context, AttributeSet attributes) {
        super(context, attributes);
        var values = context.obtainStyledAttributes(attributes, R.styleable.S3ImageView);
        try {
            mediaName = values.getString(R.styleable.S3ImageView_mediaName);
        } finally {
            values.recycle();
        }
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode() && mediaName != null) {
            MediaResources.loadImage(getContext(), MediaResources.imageUrl(mediaName), this);
        }
    }
}
